# Scalabrine
This is a scala client for the stats.nba data endpoints. It is currently a work in progress, so feel free to request support for additional endpoints.

### To Include 

1. Clone the Repo
2. mvn clean install 
3. Include in your pom
```
<dependency>
	<groupId>com.rrdinsights.scalabrine</groupId>
    <artifactId>scalabrine</artifactId>
    <version>0.0.2</version>
</dependency>
```

### Example

```
import com.rrdinsights.scalabrine.parameters._
import com.rrdinsights.scalabrine.ScalabrineClient

val teamId = TeamIdParameter.MiamiHeat
val season = SeasonParameter.Season201617
val teamGameLog = TeamGameLogEndpoint(teamId, season)
val parsedResponse = ScalabrineClient.getTeamGameLog(teamGameLog)

// resource
parsedResponse.resource //"teamgamelog"

// results
parsedResponse.teamGameLog.games // Seq[GameLog]

```