package ndk.data



import java.util.Date

import scala.io.Source
import scala.util.{Failure, Success, Try}

import ndk.io.AugmentedSource.RichSource



case class State (state: String,
                  date: Date,
                  flips: Int,
                  cases: Int,
                  deaths: Int) extends CovidDatum[String] {
  override val id: String = state
}
object State {
  lazy val states: Seq[State] = Source.fromFile("./us-states.csv").closeWhenDone(
    _.getLines()
      // Drop the header
      .drop(1)
      // Convert to a form that won't disappear
      .toArray
      // Parse state data
      .map(_.split(","))
      .flatMap(parts => Try {
        State(parts(1), CovidDatum.DateParser.parse(parts(0)), parts(2).toInt, parts(3).toInt, parts(4).toInt)
      } match {
        case Success(state) =>
          Some(state)
        case Failure(error) =>
          println(error.getMessage)
          None
      })
  )
}
