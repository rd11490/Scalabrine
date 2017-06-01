package com.rrdinsights.endpoints

import com.rrdinsights.parameters._
import org.apache.http.client.methods.{CloseableHttpResponse, HttpGet}
import org.apache.http.impl.client.CloseableHttpClient

import scala.io.Source

sealed trait Endpoint {

  def endpoint: String

  def params: Seq[ParameterValue]

  def url: String = Endpoint.BaseUrl + endpoint + "?" + params.map(_.toUrl).mkString("&")

  def get(client: CloseableHttpClient): CloseableHttpResponse = {
    val get = new HttpGet(url)
    client.execute(get)
  }
}

private[rrdinsights] object Endpoint {
  private val BaseUrl: String = "http://stats.nba.com/stats/"

  def parseResponse(response: CloseableHttpResponse): String = {
    val is = response.getEntity.getContent
    response.close()
    Source.fromInputStream(is).getLines().mkString(" ")
  }
}

//
// Team Info Commons
//
final case class TeamInfoCommon(leagueId: ParameterValue = LeagueIdParameter.defaultParameterValue,
                                season: ParameterValue = SeasonParameter.defaultParameterValue,
                                seasonType: ParameterValue = SeasonTypeParameter.defaultParameterValue) extends Endpoint {
  override val endpoint: String = "teaminfocommon"

  override val params: Seq[ParameterValue] = Seq(leagueId, season, seasonType)
}

private[rrdinsights] object TeamInfoCommon {
  def parseResponse(response: CloseableHttpResponse): String = {
    val respJson = Endpoint.parseResponse(response)
    respJson
  }
}

//
// Box Score Summary
//

final case class BoxScore(gameId: ParameterValue) extends Endpoint {
  override val endpoint: String = "boxscoresummaryv2"

  override def params: Seq[ParameterValue] = Seq(gameId)
}

private[rrdinsights] object BoxScore {
  def parseResponse(response: CloseableHttpResponse): String = {
    val is = response.getEntity.getContent
    Source.fromInputStream(is).getLines().mkString(" ")
  }
}

//
// Advanced Box Score
//
final case class AdvancedBoxScore(gameId: ParameterValue,
                                  startPeriod: ParameterValue = StartPeriodParameter.defaultParameterValue,
                                  endPeriod: ParameterValue = EndPeriodParameter.defaultParameterValue,
                                  startRange: ParameterValue = StartRangeParameter.defaultParameterValue,
                                  endRange: ParameterValue = EndRangeParameter.defaultParameterValue,
                                  rangeType: ParameterValue = RangeTypeParameter.defaultParameterValue) extends Endpoint {
  override val endpoint: String = "boxscoreadvancedv2"

  override val params: Seq[ParameterValue] = Seq(gameId, startPeriod, endPeriod, startRange, endRange, rangeType)
}

//
// Play by Play
//

final case class PlayByPlay(gameId: ParameterValue,
                            startPeriod: ParameterValue = StartPeriodParameter.defaultParameterValue,
                            endPeriod: ParameterValue = EndRangeParameter.defaultParameterValue) extends Endpoint {
  override val endpoint: String = "playByPlay"

  override val params: Seq[ParameterValue] = Seq(gameId, startPeriod, endPeriod)
}

/*
GET /data/10s/v2015/json/mobile_teams/nba/2016/scores/gamedetail/0041600223_gamedetail.json HTTP/1.1
Host: data.nba.com
Connection: keep-alive
Accept: application/json, text/plain,
Origin: http://stats.nba.com
User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36
Referer: http://stats.nba.com/game/
Accept-Encoding: gzip, deflate, sdch
Accept-Language: en-US,en;q=0.8
If-Modified-Since: Sun, 07 May 2017 03:16:16 GMT
*/

/*
GET /data/10s/v2015/json/mobile_teams/nba/2016/scores/pbp/0041600223_full_pbp.json HTTP/1.1
Host: data.nba.com
Connection: keep-alive
Accept: application/json, text/plain,
Origin: http://stats.nba.com
User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36
Referer: http://stats.nba.com/game/
Accept-Encoding: gzip, deflate, sdch
Accept-Language: en-US,en;q=0.8
If-Modified-Since: Sun, 07 May 2017 03:16:16 GMT
*/

