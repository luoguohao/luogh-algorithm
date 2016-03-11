package luogh.learn.algorithm.nonlinearequation.solution.simple;

import org.nfunk.jep.JEP;
import org.nfunk.jep.Node;

/**
 * Created by Kaola on 2015/12/31.
 *	Newton Iterative Method using tangential equation as iterative method
 *	is a better solution to compute
 *  the solution of a Non-Linear Equation,which has a faster
 *  convergence order.
 */
public class NewtonIterativeMethod {
	/**
	 *
	 * @param func Non-Linear Equation
	 * @param derivationFunc	the Derivation Function for Non-Linear Equation
	 * @param x0 the first x value
	 * @param alpha0 error limit
	 * @param alpha1	error limit
	 * @return
	 */
	public double execute(String func,String derivationFunc,double x0,double alpha0,double alpha1) throws Exception {
		JEP jep = new JEP();
		jep.addVariable("x",x0);
		Node nodeFunc = jep.parse(func);
		Node nodeDerFunc = jep.parse(derivationFunc);
		double solution;
		double solutionDer ;
		double xCur,xNext = x0;
		long iterTime = 0l;
		do {
			xCur = xNext;
			jep.addVariable("x",xCur);
			solution = (Double)jep.evaluate(nodeFunc);
			jep.addVariable("x",xCur);
			solutionDer = (Double)jep.evaluate(nodeDerFunc); //solutionDer can`t be zero
			xNext = xCur - solution/solutionDer;
			System.out.println("iterTime is :"+(++iterTime)+" and xCur value is :"+xCur+"and xNext value is :"+xNext+" and solution is :"+solution);
		} while(Math.abs(solution)>alpha0 || Math.abs(xNext-xCur)>alpha1);
		return xNext;
	}

	public static void main(String args[]) throws Exception {
		NewtonIterativeMethod nim = new NewtonIterativeMethod();
		double solution = nim.execute("x^4-x-2","4*x^3-1",2000,0.0001,0.0003);
//		double solution = nim.execute("(x-1)^3","(x-1)^2",2000,0.0001,0.0003);
		System.out.println("result is :"+solution);
	}
}


