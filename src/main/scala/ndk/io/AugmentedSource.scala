package ndk.io

import scala.io.Source

object AugmentedSource {
  implicit class RichSource (base: Source) {
    def closeWhenDone[T] (fcn: Source => T): T = {
      val result = fcn(base)
      base.close()
      result
    }
  }
}
