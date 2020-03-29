package ndk.data



import org.scalatest.FunSpec



class StateSpec extends FunSpec {
  describe("state-wide records") {
    they("should read correctly from data") {
      assert(State.stateData.nonEmpty)
    }
    it("should include all 50 states") {
      assert(State.stateData.map(_.state).toSet.size >= 50)
    }
  }
}
