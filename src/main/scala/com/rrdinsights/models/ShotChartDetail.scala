package com.rrdinsights.models

import com.rrdinsights.models.Utils._

final case class ShotChartDetails(shots: Seq[Shot],
                                  leagueAverages: Seq[ShotAverages])

final case class ShotChartDetailsResponse(resource: String,
                                     teamGameLog: ShotChartDetails)

final case class Shot(gridType: Option[String],
                      gameId: Option[String],
                      gameEventId: Option[Int],
                      playerId: Option[Int],
                      playerName: Option[String],
                      teamId: Option[Int],
                      teamName: Option[String],
                      period: Option[Int],
                      minutesRemaining: Option[Int],
                      secondsRemaining: Option[Int],
                      eventType: Option[String],
                      actionType: Option[String],
                      shotZoneBasic: Option[String],
                      shotZoneArea: Option[String],
                      shotZoneRange: Option[String],
                      shotDistance: Option[Int],
                      xCoordinate: Option[Int],
                      yCoordinate: Option[Int],
                      shotAttemptedFlag: Option[Int],
                      shotMadeFlag: Option[Int],
                      gameDate: Option[String],
                      homeTeam: Option[String],
                      awayTeam: Option[String]) extends ConvertedResultSetResponse

private[rrdinsights] case object ShotConverter extends ResultSetRawResponseConverter[Shot] {
  override val name: String = "Shot_Chart_Detail"

  override def convertRaw(rows: Array[Array[Any]]): Seq[Shot] =
    rows.map(row => Shot(
      transformToString(row(0)),
      transformToString(row(1)),
      transformToInt(row(2)),
      transformToInt(row(3)),
      transformToString(row(4)),
      transformToInt(row(5)),
      transformToString(row(6)),
      transformToInt(row(7)),
      transformToInt(row(8)),
      transformToInt(row(9)),
      transformToString(row(10)),
      transformToString(row(11)),
      transformToString(row(12)),
      transformToString(row(13)),
      transformToString(row(14)),
      transformToInt(row(15)),
      transformToInt(row(16)),
      transformToInt(row(17)),
      transformToInt(row(18)),
      transformToInt(row(19)),
      transformToString(row(20)),
      transformToString(row(21)),
      transformToString(row(22))))
}

final case class ShotAverages(gridType: Option[String],
                              shotZoneBasic: Option[String],
                              shotZoneArea: Option[String],
                              shotZoneRange: Option[String],
                              fieldGoalAttempt: Option[Int],
                              fieldGoalMade: Option[Int],
                              fieldGoalPercentage: Option[Double]
                             ) extends ConvertedResultSetResponse

private[rrdinsights] case object ShotAveragesConverter extends ResultSetRawResponseConverter[ShotAverages] {
  override val name: String = "LeagueAverages"

  override def convertRaw(rows: Array[Array[Any]]): Seq[ShotAverages] =
    rows.map(row => ShotAverages(
      transformToString(row(0)),
      transformToString(row(1)),
      transformToString(row(2)),
      transformToString(row(3)),
      transformToInt(row(4)),
      transformToInt(row(5)),
      transformToDouble(row(6))))
}


final case class ShotChartDetailsRawResponse(override val resource: String,
                                        override val resultSets: Array[ResultSetResponse]) extends RawResponse {

  def toShotChartDetailsResponse: ShotChartDetailsResponse =
    ShotChartDetailsResponse(resource, toShotChartDetails)

  def toShotChartDetails: ShotChartDetails = ShotChartDetailsRawResponse.toShotChartDetails(resultSets)

}

private[rrdinsights] object ShotChartDetailsRawResponse extends ResultSetRawResponseConverters {


  def toShotChartDetails(rawSummary: Array[ResultSetResponse]): ShotChartDetails =
    ShotChartDetails(convert[Shot](rawSummary, ShotConverter),
      convert[ShotAverages](rawSummary, ShotAveragesConverter))

  override protected val converters: Seq[ResultSetRawResponseConverter[_]] =
    Seq(ShotConverter, ShotAveragesConverter)
}