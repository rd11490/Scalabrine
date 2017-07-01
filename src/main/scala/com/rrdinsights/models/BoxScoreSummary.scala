package com.rrdinsights.models

import com.rrdinsights.parameters.{GameIdParameter, ParameterValue}
import com.rrdinsights.models.Utils._

final case class BoxScoreSummary(gameSummary: Option[GameSummary],
                           gameInfo: Option[GameInfo],
                           homeStats: TeamSummaryStats,
                           awayStats: TeamSummaryStats,
                           officials: Seq[Officials],
                           inactivePlayers: Seq[InactivePlayers],
                           lastMeeting: Option[LastMeeting],
                           seasonSeries: Option[SeasonSeries],
                           availableVideo: Option[AvailableVideo])

final case class BoxScoreSummaryResponse(resource: String,
                                   boxScoreSummary: BoxScoreSummary)


final case class GameSummary(gameDate: Option[String] = None,
                       gameSequence: Option[Int] = None,
                       gameId: Option[String] = None,
                       gameStatusId: Option[Int] = None,
                       gameStatusText: Option[String] = None,
                       gameCode: Option[String] = None,
                       homeTeamId: Option[Int] = None,
                       awayTeamId: Option[Int] = None,
                       season: Option[String] = None,
                       livePeriod: Option[Int] = None,
                       livePCTime: Option[String] = None,
                       broadcaster: Option[String] = None,
                       livePeriodTimeBroadcast: Option[String] = None,
                       whStatus: Option[Int] = None) extends ConvertedResultSetResponse

private[rrdinsights] case object GameSummaryConverter extends ResultSetRawResponseConverter[GameSummary] {

  override val name: String = "GameSummary"

  override def convertRaw(rows: Array[Array[Any]]): Seq[GameSummary] =
    rows.map(row =>
      GameSummary(
        transformToString(row(0)),
        transformToInt(row(1)),
        transformToString(row(2)),
        transformToInt(row(3)),
        transformToString(row(4)),
        transformToString(row(5)),
        transformToInt(row(6)),
        transformToInt(row(7)),
        transformToString(row(8)),
        transformToInt(row(9)),
        transformToString(row(10)),
        transformToString(row(11)),
        transformToString(row(12)),
        transformToInt(row(13))))

}

final case class OtherStats(leagueId: Option[String] = None,
                      teamId: Option[Int] = None,
                      teamAbbreviation: Option[String] = None,
                      teamCity: Option[String] = None,
                      pointsInPaint: Option[Int] = None,
                      secondChancePoints: Option[Int] = None,
                      fastBreakPoints: Option[Int] = None,
                      largestLead: Option[Int] = None,
                      leadChanges: Option[Int] = None,
                      timesTied: Option[Int] = None,
                      teamTurnovers: Option[Int] = None,
                      totalTurnovers: Option[Int] = None,
                      teamRebounds: Option[Int] = None,
                      pointsOffTurnOvers: Option[Int] = None) extends ConvertedResultSetResponse

private[rrdinsights] object OtherStatsConverter extends ResultSetRawResponseConverter[OtherStats] {
  override val name: String = "OtherStats"

  override def convertRaw(rows: Array[Array[Any]]): Seq[OtherStats] =
    rows.map(row =>
      OtherStats(
        transformToString(row(0)),
        transformToInt(row(1)),
        transformToString(row(2)),
        transformToString(row(3)),
        transformToInt(row(4)),
        transformToInt(row(5)),
        transformToInt(row(6)),
        transformToInt(row(7)),
        transformToInt(row(8)),
        transformToInt(row(9)),
        transformToInt(row(10)),
        transformToInt(row(11)),
        transformToInt(row(12)),
        transformToInt(row(13))))
}

final case class Officials(officialId: Option[Int] = None,
                     firstName: Option[String] = None,
                     lastName: Option[String] = None,
                     number: Option[String] = None) extends ConvertedResultSetResponse

private[rrdinsights] case object OfficialsConverter extends ResultSetRawResponseConverter[Officials] {
  override val name: String = "Officials"

  override def convertRaw(rows: Array[Array[Any]]): Seq[Officials] =
    rows.map(row =>
      Officials(
        transformToInt(row(0)),
        transformToString(row(1)),
        transformToString(row(2)),
        transformToString(row(3))))
}

final case class InactivePlayers(playerId: Option[Int] = None,
                           firstName: Option[String] = None,
                           lastName: Option[String] = None,
                           number: Option[String] = None,
                           teamId: Option[Int] = None,
                           teamCity: Option[String] = None,
                           teamName: Option[String] = None,
                           teamAbbreviation: Option[String] = None) extends ConvertedResultSetResponse

