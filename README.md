# Scalabrine


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

### To Use

```
val teamId = TeamIdParameter.MiamiHeat
val season = SeasonParameter.Season201617
val teamGameLog = TeamGameLogEndpoint(teamId, season)
val parsedResponse = ScalabrineClient.getTeamGameLog(teamGameLog)

// resource
parsedResponse.resource //"teamgamelog"

// results
parsedResponse.teamGameLog.games // Seq[GameLog]

```