/*
GET /stats/commonplayerinfo?LeagueID=00&PlayerID=201566&SeasonType=Regular+Season HTTP/1.1
Host: stats.nba.com
Connection: keep-alive
Accept: application/json, text/plain,
x-nba-stats-token: true
User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36
x-nba-stats-origin: stats
Referer: http://stats.nba.com/player/
Accept-Encoding: gzip, deflate, sdch
Accept-Language: en-US,en;q=0.8
Cookie: s_cc=true; s_fid=03B29E44EE8A4F10-355A5B7D5EFB9D1C; s_sq=nbag-n-league%3D%2526pid%253Dstats.nba.com%25253A%25252Fplayers%25252F%2526pidt%253D1%2526oid%253Dhttp%25253A%25252F%25252Fstats.nba.com%25252Fplayer%25252F%252523%252521%25252F201566%2526ot%253DA
*/

/*
GET /stats/playerdashboardbyyearoveryear?DateFrom=&DateTo=&GameSegment=&LastNGames=0&LeagueID=00&Location=&MeasureType=Base&Month=0&OpponentTeamID=0&Outcome=&PORound=0&PaceAdjust=N&PerMode=PerGame&Period=0&PlayerID=201566&PlusMinus=N&Rank=N&Season=2016-17&SeasonSegment=&SeasonType=Playoffs&ShotClockRange=&Split=yoy&VsConference=&VsDivision= HTTP/1.1
Host: stats.nba.com
Connection: keep-alive
Accept: application/json, text/plain,
x-nba-stats-token: true
User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36
x-nba-stats-origin: stats
Referer: http://stats.nba.com/player/
Accept-Encoding: gzip, deflate, sdch
Accept-Language: en-US,en;q=0.8
Cookie: s_cc=true; s_fid=03B29E44EE8A4F10-355A5B7D5EFB9D1C; s_sq=%5B%5BB%5D%5D
*/

/*
GET /stats/drafthistory?College=&LeagueID=00&OverallPick=&RoundNum=&RoundPick=&Season=2016&TeamID=0&TopX= HTTP/1.1
Host: stats.nba.com
Connection: keep-alive
Accept: application/json, text/plain,
x-nba-stats-token: true
User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36
x-nba-stats-origin: stats
Referer: http://stats.nba.com/draft/history/
Accept-Encoding: gzip, deflate, sdch
Accept-Language: en-US,en;q=0.8
Cookie: s_cc=true; s_fid=03B29E44EE8A4F10-355A5B7D5EFB9D1C; s_sq=nbag-n-league%3D%2526pid%253Dstats.nba.com%25253A%25252Flineups%25252Ftraditional%25252F%2526pidt%253D1%2526oid%253Dhttp%25253A%25252F%25252Fstats.nba.com%25252Fdraft%25252Fhistory%25252F%2526ot%253DA
*/

/*
GET /stats/leaguestandingsv3?LeagueID=00&Season=2016-17&SeasonType=Regular+Season HTTP/1.1
Host: stats.nba.com
Connection: keep-alive
Accept: application/json, text/plain,
x-nba-stats-token: true
User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36
x-nba-stats-origin: stats
Referer: http://stats.nba.com/standings/
Accept-Encoding: gzip, deflate, sdch
Accept-Language: en-US,en;q=0.8
Cookie: s_cc=true; s_fid=03B29E44EE8A4F10-355A5B7D5EFB9D1C; s_sq=nbag-n-league%3D%2526pid%253Dstats.nba.com%25253A%25252Fschedule%25252F%2526pidt%253D1%2526oid%253Dhttp%25253A%25252F%25252Fstats.nba.com%25252Fstandings%25252F%2526ot%253DA
*/

/*
GET /stats/teamdetails?teamID=1610612738 HTTP/1.1
Host: stats.nba.com
Connection: keep-alive
Accept: application/json, text/plain,
x-nba-stats-token: true
User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36
x-nba-stats-origin: stats
Referer: http://stats.nba.com/team/
Accept-Encoding: gzip, deflate, sdch
Accept-Language: en-US,en;q=0.8
Cookie: s_cc=true; s_fid=03B29E44EE8A4F10-355A5B7D5EFB9D1C; s_sq=%5B%5BB%5D%5D
*/

/*
GET /stats/commonteamroster?LeagueID=00&Season=2016-17&TeamID=1610612738 HTTP/1.1
Host: stats.nba.com
Connection: keep-alive
Accept: application/json, text/plain,
x-nba-stats-token: true
User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36
x-nba-stats-origin: stats
Referer: http://stats.nba.com/team/
Accept-Encoding: gzip, deflate, sdch
Accept-Language: en-US,en;q=0.8
Cookie: s_cc=true; s_fid=03B29E44EE8A4F10-355A5B7D5EFB9D1C; s_sq=%5B%5BB%5D%5D
*/

