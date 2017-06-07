package com.rrdinsights.endpoints

import com.rrdinsights.parameters._
import com.rrdinsights.{ScalabrineClient, TestSpec}
import org.json4s.DefaultFormats
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
    val param = GameIdParameter.newParameterValue(GameID)
    val boxScore = BoxScore(param)
    val parsedResponse = ScalabrineClient.getBoxScoreSummary(boxScore)

    // resource
    assert(parsedResponse.resource === "boxscoresummary")

    // parameters
    assert(parsedResponse.parameters.size === 1)
    assert(parsedResponse.parameters.head === GameIdParameter.newParameterValue("0021601219"))

    // results
    assert(parsedResponse.boxScoreSummary.gameSummary.isDefined)

    parsedResponse.boxScoreSummary.gameSummary.foreach(v => {
      assert(v.gameDate.get === "2017-04-12T00:00:00")
      assert(v.gameSequence.get === 3)
      assert(v.gameId.get === GameID)
      assert(v.gameStatusId.get === 3)
      assert(v.gameStatusText.get === "Final")
      assert(v.gameCode.get === "20170412/MILBOS")
      assert(v.homeTeamId.get === 1610612738)
      assert(v.awayTeamId.get === 1610612749)
      assert(v.season.get === "2016")
      assert(v.livePeriod.get === 4)
      assert(v.livePCTime.isEmpty)
      assert(v.broadcaster.isEmpty)
      assert(v.livePeriodTimeBroadcast.get === "Q4       -")
      assert(v.whStatus.get === 1)
    })

    parsedResponse.boxScoreSummary.gameInfo.foreach(v => {
      assert(v.gameDate.get === "WEDNESDAY, APRIL 12, 2017")
      assert(v.gameTime.get === "2:03")
      assert(v.attendance.get === 18624)
    })

    parsedResponse.boxScoreSummary.officials.foreach(v => {
      v.firstName.get match {
          case "Tom" => assert(v.lastName.get === "Washington")
          case "Mark" => assert(v.lastName.get === "Lindsay")
          case "Marat" => assert(v.lastName.get === "Kogut")
          case n => fail(s"official $n not expected")
        }
      })

    val players = parsedResponse.boxScoreSummary.inactivePlayers
      assert(players.size === 4)
      val inactivePlayer = players.find(_.playerId.get === 203114).getOrElse(fail(s"Player 203114 (Kris Middleton) not found"))
      assert(inactivePlayer.firstName.get === "Khris")
      assert(inactivePlayer.lastName.get === "Middleton")
      assert(inactivePlayer.number.get === "22")
      assert(inactivePlayer.teamAbbreviation.get === AwayTeamAbbreviation)
      assert(inactivePlayer.teamCity.get === AwayTeamCity)
      assert(inactivePlayer.teamName.get === AwayTeamName)
      assert(inactivePlayer.teamId.get === 1610612749)

    parsedResponse.boxScoreSummary.awayStats.scoreLine.foreach(v => {
      assert(v.gameId.get === GameID)
      assert(v.gameDateTimeEST.get === "2017-04-12T00:00:00")
      assert(v.gameSequence.get === 3)
      assert(v.teamId.get === TeamIdParameter.MilwaukeeBucks.value.toInt)
      assert(v.teamCityName.get === AwayTeamCity)
      assert(v.teamAbbreviation.get === AwayTeamAbbreviation)
      assert(v.teamNickName.get === AwayTeamName)
      assert(v.teamWinsLosses.get === "42-40")
      assert(v.quarter1Points.get === 36)
      assert(v.quarter2Points.get === 20)
      assert(v.quarter3Points.get === 22)
      assert(v.quarter4Points.get === 16)
      assert(v.points.get === 94)
      assert(v.ot1Points.get === 0)
      assert(v.ot2Points.get === 0)
      assert(v.ot3Points.get === 0)
      assert(v.ot4Points.get === 0)
      assert(v.ot5Points.get === 0)
      assert(v.ot6Points.get === 0)
      assert(v.ot7Points.get === 0)
      assert(v.ot8Points.get === 0)
      assert(v.ot9Points.get === 0)
      assert(v.ot10Points.get === 0)
    })

    parsedResponse.boxScoreSummary.awayStats.otherStats.foreach(v => {
      assert(v.leagueId.get === LeagueIdParameter.NBA.value)
      assert(v.teamId.get === TeamIdParameter.MilwaukeeBucks.value.toInt)
      assert(v.teamAbbreviation.get === AwayTeamAbbreviation)
      assert(v.teamCity.get === AwayTeamCity)
      assert(v.pointsInPaint.get === 48)
      assert(v.fastBreakPoints.get === 8)
      assert(v.secondChancePoints.get === 10)
      assert(v.largestLead.get === 13)
      assert(v.leadChanges.get === 9)
      assert(v.timesTied.get === 8)
      assert(v.teamTurnovers.get === 0)
      assert(v.totalTurnovers.get === 17)
      assert(v.teamRebounds.get === 8)
      assert(v.pointsOffTurnOvers.get === 20)
    })

    parsedResponse.boxScoreSummary.homeStats.scoreLine.foreach(v => {
      assert(v.gameId.get === GameID)
      assert(v.gameDateTimeEST.get === "2017-04-12T00:00:00")
      assert(v.gameSequence.get === 3)
      assert(v.teamId.get === TeamIdParameter.BostonCeltics.value.toInt)
      assert(v.teamCityName.get === HomeTeamCity)
      assert(v.teamAbbreviation.get === HomeTeamAbbreviation)
      assert(v.teamNickName.get === HomeTeamName)
      assert(v.teamWinsLosses.get === "53-29")
      assert(v.quarter1Points.get === 25)
      assert(v.quarter2Points.get === 32)
      assert(v.quarter3Points.get === 23)
      assert(v.quarter4Points.get === 32)
      assert(v.points.get === 112)
      assert(v.ot1Points.get === 0)
      assert(v.ot2Points.get === 0)
      assert(v.ot3Points.get === 0)
      assert(v.ot4Points.get === 0)
      assert(v.ot5Points.get === 0)
      assert(v.ot6Points.get === 0)
      assert(v.ot7Points.get === 0)
      assert(v.ot8Points.get === 0)
      assert(v.ot9Points.get === 0)
      assert(v.ot10Points.get === 0)
    })

    parsedResponse.boxScoreSummary.homeStats.otherStats.foreach(v => {
      assert(v.leagueId.get === LeagueIdParameter.NBA.value)
      assert(v.teamId.get === TeamIdParameter.BostonCeltics.value.toInt)
      assert(v.teamAbbreviation.get === HomeTeamAbbreviation)
      assert(v.teamCity.get === HomeTeamCity)
      assert(v.pointsInPaint.get === 46)
      assert(v.fastBreakPoints.get === 17)
      assert(v.secondChancePoints.get === 15)
      assert(v.largestLead.get === 22)
      assert(v.leadChanges.get === 9)
      assert(v.timesTied.get === 8)
      assert(v.teamTurnovers.get === 0)
      assert(v.totalTurnovers.get === 8)
      assert(v.teamRebounds.get === 11)
      assert(v.pointsOffTurnOvers.get === 10)
    })

    parsedResponse.boxScoreSummary.lastMeeting.foreach(v => {
      assert(v.currentGameId.get === GameID)
      assert(v.gameId.get === "0021601113")
      assert(v.gameDateTimeEST.get === "2017-03-29T00:00:00")
      assert(v.homeTeamId.get.toString === TeamIdParameter.BostonCeltics.value)
      assert(v.homeTeamCity.get === HomeTeamCity)
      assert(v.homeTeamAbbreviation.get === HomeTeamAbbreviation)
      assert(v.homeTeamName.get === HomeTeamName)
      assert(v.homeTeamPoints.get === 100)
      assert(v.awayTeamCity.get === AwayTeamCity)
      assert(v.awayTeamCity1.get === AwayTeamAbbreviation)
      assert(v.awayTeamId.get.toString === TeamIdParameter.MilwaukeeBucks.value)
      assert(v.awayTeamName.get === AwayTeamName)
      assert(v.awayTeamPoints.get === 103)
    })

    parsedResponse.boxScoreSummary.seasonSeries.foreach(v => {
      assert(v.gameId.get === GameID)
      assert(v.gameDateTimeEST.get === "2017-04-12T00:00:00")
      assert(v.homeTeamId.get.toString === TeamIdParameter.BostonCeltics.value)
      assert(v.awayTeamId.get.toString === TeamIdParameter.MilwaukeeBucks.value)
      assert(v.homeTeamWins.get === 2)
      assert(v.homeTeamLosses.get === 1)
      assert(v.seriesLeader.get === HomeTeamCity)

    })

    parsedResponse.boxScoreSummary.availableVideo.foreach(v => {
      assert(v.gameId.get === GameID)
      assert(v.HISTORICAL_STATUS === false)
      assert(v.HUSTLE_STATUS === true)
      assert(v.PT_AVAILABLE === true)
      assert(v.PT_XYZ_AVAILABLE === true)
      assert(v.VIDEO_AVAILABLE_FLAG === true)
      assert(v.WH_STATUS === true)
    })
  }

  test("boxscore advanced endpoint") {
    val param = GameIdParameter.newParameterValue(GameID)
    val boxScore = AdvancedBoxScore(param)
    val parsedResponse = ScalabrineClient.getAdvancedBoxScore(boxScore)

    // resource
    assert(parsedResponse.resource === "boxscore")

    // parameters
    assert(parsedResponse.parameters.size === 6)
    assert(parsedResponse.parameters.head === GameIdParameter.newParameterValue("0021601219"))

    // results
    assert(parsedResponse.boxScoreAdvanced.playerStats.nonEmpty)
    parsedResponse.boxScoreAdvanced.playerStats.foreach(v => {
      println(v.playerName)
      println(v.minutes)
      println()
    })
    assert(parsedResponse.boxScoreAdvanced.teamStats.nonEmpty)

  }
}