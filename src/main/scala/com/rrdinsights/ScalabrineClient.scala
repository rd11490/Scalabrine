package com.rrdinsights

import com.rrdinsights.endpoints.{AdvancedBoxScore, BoxScore, Endpoint}
import com.rrdinsights.models._
import com.rrdinsights.parameters.Headers
import com.rrdinsights.utils.Control._
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.impl.client.{CloseableHttpClient, HttpClientBuilder}
import org.json4s.DefaultFormats
import org.json4s.jackson.JsonMethods._

import scala.io.Source

/**
  * ScalabrineClient is a scala HTTP client for the stats.nba.com api
  */
object ScalabrineClient {
  implicit val formats = DefaultFormats

  private def get[E <: Endpoint](endpoint: E): CloseableHttpResponse = {
    using(HttpClientBuilder.create().setDefaultHeaders(Headers.headers).build()) { client =>
      endpoint.get(client)
    }
  }

  def getBoxScoreSummary(boxScore: BoxScore): BoxScoreSummaryResponse = {
    val resp = get[BoxScore](boxScore)
    parse(parseResponse(resp), useBigDecimalForDouble = true)
      .extract[BoxScoreSummaryRawResponse]
      .toBoxScoreSummaryResponse
  }

  def getAdvancedBoxScore(boxScore: AdvancedBoxScore): BoxScoreAdvancedResponse = {
    val resp = get[AdvancedBoxScore](boxScore)

    parse(parseResponse(resp), useBigDecimalForDouble = true)
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