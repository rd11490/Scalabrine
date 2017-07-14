package com.rrdinsights.models

import com.rrdinsights.models.Utils._
import java.{lang => jl}

final case class PlayByPlay(events: Seq[PlayByPlayEvent])

final case class PlayByPlayResponse(resource: String,
                                    playByPlay: PlayByPlay)

final case class PlayByPlayEvent(gameId: String,
                                 eventNumber: jl.Integer,
                                 eventMessageType: jl.Integer, // TODO - convert to case objects
                                 eventActionType: jl.Integer, // TODO - convert to case objects
                                 period: jl.Integer,
                                 wcTimeString: String, // TODO - find out what this means
                                 pcTimeString: String, // TODO - find out what this means
                                 homeDescription: String,
                                 neutralDescription: String,
                                 awayDescription: String,

                                 homeScore: jl.Integer,
                                 awayScore: jl.Integer,

                                 player1Type: jl.Integer, // TODO - find out what this means
                                 player1Id: jl.Integer,
                                 player1Name: String,
                                 player1TeamId: jl.Integer,
                                 player1TeamCity: String,
                                 player1TeamNickname: String,
                                 player1TeamAbbreviation: String,

                                 player2Type: jl.Integer, // TODO - find out what this means
                                 player2Id: jl.Integer,
                                 player2Name: String,
                                 player2TeamId: jl.Integer,
                                 player2TeamCity: String,
                                 player2TeamNickname: String,
                                 player2TeamAbbreviation: String,

                                 player3Type: jl.Integer, // TODO - find out what this means
                                 player3Id: jl.Integer,
                                 player3Name: String,
                                 player3TeamId: jl.Integer,
                                 player3TeamCity: String,
                                 player3TeamNickname: String,
                                 player3TeamAbbreviation: String) extends ConvertedResultSetResponse

private[rrdinsights] case object PlayByPlayEventConverter extends ResultSetRawResponseConverter[PlayByPlayEvent] {
  override val name: String = "PlayByPlay"

  override def convertRaw(rows: Array[Array[Any]]): Seq[PlayByPlayEvent] =
    rows.map(row => {
      val scores = convertScoreString(transformToString(row(10)))

      PlayByPlayEvent(
        transformToString(row(0)),
        transformToInt(row(1)),
        transformToInt(row(2)),
        transformToInt(row(3)),
        transformToInt(row(4)),
        transformToString(row(5)),
        transformToString(row(6)),
        transformToString(row(7)),
        transformToString(row(8)),
        transformToString(row(9)),

        scores._1, //score
        scores._2,

        transformToInt(row(12)),
        transformToInt(row(13)),
        transformToString(row(14)),
        transformToInt(row(15)),
        transformToString(row(16)),
        transformToString(row(17)),
        transformToString(row(18)),

        transformToInt(row(19)),
        transformToInt(row(20)),
        transformToString(row(21)),
        transformToInt(row(22)),
        transformToString(row(23)),
        transformToString(row(24)),
        transformToString(row(25)),

        transformToInt(row(26)),
        transformToInt(row(27)),
        transformToString(row(28)),
        transformToInt(row(29)),
        transformToString(row(30)),
        transformToString(row(31)),
        transformToString(row(32)))
    })


  private def convertScoreString(score: String): (jl.Integer, jl.Integer) = {
    val splitScore = Option(score).map(_.split("-"))
    (splitScore.map(v => Integer.valueOf(v(0).trim)).orNull, splitScore.map(v => Integer.valueOf(v(0).trim)).orNull)
  }
}

private case class Score(homeScore: jl.Integer, awayScore: jl.Integer)


final case class PlayByPlayRawResponse(override val resource: String,
                                             override val resultSets: Array[ResultSetResponse]) extends RawResponse {

  def toPlayByPlayResponse: PlayByPlayResponse =
    PlayByPlayResponse(resource, toPlayByPlay)

  def toPlayByPlay: PlayByPlay = PlayByPlayRawResponse.toPlayByPlay(resultSets)
}

private[rrdinsights] object PlayByPlayRawResponse extends ResultSetRawResponseConverters {


  def toPlayByPlay(rawSummary: Array[ResultSetResponse]): PlayByPlay =
    PlayByPlay(convert[PlayByPlayEvent](rawSummary, PlayByPlayEventConverter))

  override protected val converters: Seq[ResultSetRawResponseConverter[_]] =
    Seq(PlayByPlayEventConverter)
}