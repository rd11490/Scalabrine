package com.rrdinsights.scalabrine.models

import java.{lang => jl}

import com.rrdinsights.scalabrine.models.Utils._

final case class CommonTeamRoster(players: Seq[Player],
                                  coaches: Seq[Coach])

final case class CommonTeamRosterResponse(resource: String,
                                          commonTeamRoster: CommonTeamRoster)

final case class Player(teamId: jl.Integer,
                        season: String,
                        leagueId: String,
                        playerName: String,
                        number: String,
                        position: String,
                        height: String,
                        weight: String,
                        birthDate: String,
                        age: jl.Integer,
                        experience: String,
                        school: String,
                        playerId: jl.Integer) extends ConvertedResultSetResponse

private[rrdinsights] case object PlayerConverter extends ResultSetRawResponseConverter[Player] {
  override val name: String = "CommonTeamRoster"

  override def convertRaw(rows: Array[Array[Any]]): Seq[Player] =
    rows.map(row => Player(
      transformToInt(row(0)),
      transformToString(row(1)),
      transformToString(row(2)),
      transformToString(row(3)),
      transformToString(row(4)),
      transformToString(row(5)),
      transformToString(row(6)),
      transformToString(row(7)),
      transformToString(row(8)),
      transformToInt(row(9)),
      transformToString(row(10)),
      transformToString(row(11)),
      transformToInt(row(12))))
}


final case class Coach(teamId: jl.Integer,
                       season: String,
                       coachId: String,
                       firstName: String,
                       lastName: String,
                       coachName: String,
                       coachCode: String,
                       isAssistant: Boolean,
                       coachType: String,
                       school: String) extends ConvertedResultSetResponse

private[rrdinsights] case object CoachConverter extends ResultSetRawResponseConverter[Coach] {
  override val name: String = "Coaches"

  override def convertRaw(rows: Array[Array[Any]]): Seq[Coach] =
    rows.map(row => Coach(
      transformToInt(row(0)),
      transformToString(row(1)),
      transformToString(row(2)),
      transformToString(row(3)),
      transformToString(row(4)),
      transformToString(row(5)),
      transformToString(row(6)),
      transformToInt(row(7)).equals(1),
      transformToString(row(8)),
      transformToString(row(9))))
}

final case class CommonTeamRosterRawResponse(override val resource: String,
                                             override val resultSets: Array[ResultSetResponse])
  extends RawResponse {

  def toCommonTeamRosterResponse: CommonTeamRosterResponse =
    CommonTeamRosterResponse(resource, toCommonTeamRoster)

  def toCommonTeamRoster: CommonTeamRoster = CommonTeamRosterRawResponse.toCommonTeamRoster(resultSets)
}

private[rrdinsights] object CommonTeamRosterRawResponse extends ResultSetRawResponseConverters {


  def toCommonTeamRoster(rawSummary: Array[ResultSetResponse]): CommonTeamRoster =
    CommonTeamRoster(
      convert[Player](rawSummary, PlayerConverter),
      convert[Coach](rawSummary, CoachConverter))

  override protected val converters: Seq[ResultSetRawResponseConverter[_]] =
    Seq(PlayerConverter, CoachConverter)
}