/**
  * Created by pawisjoe on 3/25/2017 AD.
  */
import scala.math._
import scala.collection.mutable.ListBuffer
import scala.util.Sorting


class RaceCalculator(teamNumber: Int, trackLength: Double) {

  private val handlingDistance: Double = 10
  private val reAssessmentTime: Double = 2
  val teams: ListBuffer[Team] = initRacingTeam()
  var currentTime: Double = 0

  var results = ListBuffer.empty[RaceResult]

  private def initRacingTeam(): ListBuffer[Team] = {
    val teams = ListBuffer.empty[Team]
    for(i <- 1 to teamNumber) {
      teams += new Team(i)
    }
    teams
  }

  def calculateRaceResult(): Unit = {
    while(results.size != teamNumber) {
      currentTime += reAssessmentTime
      updatePosition()
      checkFinishingCar()
      checkNearbyCarAndAdjustSpeed()
      checkLastPositionAndUseNitro()
      println("currentTime: " + currentTime)
    }
  }

  def updatePosition(): Unit = {
    for(team <- teams) {
      if(team.getTimeToMaxSpeed() > reAssessmentTime) {
        team.updateDistanceTravelled(reAssessmentTime)
        team.updateCurrentSpeed(reAssessmentTime)
      }
      else {
        team.updateDistanceTravelled(team.getTimeToMaxSpeed())
        team.updateDistanceTravelledWithMaxSpeed(reAssessmentTime - team.getTimeToMaxSpeed())
        team.updateCurrentSpeedToMax()
      }
    }

  }

  def checkNearbyCarAndAdjustSpeed(): Unit = {
    if(teams.size < 2) return
    else if(teams.size == 2 && isCarNearby(teams(0), teams(1))) {
      teams(0).applyHandleFactor()
      teams(1).applyHandleFactor()
    }
    else {
      val sortedList: Array[Team] = teams.toArray
      Sorting.quickSort(sortedList)(PositionOrdering)
      if(isCarNearby(teams(returnTeamIndex(sortedList(0).id)), teams(returnTeamIndex(sortedList(1).id)))) {
        teams(returnTeamIndex(sortedList(0).id)).applyHandleFactor()
      }

      for(i <- 1 to teams.size - 2) {
        if(isCarNearby(teams(returnTeamIndex(sortedList(i).id)), teams(returnTeamIndex(sortedList(i - 1).id))) ||
          isCarNearby(teams(returnTeamIndex(sortedList(i).id)), teams(returnTeamIndex(sortedList(i + 1).id)))) {
          teams(returnTeamIndex(sortedList(i).id)).applyHandleFactor()
        }
      }

      if(isCarNearby(teams(returnTeamIndex(sortedList(teams.size - 1).id)), teams(returnTeamIndex(sortedList(teams.size - 2).id)))) {
        teams(returnTeamIndex(sortedList(teams.size - 1).id)).applyHandleFactor()
      }
    }

  }

  private def returnTeamIndex(id: Int): Int = {
    var index: Int = 0;
    for(team <- teams) {
      if(team.id == id) return index
      index += 1
    }
    return index
  }

  private def isCarNearby(team1: Team, team2: Team): Boolean = {
    abs(team1.currentPosition - team2.currentPosition) <= handlingDistance
  }

  def checkLastPositionAndUseNitro(): Unit = {
    if(teams.size < 1) return
    val sortedList: Array[Team] = teams.toArray
    Sorting.quickSort(sortedList)(PositionOrdering.reverse)
    val lastTeam = sortedList.last
    for(team <- teams) {
      if(team.id == lastTeam.id) {
        team.useNitro()
      }
    }
    //check more last position
    for(team <- teams) {
      if(team.currentPosition == lastTeam.currentPosition) {
        team.useNitro()
      }
    }
  }

  def checkFinishingCar(): Unit = {
    val finishedTeams = ListBuffer.empty[Team]
    for(team <- teams) {
      if(team.currentPosition >= trackLength) {
        results += RaceResult(team.id, team.currentSpeed, currentTime)
        finishedTeams += team
      }
    }
    for(finishedTeam <- finishedTeams) {
      teams -= finishedTeam
    }
  }

  def printResult(): Unit = {
    println("=====================================================")
    println("-------------------- Result -------------------------")
    println("=====================================================")
    for(result <- results) {
      println("TEAMID: " + result.id + " SPEED(m/s): " + result.finalSpeed + " TIME(second): " + result.completedTime)
    }
  }

}
