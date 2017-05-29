package com.rrdinsights.endpoints

import com.rrdinsights.TestSpec
import com.rrdinsights.models.BoxScoreSummaryRawResponse
import com.rrdinsights.parameters._
import com.rrdinsights.utils.Control._
import org.apache.http.impl.client.HttpClientBuilder
import org.json4s.{DefaultFormats, _}
import org.json4s.jackson.JsonMethods._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
final class EndPointTest extends TestSpec {
  private val GameID: String = "0021601219"
  // Home Team Info
  private val HomeTeamAbbreviation: String = "BOS"
  private val HomeTeamCity: String = "Boston"
  private val HomeTeamName: String = "Celtics"

  // Away Team Info
  private val AwayTeamAbbreviation: String = "MIL"
  private val AwayTeamCity: String = "Milwaukee"
  private val AwayTeamName: String = "Bucks"


  test("boxscore endpoint") {

    implicit val formats = DefaultFormats
    val param = GameIdParameter.newParameterValue(GameID)
    val boxScore = BoxScore(param)
    val resp = using(HttpClientBuilder.create().setDefaultHeaders(Headers.headers).build()) { client =>
      val out = BoxScore.parseResponse(boxScore.get(client))
      client.close()
      out
    }
    val parsed = parse(resp, useBigDecimalForDouble = true)
    val bsresp = parsed.extract[BoxScoreSummaryRawResponse]
    val parsedRespose = bsresp.toBoxScoreSummaryResponse

    // resource
    assert(parsedRespose.resource === "boxscoresummary")

    // parameters
    assert(parsedRespose.parameters.size === 1)
    assert(parsedRespose.parameters.head === GameIdParameter.newParameterValue("0021601219"))

    // results
    assert(parsedRespose.boxScoreSummary.gameSummary.isDefined)

    parsedRespose.boxScoreSummary.gameSummary.foreach(v => {
      assert(v.gameDate === "2017-04-12T00:00:00")
      assert(v.gameSequence === 3)
      assert(v.gameId === GameID)
      assert(v.gameStatusId === 3)
      assert(v.gameStatusText === "Final")
      assert(v.gameCode === "20170412/MILBOS")
      assert(v.homeTeamId === 1610612738)
      assert(v.awayTeamId === 1610612749)
      assert(v.season === "2016")
      assert(v.livePeriod === 4)
      assert(v.livePCTime.trim.isEmpty)
      assert(v.broadcaster === null)
      assert(v.livePeriodTimeBroadcast === "Q4       -")
      assert(v.whStatus === 1)
    })

    parsedRespose.boxScoreSummary.gameInfo.foreach(v => {
      assert(v.gameDate === "WEDNESDAY, APRIL 12, 2017")
      assert(v.gameTime === "2:03")
      assert(v.attendance === 18624)
    })

    parsedRespose.boxScoreSummary.officials.foreach(v => {
      v.foreach(o => {
        o.firstName match {
          case "Tom" => assert(o.lastName === "Washington")
          case "Mark" => assert(o.lastName === "Lindsay")
          case "Marat" => assert(o.lastName === "Kogut")
          case n => fail(s"official $n not expected")
        }
      })
    })

    parsedRespose.boxScoreSummary.inactivePlayers.foreach(v => {
      assert(v.size === 4)
      val inactivePlayer = v.find(_.playerId === 203114).getOrElse(fail(s"Player 203114 (Kris Middleton) not found"))
      assert(inactivePlayer.firstName === "Khris")
      assert(inactivePlayer.lastName === "Middleton")
      assert(inactivePlayer.number === "22")
      assert(inactivePlayer.teamAbbreviation === AwayTeamAbbreviation)
      assert(inactivePlayer.teamCity === AwayTeamCity)
      assert(inactivePlayer.teamName === AwayTeamName)
      assert(inactivePlayer.teamId === 1610612749)
    })

    parsedRespose.boxScoreSummary.awayStats.scoreLine.foreach(v => {
      assert(v.gameId === GameID)
      assert(v.gameDateTimeEST === "2017-04-12T00:00:00")
      assert(v.gameSequence === 3)
      assert(v.teamId === TeamIdParameter.MilwaukeeBucks.value.toInt)
      assert(v.teamCityName === AwayTeamCity)
      assert(v.teamAbbreviation === AwayTeamAbbreviation)
      assert(v.teamNickName === AwayTeamName)
      assert(v.teamWinsLosses === "42-40")
      assert(v.quarter1Points === 36)
      assert(v.quarter2Points === 20)
      assert(v.quarter3Points === 22)
      assert(v.quarter4Points === 16)
      assert(v.points === 94)
      assert(v.ot1Points === 0)
      assert(v.ot2Points === 0)
      assert(v.ot3Points === 0)
      assert(v.ot4Points === 0)
      assert(v.ot5Points === 0)
      assert(v.ot6Points === 0)
      assert(v.ot7Points === 0)
      assert(v.ot8Points === 0)
      assert(v.ot9Points === 0)
      assert(v.ot10Points === 0)
    })

    parsedRespose.boxScoreSummary.awayStats.otherStats.foreach(v => {
      assert(v.leagueId === LeagueIdParameter.NBA.value)
      assert(v.teamId === TeamIdParameter.MilwaukeeBucks.value.toInt)
      assert(v.teamAbbreviation === AwayTeamAbbreviation)
      assert(v.teamCity === AwayTeamCity)
      assert(v.pointsInPaint === 48)
      assert(v.fastBreakPoints === 8)
      assert(v.secondChancePoints === 10)
      assert(v.largestLead === 13)
      assert(v.leadChanges === 9)
      assert(v.timesTied === 8)
      assert(v.teamTurnovers === 0)
      assert(v.totalTurnovers === 17)
      assert(v.teamRebounds === 8)
      assert(v.pointsOffTurnOvers === 20)
    })

    parsedRespose.boxScoreSummary.homeStats.scoreLine.foreach(v => {
      assert(v.gameId === GameID)
      assert(v.gameDateTimeEST === "2017-04-12T00:00:00")
      assert(v.gameSequence === 3)
      assert(v.teamId === TeamIdParameter.BostonCeltics.value.toInt)
      assert(v.teamCityName === HomeTeamCity)
      assert(v.teamAbbreviation === HomeTeamAbbreviation)
      assert(v.teamNickName === HomeTeamName)
      assert(v.teamWinsLosses === "53-29")
      assert(v.quarter1Points === 25)
      assert(v.quarter2Points === 32)
      assert(v.quarter3Points === 23)
      assert(v.quarter4Points === 32)
      assert(v.points === 112)
      assert(v.ot1Points === 0)
      assert(v.ot2Points === 0)
      assert(v.ot3Points === 0)
      assert(v.ot4Points === 0)
      assert(v.ot5Points === 0)
      assert(v.ot6Points === 0)
      assert(v.ot7Points === 0)
      assert(v.ot8Points === 0)
      assert(v.ot9Points === 0)
      assert(v.ot10Points === 0)
    })

    parsedRespose.boxScoreSummary.homeStats.otherStats.foreach(v => {
      assert(v.leagueId === LeagueIdParameter.NBA.value)
      assert(v.teamId === TeamIdParameter.BostonCeltics.value.toInt)
      assert(v.teamAbbreviation === HomeTeamAbbreviation)
      assert(v.teamCity === HomeTeamCity)
      assert(v.pointsInPaint === 46)
      assert(v.fastBreakPoints === 17)
      assert(v.secondChancePoints === 15)
      assert(v.largestLead === 22)
      assert(v.leadChanges === 9)
      assert(v.timesTied === 8)
      assert(v.teamTurnovers === 0)
      assert(v.totalTurnovers === 8)
      assert(v.teamRebounds === 11)
      assert(v.pointsOffTurnOvers === 10)
    })

    parsedRespose.boxScoreSummary.lastMeeting.foreach(v => {
      assert(v.currentGameId === GameID)
      assert(v.gameId === "0021601113")
      assert(v.gameDateTimeEST === "2017-03-29T00:00:00")
      assert(v.homeTeamId.toString === TeamIdParameter.BostonCeltics.value)
      assert(v.homeTeamCity === HomeTeamCity)
      assert(v.homeTeamAbbreviation === HomeTeamAbbreviation)
      assert(v.homeTeamName === HomeTeamName)
      assert(v.homeTeamPoints === 100)
      assert(v.awayTeamCity === AwayTeamCity)
      assert(v.awayTeamCity1 === AwayTeamAbbreviation)
      assert(v.awayTeamId.toString === TeamIdParameter.MilwaukeeBucks.value)
      assert(v.awayTeamName === AwayTeamName)
      assert(v.awayTeamPoints === 103)
    })

    parsedRespose.boxScoreSummary.seasonSeries.foreach(v => {
      assert(v.gameId === GameID)
      assert(v.gameDateTimeEST === "2017-04-12T00:00:00")
      assert(v.homeTeamId.toString === TeamIdParameter.BostonCeltics.value)
      assert(v.awayTeamId.toString === TeamIdParameter.MilwaukeeBucks.value)
      assert(v.homeTeamWins === 2)
      assert(v.homeTeamLosses === 1)
      assert(v.seriesLeader === HomeTeamCity)

    })

    parsedRespose.boxScoreSummary.availableVideo.foreach(v => {
      assert(v.gameId === GameID)
      assert(v.HISTORICAL_STATUS === false)
      assert(v.HUSTLE_STATUS === true)
      assert(v.PT_AVAILABLE === true)
      assert(v.PT_XYZ_AVAILABLE === true)
      assert(v.VIDEO_AVAILABLE_FLAG === true)
      assert(v.WH_STATUS === true)
    })
  }
}
