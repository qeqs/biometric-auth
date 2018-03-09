import biometric.logic.HemmingDistance;
import org.junit.Assert;
import org.junit.Test;


public class HemmingDistanceTest {

  @Test
  public void test(){
    double left = 100;
    double right = 101;
    Assert.assertEquals(1l, HemmingDistance.dist(left, right));
  }

}
