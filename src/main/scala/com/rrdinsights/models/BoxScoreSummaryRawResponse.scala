package com.rrdinsights.models

import com.rrdinsights.parameters.{GameIdParameter, ParameterValue}

case class BoxScoreSummaryRawResponse(override val resource: String,
                                      override val parameters: BoxScoreSummaryParameterRawResponse,
                                      override val resultSets: Array[ResultSetResponse])
  extends Response[BoxScoreSummaryParameterRawResponse] {

  def toBoxScoreSummaryResponse: BoxScoreSummaryResponse =
    BoxScoreSummaryResponse(resource, parameters.toParameterValues, toBoxScoreSummary)

  def toBoxScoreSummary: BoxScoreSummary = BoxScoreSummaryRawResponse.toBoxScoreSummary(resultSets)
}

case class BoxScoreSummaryResponse(resource: String,
                                   parameters: Seq[ParameterValue],
                                   boxScoreSummary: BoxScoreSummary)

case class BoxScoreSummary(gameSummary: Option[GameSummary],
                           gameInfo: Option[GameInfo],
                           homeStats: TeamStats,
                           awayStats: TeamStats,
                           officials: Option[Seq[Officials]],
                           inactivePlayers: Option[Seq[InactivePlayers]],
                           lastMeeting: Option[LastMeeting],
                           seasonSeries: Option[SeasonSeries],
                           availableVideo: Option[AvailableVideo])

case class BoxScoreSummaryParameterRawResponse(GameID: String) extends ParameterResponse {
  def toParameterValues: Seq[ParameterValue] = Seq(GameIdParameter.newParameterValue(GameID))
}


case class GameSummary(gameDate: String,
                       gameSequence: Int,
                       gameId: String,
                       gameStatusId: Int,
                       gameStatusText: String,
                       gameCode: String,
                       homeTeamId: Int,
                       awayTeamId: Int,
                       season: String,
                       livePeriod: Int,
                       livePCTime: String,
                       broadcaster: String,
                       livePeriodTimeBroadcast: String,
                       whStatus: Int) extends ConvertedResultSetResponse

private case object GameSummaryConverter extends ResultSetRawResponseConverter[GameSummary] {

  override val name: String = "GameSummary"

  override def convertRaw(rows: Array[Array[Any]]): Seq[GameSummary] =
    rows.map(row =>
      GameSummary(
        Utils.trimOrNull(row(0).asInstanceOf[String]),
        row(1).asInstanceOf[BigInt].intValue(),
        Utils.trimOrNull(row(2).asInstanceOf[String]),
        row(3).asInstanceOf[BigInt].intValue(),
        Utils.trimOrNull(row(4).asInstanceOf[String]),
        Utils.trimOrNull(row(5).asInstanceOf[String]),
        row(6).asInstanceOf[BigInt].intValue(),
        row(7).asInstanceOf[BigInt].intValue(),
        Utils.trimOrNull(row(8).asInstanceOf[String]),
        row(9).asInstanceOf[BigInt].intValue(),
        Utils.trimOrNull(row(10).asInstanceOf[String]),
        Utils.trimOrNull(row(11).asInstanceOf[String]),
        Utils.trimOrNull(row(12).asInstanceOf[String]),
        row(13).asInstanceOf[BigInt].intValue()))

}

case class OtherStats(leagueId: String,
                      teamId: Int,
                      teamAbbreviation: String,
                      teamCity: String,
                      pointsInPaint: Int,
                      secondChancePoints: Int,
                      fastBreakPoints: Int,
                      largestLead: Int,
                      leadChanges: Int,
                      timesTied: Int,
                      teamTurnovers: Int,
                      totalTurnovers: Int,
                      teamRebounds: Int,
                      pointsOffTurnOvers: Int) extends ConvertedResultSetResponse

private object OtherStatsConverter extends ResultSetRawResponseConverter[OtherStats] {
  override val name: String = "OtherStats"

