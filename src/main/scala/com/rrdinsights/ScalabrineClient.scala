package com.rrdinsights

import com.rrdinsights.endpoints.{AdvancedBoxScore, BoxScore, Endpoint}
import com.rrdinsights.models._
import com.rrdinsights.parameters.Headers
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.impl.client.HttpClientBuilder
import org.json4s.{DefaultFormats, JValue}
import org.json4s.jackson.JsonMethods._
import com.rrdinsights.utils.Control._

import scala.io.Source

/**
  * ScalabrineClient is a scala HTTP client for the stats.nba.com api
  */
object ScalabrineClient {
  implicit val formats = DefaultFormats

  private def get[E <: Endpoint](endpoint: E): JValue = {
    using(HttpClientBuilder.create().setDefaultHeaders(Headers.headers).build()) { client =>
      val resp = endpoint.get(client)
      val parsed = parse(parseResponse(resp), useBigDecimalForDouble = true)
      resp.close()
      parsed
    }
  }


  def getBoxScoreSummary(boxScore: BoxScore): BoxScoreSummaryResponse = {
    get[BoxScore](boxScore)
      .extract[BoxScoreSummaryRawResponse]
      .toBoxScoreSummaryResponse
  }

  def getAdvancedBoxScore(boxScore: AdvancedBoxScore): BoxScoreAdvancedResponse = {
    get[AdvancedBoxScore](boxScore)
      .extract[BoxScoreAdvancedRawResponse]
      .toBoxScoreAdvancedResponse
  }


  private def parseResponse(response: CloseableHttpResponse): String = {
    val is = response.getEntity.getContent
    Source.fromInputStream(is).getLines().mkString(" ")
  }


}

/*
http://stats.nba.com/stats/teaminfocommon?LeagueID=00&Season=2016-17&SeasonType=Regular Season&TeamID=1610612738
base = http://stats.nba.com/stats/
endpoint = teaminfocommon

Parameters = LeagueID=00
 */