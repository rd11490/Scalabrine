package com.rrdinsights.models

import com.rrdinsights.parameters._
import com.rrdinsights.models.Utils._

final case class BoxScoreAdvanced(teamStats: Seq[TeamStats],
                                  playerStats: Seq[PlayerStats]) {

}

final case class BoxScoreAdvancedResponse(resource: String,
                                          parameters: Seq[ParameterValue],
                                          boxScoreAdvanced: BoxScoreAdvanced)

final case class TeamStats(gameId: Option[String],
                           teamId: Option[Int],
                           teamName: Option[String],
                           teamAbbreviation: Option[String],
                           teamCity: Option[String],
                           minutes: Option[Double],
                           offensiveRating: Option[Double],
                           defensiveRating: Option[Double],
                           netRating: Option[Double],
                           assistPercentage: Option[Double],
                           assistTurnOverRatio: Option[Double],
                           offensiveReboundPercentage: Option[Double],
                           defensiveReboundPercentage: Option[Double],
                           teamTurnOverPercentage: Option[Double],
                           effectiveFieldGoalPercentage: Option[Double],
                           trueShootingPercentage: Option[Double],
                           usage: Option[Double],
                           pace: Option[Double],
                           playerEstimatedImpact: Option[Double]) extends ConvertedResultSetResponse


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
        transformToString(row(5)).map(Utils.convertMinutesToDouble),
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
        transformToDouble(row(18))))
}

final case class PlayerStats(gameId: Option[String],
                             teamId: Option[Int],
                             teamAbbreviation: Option[String],
                             teamCity: Option[String],
                             playerId: Option[Int],
                             playerName: Option[String],
                             startPosition: Option[String],
                             comment: Option[String],
                             minutes: Option[Double],
                             offensiveRating: Option[Double],
                             defensiveRating: Option[Double],
                             netRating: Option[Double],
                             assistPercentage: Option[Double],
                             assistTurnOverRatio: Option[Double],
                             assistRatio: Option[Double],
                             offensiveReboundPercentage: Option[Double],
                             defensiveReboundPercentage: Option[Double],
                             reboundPercentage: Option[Double],
                             teamTurnOverPercentage: Option[Double],
                             effectiveFieldGoalPercentage: Option[Double],
                             trueShootingPercentage: Option[Double],
                             usage: Option[Double],
                             pace: Option[Double],
                             playerEstimatedImpact: Option[Double]) extends ConvertedResultSetResponse

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
        transformToString(row(8)).map(Utils.convertMinutesToDouble),
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
                                            override val parameters: BoxScoreAdvancedParameterRawResponse,
                                            override val resultSets: Array[ResultSetResponse])
  extends Response[BoxScoreAdvancedParameterRawResponse] {

  def toBoxScoreAdvancedResponse: BoxScoreAdvancedResponse =
    BoxScoreAdvancedResponse(resource, parameters.toParameterValues, toBoxScoreAdvanced)

  def toBoxScoreAdvanced: BoxScoreAdvanced = BoxScoreAdvancedRawResponse.toBoxScoreAdvanced(resultSets)
}

final case class BoxScoreAdvancedParameterRawResponse(GameID: String,
                                                     StartPeriod: Long,
                                                     EndPeriod: Long,
                                                     StartRange: Long,
                                                     EndRange: Long,
                                                     RangeType: Long) extends ParameterResponse {
  val toParameterValues: Seq[ParameterValue] = Seq(
    GameIdParameter.newParameterValue(GameID),
    StartPeriodParameter.newParameterValue(StartPeriod.toString),
    EndPeriodParameter.newParameterValue(EndPeriod.toString),
    StartRangeParameter.newParameterValue(StartRange.toString),
    EndRangeParameter.newParameterValue(EndRange.toString),
    EndRangeParameter.newParameterValue(RangeType.toString))
}

private[rrdinsights] object BoxScoreAdvancedRawResponse extends ResultSetRawResponseConverters {


  def toBoxScoreAdvanced(rawSummary: Array[ResultSetResponse]): BoxScoreAdvanced =
    BoxScoreAdvanced(
      convert[TeamStats](rawSummary, TeamStatsConverter),
      convert[PlayerStats](rawSummary, PlayerStatsConverter))

  override protected val converters: Seq[ResultSetRawResponseConverter[_]] =
    Seq(PlayerStatsConverter, TeamStatsConverter)
}