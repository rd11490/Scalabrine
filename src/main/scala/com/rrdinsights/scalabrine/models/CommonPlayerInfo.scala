package com.rrdinsights.scalabrine.models

import java.{lang => jl}
import com.rrdinsights.scalabrine.models.Utils._

final case class CommonPlayerInfo(playerInfo: Option[PlayerInfo],
                                  playerHeadlineStats: Option[PlayerHeadlineStats])

final case class CommonPlayerInfoResponse(resource: String,
                                          commonPlayerInfo: CommonPlayerInfo)

final case class PlayerInfo(playerId: jl.Integer,
                            firstName: String,
                            lastName: String,
                            displayFirstLast: String,
                            displayLastCommaFirst: String,
                            displayInitialLast: String,
                            birthday: String, //YYYY-MM-DDTHH:MM:SS
                            school: String,
                            country: String,
                            lastAffiliation: String,
                            height: String,
                            weight: String,
                            yearsExerience: jl.Integer,
                            jerseyNumber: String,
                            position: String,
                            rosterStatus: String,
                            teamId: jl.Integer,
                            teamName: String,
                            teamAbbreviation: String,
                            teamCode: String,
                            teamCity: String,
                            playerCode: String,
                            fromYear: jl.Integer,
                            toYear: jl.Integer,
                            dLeagueFlag: String,
                            gamesPlayedFlag: String,
                            draftYear: String,
                            draftRound: String,
                            draftNumber: String) extends ConvertedResultSetResponse

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
      transformToInt(row(12)),
      transformToString(row(13)),
      transformToString(row(14)),
      transformToString(row(15)),

      transformToInt(row(16)),

      transformToString(row(17)),
      transformToString(row(18)),
      transformToString(row(19)),
      transformToString(row(20)),
      transformToString(row(21)),

      transformToInt(row(22)),
      transformToInt(row(23)),

      transformToString(row(24)),
      transformToString(row(25)),
      transformToString(row(26)),
      transformToString(row(27)),
      transformToString(row(27))))
}

final case class PlayerHeadlineStats(playerId: jl.Integer,
                                     playerName: String,
                                     timeFrame: String,
                                     points: jl.Double,
                                     assists: jl.Double,
                                     rebounds: jl.Double,
                                     playerImpact: jl.Double) extends ConvertedResultSetResponse

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

final case class CommonPlayerInfoRawResponse(override val resource: String,
                                             override val resultSets: Array[ResultSetResponse])
  extends RawResponse {

  def toCommonPlayerInfoResponse: CommonPlayerInfoResponse =
    CommonPlayerInfoResponse(resource, toCommonPlayerInfo)

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