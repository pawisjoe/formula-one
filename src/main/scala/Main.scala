/**
  * Created by pawisjoe on 3/25/2017 AD.
  */
object Main {
  def main(args: Array[String]): Unit = {
    try {
      val teamNumber: Int = args(0).toInt
      if(teamNumber < 1) return println("Please enter team number more than 0")
      val trackLength: Double = args(1).toInt
      if(trackLength < 1) return println("Please enter track length more than 0")

      val raceCal = new RaceCalculator(teamNumber, trackLength)
      raceCal.calculateRaceResult()
      raceCal.printResult()
    } catch {
      case _: Throwable => println("Please enter number only or integer for team number")
    }
  }
}
