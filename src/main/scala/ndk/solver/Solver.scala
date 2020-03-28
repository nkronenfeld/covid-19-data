package ndk.solver

import scala.util.Try

class Solver {
  /**
   * Find the best-fit line of the form
   *
   * <code> y = A + B x </code>
   *
   * to the given data
   *
   * @param data The data to fit
   * @return (A, B)
   */
  def fitLinear(data: Iterable[(Double, Double)]): (Double, Double) = {
    val n = data.size
    val sumX  = data.map { case (x, _) => x }.sum
    val sumX2 = data.map { case (x, _) => x * x }.sum
    val sumY  = data.map { case (_, y) => y }.sum
    val sumXY = data.map { case (x, y) => x * y }.sum

    val intercept = ((sumX2 * sumY - sumX * sumXY) / (n * sumX2 - sumX * sumX))
    val slope = (n * sumXY - sumX * sumY) / (n * sumX2 - sumX * sumX)
    (intercept, slope)
  }

  /**
   * Find the best-fit exponential curve of the form
   *
   * <code> y = A e^(BX)</code>
   *
   * to the given data
   *
   * @param data The data to fit
   * @return (A, B)
   */
  def fitExponential(data: Iterable[(Double, Double)]): (Double, Double) = {
    val n = data.size
    val sumX  = data.map { case (x, _) =>     x }.sum
    val sumX2 = data.map { case (x, _) => x * x }.sum
    val sumY  = data.map { case (_, y) => math.log(y) }.sum
    val sumXY = data.map { case (x, y) => x * math.log(y) }.sum

    val A = math.exp((sumX2 * sumY - sumX * sumXY) / (n * sumX2 - sumX * sumX))
    val B = (n * sumXY - sumX * sumY) / (n * sumX2 - sumX * sumX)
    (A, B)
  }

  /**
   * Find the mean square error of a function over a given set of data
   *
   * @param f    The function
   * @param data The data
   * @return The error
   */
  def error(f: Double => Double)(data: Iterable[(Double, Double)]): Double = {
    val n = data.size

    val a = data.flatMap { case (x, y) =>
      val fx = f(x)
      Try {
        (fx - y) * (fx - y) / n
      }.toOption
    }
    val b = a.map(y => (y, 1))
    val c = b.reduce((a, b) => (a._1 + b._1, a._2 + b._2))

    val (xTotal, yTotal) = data.flatMap { case (x, y) =>
      val fx = f(x)
      Try {
        (fx - y) * (fx - y) / n
      }.toOption
    }
      .map(y => (y, 1))
      .reduce((a, b) => (a._1 + b._1, a._2 + b._2))
    (xTotal / yTotal) * n
  }
}
