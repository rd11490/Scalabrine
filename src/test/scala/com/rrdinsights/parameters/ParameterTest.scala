package com.rrdinsights.parameters

import com.rrdinsights.TestSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner


@RunWith(classOf[JUnitRunner])
class ParameterTest extends TestSpec {
  test("toUrl") {
    assert(LeagueIdParameter.NBA.toUrl === "LeagueID=00")
    assert(SeasonParameter.Season201617.toUrl === "Season=2016-17")
    assert(SeasonTypeParameter.RegularSeason.toUrl === "SeasonType=Regular Season")
    assert(TeamIdParameter.BostonCeltics.toUrl === "TeamID=1610612738")
  }
}
