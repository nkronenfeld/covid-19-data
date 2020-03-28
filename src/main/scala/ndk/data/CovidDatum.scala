package ndk.data



import java.util.Date
import java.text.SimpleDateFormat



trait CovidDatum[T] {
  import CovidDatum._

  val id: T
  val date: Date
  val flips: Int
  val cases: Int
  val deaths: Int

  val day: Int = ((date.getTime - Day0.getTime).toDouble / Day).round.toInt
}
object CovidDatum {
  final val Day: Long = 1000L * 60L * 60L * 24L
  final val DateParser = new SimpleDateFormat("yyyy-MM-dd")
  final val Day0: Date = DateParser.parse("2020-01-01")
}
