/**
  * Created by pawisjoe on 3/25/2017 AD.
  */
import scala.math._

class Team(var id: Int) {
  val handlingFactor: Double = 0.8
  var maxSpeed: Double = toMeterPerSec(150 + (10 * id))
  var acceleration: Double = 2 * id
  var currentPosition: Double = -200 * (id - 1)
  var currentSpeed: Double = 0
  var isNitroUsed: Boolean = false

  def toMeterPerSec(kiloMeterPerHour: Double): Double = {
    kiloMeterPerHour * 1000 / 3600
  }

  def applyHandleFactor(): Unit = {
    this.currentSpeed *= handlingFactor
  }

  def useNitro(): Unit = {
    if(!isNitroUsed) {
      this.currentSpeed = min(currentSpeed * 2, maxSpeed)
      isNitroUsed = true
      println("teamID: " + this.id + " use Nitro")
    }
  }

  def updateDistanceTravelled(time: Double): Unit = {
    currentPosition += (currentSpeed * time) + (1/2 * acceleration * pow(time, 2))
    println("teamID: " + this.id + " currentPosition: " + currentPosition)
  }

  def updateDistanceTravelledWithMaxSpeed(time: Double): Unit = {
    currentPosition += maxSpeed * (time)
  }

  def updateCurrentSpeed(time: Double): Unit = {
    currentSpeed = currentSpeed + (acceleration * time)
    println("teamID: " + this.id + " currentSpeed: " + currentSpeed)
  }

  def updateCurrentSpeedToMax(): Unit = {
    currentSpeed = maxSpeed
  }

  def getTimeToMaxSpeed(): Double = {
    (maxSpeed - currentSpeed) / acceleration
  }
}

object PositionOrdering extends  Ordering[Team] {
  override def compare(x: Team, y: Team) = x.currentPosition compare y.currentPosition
}