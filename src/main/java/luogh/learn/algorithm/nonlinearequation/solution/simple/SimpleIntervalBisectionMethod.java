package luogh.learn.algorithm.nonlinearequation.solution.simple;

import luogh.learn.algorithm.utils.MathUtil;
import org.nfunk.jep.JEP;
import org.nfunk.jep.Node;

/**
 * Created by Kaola on 2015/12/29. This class is called Simple Interval Bisection Method which used
 * to compute one of the solution of the NonLinear Equation;
 *
 * see:����ֵ�������㷨�� ���Ľ�
 */
public class SimpleIntervalBisectionMethod {

  public static void main(String args[]) throws Exception {
    SimpleIntervalBisectionMethod ibm = new SimpleIntervalBisectionMethod();
    double threshold = Math.pow(2, -52);
    double solution = ibm.execute("x^4-x-2", 1.0, 1.5, threshold);
    System.out.println("The solution is :" + solution);
  }

  /**
   * @return one of the solution for the Non-Linear Equation on the Interval
   * [startInterval,endInterval]
   */
  public double execute(String nonLinearEquation, Double startInterval, Double endInterval,
      Double threshold) throws Exception {
    //first, check the Euquation at least  has one solution on the Interval [startInterval,endInterval]
    //according to the function(startInterval)*function(end)<0.
    JEP jep = new JEP();
    jep.addVariable("x", startInterval);
    Node node = jep.parse(nonLinearEquation);
    double startResult = (Double) jep.evaluate(node);
    jep.addVariable("x", endInterval);
    double endResult = (Double) jep.evaluate(node);
    if (startResult * endResult > 0) {
      throw new RuntimeException("we think this Interval:[" + startInterval + "," + endInterval
          + "] does not exit at least one resolution for" +
          "the equation ,please check.");
    }

    double median_value;
    double median_result;
    long iterTime = 0L;
    while (endInterval - startInterval > threshold) {
      iterTime++;
      median_value = startInterval + (endInterval - startInterval) / 2;
      jep.addVariable("x", startInterval);
      startResult = (Double) jep.evaluate(node);
      jep.addVariable("x", median_value);
      median_result = (Double) jep.evaluate(node);

      System.out
          .println("Iterate Time :(" + iterTime + ") and the Interval [" + startInterval + "," +
              endInterval + "]" + " and f(x) :" + median_result);

      if (MathUtil.sgn(median_result) == MathUtil.sgn(startResult)) { // has the same sgn
        startInterval = median_value;
      } else {
        endInterval = median_value;
      }

    }

    median_value = startInterval + (endInterval - startInterval) / 2;
    jep.addVariable("x", median_value);
    startResult = (Double) jep.evaluate(node);
    System.out.println("the final f(x) is :" + startResult);
    return median_value;
  }


}
