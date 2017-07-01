package com.rrdinsights.models

import com.rrdinsights.parameters._
import com.rrdinsights.models.Utils._

final case class PlayByPlay(events: Seq[PlayByPlayEvent]) {

}

final case class PlayByPlayResponse(resource: String,
                                    playByPlay: PlayByPlay)

final case class PlayByPlayEvent(gameId: Option[String],
                                 eventNumber: Option[Int],
                                 eventMessageType: Option[Int], // TODO - convert to case objects
                                 eventActionType: Option[Int], // TODO - convert to case objects
                                 period: Option[Int],
                                 wcTimeString: Option[String], // TODO - find out what this means
                                 pcTimeString: Option[String], // TODO - find out what this means
                                 homeDescription: Option[String],
                                 neutralDescription: Option[String],
                                 awayDescription: Option[String],

                                 homeScore: Option[Int],
                                 awayScore: Option[Int],

                                 player1Type: Option[Int], // TODO - find out what this means
                                 player1Id: Option[Int],
                                 player1Name: Option[String],
                                 player1TeamId: Option[Int],
                                 player1TeamCity: Option[String],
                                 player1TeamNickname: Option[String],
                                 player1TeamAbbreviation: Option[String],

                                 player2Type: Option[Int], // TODO - find out what this means
                                 player2Id: Option[Int],
                                 player2Name: Option[String],
                                 player2TeamId: Option[Int],
                                 player2TeamCity: Option[String],
                                 player2TeamNickname: Option[String],
                                 player2TeamAbbreviation: Option[String],

                                 player3Type: Option[Int], // TODO - find out what this means
                                 player3Id: Option[Int],
                                 player3Name: Option[String],
                                 player3TeamId: Option[Int],
                                 player3TeamCity: Option[String],
                                 player3TeamNickname: Option[String],
                                 player3TeamAbbreviation: Option[String]) extends ConvertedResultSetResponse

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


  private def convertScoreString(score: Option[String]): (Option[Int], Option[Int]) = {
    val splitScore = score.map(_.split("-"))
    (splitScore.map(v => v(0).trim.toInt), splitScore.map(v => v(1).trim.toInt))
  }
}

private case class Score(homeScore: Option[Int], awayScore: Option[Int])


final case class PlayByPlayRawResponse(override val resource: String,
                                             override val resultSets: Array[ResultSetResponse])
  extends Response {

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