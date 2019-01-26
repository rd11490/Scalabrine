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
                                          season: ParameterValue,
                                          seasonType: ParameterValue = SeasonTypeParameter.defaultParameterValue,
                                          startPeriod: ParameterValue = StartPeriodParameter.defaultParameterValue,
                                          endPeriod: ParameterValue = EndPeriodParameter.defaultParameterValue,
                                          startRange: ParameterValue = StartRangeParameter.defaultParameterValue,
                                          endRange: ParameterValue = EndRangeParameter.defaultParameterValue,
                                          rangeType: ParameterValue = RangeTypeParameter.defaultParameterValue) extends Endpoint {
  override val endpoint: String = "boxscoreadvancedv2"

  override val params: Seq[ParameterValue] = Seq(gameId, startPeriod, endPeriod, startRange, endRange, rangeType, season, seasonType)
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

final case class ScoreboardEndpoint(gameDate: ParameterValue,
                                    dayOffset: ParameterValue = DayOffsetParameter.defaultParameterValue,
                                    leagueId: ParameterValue = LeagueIdParameter.defaultParameterValue) extends Endpoint {

  override val endpoint: String = "scoreboardv2"

  override val params: Seq[ParameterValue] = Seq(gameDate, leagueId, dayOffset)
}

final case class PlayerBasicSplitsEndpoint(playerId: ParameterValue,
                                           season: ParameterValue,
                                           perMode: ParameterValue = PerModeParameter.PerPossession,
                                           plusMinis: ParameterValue = PlusMinusParameter.defaultParameterValue,
                                           paceAdjusted: ParameterValue = PaceAdjustParameter.defaultParameterValue,
                                           rank: ParameterValue = RankParameter.defaultParameterValue,
                                           leagueId: ParameterValue = LeagueIdParameter.defaultParameterValue,
                                           seasonType: ParameterValue = SeasonTypeParameter.defaultParameterValue,
                                           playOffRound: ParameterValue = PlayOffRoundParameter.defaultParameterValue,
                                           outcome: ParameterValue = OutcomeParameter.defaultParameterValue,
                                           location: ParameterValue = LocationParameter.defaultParameterValue,
                                           month: ParameterValue = MonthParameter.defaultParameterValue,
                                           seasonSegment: ParameterValue = SeasonSegmentParameter.defaultParameterValue,
                                           dateFrom: ParameterValue = DateFromParameter.defaultParameterValue,
                                           dateTo: ParameterValue = DateToParameter.defaultParameterValue,
                                           opponentTeamId: ParameterValue = OpponentTeamIdParameter.defaultParameterValue,
                                           vsConference: ParameterValue = VsConferenceParameter.defaultParameterValue,
                                           vsDivision: ParameterValue = VsDivisionParameter.defaultParameterValue,
                                           gameSegment: ParameterValue = GameSegmentParameter.defaultParameterValue,
                                           period: ParameterValue = PeriodParameter.defaultParameterValue,
                                           shotClockRange: ParameterValue = ShotClockRangeParameter.defaultParameterValue,
                                           lastNGames: ParameterValue = LastNGamesParameter.defaultParameterValue) extends Endpoint {
  override val endpoint: String = "playerdashboardbyyearoveryear"

  override val params: Seq[ParameterValue] = Seq(
    MeasureTypeParameter.Base,
    playerId,
    season,
    perMode,
    plusMinis,
    paceAdjusted,
    rank,
    leagueId,
    seasonType,
    playOffRound,
    outcome,
    location,
    month,
    seasonSegment,
    dateFrom,
    dateTo,
    opponentTeamId,
    vsConference,
    vsDivision,
    gameSegment,
    period,
    shotClockRange,
    lastNGames)

}

final case class PlayerProfileEndpoint(playerId: ParameterValue,
                                       perMode: ParameterValue = PerModeParameter.Totals,
                                       leagueId: ParameterValue = LeagueIdParameter.defaultParameterValue) extends Endpoint {

  override val endpoint: String = "playerprofilev2"

  override val params: Seq[ParameterValue] = Seq(playerId, leagueId, perMode)
}


///
/// Euro
///

/*
Adriatic League
http://www.aba-liga.com/live-match/rezultati-1718/create_shooting_chart.php?id=87&sez=17&lea=1
[{"ekipa":"2","koordinata_x":"83","koordinata_y":"68","koordinata_uspeh":"1","player_name":"Marko Ljubi\u010di\u0107"}]


 */

/*
Players
http://live.euroleague.net/api/Players?seasoncode=E2017&temp=E2017&equipo=IST&gamecode=1

Play By Play
http://livex/api/PlayByPlay?gamecode=3&seasoncode=E2017
{
    "Live": false,
    "TeamA": "CSKA Moscow",
    "TeamB": "AX Armani Exchange Olimpia Milan",
    "CodeTeamA": "CSK       ",
    "CodeTeamB": "MIL       ",
    "ActualQuarter": 4,
    "FirstQuarter": [
        {
            "TYPE": 0,
            "NUMBEROFPLAY": 2,
            "CODETEAM": "          ",
            "PLAYER_ID": "          ",
            "PLAYTYPE": "BP",
            "PLAYER": null,
            "TEAM": null,
            "DORSAL": null,
            "MINUTE": 1,
            "MARKERTIME": "",
            "POINTS_A": null,
            "POINTS_B": null,
            "COMMENT": "",
            "PLAYINFO": "Begin Period"
        }],
        "SecondQuarter": [],
        "ThirdQuarter": [],
        "FourthQuarter": [],
        "ExtraTime": []
        ""

 */
/*
Shots:

http://live.euroleague.net/api/Points?gamecode=3&seasoncode=E2017

[{
"NUM_ANOT": 562,
"TEAM": "CSK       ",
"ID_PLAYER": "PCVM      ",
"PLAYER": "RODRIGUEZ, SERGIO",
"ID_ACTION": "3FGA",
"ACTION": "Missed Three Pointer",
"POINTS": 0,
"COORD_X": 207,
"COORD_Y": 683,
"ZONE": "I",
"FASTBREAK": "0",
"SECOND_CHANCE": "0",
"POINTS_OFF_TURNOVER": "0",
"MINUTE": 40,
"CONSOLE": "00:57",
"POINTS_A": 89,
"POINTS_B": 82
}]
 */


///
/// NBA
///

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