package com.rrdinsights.scalabrine.models

import com.rrdinsights.scalabrine.models.Utils._
import java.{lang => jl}

final case class Scoreboard(gameHeader: Seq[GameHeader],
                            easternConferenceStandings: Seq[ConferenceStanding],
                            westernConferenceStandings: Seq[ConferenceStanding])

final case class ScoreboardResponse(resource: String,
                                    scoreboard: Scoreboard)

final case class GameHeader(gameDate: String,
                             gameSequence: jl.Integer,
                             gameId: String,
                             gameStatusId: jl.Integer,
                             gameStatusText: String,
                             gameCode: String,
                             homeTeamId: jl.Integer,
                             awayTeamId: jl.Integer,
                             season: String,
                             livePeriod: jl.Integer,
                             livePCTime: String,
                             broadcasterNational: String,
                             broadcasterHome: String,
                             broadcasterAway: String,
                             livePeriodTimeBroadcast: String,
                              arena: String,
                             whStatus: jl.Integer) extends ConvertedResultSetResponse

private[rrdinsights] case object GameHeaderConverter extends ResultSetRawResponseConverter[GameHeader] {
  override val name: String = "GameHeader"

  override def convertRaw(rows: Array[Array[Any]]): Seq[GameHeader] =
    rows.map(row =>
      GameHeader(
        transformToString(row(0)),
        transformToInt(row(1)),
        transformToString(row(2)),
        transformToInt(row(3)),
        transformToString(row(4)),
        transformToString(row(5)),
        transformToInt(row(6)),
        transformToInt(row(7)),
        transformToString(row(8)),
        transformToInt(row(9)),
        transformToString(row(10)),
        transformToString(row(11)),
        transformToString(row(12)),
        transformToString(row(13)),
        transformToString(row(14)),
        transformToString(row(15)),
        transformToInt(row(16))))
}

final case class ConferenceStanding(teamId: jl.Integer,
                                    leagueId: String,
                                    seasonId: String,
                                    date: String,
                                    conference: String,
                                    team: String,
                                    games: jl.Integer,
                                    wins: jl.Integer,
                                    loss: jl.Integer,
                                    winPercent: jl.Double,
                                    homeRecord: String,
                                    awayRecord: String) extends ConvertedResultSetResponse

sealed trait ConferenceStandingConverter extends ResultSetRawResponseConverter[ConferenceStanding] {

    override def convertRaw(rows: Array[Array[Any]]): Seq[ConferenceStanding] =
      rows.map(row =>
        ConferenceStanding(
          transformToInt(row(0)),
          transformToString(row(1)),
          transformToString(row(2)),
          transformToString(row(3)),
          transformToString(row(4)),
          transformToString(row(5)),
          transformToInt(row(6)),
          transformToInt(row(7)),
          transformToInt(row(8)),
          transformToDouble(row(9)),
          transformToString(row(10)),
          transformToString(row(11))))
  }

private[rrdinsights] case object EasternConferenceStandingConverter extends ConferenceStandingConverter {
  override val name = "EastConfStandingsByDay"
}

private[rrdinsights] case object WesternConferenceStandingConverter extends ConferenceStandingConverter {
  override val name = "WestConfStandingsByDay"
}


final case class ScoreboardRawResponse(override val resource: String,
                                            override val resultSets: Array[ResultSetResponse])
  extends RawResponse {

  def toScoreboardResponse: ScoreboardResponse =
    ScoreboardResponse(resource, toScoreboard)

  def toScoreboard: Scoreboard = ScoreboardRawResponse.toScoreboard(resultSets)
}

private[rrdinsights] object ScoreboardRawResponse extends ResultSetRawResponseConverters {


  def toScoreboard(rawSummary: Array[ResultSetResponse]): Scoreboard =
    Scoreboard(
      convert[GameHeader](rawSummary, GameHeaderConverter),
      convert[ConferenceStanding](rawSummary, EasternConferenceStandingConverter),
      convert[ConferenceStanding](rawSummary, WesternConferenceStandingConverter))

  override protected val converters: Seq[ResultSetRawResponseConverter[_]] =
    Seq(GameHeaderConverter, EasternConferenceStandingConverter, WesternConferenceStandingConverter)
}