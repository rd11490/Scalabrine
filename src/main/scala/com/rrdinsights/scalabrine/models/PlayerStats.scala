package com.rrdinsights.scalabrine.models

import java.{lang => jl}

import com.rrdinsights.scalabrine.models.Utils.{transformToDouble, transformToInt, transformToString}

final case class PlayerBasicSplitsResponse(
                                      resource: String,
                                      playerBasicSplits: PlayerBasicSplits)

final case class PlayerBasicSplits(
                              career: Option[PlayerBasicSplitsRow],
                              byYear: Seq[PlayerBasicSplitsRow])

final case class PlayerBasicSplitsRow(
                                 grouping: String,
                                 group: String,
                                 teamId: Integer,
                                 teamAbbreviation: String,
                                 latestGameDate: String,
                                 gamesPlayed: Integer,
                                 wins: Integer,
                                 losses: Integer,
                                 winPercent: jl.Double,
                                 minutesPlayed: jl.Double,
                                 fieldGoalsMade: jl.Double,
                                 fieldGoalsAttempted: jl.Double,
                                 fieldGoalPercentage: jl.Double,
                                 fieldGoalsMade3: jl.Double,
                                 fieldGoalsAttempted3: jl.Double,
                                 fieldGoalPercentage3: jl.Double,
                                 freeThrowsMade: jl.Double,
                                 freeThrowsAttempted: jl.Double,
                                 freeThrowPercentage: jl.Double,
                                 offensiveRebounds: jl.Double,
                                 defenseRebounds: jl.Double,
                                 rebounds: jl.Double,
                                 assists: jl.Double,
                                 turnovers: jl.Double,
                                 steals: jl.Double,
                                 blocks: jl.Double,
                                 blockedShots: jl.Double,
                                 personalFouls: jl.Double,
                                 fouled: jl.Double,
                                 points: jl.Double,
                                 plusMinus: jl.Double) extends ConvertedResultSetResponse


private[rrdinsights] case object PlayerBasicSplitsRowConverter extends ResultSetRawResponseConverter[PlayerBasicSplitsRow] {
  override val name: String = "ByYearPlayerDashboard"

  override def convertRaw(rows: Array[Array[Any]]): Seq[PlayerBasicSplitsRow] =
    rows.map(row =>
      PlayerBasicSplitsRow(
        transformToString(row(0)),
        transformToString(row(1)),
        transformToInt(row(2)),
        transformToString(row(3)),
        transformToString(row(4)),
        transformToInt(row(5)),
        transformToInt(row(6)),
        transformToInt(row(7)),
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
        transformToDouble(row(20)),
        transformToDouble(row(21)),
        transformToDouble(row(22)),
        transformToDouble(row(23)),
        transformToDouble(row(24)),
        transformToDouble(row(25)),
        transformToDouble(row(26)),
        transformToDouble(row(27)),
        transformToDouble(row(28)),
        transformToDouble(row(29)),
        transformToDouble(row(30))))
}

//        Utils.convertMinutesToDouble(transformToString(row(5))),


private[rrdinsights] case object PlayerBasicSplitsRowCareerConverter extends ResultSetRawResponseConverter[PlayerBasicSplitsRow] {
  override val name: String = "OverallPlayerDashboard"

  override def convertRaw(rows: Array[Array[Any]]): Seq[PlayerBasicSplitsRow] =
    rows.map(row =>
      PlayerBasicSplitsRow(
        transformToString(row(0)),
        transformToString(row(1)),
        transformToInt(row(2)),
        transformToString(row(3)),
        transformToString(row(4)),
        transformToInt(row(5)),
        transformToInt(row(6)),
        transformToInt(row(7)),
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
        transformToDouble(row(20)),
        transformToDouble(row(21)),
        transformToDouble(row(22)),
        transformToDouble(row(23)),
        transformToDouble(row(24)),
        transformToDouble(row(25)),
        transformToDouble(row(26)),
        transformToDouble(row(27)),
        transformToDouble(row(28)),
        transformToDouble(row(29)),
        transformToDouble(row(30))))
}


final case class PlayerBasicSplitsRawResponse(override val resource: String,
                                             override val resultSets: Array[ResultSetResponse])
  extends RawResponse {

  def toPlayerBasicSplitsResponse: PlayerBasicSplitsResponse =
    PlayerBasicSplitsResponse(resource, toPlayerBasicSplits)

  def toPlayerBasicSplits: PlayerBasicSplits = PlayerBasicSplitsRawResponse.toPlayerBasicSplits(resultSets)
}

private[rrdinsights] object PlayerBasicSplitsRawResponse extends ResultSetRawResponseConverters {


  def toPlayerBasicSplits(rawSummary: Array[ResultSetResponse]): PlayerBasicSplits =
    PlayerBasicSplits(
      convert[PlayerBasicSplitsRow](rawSummary, PlayerBasicSplitsRowCareerConverter).headOption,
      convert[PlayerBasicSplitsRow](rawSummary, PlayerBasicSplitsRowConverter))

  override protected val converters: Seq[ResultSetRawResponseConverter[_]] =
    Seq(PlayerStatsConverter, TeamStatsConverter)
}