package com.rrdinsights.scalabrine.endpoints

import com.rrdinsights.scalabrine.parameters._
import com.rrdinsights.scalabrine.{ScalabrineClient, TestSpec}
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

    // results
    assert(parsedResponse.boxScoreSummary.gameSummary.isDefined)

    parsedResponse.boxScoreSummary.gameSummary.foreach(v => {
      assert(v.gameDate === "2017-04-12T00:00:00")
      assert(v.gameSequence.intValue() === 3)
      assert(v.gameId === GameID)
      assert(v.gameStatusId.intValue() === 3)
      assert(v.gameStatusText === "Final")
      assert(v.gameCode === "20170412/MILBOS")
      assert(v.homeTeamId.intValue() === 1610612738)
      assert(v.awayTeamId.intValue() === 1610612749)
      assert(v.season === "2016")
      assert(v.livePeriod.intValue() === 4)
      assert(v.livePCTime === null)
      assert(v.broadcaster === null)
      assert(v.livePeriodTimeBroadcast === "Q4       -")
      assert(v.whStatus.intValue() === 1)
    })

    parsedResponse.boxScoreSummary.gameInfo.foreach(v => {
      assert(v.gameDate === "WEDNESDAY, APRIL 12, 2017")
      assert(v.gameTime === "2:03")
      assert(v.attendance.intValue() === 18624)
    })

    parsedResponse.boxScoreSummary.officials.foreach(v => {
      v.firstName match {
        case "Tom" => assert(v.lastName === "Washington")
        case "Mark" => assert(v.lastName === "Lindsay")
        case "Marat" => assert(v.lastName === "Kogut")
        case n => fail(s"official $n not expected")
      }
    })

    val players = parsedResponse.boxScoreSummary.inactivePlayers
    assert(players.size === 4)
    val inactivePlayer = players.find(_.playerId.intValue() === 203114).getOrElse(fail(s"Player 203114 (Kris Middleton) not found"))
    assert(inactivePlayer.firstName === "Khris")
    assert(inactivePlayer.lastName === "Middleton")
    assert(inactivePlayer.number === "22")
    assert(inactivePlayer.teamAbbreviation === AwayTeamAbbreviation)
    assert(inactivePlayer.teamCity === AwayTeamCity)
    assert(inactivePlayer.teamName === AwayTeamName)
    assert(inactivePlayer.teamId.intValue() === 1610612749)

    parsedResponse.boxScoreSummary.awayStats.scoreLine.foreach(v => {
      assert(v.gameId === GameID)
      assert(v.gameDateTimeEST === "2017-04-12T00:00:00")
      assert(v.gameSequence.intValue() === 3)
      assert(v.teamId.intValue() === TeamIdParameter.MilwaukeeBucks.value.toInt)
      assert(v.teamCityName === AwayTeamCity)
      assert(v.teamAbbreviation === AwayTeamAbbreviation)
      assert(v.teamNickName === AwayTeamName)
      assert(v.teamWinsLosses === "42-40")
      assert(v.quarter1Points.intValue() === 36)
      assert(v.quarter2Points.intValue() === 20)
      assert(v.quarter3Points.intValue() === 22)
      assert(v.quarter4Points.intValue() === 16)
      assert(v.points.intValue() === 94)
      assert(v.ot1Points.intValue() === 0)
      assert(v.ot2Points.intValue() === 0)
      assert(v.ot3Points.intValue() === 0)
      assert(v.ot4Points.intValue() === 0)
      assert(v.ot5Points.intValue() === 0)
      assert(v.ot6Points.intValue() === 0)
      assert(v.ot7Points.intValue() === 0)
      assert(v.ot8Points.intValue() === 0)
      assert(v.ot9Points.intValue() === 0)
      assert(v.ot10Points.intValue() === 0)
    })

    parsedResponse.boxScoreSummary.awayStats.otherStats.foreach(v => {
      assert(v.leagueId === LeagueIdParameter.NBA.value)
      assert(v.teamId.intValue() === TeamIdParameter.MilwaukeeBucks.value.toInt)
      assert(v.teamAbbreviation === AwayTeamAbbreviation)
      assert(v.teamCity === AwayTeamCity)
      assert(v.pointsInPaint.intValue() === 48)
      assert(v.fastBreakPoints.intValue() === 8)
      assert(v.secondChancePoints.intValue() === 10)
      assert(v.largestLead.intValue() === 13)
      assert(v.leadChanges.intValue() === 9)
      assert(v.timesTied.intValue() === 8)
      assert(v.teamTurnovers.intValue() === 0)
      assert(v.totalTurnovers.intValue() === 17)
      assert(v.teamRebounds.intValue() === 8)
      assert(v.pointsOffTurnOvers.intValue() === 20)
    })

    parsedResponse.boxScoreSummary.homeStats.scoreLine.foreach(v => {
      assert(v.gameId === GameID)
      assert(v.gameDateTimeEST === "2017-04-12T00:00:00")
      assert(v.gameSequence.intValue() === 3)
      assert(v.teamId.intValue() === TeamIdParameter.BostonCeltics.value.toInt)
      assert(v.teamCityName === HomeTeamCity)
      assert(v.teamAbbreviation === HomeTeamAbbreviation)
      assert(v.teamNickName === HomeTeamName)
      assert(v.teamWinsLosses === "53-29")
      assert(v.quarter1Points.intValue() === 25)
      assert(v.quarter2Points.intValue() === 32)
      assert(v.quarter3Points.intValue() === 23)
      assert(v.quarter4Points.intValue() === 32)
      assert(v.points.intValue() === 112)
      assert(v.ot1Points.intValue() === 0)
      assert(v.ot2Points.intValue() === 0)
      assert(v.ot3Points.intValue() === 0)
      assert(v.ot4Points.intValue() === 0)
      assert(v.ot5Points.intValue() === 0)
      assert(v.ot6Points.intValue() === 0)
      assert(v.ot7Points.intValue() === 0)
      assert(v.ot8Points.intValue() === 0)
      assert(v.ot9Points.intValue() === 0)
      assert(v.ot10Points.intValue() === 0)
    })

    parsedResponse.boxScoreSummary.homeStats.otherStats.foreach(v => {
      assert(v.leagueId === LeagueIdParameter.NBA.value)
      assert(v.teamId.intValue() === TeamIdParameter.BostonCeltics.value.toInt)
      assert(v.teamAbbreviation === HomeTeamAbbreviation)
      assert(v.teamCity === HomeTeamCity)
      assert(v.pointsInPaint.intValue() === 46)
      assert(v.fastBreakPoints.intValue() === 17)
      assert(v.secondChancePoints.intValue() === 15)
      assert(v.largestLead.intValue() === 22)
      assert(v.leadChanges.intValue() === 9)
      assert(v.timesTied.intValue() === 8)
      assert(v.teamTurnovers.intValue() === 0)
      assert(v.totalTurnovers.intValue() === 8)
      assert(v.teamRebounds.intValue() === 11)
      assert(v.pointsOffTurnOvers.intValue() === 10)
    })

    parsedResponse.boxScoreSummary.lastMeeting.foreach(v => {
      assert(v.currentGameId === GameID)
      assert(v.gameId === "0021601113")
      assert(v.gameDateTimeEST === "2017-03-29T00:00:00")
      assert(v.homeTeamId.toString === TeamIdParameter.BostonCeltics.value)
      assert(v.homeTeamCity === HomeTeamCity)
      assert(v.homeTeamAbbreviation === HomeTeamAbbreviation)
      assert(v.homeTeamName === HomeTeamName)
      assert(v.homeTeamPoints.intValue() === 100)
      assert(v.awayTeamCity === AwayTeamCity)
      assert(v.awayTeamCity1 === AwayTeamAbbreviation)
      assert(v.awayTeamId.toString === TeamIdParameter.MilwaukeeBucks.value)
      assert(v.awayTeamName === AwayTeamName)
      assert(v.awayTeamPoints.intValue() === 103)
    })

    parsedResponse.boxScoreSummary.seasonSeries.foreach(v => {
      assert(v.gameId === GameID)
      assert(v.gameDateTimeEST === "2017-04-12T00:00:00")
      assert(v.homeTeamId.toString === TeamIdParameter.BostonCeltics.value)
      assert(v.awayTeamId.toString === TeamIdParameter.MilwaukeeBucks.value)
      assert(v.homeTeamWins.intValue() === 2)
      assert(v.homeTeamLosses.intValue() === 1)
      assert(v.seriesLeader === HomeTeamCity)

    })

    parsedResponse.boxScoreSummary.availableVideo.foreach(v => {
      assert(v.gameId === GameID)
      assert(v.HISTORICAL_STATUS === false)
      assert(v.HUSTLE_STATUS === true)
      assert(v.PT_AVAILABLE === true)
      assert(v.PT_XYZ_AVAILABLE === true)
      assert(v.VIDEO_AVAILABLE_FLAG === true)
      assert(v.WH_STATUS === true)
    })
  }

  ignore("boxscore advanced endpoint") {
    val game = GameIdParameter.newParameterValue(GameID)
    val season = GameIdParameter.newParameterValue(GameID)

    val boxScore = AdvancedBoxScoreEndpoint(gameId = game, season = season)
    val parsedResponse = ScalabrineClient.getAdvancedBoxScore(boxScore)

    // resource
    assert(parsedResponse.resource === "boxscore")

    // results

    // player stats
    assert(parsedResponse.boxScoreAdvanced.playerStats.size === 26)
    val testPlayer = parsedResponse.boxScoreAdvanced.playerStats.find(_.playerId.intValue() === 201563)
    assert(testPlayer.isDefined)
    testPlayer.foreach(p => {
      assert(p.playerId.intValue() === 201563)
      assert(p.teamId.intValue() === 1610612749)
      assert(p.gameId === "0021601219")
      assert(p.teamAbbreviation === "MIL")
      assert(p.teamCity === "Milwaukee")
      assert(p.playerName === "Michael Beasley")
      assert(p.startPosition === "F")
      assert(p.comment === null)
      assert(p.minutes.doubleValue() === 34.0 + 56.0 / 60.0)
      assert(p.offensiveRating.doubleValue() === 93.5)
      assert(p.defensiveRating.doubleValue() === 113.3)
      assert(p.netRating.doubleValue() === -19.9)
      assert(p.assistPercentage.doubleValue() === 0.091)
      assert(p.assistTurnOverRatio.intValue() === 2)
      assert(p.assistRatio.doubleValue() === 9.8)
      assert(p.offensiveReboundPercentage.doubleValue() === 0.03)
      assert(p.defensiveReboundPercentage.doubleValue() === 0.121)
      assert(p.reboundPercentage.doubleValue() === 0.076)
      assert(p.teamTurnOverPercentage.doubleValue() === 4.9)
      assert(p.effectiveFieldGoalPercentage.doubleValue() === 0.412)
      assert(p.trueShootingPercentage.doubleValue() === 0.43)
      assert(p.usage.doubleValue() === 0.225)
      assert(p.pace.doubleValue() === 102.5)
      assert(p.playerEstimatedImpact.doubleValue() === 0.08)
    })

    // team stats
    assert(parsedResponse.boxScoreAdvanced.teamStats.size === 2)
    val testTeam = parsedResponse.boxScoreAdvanced.teamStats.find(_.teamId.intValue() == 1610612738)

    testTeam.foreach(t => {
      assert(t.gameId === "0021601219")
      assert(t.teamId.intValue() === 1610612738)
      assert(t.teamName === "Celtics")
      assert(t.teamAbbreviation === "BOS")
      assert(t.teamCity === "Boston")
      assert(t.minutes.doubleValue() === 240.0)
      assert(t.offensiveRating.doubleValue() === 112.6)
      assert(t.defensiveRating.doubleValue() === 94.5)
      assert(t.netRating.doubleValue() === 18.1)
      assert(t.assistPercentage.doubleValue() === 0.756)
      assert(t.assistTurnOverRatio.doubleValue() === 3.88)
      assert(t.assistRatio.doubleValue() === 21.9)
      assert(t.offensiveReboundPercentage.doubleValue() === 0.244)
      assert(t.defensiveReboundPercentage.doubleValue() === 0.795)
      assert(t.reboundPercentage.doubleValue() === 0.517)
      assert(t.teamTurnOverPercentage.doubleValue() === 8.045)
      assert(t.effectiveFieldGoalPercentage.doubleValue() === 0.516)
      assert(t.trueShootingPercentage.doubleValue() === 0.547)
      assert(t.usage.doubleValue() === 0.2)
      assert(t.pace.doubleValue() === 99.46)
      assert(t.playerEstimatedImpact.doubleValue() === 0.582)
    })
  }

  ignore("play by play endpoint") {
    val param = GameIdParameter.newParameterValue(GameID)
    val playByPlay = PlayByPlayEndpoint(param)
    val parsedResponse = ScalabrineClient.getPlayByPlay(playByPlay)

    // resource
    assert(parsedResponse.resource === "playbyplay")

    // results
    assert(parsedResponse.playByPlay.events.size === 448)
  }

  ignore("team game log endpoint") {
    val teamId = TeamIdParameter.MiamiHeat
    val season = SeasonParameter.Season201617
    val teamGameLog = TeamGameLogEndpoint(teamId, season)
    val parsedResponse = ScalabrineClient.getTeamGameLog(teamGameLog)

    // resource
    assert(parsedResponse.resource === "teamgamelog")

    // results
    assert(parsedResponse.teamGameLog.games.size === 82)
  }

  ignore("common player info endpoint") {
    val playerId = PlayerIdParameter.newParameterValue("201566")
    val playerCommonInfo = CommonPlayerInfoEndpoint(playerId)
    val parsedResponse = ScalabrineClient.getCommonPlayerInfo(playerCommonInfo)

    // resource
    assert(parsedResponse.resource === "commonplayerinfo")

    // results
    assert(parsedResponse.commonPlayerInfo.playerInfo.map(_.playerId).getOrElse(0) === 201566)
  }

  ignore("test grabbing a bunch of teams at once") {
    TeamIdParameter.TeamIds
      .map(v => TeamGameLogEndpoint(v, SeasonParameter.Season201617))
      .map(v => {
        Thread.sleep(1000)
        ScalabrineClient.getTeamGameLog(v)
      })
      .flatMap(_.teamGameLog.games)
      .foreach(println)
  }

  ignore("test scoreboard") {
    val gameDate = GameDateParameter.newParameterValue("11/04/2016")
    val scoreboard = ScoreboardEndpoint(gameDate)

    val parsedResponse = ScalabrineClient.getScoreboard(scoreboard)

    // resource
    assert(parsedResponse.resource === "scoreboardV2")

    // results
    assert(parsedResponse.scoreboard.gameHeader.size === 9)
    assert(parsedResponse.scoreboard.easternConferenceStandings.size === 15)
    assert(parsedResponse.scoreboard.westernConferenceStandings.size === 15)
  }

  ignore("test basic splits") {
    val endpoint = PlayerBasicSplitsEndpoint(PlayerIdParameter.newParameterValue("203114"), SeasonParameter.Season201718)
    val parsedResponse = ScalabrineClient.getBasicSplitsPerPos(endpoint)

    println(endpoint.url)

    println(parsedResponse.playerBasicSplits.career.map(_.freeThrowPercentage).orNull)
  }

  ignore("test player profile") {
    val endpoint = PlayerProfileEndpoint(PlayerIdParameter.newParameterValue("203114"))
    val parsedResponse = ScalabrineClient.getPlayerProfileTotals(endpoint)

    println(endpoint.url)

    println(parsedResponse.playerProfile.careerTotals.map(_.freeThrowPercent).orNull)
  }
}