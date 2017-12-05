package com.rrdinsights.scalabrine.models

import java.{lang => jl}

import com.rrdinsights.scalabrine.models.Utils._

final case class PlayerProfileResponse(resource: String,
                                       playerProfile: PlayerProfile)

final case class PlayerProfile(
                                careerTotals: Option[PlayerProfileCareer],
                                seasonTotalsRegularSeason: Seq[PlayerProfileSeason])

final case class PlayerProfileSeason(
                                      playerId: Integer,
                                      season: String,
                                      teamId: Integer,
                                      playerAge: jl.Double,
                                      gamesPlayed: Integer,
                                      gamesStarted: Integer,
                                      minutes: jl.Double,
                                      fieldGoalsMade: jl.Double,
                                      fieldGoalsAttempted: jl.Double,
                                      fieldGoalPercent: jl.Double,
                                      threePointFieldGoalsMade: jl.Double,
                                      threePointFieldGoalsAttempted: jl.Double,
                                      threePointFieldGoalPercent: jl.Double,
                                      freeThrowsMade: jl.Double,
                                      freeThrowsAttempted: jl.Double,
                                      freeThrowPercent: jl.Double,
                                      offensiveRebounds: jl.Double,
                                      defensiveRebounds: jl.Double,
                                      rebounds: jl.Double,
                                      assists: jl.Double,
                                      steals: jl.Double,
                                      blocks: jl.Double,
                                      turnovers: jl.Double,
                                      fouls: jl.Double,
                                      points: jl.Double) extends ConvertedResultSetResponse

final case class PlayerProfileCareer(
                                      playerId: Integer,
                                      gamesPlayed: Integer,
                                      gamesStarted: Integer,
                                      minutes: jl.Double,
                                      fieldGoalsMade: jl.Double,
                                      fieldGoalsAttempted: jl.Double,
                                      fieldGoalPercent: jl.Double,
                                      threePointFieldGoalsMade: jl.Double,
                                      threePointFieldGoalsAttempted: jl.Double,
                                      threePointFieldGoalPercent: jl.Double,
                                      freeThrowsMade: jl.Double,
                                      freeThrowsAttempted: jl.Double,
                                      freeThrowPercent: jl.Double,
                                      offensiveRebounds: jl.Double,
                                      defensiveRebounds: jl.Double,
                                      rebounds: jl.Double,
                                      assists: jl.Double,
                                      steals: jl.Double,
                                      blocks: jl.Double,
                                      turnovers: jl.Double,
                                      fouls: jl.Double,
                                      points: jl.Double) extends ConvertedResultSetResponse

private[rrdinsights] case object PlayerProfileCareerConverter extends ResultSetRawResponseConverter[PlayerProfileCareer] {
  override val name: String = "CareerTotalsRegularSeason"
  
  override def convertRaw(rows: Array[Array[Any]]): Seq[PlayerProfileCareer] =
    rows.map(row =>
      PlayerProfileCareer(
        transformToInt(row(0)),
        transformToInt(row(3)),
        transformToInt(row(4)),
        transformToIntOrDouble(row(5)),
        transformToIntOrDouble(row(6)),
        transformToIntOrDouble(row(7)),
        transformToIntOrDouble(row(8)),
        transformToIntOrDouble(row(9)),
        transformToIntOrDouble(row(10)),
        transformToIntOrDouble(row(11)),
        transformToIntOrDouble(row(12)),
        transformToIntOrDouble(row(13)),
        transformToIntOrDouble(row(14)),
        transformToIntOrDouble(row(15)),
        transformToIntOrDouble(row(16)),
        transformToIntOrDouble(row(17)),
        transformToIntOrDouble(row(18)),
        transformToIntOrDouble(row(19)),
        transformToIntOrDouble(row(20)),
        transformToIntOrDouble(row(21)),
        transformToIntOrDouble(row(22)),
        transformToIntOrDouble(row(23))))
}

//        Utils.convertMinutesToDouble(transformToString(row(5))),


private[rrdinsights] case object PlayerProfileSeasonConverter extends ResultSetRawResponseConverter[PlayerProfileSeason] {
  override val name: String = "SeasonTotalsRegularSeason"

  override def convertRaw(rows: Array[Array[Any]]): Seq[PlayerProfileSeason] =
    rows.map(row =>
      PlayerProfileSeason(
        transformToInt(row(0)),
        transformToString(row(1)),
        transformToInt(row(3)),
        transformToIntOrDouble(row(5)),
        transformToInt(row(6)),
        transformToInt(row(7)),
        transformToIntOrDouble(row(8)),
        transformToIntOrDouble(row(9)),
        transformToIntOrDouble(row(10)),
        transformToIntOrDouble(row(11)),
        transformToIntOrDouble(row(12)),
        transformToIntOrDouble(row(13)),
        transformToIntOrDouble(row(14)),
        transformToIntOrDouble(row(15)),
        transformToIntOrDouble(row(16)),
        transformToIntOrDouble(row(17)),
        transformToIntOrDouble(row(18)),
        transformToIntOrDouble(row(19)),
        transformToIntOrDouble(row(20)),
        transformToIntOrDouble(row(21)),
        transformToIntOrDouble(row(22)),
        transformToIntOrDouble(row(23)),
        transformToIntOrDouble(row(24)),
        transformToIntOrDouble(row(25)),
        transformToIntOrDouble(row(26))))
}


final case class PlayerProfileRawResponse(override val resource: String,
                                              override val resultSets: Array[ResultSetResponse])
  extends RawResponse {

  def toPlayerProfileResponse: PlayerProfileResponse =
    PlayerProfileResponse(resource, toPlayerProfile)

  def toPlayerProfile: PlayerProfile = PlayerProfileRawResponse.toPlayerProfile(resultSets)
}

private[rrdinsights] object PlayerProfileRawResponse extends ResultSetRawResponseConverters {


  def toPlayerProfile(rawSummary: Array[ResultSetResponse]): PlayerProfile =
    PlayerProfile(
      convert[PlayerProfileCareer](rawSummary, PlayerProfileCareerConverter).headOption,
      convert[PlayerProfileSeason](rawSummary, PlayerProfileSeasonConverter))

  override protected val converters: Seq[ResultSetRawResponseConverter[_]] =
    Seq(PlayerStatsConverter, TeamStatsConverter)
}