  override def convertRaw(rows: Array[Array[Any]]): Seq[OtherStats] =
    rows.map(row =>
      OtherStats(
        Utils.trimOrNull(row(0).asInstanceOf[String]),
        row(1).asInstanceOf[BigInt].intValue(),
        Utils.trimOrNull(row(2).asInstanceOf[String]),
        Utils.trimOrNull(row(3).asInstanceOf[String]),
        row(4).asInstanceOf[BigInt].intValue(),
        row(5).asInstanceOf[BigInt].intValue(),
        row(6).asInstanceOf[BigInt].intValue(),
        row(7).asInstanceOf[BigInt].intValue(),
        row(8).asInstanceOf[BigInt].intValue(),
        row(9).asInstanceOf[BigInt].intValue(),
        row(10).asInstanceOf[BigInt].intValue(),
        row(11).asInstanceOf[BigInt].intValue(),
        row(12).asInstanceOf[BigInt].intValue(),
        row(13).asInstanceOf[BigInt].intValue()))
}

case class Officials(officialId: Int,
                     firstName: String,
                     lastName: String,
                     number: String) extends ConvertedResultSetResponse

private case object OfficialsConverter extends ResultSetRawResponseConverter[Officials] {
  override val name: String = "Officials"

  override def convertRaw(rows: Array[Array[Any]]): Seq[Officials] =
    rows.map(row =>
      Officials(
        row(0).asInstanceOf[BigInt].intValue(),
        Utils.trimOrNull(row(1).asInstanceOf[String]),
        Utils.trimOrNull(row(2).asInstanceOf[String]),
        Utils.trimOrNull(row(3).asInstanceOf[String])))
}

case class InactivePlayers(playerId: Int,
                           firstName: String,
                           lastName: String,
                           number: String,
                           teamId: Int,
                           teamCity: String,
                           teamName: String,
                           teamAbbreviation: String) extends ConvertedResultSetResponse

private case object InactivePlayersConverter extends ResultSetRawResponseConverter[InactivePlayers] {
  override val name: String = "InactivePlayers"

  override def convertRaw(rows: Array[Array[Any]]): Seq[InactivePlayers] =
    rows.map(row =>
      InactivePlayers(
        row(0).asInstanceOf[BigInt].intValue(),
        Utils.trimOrNull(row(1).asInstanceOf[String]),
        Utils.trimOrNull(row(2).asInstanceOf[String]),
        Utils.trimOrNull(row(3).asInstanceOf[String]),
        row(4).asInstanceOf[BigInt].intValue(),
        Utils.trimOrNull(row(5).asInstanceOf[String]),
        Utils.trimOrNull(row(6).asInstanceOf[String]),
        Utils.trimOrNull(row(7).asInstanceOf[String])))
}

case class GameInfo(gameDate: String,
                    attendance: Int,
                    gameTime: String) extends ConvertedResultSetResponse

private case object GameInfoConverter extends ResultSetRawResponseConverter[GameInfo] {

  override val name: String = "GameInfo"

  override def convertRaw(rows: Array[Array[Any]]): Seq[GameInfo] =
    rows.map(row =>
      GameInfo(
        Utils.trimOrNull(row(0).asInstanceOf[String]),
        row(1).asInstanceOf[BigInt].intValue(),
        Utils.trimOrNull(row(2).asInstanceOf[String])))
}

case class ScoreLine(gameDateTimeEST: String,
                     gameSequence: Int,
                     gameId: String,
                     teamId: Int,
                     teamAbbreviation: String,
                     teamCityName: String,
                     teamNickName: String,
                     teamWinsLosses: String,
                     quarter1Points: Int,
                     quarter2Points: Int,
                     quarter3Points: Int,
                     quarter4Points: Int,
                     ot1Points: Int,
                     ot2Points: Int,
                     ot3Points: Int,
                     ot4Points: Int,
                     ot5Points: Int,
                     ot6Points: Int,
                     ot7Points: Int,
                     ot8Points: Int,
                     ot9Points: Int,
                     ot10Points: Int,
                     points: Int) extends ConvertedResultSetResponse