private[rrdinsights] case object InactivePlayersConverter extends ResultSetRawResponseConverter[InactivePlayers] {
  override val name: String = "InactivePlayers"

  override def convertRaw(rows: Array[Array[Any]]): Seq[InactivePlayers] =
    rows.map(row =>
      InactivePlayers(
        transformToInt(row(0)),
        transformToString(row(1)),
        transformToString(row(2)),
        transformToString(row(3)),
        transformToInt(row(4)),
        transformToString(row(5)),
        transformToString(row(6)),
        transformToString(row(7))))
}

final case class GameInfo(gameDate: Option[String] = None,
                    attendance: Option[Int] = None,
                    gameTime: Option[String] = None) extends ConvertedResultSetResponse

private[rrdinsights] case object GameInfoConverter extends ResultSetRawResponseConverter[GameInfo] {

  override val name: String = "GameInfo"

  override def convertRaw(rows: Array[Array[Any]]): Seq[GameInfo] =
    rows.map(row =>
      GameInfo(
        transformToString(row(0)),
        transformToInt(row(1)),
        transformToString(row(2))))
}

final case class ScoreLine(gameDateTimeEST: Option[String] = None,
                     gameSequence: Option[Int] = None,
                     gameId: Option[String] = None,
                     teamId: Option[Int] = None,
                     teamAbbreviation: Option[String] = None,
                     teamCityName: Option[String] = None,
                     teamNickName: Option[String] = None,
                     teamWinsLosses: Option[String] = None,
                     quarter1Points: Option[Int] = None,
                     quarter2Points: Option[Int] = None,
                     quarter3Points: Option[Int] = None,
                     quarter4Points: Option[Int] = None,
                     ot1Points: Option[Int] = None,
                     ot2Points: Option[Int] = None,
                     ot3Points: Option[Int] = None,
                     ot4Points: Option[Int] = None,
                     ot5Points: Option[Int] = None,
                     ot6Points: Option[Int] = None,
                     ot7Points: Option[Int] = None,
                     ot8Points: Option[Int] = None,
                     ot9Points: Option[Int] = None,
                     ot10Points: Option[Int] = None,
                     points: Option[Int] = None) extends ConvertedResultSetResponse

private[rrdinsights] case object ScoreLineConverter extends ResultSetRawResponseConverter[ScoreLine] {
  override val name: String = "LineScore"

  override def convertRaw(rows: Array[Array[Any]]): Seq[ScoreLine] =
    rows.map(row =>
      ScoreLine(
        transformToString(row(0)),
        transformToInt(row(1)),
        transformToString(row(2)),
        transformToInt(row(3)),
        transformToString(row(4)),
        transformToString(row(5)),
        transformToString(row(6)),
        transformToString(row(7)),
        transformToInt(row(8)),
        transformToInt(row(9)),
        transformToInt(row(10)),
        transformToInt(row(11)),
        transformToInt(row(12)),
        transformToInt(row(13)),
        transformToInt(row(14)),
        transformToInt(row(15)),
        transformToInt(row(16)),
        transformToInt(row(17)),
        transformToInt(row(18)),
        transformToInt(row(19)),
        transformToInt(row(20)),
        transformToInt(row(21)),
        transformToInt(row(22))))
}

final case class LastMeeting(currentGameId: Option[String] = None,
                       gameId: Option[String] = None,
                       gameDateTimeEST: Option[String] = None,
                       homeTeamId: Option[Int] = None,
                       homeTeamCity: Option[String] = None,
                       homeTeamName: Option[String] = None,
                       homeTeamAbbreviation: Option[String] = None,
                       homeTeamPoints: Option[Int] = None,
                       awayTeamId: Option[Int] = None,
                       awayTeamCity: Option[String] = None,
                       awayTeamName: Option[String] = None,
                       awayTeamCity1: Option[String] = None,
                       awayTeamPoints: Option[Int] = None) extends ConvertedResultSetResponse

private[rrdinsights] case object LastMeetingConverter extends ResultSetRawResponseConverter[LastMeeting] {
  override val name: String = "LastMeeting"

  override def convertRaw(rows: Array[Array[Any]]): Seq[LastMeeting] =
    rows.map(row =>
      LastMeeting(
        transformToString(row(0)),
        transformToString(row(1)),
        transformToString(row(2)),
        transformToInt(row(3)),
        transformToString(row(4)),
        transformToString(row(5)),
        transformToString(row(6)),
        transformToInt(row(7)),
        transformToInt(row(8)),
        transformToString(row(9)),
        transformToString(row(10)),
        transformToString(row(11)),
        transformToInt(row(12))))
}

