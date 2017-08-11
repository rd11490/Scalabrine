package com.rrdinsights.scalabrine.models

import com.rrdinsights.scalabrine.models.Utils._
import java.{lang => jl}

final case class TeamGameLog(games: Seq[GameLog])

final case class TeamGameLogResponse(resource: String,
                                     teamGameLog: TeamGameLog)

final case class GameLog(teamId: jl.Integer,
                         gameId: String,
                         gameDate: String,
                         matchup: String,
                         result: String, // TODO - convert to case objects
                         wins: jl.Integer,
                         losses: jl.Integer,
                         winPercentage: jl.Double,
                         minutes: jl.Double,

                         fieldGoalsMade: jl.Integer,
                         fieldGoalAttempts: jl.Integer,
                         fieldGoalPercentage: jl.Double,

                         threePointFieldGoalsMade: jl.Integer,
                         threePointFieldGoalAttempts: jl.Integer,
                         threePointFieldGoalPercentage: jl.Double,

                         freeThrowsMade: jl.Integer,
                         freeThrowAttempts: jl.Integer,
                         freeThrowPercentage: jl.Double,

                         offensiveRebounds: jl.Integer,
                         defensiveRebounds: jl.Integer,
                         totalRebounds: jl.Integer,

                         assists: jl.Integer,
                         steals: jl.Integer,
                         blocks: jl.Integer,
                         turnovers: jl.Integer,
                         personalFouls: jl.Integer,
                         points: jl.Integer) extends ConvertedResultSetResponse

private[rrdinsights] case object GameConverter extends ResultSetRawResponseConverter[GameLog] {
  override val name: String = "TeamGameLog"

  override def convertRaw(rows: Array[Array[Any]]): Seq[GameLog] =
    rows.map(row => GameLog(
      transformToInt(row(0)),
      transformToString(row(1)),
      transformToString(row(2)),
      transformToString(row(3)),
      transformToString(row(4)),

      transformToInt(row(5)),
      transformToInt(row(6)),
      transformToDouble(row(7)),
      minutesToDouble(transformToInt(row(8))),

      transformToInt(row(9)),
      transformToInt(row(10)),
      transformToDouble(row(11)),

      transformToInt(row(12)),
      transformToInt(row(13)),
      transformToDouble(row(14)),

      transformToInt(row(15)),
      transformToInt(row(18)),
      transformToDouble(row(17)),

      transformToInt(row(18)),
      transformToInt(row(19)),
      transformToInt(row(20)),

      transformToInt(row(21)),
      transformToInt(row(22)),
      transformToInt(row(23)),
      transformToInt(row(24)),
      transformToInt(row(25)),
      transformToInt(row(26))))

  private def minutesToDouble(minutes: jl.Integer): jl.Double = minutes.toDouble
}


final case class TeamGameLogRawResponse(override val resource: String,
                                        override val resultSets: Array[ResultSetResponse]) extends RawResponse {

  def toTeamGameLogResponse: TeamGameLogResponse =
    TeamGameLogResponse(resource, toTeamGameLog)

  def toTeamGameLog: TeamGameLog = TeamGameLogRawResponse.toTeamGameLog(resultSets)

}

private[rrdinsights] object TeamGameLogRawResponse extends ResultSetRawResponseConverters {


  def toTeamGameLog(rawSummary: Array[ResultSetResponse]): TeamGameLog =
    TeamGameLog(convert[GameLog](rawSummary, GameConverter))

  override protected val converters: Seq[ResultSetRawResponseConverter[_]] =
    Seq(GameConverter)
}