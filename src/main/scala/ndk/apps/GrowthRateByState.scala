package ndk.apps

import ndk.data.State
import ndk.solver.Solver

object GrowthRateByState {
  def main(args: Array[String]): Unit = {
    val solver = new Solver

    State.stateByStateData.toSeq.sortBy(_._1).foreach { case (state, data) =>
      val (sA, sB) = solver.fitExponential(data.map(datum => (datum.day.toDouble, datum.cases.toDouble)))
//      println(f"Growth rate for $state%20s is $sB%.12f (i.e., cases_n+1 = cases_n * ${math.exp(sB)}%.12f) [initial=$sA]")
      println(s"$state,${math.exp(sB)}")
    }
  }
}