private case object ScoreLineConverter extends ResultSetRawResponseConverter[ScoreLine] {
  override val name: String = "LineScore"

  override def convertRaw(rows: Array[Array[Any]]): Seq[ScoreLine] =
    rows.map(row =>
      ScoreLine(
        Utils.trimOrNull(row(0).asInstanceOf[String]),
        row(1).asInstanceOf[BigInt].intValue(),
        Utils.trimOrNull(row(2).asInstanceOf[String]),
        row(3).asInstanceOf[BigInt].intValue(),
        Utils.trimOrNull(row(4).asInstanceOf[String]),
        Utils.trimOrNull(row(5).asInstanceOf[String]),
        Utils.trimOrNull(row(6).asInstanceOf[String]),
        Utils.trimOrNull(row(7).asInstanceOf[String]),
        row(8).asInstanceOf[BigInt].intValue(),
        row(9).asInstanceOf[BigInt].intValue(),
        row(10).asInstanceOf[BigInt].intValue(),
        row(11).asInstanceOf[BigInt].intValue(),
        row(12).asInstanceOf[BigInt].intValue(),
        row(13).asInstanceOf[BigInt].intValue(),
        row(14).asInstanceOf[BigInt].intValue(),
        row(15).asInstanceOf[BigInt].intValue(),
        row(16).asInstanceOf[BigInt].intValue(),
        row(17).asInstanceOf[BigInt].intValue(),
        row(18).asInstanceOf[BigInt].intValue(),
        row(19).asInstanceOf[BigInt].intValue(),
        row(20).asInstanceOf[BigInt].intValue(),
        row(21).asInstanceOf[BigInt].intValue(),
        row(22).asInstanceOf[BigInt].intValue()))
}

case class LastMeeting(currentGameId: String,
                       gameId: String,
                       gameDateTimeEST: String,
                       homeTeamId: Int,
                       homeTeamCity: String,
                       homeTeamName: String,
                       homeTeamAbbreviation: String,
                       homeTeamPoints: Int,
                       awayTeamId: Int,
                       awayTeamCity: String,
                       awayTeamName: String,
                       awayTeamCity1: String,
                       awayTeamPoints: Int) extends ConvertedResultSetResponse

private case object LastMeetingConverter extends ResultSetRawResponseConverter[LastMeeting] {
  override val name: String = "LastMeeting"

  override def convertRaw(rows: Array[Array[Any]]): Seq[LastMeeting] =
    rows.map(row =>
      LastMeeting(
        Utils.trimOrNull(row(0).asInstanceOf[String]),
        Utils.trimOrNull(row(1).asInstanceOf[String]),
        Utils.trimOrNull(row(2).asInstanceOf[String]),
        row(3).asInstanceOf[BigInt].intValue(),
        Utils.trimOrNull(row(4).asInstanceOf[String]),
        Utils.trimOrNull(row(5).asInstanceOf[String]),
        Utils.trimOrNull(row(6).asInstanceOf[String]),
        row(7).asInstanceOf[BigInt].intValue(),
        row(8).asInstanceOf[BigInt].intValue(),
        Utils.trimOrNull(row(9).asInstanceOf[String]),
        Utils.trimOrNull(row(10).asInstanceOf[String]),
        Utils.trimOrNull(row(11).asInstanceOf[String]),
        row(12).asInstanceOf[BigInt].intValue()))
}

case class SeasonSeries(gameId: String,
                        homeTeamId: Int,
                        awayTeamId: Int,
                        gameDateTimeEST: String,
                        homeTeamWins: Int,
                        homeTeamLosses: Int,
                        seriesLeader: String) extends ConvertedResultSetResponse

private case object SeasonSeriesConverter extends ResultSetRawResponseConverter[SeasonSeries] {
  override val name: String = "SeasonSeries"

  override def convertRaw(rows: Array[Array[Any]]): Seq[SeasonSeries] =
    rows.map(row => SeasonSeries(
      Utils.trimOrNull(row(0).asInstanceOf[String]),
      row(1).asInstanceOf[BigInt].intValue(),
      row(2).asInstanceOf[BigInt].intValue(),
      Utils.trimOrNull(row(3).asInstanceOf[String]),
      row(4).asInstanceOf[BigInt].intValue(),
      row(5).asInstanceOf[BigInt].intValue(),
      Utils.trimOrNull(row(6).asInstanceOf[String])))
}

