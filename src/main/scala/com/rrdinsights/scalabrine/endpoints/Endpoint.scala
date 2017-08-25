package com.rrdinsights.scalabrine.endpoints

import com.rrdinsights.scalabrine.parameters._
import org.apache.http.client.methods.{CloseableHttpResponse, HttpGet}
import org.apache.http.impl.client.CloseableHttpClient

import scala.io.Source

sealed trait Endpoint {

  def endpoint: String

  def params: Seq[ParameterValue]

  def url: String = Endpoint.BaseUrl + endpoint + "?" + params.map(_.toUrl).mkString("&")

  def get(client: CloseableHttpClient): CloseableHttpResponse = {
    try {
      val get = new HttpGet(url)
      client.execute(get)
    } catch {
      case ex: Throwable => {
        println(ex)
        throw ex
      }
    }

  }
}

private[rrdinsights] object Endpoint {
  private val BaseUrl: String = "http://stats.nba.com/stats/"
}

//
// Team Info Commons
//
final case class TeamInfoCommonEndpoint(leagueId: ParameterValue = LeagueIdParameter.defaultParameterValue,
                                        season: ParameterValue = SeasonParameter.defaultParameterValue,
                                        seasonType: ParameterValue = SeasonTypeParameter.defaultParameterValue) extends Endpoint {
  override val endpoint: String = "teaminfocommon"

  override val params: Seq[ParameterValue] = Seq(leagueId, season, seasonType)
}

//
// Box Score Summary
//

final case class BoxScoreEndpoint(gameId: ParameterValue) extends Endpoint {
  override val endpoint: String = "boxscoresummaryv2"

  override def params: Seq[ParameterValue] = Seq(gameId)
}

//
// Advanced Box Score
//
final case class AdvancedBoxScoreEndpoint(gameId: ParameterValue,
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

final case class PlayByPlayEndpoint(gameId: ParameterValue,
                                    startPeriod: ParameterValue = StartPeriodParameter.defaultParameterValue,
                                    endPeriod: ParameterValue = EndPeriodParameter.defaultParameterValue) extends Endpoint {
  override val endpoint: String = "playByPlayv2"

  override val params: Seq[ParameterValue] = Seq(gameId, startPeriod, endPeriod)
}

final case class TeamGameLogEndpoint(teamId: ParameterValue,
                                     season: ParameterValue,
                                     seasonTypeParameter: ParameterValue = SeasonTypeParameter.defaultParameterValue,
                                     leagueID: ParameterValue = LeagueIdParameter.defaultParameterValue,
                                     dateFrom: ParameterValue = DateFromParameter.defaultParameterValue,
                                     dateTo: ParameterValue = DateToParameter.defaultParameterValue) extends Endpoint {

  override val endpoint: String = "teamgamelog"

  override val params: Seq[ParameterValue] = Seq(teamId, seasonTypeParameter, season, leagueID, dateFrom, dateTo)
}

final case class CommonPlayerInfoEndpoint(playerId: ParameterValue,
                                          leagueId: ParameterValue = LeagueIdParameter.defaultParameterValue,
                                          seasonType: ParameterValue = SeasonTypeParameter.defaultParameterValue) extends Endpoint {

  override val endpoint: String = "commonplayerinfo"

  override val params: Seq[ParameterValue] = Seq(playerId, leagueId, seasonType)
}

final case class ShotChartDetailEndpoint(playerId: ParameterValue,
                                         leagueId: ParameterValue = LeagueIdParameter.defaultParameterValue,
                                         teamId: ParameterValue = TeamIdParameter.defaultParameterValue,
                                         gameId: ParameterValue = GameIdParameter.defaultParameterValue,
                                         season: ParameterValue = SeasonParameter.defaultParameterValue,
                                         seasonType: ParameterValue = SeasonTypeParameter.defaultParameterValue,
                                         seasonSegment: ParameterValue = SeasonSegmentParameter.defaultParameterValue,
                                         contextMeasure: ParameterValue = ContextMeasureParameter.defaultParameterValue,
                                         contextFilter: ParameterValue = ContextFilterParameter.defaultParameterValue,
                                         position: ParameterValue = PositionParameter.defaultParameterValue,
                                         playerPosition: ParameterValue = PlayerPositionParameter.defaultParameterValue,
                                         location: ParameterValue = LocationParameter.defaultParameterValue,
                                         period: ParameterValue = PeriodParameter.defaultParameterValue,
                                         vsConference: ParameterValue = VsConferenceParameter.defaultParameterValue,
                                         vsDivision: ParameterValue = VsDivisionParameter.defaultParameterValue,
                                         lastNGames: ParameterValue = LastNGamesParameter.defaultParameterValue,
                                         outcome: ParameterValue = OutcomeParameter.defaultParameterValue,
                                         dateFrom: ParameterValue = DateFromParameter.defaultParameterValue,
                                         dateTo: ParameterValue = DateToParameter.defaultParameterValue,
                                         startPeriod: ParameterValue = StartPeriodParameter.defaultParameterValue,
                                         endPeriod: ParameterValue = EndRangeParameter.defaultParameterValue,
                                         startRange: ParameterValue = StartRangeParameter.defaultParameterValue,
                                         endRange: ParameterValue = EndRangeParameter.defaultParameterValue,
                                         rangeType: ParameterValue = EndRangeParameter.defaultParameterValue,
                                         opponentTeamId: ParameterValue = OpponentTeamIdParameter.defaultParameterValue,
                                         aheadBehind: ParameterValue = AheadBehindParameter.defaultParameterValue,
                                         pointDiff: ParameterValue = PointDiffParameter.defaultParameterValue,
                                         rookieYear: ParameterValue = RookieYearParameter.defaultParameterValue,
                                         gameSegment: ParameterValue = GameSegmentParameter.defaultParameterValue,
                                         month: ParameterValue = MonthParameter.defaultParameterValue,
                                         clutchTime: ParameterValue = ClutchTimeParameter.defaultParameterValue) extends Endpoint {

  override val endpoint: String = "shotchartdetail"

  override val params: Seq[ParameterValue] =
    Seq(playerId, teamId, leagueId, gameId, season, seasonSegment, seasonType,
      contextFilter, contextMeasure, position, playerPosition, location, period, endPeriod, startPeriod, startRange,
      endRange, dateFrom, dateTo, vsConference, vsDivision, lastNGames, outcome, rangeType, opponentTeamId, aheadBehind,
      pointDiff, rookieYear, gameSegment, month, clutchTime)
}

final case class CommonTeamRosterEndpoint(teamId: ParameterValue,
                                  season: ParameterValue,
                                  leagueId: ParameterValue = LeagueIdParameter.defaultParameterValue) extends Endpoint {

  override val endpoint: String = "commonteamroster"

  override val params: Seq[ParameterValue] = Seq(teamId, leagueId, season)
}


/*
http://stats.nba.com/stats/shotchartdetail?Period=0&VsConference=&LeagueID=00&LastNGames=0&TeamID=0&Position=&Location=&Outcome=&ContextMeasure=FGA&DateFrom=&StartPeriod=&DateTo=&OpponentTeamID=0&ContextFilter=&RangeType=&Season=&AheadBehind=&PlayerID=201935&EndRange=&VsDivision=&PointDiff=&RookieYear=&GameSegment=&Month=0&ClutchTime=&StartRange=&EndPeriod=&SeasonType=Regular+Season&SeasonSegment=&GameID=&PlayerPosition=
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