final case class SeasonSeries(gameId: Option[String] = None,
                        homeTeamId: Option[Int] = None,
                        awayTeamId: Option[Int] = None,
                        gameDateTimeEST: Option[String] = None,
                        homeTeamWins: Option[Int] = None,
                        homeTeamLosses: Option[Int] = None,
                        seriesLeader: Option[String] = None) extends ConvertedResultSetResponse

private[rrdinsights] case object SeasonSeriesConverter extends ResultSetRawResponseConverter[SeasonSeries] {
  override val name: String = "SeasonSeries"

  override def convertRaw(rows: Array[Array[Any]]): Seq[SeasonSeries] =
    rows.map(row => SeasonSeries(
      transformToString(row(0)),
      transformToInt(row(1)),
      transformToInt(row(2)),
      transformToString(row(3)),
      transformToInt(row(4)),
      transformToInt(row(5)),
      transformToString(row(6))))
}

final case class AvailableVideo(gameId: Option[String] = None,
                          VIDEO_AVAILABLE_FLAG: Boolean,
                          PT_AVAILABLE: Boolean,
                          PT_XYZ_AVAILABLE: Boolean,
                          WH_STATUS: Boolean,
                          HUSTLE_STATUS: Boolean,
                          HISTORICAL_STATUS: Boolean) extends ConvertedResultSetResponse

private[rrdinsights] case object AvailableVideoConverter extends ResultSetRawResponseConverter[AvailableVideo] {
  override val name: String = "AvailableVideo"

  override def convertRaw(rows: Array[Array[Any]]): Seq[AvailableVideo] =
    rows.map(row =>
      AvailableVideo(
        transformToString(row(0)),
        transformToInt(row(1)).contains(1),
        transformToInt(row(2)).contains(1),
        transformToInt(row(3)).contains(1),
        transformToInt(row(4)).contains(1),
        transformToInt(row(5)).contains(1),
        transformToInt(row(6)).contains(1)))
}

final case class BoxScoreSummaryRawResponse(override val resource: String,
                                            override val resultSets: Array[ResultSetResponse])
  extends Response {

  def toBoxScoreSummaryResponse: BoxScoreSummaryResponse =
    BoxScoreSummaryResponse(resource, toBoxScoreSummary)

  def toBoxScoreSummary: BoxScoreSummary = BoxScoreSummaryRawResponse.toBoxScoreSummary(resultSets)
}

private[rrdinsights] object BoxScoreSummaryRawResponse extends ResultSetRawResponseConverters {

  private[rrdinsights] def findTeamScoreLine(scoreLines: Seq[ScoreLine], teamId: Option[Int]): Option[ScoreLine] =
    teamId.flatMap(v => scoreLines.find(_.teamId == v))

  private[rrdinsights] def findTeamOtherStats(otherStats: Seq[OtherStats], teamId: Option[Int]): Option[OtherStats] =
    teamId.flatMap(v => otherStats.find(_.teamId == v))

  private[rrdinsights] def toTeamStats(scoreLines: Seq[ScoreLine], otherStats: Seq[OtherStats], teamId: Option[Int]): TeamSummaryStats = {
    TeamSummaryStats(findTeamScoreLine(scoreLines, teamId), findTeamOtherStats(otherStats, teamId))
  }


  def toBoxScoreSummary(rawSummary: Array[ResultSetResponse]): BoxScoreSummary = {
    val gameSummary =  convert[GameSummary](rawSummary, GameSummaryConverter).headOption

    val scoreLines = convert[ScoreLine](rawSummary, ScoreLineConverter)
    val otherStats = convert[OtherStats](rawSummary, OtherStatsConverter)

    val homeTeamId = gameSummary.flatMap(_.homeTeamId)
    val awayTeamId = gameSummary.flatMap(_.awayTeamId)

    BoxScoreSummary(
      gameSummary,
      convert[GameInfo](rawSummary, GameInfoConverter).headOption,
      toTeamStats(scoreLines, otherStats, homeTeamId),
      toTeamStats(scoreLines, otherStats, awayTeamId),
      convert[Officials](rawSummary, OfficialsConverter),
      convert[InactivePlayers](rawSummary, InactivePlayersConverter),
      convert[LastMeeting](rawSummary, LastMeetingConverter).headOption,
      convert[SeasonSeries](rawSummary, SeasonSeriesConverter).headOption,
      convert[AvailableVideo](rawSummary, AvailableVideoConverter).headOption)
  }

  override protected val converters: Seq[ResultSetRawResponseConverter[_]] =
    Seq(GameSummaryConverter, GameInfoConverter, OtherStatsConverter, OfficialsConverter, InactivePlayersConverter,
      ScoreLineConverter, LastMeetingConverter, SeasonSeriesConverter, AvailableVideoConverter)
}

private[models] case class TeamSummaryStats(scoreLine: Option[ScoreLine], otherStats: Option[OtherStats])