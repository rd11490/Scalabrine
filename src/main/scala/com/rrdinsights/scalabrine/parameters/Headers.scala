package com.rrdinsights.scalabrine.parameters

import org.apache.http.message.BasicHeader

import scala.collection.JavaConverters._

object Headers {
  val AcceptEncoding: ParameterValue = Parameter.newParameterValue("Accept-Encoding", "gzip, deflate, sdch")
  val AcceptLanguage: ParameterValue = Parameter.newParameterValue("Accept-Languageg", "n-US,en;q=0.8")
  val UpgradeInsecureRequests: ParameterValue = Parameter.newParameterValue("Upgrade-Insecure-Requests", "1")
  val UserAgent: ParameterValue = Parameter.newParameterValue("User-Agent", "RRDInsights")
  val Accept: ParameterValue = Parameter.newParameterValue("Accept", "application/json")
  val CacheControl: ParameterValue = Parameter.newParameterValue("Cache-Control", "max-age=0")
  val Connection: ParameterValue = Parameter.newParameterValue("Connection", "keep-alive")

  val headers: java.util.List[BasicHeader] = Seq(
    AcceptEncoding,
    AcceptLanguage,
    UpgradeInsecureRequests,
    UserAgent,
    Accept,
    CacheControl,
    Connection)
    .map(v => new BasicHeader(v.name, v.value)).asJava
}