/*
GET /stats/teamplayeronoffsummary?DateFrom=&DateTo=&GameSegment=&LastNGames=0&LeagueID=00&Location=&MeasureType=Base&Month=0&OpponentTeamID=0&Outcome=&PaceAdjust=N&PerMode=Totals&Period=0&PlusMinus=N&Rank=N&Season=2016-17&SeasonSegment=&SeasonType=Playoffs&TeamID=1610612738&VsConference=&VsDivision= HTTP/1.1
Host: stats.nba.com
Connection: keep-alive
Accept: application/json, text/plain,
x-nba-stats-token: true
User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36
x-nba-stats-origin: stats
Referer: http://stats.nba.com/team/
Accept-Encoding: gzip, deflate, sdch
Accept-Language: en-US,en;q=0.8
Cookie: s_cc=true; s_fid=03B29E44EE8A4F10-355A5B7D5EFB9D1C; s_sq=%5B%5BB%5D%5D
*/

/*
GET /stats/teamdashboardbygeneralsplits?DateFrom=&DateTo=&GameSegment=&LastNGames=0&LeagueID=00&Location=&MeasureType=Four+Factors&Month=0&OpponentTeamID=0&Outcome=&PORound=0&PaceAdjust=N&PerMode=PerGame&Period=0&PlusMinus=N&Rank=N&Season=2016-17&SeasonSegment=&SeasonType=Playoffs&ShotClockRange=&Split=general&TeamID=1610612738&VsConference=&VsDivision= HTTP/1.1
Host: stats.nba.com
Connection: keep-alive
Accept: application/json, text/plain,
x-nba-stats-token: true
User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36
x-nba-stats-origin: stats
Referer: http://stats.nba.com/team/
Accept-Encoding: gzip, deflate, sdch
Accept-Language: en-US,en;q=0.8
Cookie: s_cc=true; s_fid=03B29E44EE8A4F10-355A5B7D5EFB9D1C; s_sq=nbag-n-league%3D%2526pid%253Dstats.nba.com%25253A%25252Fteam%25252F%2526pidt%253D1%2526oid%253Dhttp%25253A%25252F%25252Fstats.nba.com%25252Fteam%25252F%252523%252521%25252F1610612738%25252Ffour-factors%25252F%2526ot%253DA

*/

/*
GET /stats/teamdashboardbyshootingsplits?DateFrom=&DateTo=&GameSegment=&LastNGames=0&LeagueID=00&Location=&MeasureType=Base&Month=0&OpponentTeamID=0&Outcome=&PORound=0&PaceAdjust=N&PerMode=PerGame&Period=0&PlusMinus=N&Rank=N&Season=2016-17&SeasonSegment=&SeasonType=Playoffs&ShotClockRange=&TeamID=1610612738&VsConference=&VsDivision= HTTP/1.1
Host: stats.nba.com
Connection: keep-alive
Accept: application/json, text/plain,
x-nba-stats-token: true
User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36
x-nba-stats-origin: stats
Referer: http://stats.nba.com/team/
Accept-Encoding: gzip, deflate, sdch
Accept-Language: en-US,en;q=0.8
Cookie: s_cc=true; s_fid=03B29E44EE8A4F10-355A5B7D5EFB9D1C; s_sq=%5B%5BB%5D%5D
*/

/*
GET /stats/teamdashlineups?DateFrom=&DateTo=&GameID=&GameSegment=&GroupQuantity=5&LastNGames=0&LeagueID=00&Location=&MeasureType=Base&Month=0&OpponentTeamID=0&Outcome=&PORound=0&PaceAdjust=N&PerMode=PerGame&Period=0&PlusMinus=N&Rank=N&Season=2016-17&SeasonSegment=&SeasonType=Playoffs&TeamID=1610612738&VsConference=&VsDivision= HTTP/1.1
Host: stats.nba.com
Connection: keep-alive
Accept: application/json, text/plain,
x-nba-stats-token: true
User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36
x-nba-stats-origin: stats
Referer: http://stats.nba.com/team/
Accept-Encoding: gzip, deflate, sdch
Accept-Language: en-US,en;q=0.8
Cookie: s_cc=true; s_fid=03B29E44EE8A4F10-355A5B7D5EFB9D1C; s_sq=nbag-n-league%3D%2526pid%253Dstats.nba.com%25253A%25252Fteam%25252F%2526pidt%253D1%2526oid%253Dhttp%25253A%25252F%25252Fstats.nba.com%25252Fteam%25252F%252523%252521%25252F1610612738%25252Flineups-traditional%25252F%2526ot%253DA
*/

