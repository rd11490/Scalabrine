package com.rrdinsights.scalabrine.parameters

import java.net.URLEncoder._

sealed trait Parameter {
  def parameterName: String

  def newParameterValue(value: String): ParameterValue =
    Parameter.newParameterValue(parameterName, value)

  def defaultParameterValue: ParameterValue = newParameterValue("")
}

sealed trait YesNoParameter extends Parameter {
  def Yes: ParameterValue = newParameterValue("Y")

  def No: ParameterValue = newParameterValue("N")

  override def defaultParameterValue: ParameterValue = No
}

sealed trait NumericParameter extends Parameter {
  override def defaultParameterValue: ParameterValue = newParameterValue("0")
}

object Parameter {
  def newParameterValue(name: String, value: String): ParameterValue =
    ParameterValue(name, value)
}

final case class ParameterValue(name: String, value: String) {
  def toUrl: String =
    s"${encodeValue(name)}=${encodeValue(value)}"

  def encodeValue(str: String): String = encode(str, "utf-8")
}

object LeagueIdParameter extends Parameter {
  override val parameterName: String = "LeagueID"

  val NBA: ParameterValue = newParameterValue("00")
  val DLeague: ParameterValue = newParameterValue("20")

  override val defaultParameterValue: ParameterValue = NBA
}

object SeasonParameter extends Parameter {
  override val parameterName: String = "Season"

  val Season201617: ParameterValue = newParameterValue("2016-17")
  val Season201516: ParameterValue = newParameterValue("2015-16")
  val Season201415: ParameterValue = newParameterValue("2014-15")
  val Season201314: ParameterValue = newParameterValue("2013-14")
  val Season201213: ParameterValue = newParameterValue("2012-13")
  val Season201112: ParameterValue = newParameterValue("2011-12")
}

object SeasonTypeParameter extends Parameter {
  override val parameterName: String = "SeasonType"

  val RegularSeason: ParameterValue = newParameterValue("Regular Season")
  val Playoffs: ParameterValue = newParameterValue("Playoffs")
  val PreSeason: ParameterValue = newParameterValue("Preseason")
  val Pre_Season: ParameterValue = newParameterValue("Pre Season")
  val AllStar: ParameterValue = newParameterValue("All-Star")
  val All_Star: ParameterValue = newParameterValue("All Star")

  override val defaultParameterValue: ParameterValue = RegularSeason
}

object TeamIdParameter extends NumericParameter {
  override val parameterName: String = "TeamID"

  val AtlantaHawks: ParameterValue = newParameterValue("1610612737")
  val BostonCeltics: ParameterValue = newParameterValue("1610612738")
  val BrooklynNets: ParameterValue = newParameterValue("1610612751")
  val CharlotteHornets: ParameterValue = newParameterValue("1610612766")
  val ChicagoBulls: ParameterValue = newParameterValue("1610612741")
  val ClevelandCavaliers: ParameterValue = newParameterValue("1610612739")
  val DallasMavericks: ParameterValue = newParameterValue("1610612742")
  val DenverNuggets: ParameterValue = newParameterValue("1610612743")
  val DetroitPistons: ParameterValue = newParameterValue("1610612765")
  val GoldenStateWarriors: ParameterValue = newParameterValue("1610612744")
  val HoustonRockets: ParameterValue = newParameterValue("1610612745")
  val IndianaPacers: ParameterValue = newParameterValue("1610612754")
  val LosAngelesClippers: ParameterValue = newParameterValue("1610612746")
  val LosAngelesLakers: ParameterValue = newParameterValue("1610612747")
  val MemphisGrizzlies: ParameterValue = newParameterValue("1610612763")
  val MiamiHeat: ParameterValue = newParameterValue("1610612748")
  val MilwaukeeBucks: ParameterValue = newParameterValue("1610612749")
  val MinnesotaTimberwolves: ParameterValue = newParameterValue("1610612750")
  val NewOrleansPelicans: ParameterValue = newParameterValue("1610612740")
  val NewYorkKnicks: ParameterValue = newParameterValue("1610612752")
  val OklahomaCityThunder: ParameterValue = newParameterValue("1610612760")
  val OrlandoMagic: ParameterValue = newParameterValue("1610612753")
  val Philadelphia76ers: ParameterValue = newParameterValue("1610612755")
  val PhoenixSuns: ParameterValue = newParameterValue("1610612756")
  val PortlandTrailBlazers: ParameterValue = newParameterValue("1610612757")
  val SacramentoKings: ParameterValue = newParameterValue("1610612758")
  val SanAntonioSpurs: ParameterValue = newParameterValue("1610612759")
  val TorontoRaptors: ParameterValue = newParameterValue("1610612761")
  val UtahJazz: ParameterValue = newParameterValue("1610612762")
  val WashingtonWizards: ParameterValue = newParameterValue("1610612764")

