package com.rrdinsights.models

import com.rrdinsights.parameters._
import com.rrdinsights.models.Utils._

final case class CommonPlayerInfo(playerInfo: Option[PlayerInfo],
                                  playerHeadlineStats: Option[PlayerHeadlineStats]) {

}

final case class CommonPlayerInfoResponse(resource: String,
                                          parameters: Seq[ParameterValue],
                                          commonPlayerInfo: CommonPlayerInfo)

final case class PlayerInfo(playerId: Option[Int],
                            firstName: Option[String],
                            lastName: Option[String],
                            displayFirstLast: Option[String],
                            displayLastCommaFirst: Option[String],
                            displayInitialLast: Option[String],
                            birthday: Option[String], //YYYY-MM-DDTHH:MM:SS
                            school: Option[String],
                            country: Option[String],
                            lastAffiliation: Option[String],
                            height: Option[String],
                            weight: Option[String],
                            jerseyNumber: Option[String],
                            position: Option[String],
                            rosterStatus: Option[String],
                            teamId: Option[Int],
                            teamName: Option[String],
                            teamAbbreviation: Option[String],
                            teamCode: Option[String],
                            teamCity: Option[String],
                            playerCode: Option[String],
                            fromYear: Option[Int],
                            toYear: Option[Int],
                            dLeagueFlag: Option[String],
                            gamesPlayedFlag: Option[String],
                            draftYear: Option[String],
                            draftRound: Option[String],
                            draftNumber: Option[String]) extends ConvertedResultSetResponse

private[rrdinsights] case object PlayerInfoConverter extends ResultSetRawResponseConverter[PlayerInfo] {
  override val name: String = "CommonPlayerInfo"

  override def convertRaw(rows: Array[Array[Any]]): Seq[PlayerInfo] =
    rows.map(row => PlayerInfo(
      transformToInt(row(0)),
      transformToString(row(1)),
      transformToString(row(2)),
      transformToString(row(3)),
      transformToString(row(4)),
      transformToString(row(5)),
      transformToString(row(6)),
      transformToString(row(7)),
      transformToString(row(8)),
      transformToString(row(9)),
      transformToString(row(10)),
      transformToString(row(11)),
      transformToString(row(12)),
      transformToString(row(13)),
      transformToString(row(14)),

      transformToInt(row(15)),

      transformToString(row(18)),
      transformToString(row(17)),
      transformToString(row(18)),
      transformToString(row(19)),
      transformToString(row(20)),

      transformToInt(row(21)),
      transformToInt(row(22)),

      transformToString(row(23)),
      transformToString(row(24)),
      transformToString(row(25)),
      transformToString(row(26)),
      transformToString(row(27))))
}

final case class PlayerHeadlineStats(playerId: Option[Int],
                                     playerName: Option[String],
                                     timeFrame: Option[String],
                                     points: Option[Double],
                                     assists: Option[Double],
                                     rebounds: Option[Double],
                                     playerImpact: Option[Double]) extends ConvertedResultSetResponse

private[rrdinsights] case object PlayerHeadlineStatsConverter extends ResultSetRawResponseConverter[PlayerHeadlineStats] {
  override val name: String = "PlayerHeadlineStats"

  override def convertRaw(rows: Array[Array[Any]]): Seq[PlayerHeadlineStats] =
    rows.map(row => PlayerHeadlineStats(
      transformToInt(row(0)),
      transformToString(row(1)),
      transformToString(row(2)),
      transformToDouble(row(3)),
      transformToDouble(row(4)),
      transformToDouble(row(5)),
      transformToDouble(row(6))))
}

final case class CommonPlayerInfoParameterRawResponse(PlayerID: Int,
                                                      LeagueID: String) extends ParameterResponse {
  val toParameterValues: Seq[ParameterValue] = Seq(
    PlayerIdParameter.newParameterValue(PlayerID.toString),
    LeagueIdParameter.newParameterValue(LeagueID))

}

final case class CommonPlayerInfoRawResponse(override val resource: String,
                                             override val parameters: CommonPlayerInfoParameterRawResponse,
                                             override val resultSets: Array[ResultSetResponse])
  extends Response[CommonPlayerInfoParameterRawResponse] {

  def toCommonPlayerInfoResponse: CommonPlayerInfoResponse =
    CommonPlayerInfoResponse(resource, parameters.toParameterValues, toCommonPlayerInfo)

  def toCommonPlayerInfo: CommonPlayerInfo = CommonPlayerInfoRawResponse.toCommonPlayerInfo(resultSets)
}

private[rrdinsights] object CommonPlayerInfoRawResponse extends ResultSetRawResponseConverters {


  def toCommonPlayerInfo(rawSummary: Array[ResultSetResponse]): CommonPlayerInfo =
    CommonPlayerInfo(
      convert[PlayerInfo](rawSummary, PlayerInfoConverter).headOption,
      convert[PlayerHeadlineStats](rawSummary, PlayerHeadlineStatsConverter).headOption)

  override protected val converters: Seq[ResultSetRawResponseConverter[_]] =
    Seq(PlayerInfoConverter, PlayerHeadlineStatsConverter)
}