/*
GET /stats/teamplayerdashboard?DateFrom=&DateTo=&GameSegment=&LastNGames=0&LeagueID=00&Location=&MeasureType=Base&Month=0&OpponentTeamID=0&Outcome=&PORound=0&PaceAdjust=N&PerMode=PerGame&Period=0&PlusMinus=N&Rank=N&Season=2016-17&SeasonSegment=&SeasonType=Playoffs&TeamId=1610612738&VsConference=&VsDivision= HTTP/1.1
Host: stats.nba.com
Connection: keep-alive
Accept: application/json, text/plain,
x-nba-stats-token: true
User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36
x-nba-stats-origin: stats
Referer: http://stats.nba.com/team/
Accept-Encoding: gzip, deflate, sdch
Accept-Language: en-US,en;q=0.8
Cookie: s_cc=true; s_fid=03B29E44EE8A4F10-355A5B7D5EFB9D1C; s_sq=nbag-n-league%3D%2526pid%253Dstats.nba.com%25253A%25252Fteam%25252F%2526pidt%253D1%2526oid%253Dhttp%25253A%25252F%25252Fstats.nba.com%25252Fteam%25252F%252523%252521%25252F1610612738%25252Fplayers-traditional%25252F%2526ot%253DA
*/

/*
GET /stats/teamgamelog?DateFrom=&DateTo=&LeagueID=00&Season=2016-17&SeasonType=Playoffs&TeamID=1610612738 HTTP/1.1
Host: stats.nba.com
Connection: keep-alive
Accept: application/json, text/plain,
x-nba-stats-token: true
User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36
x-nba-stats-origin: stats
Referer: http://stats.nba.com/team/
Accept-Encoding: gzip, deflate, sdch
Accept-Language: en-US,en;q=0.8
Cookie: s_cc=true; s_fid=03B29E44EE8A4F10-355A5B7D5EFB9D1C; s_sq=nbag-n-league%3D%2526pid%253Dstats.nba.com%25253A%25252Fteam%25252F%2526pidt%253D1%2526oid%253Dhttp%25253A%25252F%25252Fstats.nba.com%25252Fteam%25252F%252523%252521%25252F1610612738%25252Fboxscores%2526ot%253DA


*/

/*
GET /stats/teamgamelogs?DateFrom=&DateTo=&GameSegment=&LastNGames=0&LeagueID=00&Location=&MeasureType=Base&Month=0&OpponentTeamID=0&Outcome=&PORound=0&PaceAdjust=N&PerMode=Totals&Period=0&PlusMinus=N&Rank=N&Season=2016-17&SeasonSegment=&SeasonType=Playoffs&ShotClockRange=&TeamID=1610612738&VsConference=&VsDivision= HTTP/1.1
Host: stats.nba.com
Connection: keep-alive
Accept: application/json, text/plain,
x-nba-stats-token: true
User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36
x-nba-stats-origin: stats
Referer: http://stats.nba.com/team/
Accept-Encoding: gzip, deflate, sdch
Accept-Language: en-US,en;q=0.8
Cookie: s_cc=true; s_fid=03B29E44EE8A4F10-355A5B7D5EFB9D1C; s_sq=%5B%5BB%5D%5D

*/

/*
GET /stats/teamdashptshots?DateFrom=&DateTo=&GameSegment=&LastNGames=0&LeagueID=00&Location=&MeasureType=Base&Month=0&OpponentTeamID=0&Outcome=&PaceAdjust=N&PerMode=PerGame&Period=0&PlusMinus=N&Rank=N&Season=2016-17&SeasonSegment=&SeasonType=Playoffs&TeamID=1610612738&VsConference=&VsDivision= HTTP/1.1
Host: stats.nba.com
Connection: keep-alive
Accept: application/json, text/plain,
x-nba-stats-token: true
User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36
x-nba-stats-origin: stats
Referer: http://stats.nba.com/team/
Accept-Encoding: gzip, deflate, sdch
Accept-Language: en-US,en;q=0.8
Cookie: s_cc=true; s_fid=03B29E44EE8A4F10-355A5B7D5EFB9D1C; s_sq=nbag-n-league%3D%2526pid%253Dstats.nba.com%25253A%25252Fteam%25252F%2526pidt%253D1%2526oid%253Dhttp%25253A%25252F%25252Fstats.nba.com%25252Fteam%25252F%252523%252521%25252F1610612738%25252Fshots-dash%2526ot%253DA
*/

