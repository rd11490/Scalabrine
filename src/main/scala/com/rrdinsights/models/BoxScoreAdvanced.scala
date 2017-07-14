package com.rrdinsights.models

import com.rrdinsights.models.Utils._
import java.{lang => jl}

final case class BoxScoreAdvanced(teamStats: Seq[TeamStats],
                                  playerStats: Seq[PlayerStats])

final case class BoxScoreAdvancedResponse(resource: String,
                                          boxScoreAdvanced: BoxScoreAdvanced)

final case class TeamStats(gameId: String,
                           teamId: jl.Integer,
                           teamName: String,
                           teamAbbreviation: String,
                           teamCity: String,
                           minutes: jl.Double,
                           offensiveRating: jl.Double,
                           defensiveRating: jl.Double,
                           netRating: jl.Double,
                           assistPercentage: jl.Double,
                           assistTurnOverRatio: jl.Double,
                           assistRatio: jl.Double,
                           offensiveReboundPercentage: jl.Double,
                           defensiveReboundPercentage: jl.Double,
                           reboundPercentage: jl.Double,
                           teamTurnOverPercentage: jl.Double,
                           effectiveFieldGoalPercentage: jl.Double,
                           trueShootingPercentage: jl.Double,
                           usage: jl.Double,
                           pace: jl.Double,
                           playerEstimatedImpact: jl.Double) extends ConvertedResultSetResponse


private[rrdinsights] case object TeamStatsConverter extends ResultSetRawResponseConverter[TeamStats] {
  override val name: String = "TeamStats"

  override def convertRaw(rows: Array[Array[Any]]): Seq[TeamStats] =
    rows.map(row =>
      TeamStats(
        transformToString(row(0)),
        transformToInt(row(1)),
        transformToString(row(2)),
        transformToString(row(3)),
        transformToString(row(4)),
        Utils.convertMinutesToDouble(transformToString(row(5))),
        transformToDouble(row(6)),
        transformToDouble(row(7)),
        transformToDouble(row(8)),
        transformToDouble(row(9)),
        transformToDouble(row(10)),
        transformToDouble(row(11)),
        transformToDouble(row(12)),
        transformToDouble(row(13)),
        transformToDouble(row(14)),
        transformToDouble(row(15)),
        transformToDouble(row(16)),
        transformToDouble(row(17)),
        transformToDouble(row(18)),
        transformToDouble(row(19)),
        transformToDouble(row(20))))
}

final case class PlayerStats(gameId: String,
                             teamId: jl.Integer,
                             teamAbbreviation: String,
                             teamCity: String,
                             playerId: jl.Integer,
                             playerName: String,
                             startPosition: String,
                             comment: String,
                             minutes: jl.Double,
                             offensiveRating: jl.Double,
                             defensiveRating: jl.Double,
                             netRating: jl.Double,
                             assistPercentage: jl.Double,
                             assistTurnOverRatio: jl.Double,
                             assistRatio: jl.Double,
                             offensiveReboundPercentage: jl.Double,
                             defensiveReboundPercentage: jl.Double,
                             reboundPercentage: jl.Double,
                             teamTurnOverPercentage: jl.Double,
                             effectiveFieldGoalPercentage: jl.Double,
                             trueShootingPercentage: jl.Double,
                             usage: jl.Double,
                             pace: jl.Double,
                             playerEstimatedImpact: jl.Double) extends ConvertedResultSetResponse

private[rrdinsights] case object PlayerStatsConverter extends ResultSetRawResponseConverter[PlayerStats] {
  override val name: String = "PlayerStats"

  override def convertRaw(rows: Array[Array[Any]]): Seq[PlayerStats] =
    rows.map(row =>
      PlayerStats(
        transformToString(row(0)),
        transformToInt(row(1)),
        transformToString(row(2)),
        transformToString(row(3)),
        transformToInt(row(4)),
        transformToString(row(5)),
        transformToString(row(6)),
        transformToString(row(7)),
        Utils.convertMinutesToDouble(transformToString(row(8))),
        transformToDouble(row(9)),
        transformToDouble(row(10)),
        transformToDouble(row(11)),
        transformToDouble(row(12)),
        transformToDouble(row(13)),
        transformToDouble(row(14)),
        transformToDouble(row(15)),
        transformToDouble(row(16)),
        transformToDouble(row(17)),
        transformToDouble(row(18)),
        transformToDouble(row(19)),
        transformToDouble(row(20)),
        transformToDouble(row(21)),
        transformToDouble(row(22)),
        transformToDouble(row(23))))
}

final case class BoxScoreAdvancedRawResponse(override val resource: String,
                                            override val resultSets: Array[ResultSetResponse])
  extends RawResponse {

  def toBoxScoreAdvancedResponse: BoxScoreAdvancedResponse =
    BoxScoreAdvancedResponse(resource, toBoxScoreAdvanced)

  def toBoxScoreAdvanced: BoxScoreAdvanced = BoxScoreAdvancedRawResponse.toBoxScoreAdvanced(resultSets)
}

private[rrdinsights] object BoxScoreAdvancedRawResponse extends ResultSetRawResponseConverters {


  def toBoxScoreAdvanced(rawSummary: Array[ResultSetResponse]): BoxScoreAdvanced =
    BoxScoreAdvanced(
      convert[TeamStats](rawSummary, TeamStatsConverter),
      convert[PlayerStats](rawSummary, PlayerStatsConverter))

  override protected val converters: Seq[ResultSetRawResponseConverter[_]] =
    Seq(PlayerStatsConverter, TeamStatsConverter)
}