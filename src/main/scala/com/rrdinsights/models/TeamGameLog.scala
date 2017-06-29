package com.rrdinsights.models

import com.rrdinsights.parameters._
import com.rrdinsights.models.Utils._

final case class TeamGameLog(games: Seq[GameLog]) {

}

final case class TeamGameLogResponse(resource: String,
                                     parameters: Seq[ParameterValue],
                                     teamGameLog: TeamGameLog)

final case class GameLog(teamId: Option[Int],
                         gameId: Option[String],
                         gameDate: Option[String],
                         matchup: Option[String],
                         result: Option[String], // TODO - convert to case objects
                         wins: Option[Int],
                         losses: Option[Int],
                         winPercentage: Option[Double],
                         minutes: Option[Double],

                         fieldGoalsMade: Option[Int],
                         fieldGoalAttempts: Option[Int],
                         fieldGoalPercentage: Option[Double],

                         threePointFieldGoalsMade: Option[Int],
                         threePointFieldGoalAttempts: Option[Int],
                         threePointFieldGoalPercentage: Option[Double],

                         freeThrowsMade: Option[Int],
                         freeThrowAttempts: Option[Int],
                         freeThrowPercentage: Option[Double],

                         offensiveRebounds: Option[Int],
                         deffensiveRebounds: Option[Int],
                         totalRebounds: Option[Int],

                         assists: Option[Int],
                         steals: Option[Int],
                         blocks: Option[Int],
                         turnovers: Option[Int],
                         personalFouls: Option[Int],
                         points: Option[Int]) extends ConvertedResultSetResponse

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

  private def minutesToDouble(minutes: Option[Int]): Option[Double] = minutes.map(_.toDouble)
}

final case class TeamGameLogParameterRawResponse(TeamID: Int,
                                                 LeagueID: String,
                                                 Season: String,
                                                 SeasonType: String,
                                                 DateFrom: String,
                                                 DateTo: String) extends ParameterResponse {
  val toParameterValues: Seq[ParameterValue] = Seq(
    TeamIdParameter.newParameterValue(TeamID.toString),
    SeasonParameter.newParameterValue(Season),
    SeasonTypeParameter.newParameterValue(SeasonType),
    DateFromParameter.newParameterValue(DateFrom),
    DateToParameter.newParameterValue(DateTo))

}

final case class TeamGameLogRawResponse(override val resource: String,
                                       override val parameters: TeamGameLogParameterRawResponse,
                                       override val resultSets: Array[ResultSetResponse])
  extends Response[TeamGameLogParameterRawResponse] {

  def toTeamGameLogResponse: TeamGameLogResponse =
    TeamGameLogResponse(resource, parameters.toParameterValues, toTeamGameLog)

  def toTeamGameLog: TeamGameLog = TeamGameLogRawResponse.toTeamGameLog(resultSets)
}

private[rrdinsights] object TeamGameLogRawResponse extends ResultSetRawResponseConverters {


  def toTeamGameLog(rawSummary: Array[ResultSetResponse]): TeamGameLog =
    TeamGameLog(convert[GameLog](rawSummary, GameConverter))

  override protected val converters: Seq[ResultSetRawResponseConverter[_]] =
    Seq(GameConverter)
}