  val TeamIds: Seq[ParameterValue] = Seq(
    AtlantaHawks, BostonCeltics, BrooklynNets, CharlotteHornets, ChicagoBulls, ClevelandCavaliers, DallasMavericks,
    DenverNuggets, DetroitPistons, GoldenStateWarriors, HoustonRockets, IndianaPacers, LosAngelesClippers,
    LosAngelesLakers, MemphisGrizzlies, MiamiHeat, MilwaukeeBucks, MinnesotaTimberwolves, NewOrleansPelicans,
    NewYorkKnicks, OklahomaCityThunder, OrlandoMagic, Philadelphia76ers, PhoenixSuns, PortlandTrailBlazers,
    SacramentoKings, SanAntonioSpurs, TorontoRaptors, UtahJazz, WashingtonWizards)
}

object GameIdParameter extends Parameter {
  override val parameterName: String = "GameID"
}

object GameDateParameter extends Parameter {
  override val parameterName: String = "GameDate"
}

object StartPeriodParameter extends NumericParameter {
  override val parameterName: String = "StartPeriod"
}

object StartRangeParameter extends NumericParameter {
  override val parameterName: String = "StartRange"
}

object EndPeriodParameter extends NumericParameter {
  override val parameterName: String = "EndPeriod"
}

object EndRangeParameter extends NumericParameter {
  override val parameterName: String = "EndRange"
}

object RangeTypeParameter extends NumericParameter {
  override val parameterName: String = "RangeType"
}

object MeasureTypeParameter extends Parameter {
  override val parameterName: String = "MeasureType"

  val Base: ParameterValue = newParameterValue("Base")
  val Advanced: ParameterValue = newParameterValue("Advanced")
  val FourFactors: ParameterValue = newParameterValue("Four Factors")
  val Misc: ParameterValue = newParameterValue("Misc")
  val Scoring: ParameterValue = newParameterValue("Scoring")
  val Opponent: ParameterValue = newParameterValue("Opponent")

  override val defaultParameterValue: ParameterValue = Base
}

object PlayerIdParameter extends Parameter {
  override val parameterName: String = "PlayerID"

  // TODO hard code some more important players
  // TODO Eventually hard card all active players - Even if it means holding a map
}

object DateFromParameter extends Parameter {
  override val parameterName: String = "DateFrom"
}

object DateToParameter extends Parameter {
  override val parameterName: String = "DateTo"
}

object DayOffsetParameter extends NumericParameter {
  override val parameterName: String = "DayOffset"
}

object GameSegmentParameter extends Parameter {
  override val parameterName: String = "GameSegment"

  val FirstHalf: ParameterValue = newParameterValue("First Half")
  val SecondHalf: ParameterValue = newParameterValue("Second Half")
  val Overtime: ParameterValue = newParameterValue("Overtime")
}

object LastNGamesParameter extends NumericParameter {
  override val parameterName: String = "LastNGames"
}

object LocationParameter extends Parameter {
  override val parameterName: String = "Location"
}

object MonthParameter extends NumericParameter {
  override val parameterName: String = "Month"
}

object OpponentTeamIdParameter extends NumericParameter {
  override val parameterName: String = "OpponentTeamID"
}

object OutcomeParameter extends Parameter {
  override val parameterName: String = "Outcome"

  val Win: ParameterValue = newParameterValue("W")
  val Loss: ParameterValue = newParameterValue("L")
}

