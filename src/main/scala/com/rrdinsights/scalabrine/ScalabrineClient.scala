package com.rrdinsights.scalabrine

import com.rrdinsights.scalabrine.endpoints._
import com.rrdinsights.scalabrine.models._
import com.rrdinsights.scalabrine.parameters.Headers
import com.rrdinsights.scalabrine.utils.Control._
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.impl.client.HttpClientBuilder
import org.json4s.jackson.JsonMethods._
import org.json4s.{DefaultFormats, JValue}

import scala.io.Source

/**
  * ScalabrineClient is a scala HTTP client for the stats.nba.com api
  */
object ScalabrineClient {
  implicit val formats: DefaultFormats.type = DefaultFormats

  private def get[E <: Endpoint](endpoint: E): JValue = {
    val httpParams = RequestConfig.custom().
      setConnectionRequestTimeout(20000)
      .setSocketTimeout(20000)
      .setConnectTimeout(20000)
      .build()

    val httpClient = HttpClientBuilder.create()
      .setDefaultHeaders(Headers.headers)
      .setDefaultRequestConfig(httpParams)
      .build()

    using(httpClient) { client =>
      val resp = endpoint.get(client)
      val parsedResp = parseResponse(resp)
      val parsed = try {
        parse(parsedResp, useBigDecimalForDouble = true)
      } catch {
        case e: Throwable =>
          println(endpoint.url)
          println(parsedResp)
          println(e)
          throw e
      }
      resp.close()
      parsed
    }
  }

  def getBoxScoreSummary(boxScore: BoxScoreEndpoint): BoxScoreSummaryResponse =
    get[BoxScoreEndpoint](boxScore)
      .extract[BoxScoreSummaryRawResponse]
      .toBoxScoreSummaryResponse

  def getAdvancedBoxScore(boxScore: AdvancedBoxScoreEndpoint): BoxScoreAdvancedResponse = {
    get[AdvancedBoxScoreEndpoint](boxScore)
      .extract[BoxScoreAdvancedRawResponse]
      .toBoxScoreAdvancedResponse
  }

  def getPlayByPlay(playByPlay: PlayByPlayEndpoint): PlayByPlayResponse =
    get[PlayByPlayEndpoint](playByPlay)
      .extract[PlayByPlayRawResponse]
      .toPlayByPlayResponse

  def getTeamGameLog(teamGameLog: TeamGameLogEndpoint): TeamGameLogResponse =
    get[TeamGameLogEndpoint](teamGameLog)
      .extract[TeamGameLogRawResponse]
      .toTeamGameLogResponse

  def getCommonPlayerInfo(commonPlayerInfo: CommonPlayerInfoEndpoint): CommonPlayerInfoResponse =
    get[CommonPlayerInfoEndpoint](commonPlayerInfo)
      .extract[CommonPlayerInfoRawResponse]
      .toCommonPlayerInfoResponse

  def getCommonTeamRoster(commonTeamRoster: CommonTeamRosterEndpoint): CommonTeamRosterResponse =
    get[CommonTeamRosterEndpoint](commonTeamRoster)
      .extract[CommonTeamRosterRawResponse]
      .toCommonTeamRosterResponse

  def getScoreboard(scoreboard: ScoreboardEndpoint): ScoreboardResponse =
    get[ScoreboardEndpoint](scoreboard)
      .extract[ScoreboardRawResponse]
      .toScoreboardResponse

  def getShotChart(shotchart: ShotChartDetailEndpoint): ShotChartDetailsResponse =
    get[ShotChartDetailEndpoint](shotchart)
      .extract[ShotChartDetailsRawResponse]
      .toShotChartDetailsResponse


  private def parseResponse(response: CloseableHttpResponse): String = {
    val is = response.getEntity.getContent
    Source.fromInputStream(is).getLines().mkString(" ")
  }

}