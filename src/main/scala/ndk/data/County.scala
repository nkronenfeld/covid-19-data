package ndk.data


import java.util.Date

import ndk.data.State.stateData

import scala.io.Source
import scala.util.{Failure, Success, Try}
import ndk.io.AugmentedSource.RichSource


case class County(state: String,
                  county: String,
                  date: Date,
                  flips: Int,
                  cases: Int,
                  deaths: Int) extends CovidDatum[(String, String)] {
  override val id: (String, String) = (state, county)
}

object County {
  lazy val countyData: Seq[County] = Source.fromFile("./us-counties.csv").closeWhenDone(
    _.getLines()
      // Drop the header
      .drop(1)
      // Conovert to a form that won't disappear
      .toArray
      // Parse county data
      .map(_.split(","))
      .flatMap(parts => Try {
        County(parts(2), parts(1), CovidDatum.DateParser.parse(parts(0)), parts(3).toInt, parts(4).toInt, parts(5).toInt)
      } match {
        case Success(county) =>
          Some(county)
        case Failure(error) =>
          println(error.getMessage)
          None
      })
  )

  /** The state-by-state data, broken out by state, each with data ordered by date */
  lazy val countyByCountyData: Map[String, Map[String, Seq[County]]] =
    countyData.groupBy(_.state).map { case (state, stateData) =>
      state -> stateData.groupBy(_.county).map { case (county, data) =>
        county -> data.sortBy(_.date.getTime)
      }
    }
}