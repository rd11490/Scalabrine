package com.rrdinsights.models

import com.rrdinsights.parameters.{GameIdParameter, ParameterValue}
import com.rrdinsights.models.Utils._
import java.{lang => jl}

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


final case class GameSummary(gameDate: String,
                             gameSequence: jl.Integer,
                             gameId: String,
                             gameStatusId: jl.Integer,
                             gameStatusText: String,
                             gameCode: String,
                             homeTeamId: jl.Integer,
                             awayTeamId: jl.Integer,
                             season: String,
                             livePeriod: jl.Integer,
                             livePCTime: String,
                             broadcaster: String,
                             livePeriodTimeBroadcast: String,
                             whStatus: jl.Integer) extends ConvertedResultSetResponse

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

final case class OtherStats(leagueId: String,
                            teamId: jl.Integer,
                            teamAbbreviation: String,
                            teamCity: String,
                            pointsInPaint: jl.Integer,
                            secondChancePoints: jl.Integer,
                            fastBreakPoints: jl.Integer,
                            largestLead: jl.Integer,
                            leadChanges: jl.Integer,
                            timesTied: jl.Integer,
                            teamTurnovers: jl.Integer,
                            totalTurnovers: jl.Integer,
                            teamRebounds: jl.Integer,
                            pointsOffTurnOvers: jl.Integer) extends ConvertedResultSetResponse

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

final case class Officials(officialId: jl.Integer,
                           firstName: String,
                           lastName: String,
                           number: String) extends ConvertedResultSetResponse

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

final case class InactivePlayers(playerId: jl.Integer,
                                 firstName: String,
                                 lastName: String,
                                 number: String,
                                 teamId: jl.Integer,
                                 teamCity: String,
                                 teamName: String,
                                 teamAbbreviation: String) extends ConvertedResultSetResponse

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

final case class GameInfo(gameDate: String,
                          attendance: jl.Integer,
                          gameTime: String) extends ConvertedResultSetResponse

private[rrdinsights] case object GameInfoConverter extends ResultSetRawResponseConverter[GameInfo] {

  override val name: String = "GameInfo"

  override def convertRaw(rows: Array[Array[Any]]): Seq[GameInfo] =
    rows.map(row =>
      GameInfo(
        transformToString(row(0)),
        transformToInt(row(1)),
        transformToString(row(2))))
}

final case class ScoreLine(gameDateTimeEST: String,
                           gameSequence: jl.Integer,
                           gameId: String,
                           teamId: jl.Integer,
                           teamAbbreviation: String,
                           teamCityName: String,
                           teamNickName: String,
                           teamWinsLosses: String,
                           quarter1Points: jl.Integer,
                           quarter2Points: jl.Integer,
                           quarter3Points: jl.Integer,
                           quarter4Points: jl.Integer,
                           ot1Points: jl.Integer,
                           ot2Points: jl.Integer,
                           ot3Points: jl.Integer,
                           ot4Points: jl.Integer,
                           ot5Points: jl.Integer,
                           ot6Points: jl.Integer,
                           ot7Points: jl.Integer,
                           ot8Points: jl.Integer,
                           ot9Points: jl.Integer,
                           ot10Points: jl.Integer,
                           points: jl.Integer) extends ConvertedResultSetResponse

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

final case class LastMeeting(currentGameId: String,
                             gameId: String,
                             gameDateTimeEST: String,
                             homeTeamId: jl.Integer,
                             homeTeamCity: String,
                             homeTeamName: String,
                             homeTeamAbbreviation: String,
                             homeTeamPoints: jl.Integer,
                             awayTeamId: jl.Integer,
                             awayTeamCity: String,
                             awayTeamName: String,
                             awayTeamCity1: String,
                             awayTeamPoints: jl.Integer) extends ConvertedResultSetResponse

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

final case class SeasonSeries(gameId: String,
                              homeTeamId: jl.Integer,
                              awayTeamId: jl.Integer,
                              gameDateTimeEST: String,
                              homeTeamWins: jl.Integer,
                              homeTeamLosses: jl.Integer,
                              seriesLeader: String) extends ConvertedResultSetResponse

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

final case class AvailableVideo(gameId: String,
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
        transformToInt(row(1)).equals(1),
        transformToInt(row(2)).equals(1),
        transformToInt(row(3)).equals(1),
        transformToInt(row(4)).equals(1),
        transformToInt(row(5)).equals(1),
        transformToInt(row(6)).equals(1)))
}

final case class BoxScoreSummaryRawResponse(override val resource: String,
                                            override val resultSets: Array[ResultSetResponse]) extends RawResponse {

  def toBoxScoreSummaryResponse: BoxScoreSummaryResponse =
    BoxScoreSummaryResponse(resource, toBoxScoreSummary)

  def toBoxScoreSummary: BoxScoreSummary = BoxScoreSummaryRawResponse.toBoxScoreSummary(resultSets)
}

private[rrdinsights] object BoxScoreSummaryRawResponse extends ResultSetRawResponseConverters {

  private[rrdinsights] def findTeamScoreLine(scoreLines: Seq[ScoreLine], teamId: Option[jl.Integer]): Option[ScoreLine] =
    scoreLines.find(_.teamId == teamId)

  private[rrdinsights] def findTeamOtherStats(otherStats: Seq[OtherStats], teamId: Option[jl.Integer]): Option[OtherStats] =
    otherStats.find(_.teamId == teamId)

  private[rrdinsights] def toTeamStats(scoreLines: Seq[ScoreLine], otherStats: Seq[OtherStats], teamId: Option[jl.Integer]): TeamSummaryStats = {
    TeamSummaryStats(findTeamScoreLine(scoreLines, teamId), findTeamOtherStats(otherStats, teamId))
  }


  def toBoxScoreSummary(rawSummary: Array[ResultSetResponse]): BoxScoreSummary = {
    val gameSummary = convert[GameSummary](rawSummary, GameSummaryConverter).headOption

    val scoreLines = convert[ScoreLine](rawSummary, ScoreLineConverter)
    val otherStats = convert[OtherStats](rawSummary, OtherStatsConverter)

    val homeTeamId = gameSummary.map(_.homeTeamId)
    val awayTeamId = gameSummary.map(_.awayTeamId)

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