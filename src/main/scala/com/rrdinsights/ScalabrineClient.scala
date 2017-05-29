package com.rrdinsights

import com.rrdinsights.endpoints.Endpoint
import com.rrdinsights.models.{ParameterResponse, Response}
import com.rrdinsights.parameters.ParameterValue

/**
  * ScalabrineClient is a scala HTTP client for the stats.nba.com api
  */
object ScalabrineClient {

  private def get[P <: ParameterResponse](endpoint: Endpoint, params: Seq[ParameterValue]): Option[Response[P]] = {
    //endpoint.get()
    None
  }

}

/*
http://stats.nba.com/stats/teaminfocommon?LeagueID=00&Season=2016-17&SeasonType=Regular Season&TeamID=1610612738
base = http://stats.nba.com/stats/
endpoint = teaminfocommon

Parameters = LeagueID=00
 */