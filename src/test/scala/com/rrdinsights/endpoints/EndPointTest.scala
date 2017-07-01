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


  ignore("boxscore endpoint") {
    val param = GameIdParameter.newParameterValue(GameID)
    val boxScore = BoxScoreEndpoint(param)
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

  ignore("boxscore advanced endpoint") {
    val param = GameIdParameter.newParameterValue(GameID)
    val boxScore = AdvancedBoxScoreEndpoint(param)
    val parsedResponse = ScalabrineClient.getAdvancedBoxScore(boxScore)

    // resource
    assert(parsedResponse.resource === "boxscore")

    // parameters
    assert(parsedResponse.parameters.size === 6)
    assert(parsedResponse.parameters.head === GameIdParameter.newParameterValue("0021601219"))

    // results

    // player stats
    assert(parsedResponse.boxScoreAdvanced.playerStats.size === 26)
    val testPlayer = parsedResponse.boxScoreAdvanced.playerStats.find(_.playerId.contains(201563))
    assert(testPlayer.isDefined)
    testPlayer.foreach(p => {
      assert(p.playerId.contains(201563))
      assert(p.teamId.contains(1610612749))
      assert(p.gameId.contains("0021601219"))
      assert(p.teamAbbreviation.contains("MIL"))
      assert(p.teamCity.contains("Milwaukee"))
      assert(p.playerName.contains("Michael Beasley"))
      assert(p.startPosition.contains("F"))
      assert(p.comment.isEmpty)
      assert(p.minutes.get === 34.0 + 56.0/60.0)
      assert(p.offensiveRating.contains(93.5))
      assert(p.defensiveRating.contains(113.3))
      assert(p.netRating.contains(-19.9))
      assert(p.assistPercentage.contains(0.091))
      assert(p.assistTurnOverRatio.contains(2))
      assert(p.assistRatio.contains(9.8))
      assert(p.offensiveReboundPercentage.contains(0.03))
      assert(p.defensiveReboundPercentage.contains(0.121))
      assert(p.reboundPercentage.contains(0.076))
      assert(p.teamTurnOverPercentage.contains(4.9))
      assert(p.effectiveFieldGoalPercentage.contains(0.412))
      assert(p.trueShootingPercentage.contains(0.43))
      assert(p.usage.contains(0.225))
      assert(p.pace.contains(102.5))
      assert(p.playerEstimatedImpact.contains(0.08))
    })

    // team stats
    assert(parsedResponse.boxScoreAdvanced.teamStats.size === 2)
    val testTeam = parsedResponse.boxScoreAdvanced.teamStats.find(_.teamId.contains(1610612738))

    testTeam.foreach(t => {
      assert(t.gameId.contains("0021601219"))
      assert(t.teamId.contains(1610612738))
      assert(t.teamName.contains("Celtics"))
      assert(t.teamAbbreviation.contains("BOS"))
      assert(t.teamCity.contains("Boston"))
      assert(t.minutes.contains(240.0))
      assert(t.offensiveRating.contains(112.6))
      assert(t.defensiveRating.contains(94.5))
      assert(t.netRating.contains(18.1))
      assert(t.assistPercentage.contains(0.756))
      assert(t.assistTurnOverRatio.contains(3.88))
      assert(t.assistRatio.contains(21.9))
      assert(t.offensiveReboundPercentage.contains(0.244))
      assert(t.defensiveReboundPercentage.contains(0.795))
      assert(t.reboundPercentage.contains(0.517))
      assert(t.teamTurnOverPercentage.contains(8.045))
      assert(t.effectiveFieldGoalPercentage.contains(0.516))
      assert(t.trueShootingPercentage.contains(0.547))
      assert(t.usage.contains(0.2))
      assert(t.pace.contains(99.46))
      assert(t.playerEstimatedImpact.contains(0.582))
    })
  }

  ignore("play by play endpoint") {
    val param = GameIdParameter.newParameterValue(GameID)
    val playByPlay = PlayByPlayEndpoint(param)
    val parsedResponse = ScalabrineClient.getPlayByPlay(playByPlay)

    // resource
    assert(parsedResponse.resource === "playbyplay")

    // parameters
    assert(parsedResponse.parameters.size === 3)
    assert(parsedResponse.parameters.head === GameIdParameter.newParameterValue("0021601219"))

    // results
    assert(parsedResponse.playByPlay.events.size === 448)
  }

  ignore("team game log endpoint") {
    val teamId = TeamIdParameter.BostonCeltics
    val teamGameLog = TeamGameLogEndpoint(teamId)
    val parsedResponse = ScalabrineClient.getTeamGameLog(teamGameLog)

    // resource
    assert(parsedResponse.resource === "teamgamelog")

    // parameters
    assert(parsedResponse.parameters.size === 5)
    assert(parsedResponse.parameters.head === TeamIdParameter.BostonCeltics)

    // results
    assert(parsedResponse.teamGameLog.games.size === 82)
  }

  test("common player info endpoint") {
    val playerId = PlayerIdParameter.newParameterValue("201566")
    val playerCommonInfo = CommonPlayerInfoEndpoint(playerId)
    val parsedResponse = ScalabrineClient.getCommonPlayerInfo(playerCommonInfo)

    // resource
    assert(parsedResponse.resource === "commonplayerinfo")

    // parameters
    assert(parsedResponse.parameters.head === playerId)
    assert(parsedResponse.parameters.size === 2)

    // results
    assert(parsedResponse.commonPlayerInfo.playerInfo.flatMap(_.playerId).get === 201566)
  }
}