package ndk.solver

import org.scalactic.TolerantNumerics
import org.scalatest.FunSpec

class SolverSpec extends FunSpec {
  implicit private val tolerantDouble = TolerantNumerics.tolerantDoubleEquality(1e-6)
  describe("solving exact fits") {
    val solver = new Solver()

    it("should solve a simple linear equation trivially") {
      val data = (1 to 100).map(n => (1.0 * n, 5.0 * n + 3.0))
      val f: Seq[Double] => Double => Double = p => x => p(0) + x * p(1)

      assert(solver.fitLinear(data) === (3.0, 5.0))
    }
    it("should solve a simple exponential equation trivially") {
      val data = (1 to 100).map(n => (1.0 * n, 3.0 * math.pow(5.0, n)))
      val f: Seq[Double] => Double => Double = p => x => math.pow(p(0), x) * p(1)

      val best = solver.fitExponential(data)
      assert(best._1 === 3.0)
      assert(best._2 === math.log(5.0))
    }
  }
}
