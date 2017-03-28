import org.scalatest._

class RaceCalculatorSpec extends FlatSpec with Matchers {

  "RaceCalculator" should "update speed and position" in {
    val raceCal = new RaceCalculator(1, 100)
    raceCal.currentTime = 2
    raceCal.teams(0).currentSpeed = 10
    raceCal.updatePosition()
    assert(raceCal.teams(0).currentSpeed == 14)
    assert(raceCal.teams(0).currentPosition == 20)
  }

  "RaceCalculator" should "update speed with max speed" in {
    val raceCal = new RaceCalculator(1, 100)
    raceCal.currentTime = 2
    raceCal.teams(0).currentSpeed = 42
    raceCal.updatePosition()
    assert(raceCal.teams(0).currentSpeed == raceCal.teams(0).maxSpeed)
  }

  "RaceCalculator" should "check last position and use nitro" in {
    val raceCal = new RaceCalculator(3, 100)
    raceCal.teams(0).currentPosition = 1
    raceCal.teams(0).currentSpeed = 10
    raceCal.teams(1).currentPosition = 2
    raceCal.teams(2).currentPosition = 3
    raceCal.checkLastPositionAndUseNitro()
    assert(raceCal.teams(0).currentSpeed == 20)
  }

  "RaceCalculator" should "check last position and use nitro (more than 1 team)" in {
    val raceCal = new RaceCalculator(3, 100)
    raceCal.teams(0).currentPosition = 1
    raceCal.teams(0).currentSpeed = 10
    raceCal.teams(1).currentPosition = 1
    raceCal.teams(1).currentSpeed = 10
    raceCal.teams(2).currentPosition = 2
    raceCal.checkLastPositionAndUseNitro()
    assert(raceCal.teams(0).currentSpeed == 20)
    assert(raceCal.teams(1).currentSpeed == 20)
  }

  "RaceCalculator" should "check finished team" in {
    val raceCal = new RaceCalculator(2, 100)
    raceCal.currentTime = 2
    raceCal.teams(0).currentPosition = 110
    raceCal.teams(0).currentSpeed = 10
    raceCal.teams(1).currentPosition = 90
    raceCal.teams(1).currentSpeed = 5
    raceCal.checkFinishingCar()
    assert(raceCal.results.size == 1)
    assert(raceCal.results(0).completedTime == 2)
    assert(raceCal.results(0).finalSpeed == 10)
    assert(raceCal.results(0).id == 1)
    assert(raceCal.teams.size == 1)
  }

  "RaceCalculator" should "check nearby car and reduce speed (only 2 cars)" in {
    val raceCal = new RaceCalculator(2, 100)
    raceCal.currentTime = 2
    raceCal.teams(0).currentPosition = 40
    raceCal.teams(0).currentSpeed = 10
    raceCal.teams(1).currentPosition = 50
    raceCal.teams(1).currentSpeed = 10
    raceCal.checkNearbyCarAndAdjustSpeed()
    assert(raceCal.teams(0).currentSpeed == 8)
    assert(raceCal.teams(1).currentSpeed == 8)
  }

  "RaceCalculator" should "check nearby car and reduce speed (more than 2 cars)" in {
    val raceCal = new RaceCalculator(5, 100)
    raceCal.currentTime = 2
    raceCal.teams(0).currentPosition = 10
    raceCal.teams(0).currentSpeed = 10
    raceCal.teams(1).currentPosition = 30
    raceCal.teams(1).currentSpeed = 10
    raceCal.teams(2).currentPosition = 35
    raceCal.teams(2).currentSpeed = 10
    raceCal.teams(3).currentPosition = 45
    raceCal.teams(3).currentSpeed = 10
    raceCal.teams(4).currentPosition = 70
    raceCal.teams(4).currentSpeed = 10
    raceCal.checkNearbyCarAndAdjustSpeed()
    assert(raceCal.teams(0).currentSpeed == 10)
    assert(raceCal.teams(1).currentSpeed == 8)
    assert(raceCal.teams(2).currentSpeed == 8)
    assert(raceCal.teams(3).currentSpeed == 8)
    assert(raceCal.teams(4).currentSpeed == 10)
  }
}