case class AvailableVideo(gameId: String,
                          VIDEO_AVAILABLE_FLAG: Boolean,
                          PT_AVAILABLE: Boolean,
                          PT_XYZ_AVAILABLE: Boolean,
                          WH_STATUS: Boolean,
                          HUSTLE_STATUS: Boolean,
                          HISTORICAL_STATUS: Boolean) extends ConvertedResultSetResponse

private case object AvailableVideoConverter extends ResultSetRawResponseConverter[AvailableVideo] {
  override val name: String = "AvailableVideo"

  override def convertRaw(rows: Array[Array[Any]]): Seq[AvailableVideo] =
    rows.map(row =>
      AvailableVideo(
        Utils.trimOrNull(row(0).asInstanceOf[String]),
        row(1).asInstanceOf[BigInt].intValue() == 1,
        row(2).asInstanceOf[BigInt].intValue() == 1,
        row(3).asInstanceOf[BigInt].intValue() == 1,
        row(4).asInstanceOf[BigInt].intValue() == 1,
        row(5).asInstanceOf[BigInt].intValue() == 1,
        row(6).asInstanceOf[BigInt].intValue() == 1))
}

private object BoxScoreSummaryRawResponse extends ResultSetRawResponseConverters {

  private def findTeamScoreLine(scoreLines: Option[Seq[ScoreLine]], teamId: Option[Int]): Option[ScoreLine] = {
    (scoreLines, teamId) match {
      case (Some(sl), Some(id)) => sl.find(_.teamId == id)
      case _ => None
    }
  }

  private def findTeamOtherStats(otherStats: Option[Seq[OtherStats]], teamId: Option[Int]): Option[OtherStats] = {
    (otherStats, teamId) match {
      case (Some(ot), Some(id)) => ot.find(_.teamId == id)
      case _ => None
    }
  }

  private def toTeamStats(scoreLines: Option[Seq[ScoreLine]], otherStats: Option[Seq[OtherStats]], teamId: Option[Int]): TeamStats =
    TeamStats(findTeamScoreLine(scoreLines, teamId), findTeamOtherStats(otherStats, teamId))

  def toBoxScoreSummary(rawSummary: Array[ResultSetResponse]): BoxScoreSummary = {
    val gameSummary =  convert[GameSummary](rawSummary, GameSummaryConverter).map(_.head)

    val homeTeamId = gameSummary.map(_.homeTeamId)
    val awayTeamId = gameSummary.map(_.awayTeamId)

    val scoreLines = convert[ScoreLine](rawSummary, ScoreLineConverter)
    val otherStats = convert[OtherStats](rawSummary, OtherStatsConverter)

    BoxScoreSummary(
      gameSummary,
      convert[GameInfo](rawSummary, GameInfoConverter).map(_.head),
      toTeamStats(scoreLines, otherStats, homeTeamId),
      toTeamStats(scoreLines, otherStats, awayTeamId),
      convert[Officials](rawSummary, OfficialsConverter),
      convert[InactivePlayers](rawSummary, InactivePlayersConverter),
      convert[LastMeeting](rawSummary, LastMeetingConverter).map(_.head),
      convert[SeasonSeries](rawSummary, SeasonSeriesConverter).map(_.head),
      convert[AvailableVideo](rawSummary, AvailableVideoConverter).map(_.head))
  }

  override protected val converters: Seq[ResultSetRawResponseConverter[_]] =
    Seq(GameSummaryConverter, GameInfoConverter, OtherStatsConverter, OfficialsConverter, InactivePlayersConverter,
      ScoreLineConverter, LastMeetingConverter, SeasonSeriesConverter, AvailableVideoConverter)
}

private[models] case class TeamStats(scoreLine: Option[ScoreLine], otherStats: Option[OtherStats])