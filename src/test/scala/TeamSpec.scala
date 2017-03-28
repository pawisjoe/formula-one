import org.scalatest._

class TeamSpec extends FlatSpec with Matchers {

  "Team" should "init acceleration, position, max speed," in {
    val team = new Team(1)
    assert(team.maxSpeed == (160.0 * 1000 / 3600))
    assert(team.acceleration == 2)
    assert(team.currentPosition == 0)
  }

  "Team" should "applyHandleFactor" in {
    val team = new Team(1)
    team.currentSpeed = 100
    team.applyHandleFactor()
    assert(team.currentSpeed == 80)
  }

  "Team" should "use nitro" in {
    val team = new Team(1)
    team.currentSpeed = 100
    team.useNitro()
    assert(team.currentSpeed == 160.0 * 1000 / 3600)
  }

  "Team" should "use nitro once" in {
    val team = new Team(1)
    team.currentSpeed = 20
    team.useNitro()
    assert(team.currentSpeed == 40)
    team.useNitro()
    assert(team.currentSpeed == 40)
  }
}