object PlayOffRoundParameter extends NumericParameter {
  override val parameterName: String = "PORound"
}

object PaceAdjustParameter extends YesNoParameter {
  override val parameterName: String = "PaceAdjust"
}

object PerModeParameter extends Parameter {
  override val parameterName: String = "PerMode"

  val Totals: ParameterValue = newParameterValue("Totals")
  val MinutesPer: ParameterValue = newParameterValue("MinutesPer")
  val Per48: ParameterValue = newParameterValue("Per48")
  val Per40: ParameterValue = newParameterValue("Per40")
  val Per36: ParameterValue = newParameterValue("Per36")
  val PerGame: ParameterValue = newParameterValue("PerGame")
  val PerMinute: ParameterValue = newParameterValue("PerMinute")
  val PerPossession: ParameterValue = newParameterValue("PerPossession")
  val PerPlay: ParameterValue = newParameterValue("PerPlay")
  val Per100Possessions: ParameterValue = newParameterValue("Per100Possessions")
  val Per100Plays: ParameterValue = newParameterValue("Per100Plays")

  override val defaultParameterValue: ParameterValue = Totals
}

object PeriodParameter extends NumericParameter {
  override val parameterName: String = "Period"
}

object PlusMinusParameter extends YesNoParameter {
  override val parameterName: String = "PlusMinus"
}

object RankParameter extends YesNoParameter {
  override val parameterName: String = "Rank"
}

object SeasonSegmentParameter extends Parameter {
  override val parameterName: String = "SeasonSegment"

  val PostAllStar: String = "Post All-Star"
  val PreAllStar: String = "Pre All-Star"
}

object SplitParameter extends Parameter {
  override val parameterName: String = "Split"

  override val defaultParameterValue: ParameterValue = newParameterValue("yoy")
}

object ShotClockRangeParameter extends Parameter {
  override val parameterName: String = "ShotClockRange"
}

object VsConferenceParameter extends Parameter {
  override val parameterName: String = "VsConference"
}

object VsDivisionParameter extends Parameter {
  override val parameterName: String = "VsDivision"
}

object ContextMeasureParameter extends Parameter {
  override val parameterName: String = "ContextMeasure"

  val Points: ParameterValue = newParameterValue("PTS")
  val FieldGoalsMade: ParameterValue = newParameterValue("FGM")
  val FieldGoalsAttempted: ParameterValue = newParameterValue("FGA")
  val FieldGoalPercentage: ParameterValue = newParameterValue("FG_PCT")
  val ThreePointFieldGoalsMade: ParameterValue = newParameterValue("FG3M")
  val ThreePointFieldGoalsAttempted: ParameterValue = newParameterValue("FG3A")
  val ThreePointFieldGoalPercentage: ParameterValue = newParameterValue("FG3_PCT")
  val PersonalFoul: ParameterValue = newParameterValue("PF")
  val EffectiveFieldGoalPercentage: ParameterValue = newParameterValue("EFG_PCT")
  val TrueShootingPercentage: ParameterValue = newParameterValue("TS_PCT")
  val FastBreakPoints: ParameterValue = newParameterValue("PTS_FB")
  val PointsOffTurnovers: ParameterValue = newParameterValue("PTS_OFF_TOV")
  val SecondChancePoints: ParameterValue = newParameterValue("PTS_2ND_CHANCE")

  override val defaultParameterValue: ParameterValue = FieldGoalsAttempted
}

object ContextFilterParameter extends Parameter {
  override val parameterName: String = "ContextFilter"
}

object PositionParameter extends Parameter {
  override val parameterName: String = "Position"
}

object AheadBehindParameter extends Parameter {
  override val parameterName: String = "AheadBehind"
}

object PointDiffParameter extends Parameter {
  override val parameterName: String = "PointDiff"
}

object RookieYearParameter extends Parameter {
  override val parameterName: String = "RookieYear"
}

object ClutchTimeParameter extends Parameter {
  override val parameterName: String = "ClutchTime"
}

object PlayerPositionParameter extends Parameter {
  override val parameterName: String = "PlayerPosition"
}