/*
GET /stats/boxscoretraditionalv2?EndPeriod=10&EndRange=28800&GameID=0021601219&RangeType=0&Season=2016-17&SeasonType=Regular+Season&StartPeriod=1&StartRange=0 HTTP/1.1
Host: stats.nba.com
Connection: keep-alive
Accept: application/json, text/plain,
x-nba-stats-token: true
User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36
x-nba-stats-origin: stats
Referer: http://stats.nba.com/game/
Accept-Encoding: gzip, deflate, sdch
Accept-Language: en-US,en;q=0.8
Cookie: s_cc=true; s_fid=03B29E44EE8A4F10-355A5B7D5EFB9D1C; s_sq=%5B%5BB%5D%5D
*/

/*
GET /stats/leaguedashplayerstats?College=&Conference=&Country=&DateFrom=&DateTo=&Division=&DraftPick=&DraftYear=&GameScope=&GameSegment=&Height=&LastNGames=0&LeagueID=00&Location=&MeasureType=Base&Month=0&OpponentTeamID=0&Outcome=&PORound=0&PaceAdjust=N&PerMode=PerGame&Period=0&PlayerExperience=&PlayerPosition=&PlusMinus=N&Rank=N&Season=2016-17&SeasonSegment=&SeasonType=Playoffs&ShotClockRange=&StarterBench=&TeamID=0&VsConference=&VsDivision=&Weight= HTTP/1.1
Host: stats.nba.com
Connection: keep-alive
Accept: application/json, text/plain,
x-nba-stats-token: true
User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36
x-nba-stats-origin: stats
Referer: http://stats.nba.com/game/
Accept-Encoding: gzip, deflate, sdch
Accept-Language: en-US,en;q=0.8
Cookie: s_cc=true; s_fid=03B29E44EE8A4F10-355A5B7D5EFB9D1C; s_sq=%5B%5BB%5D%5D
*/

/*
GET /stats/scoreboardV2?DayOffset=0&LeagueID=00&gameDate=04%2F12%2F2017 HTTP/1.1
Host: stats.nba.com
Connection: keep-alive
Accept: application/json, text/plain,
x-nba-stats-token: true
User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36
x-nba-stats-origin: stats
Referer: http://stats.nba.com/game/
Accept-Encoding: gzip, deflate, sdch
Accept-Language: en-US,en;q=0.8
Cookie: s_cc=true; s_fid=03B29E44EE8A4F10-355A5B7D5EFB9D1C; s_sq=%5B%5BB%5D%5D
*/

/*
GET /stats/playbyplayv2?EndPeriod=10&EndRange=55800&GameID=0021601219&RangeType=2&Season=2016-17&SeasonType=Regular+Season&StartPeriod=1&StartRange=0 HTTP/1.1
Host: stats.nba.com
Connection: keep-alive
Accept: application/json, text/plain,
x-nba-stats-token: true
User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36
x-nba-stats-origin: stats
Referer: http://stats.nba.com/game/
Accept-Encoding: gzip, deflate, sdch
Accept-Language: en-US,en;q=0.8
Cookie: s_cc=true; s_fid=03B29E44EE8A4F10-355A5B7D5EFB9D1C; s_sq=%5B%5BB%5D%5D

*/

/*
GET /stats/winprobabilitypbp?GameID=0021601219&RunType=each+second HTTP/1.1
Host: stats.nba.com
Connection: keep-alive
Accept: application/json, text/plain,
x-nba-stats-token: true
User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36
x-nba-stats-origin: stats
Referer: http://stats.nba.com/game/
Accept-Encoding: gzip, deflate, sdch
Accept-Language: en-US,en;q=0.8
Cookie: s_cc=true; s_fid=03B29E44EE8A4F10-355A5B7D5EFB9D1C; s_sq=nbag-n-league%3D%2526pid%253Dstats.nba.com%25253A%25252Fgame%25252F%2526pidt%253D1%2526oid%253Dhttp%25253A%25252F%25252Fstats.nba.com%25252Fgame%25252F%252523%252521%25252F0021601219%25252Fcharts%25252F%2526ot%253DA
*/