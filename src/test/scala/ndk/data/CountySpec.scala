package ndk.data



import org.scalatest.FunSpec



class CountySpec extends FunSpec {
  describe("county records") {
    they("should read correctly from data") {
      assert(County.counties.nonEmpty)
    }
    it("should include all 50 states") {
      assert(County.counties.map(_.state).toSet.size == 50)
    }
  }
}
