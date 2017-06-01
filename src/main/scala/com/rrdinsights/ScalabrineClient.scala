package com.rrdinsights

import com.rrdinsights.endpoints.BoxScore
import com.rrdinsights.models.{BoxScoreSummaryRawResponse, BoxScoreSummaryResponse}
import com.rrdinsights.parameters.Headers
import com.rrdinsights.utils.Control._
import org.apache.http.impl.client.{CloseableHttpClient, HttpClientBuilder}
import org.json4s.DefaultFormats
import org.json4s.jackson.JsonMethods._

/**
  * ScalabrineClient is a scala HTTP client for the stats.nba.com api
  */
object ScalabrineClient {
  implicit val formats = DefaultFormats

  private val Client: CloseableHttpClient = HttpClientBuilder.create().setDefaultHeaders(Headers.headers).build()

  def getBoxScoreSummary(boxScore: BoxScore): BoxScoreSummaryResponse = {
    val resp = using(Client) { client =>
      BoxScore.parseResponse(boxScore.get(client))
    }

    parse(resp, useBigDecimalForDouble = true)
      .extract[BoxScoreSummaryRawResponse]
      .toBoxScoreSummaryResponse
  }
}

/*
http://stats.nba.com/stats/teaminfocommon?LeagueID=00&Season=2016-17&SeasonType=Regular Season&TeamID=1610612738
base = http://stats.nba.com/stats/
endpoint = teaminfocommon

Parameters = LeagueID=00
 */