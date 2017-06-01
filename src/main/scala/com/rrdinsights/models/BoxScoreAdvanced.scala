package com.rrdinsights.models

import com.rrdinsights.parameters.ParameterValue

final case class BoxScoreAdvanced() {

}

final case class BoxScoreAdvancedResponse(resource: String,
                                          parameters: Seq[ParameterValue],
                                          boxScoreAdvanced: BoxScoreAdvanced)

final case class TeamStats(teamId: Int,
                           teamName: String,
                           teamAbbreviation: String,
                           teamCity: String,
                           minutes: Double,
                           offensiveRating: Double,
                           defensiveRating: Double,
                           netRating: Double,
                           assistPercentage: Double,
                           assistTurnOverRatio: Double,
                           offensiveReboundPercentage: Double,
                           defensiveReboundPercentage: Double,
                           teamTurnOverPercentage: Double,
                           effectiveFieldGoalPercentage: Double,
                           trueShootingPercentage: Double,
                           usage: Double,
                           pace: Double,
                           playerEstimatedImpact: Double)

final case class PlayerStats(gameId: String,
                             teamId: Int,
                             teamAbbreviation: String,
                             teamCity: String,
                             playerId: String,
                             playerName: String,
                             startPosition: String,
                             comment: String,
                             minutes: Double,
                             offensiveRating: Double,
                             defensiveRating: Double,
                             netRating: Double,
                             assistPercentage: Double,
                             assistTurnOverRatio: Double,
                             assistRatio: Double,
                             offensiveReboundPercentage: Double,
                             defensiveReboundPercentage: Double,
                             reboundPercentage: Double,
                             teamTurnOverPercentage: Double,
                             effectiveFieldGoalPercentage: Double,
                             trueShootingPercentage: Double,
                             usage: Double,
                             pace: Double,
                             playerEstimatedImpact: Double)