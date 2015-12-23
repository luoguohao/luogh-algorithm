package luogh.learn.algorithm.utils;

import javax.management.RuntimeMBeanException;
import java.util.*;

/**
 * Created by Kaola on 2015/12/23.
 * this util is uesed to compute the matrix metric
 * such as matrix Rank, Inverse Matrix,Adjoint Matrix,
 * the Determinant of Square Matrix ,etc .
 */
public class MatrixUtil {

	/**
	 * compute the Determinant of the given matrix.
	 * @param matrix
	 * @return
	 */
	public static double det(double[][] matrix){
		int xDim = 0 ;
		//the matrix must be Square Matrix
		if(matrix!=null) {
			xDim = matrix.length;
			if(!isSquareMaxtrix(matrix))
				throw new RuntimeException("the matrix must be a Square Matrix!");
		}
		//first,compute the premutation of the ordinal
		Set<int[]> premutNums = new HashSet<int[]>();
		premut(orderSeq(1,xDim),0,premutNums);

		//sum
		double sum = 0;
		for(int[] premuteNum : premutNums) {
			//inverseOrderNum
			int inverseNum = inverseOrderNum(premuteNum);

			double elementsProduct = 1;
			for(int i=0;i<xDim;i++) {
				elementsProduct *= matrix[i][premuteNum[i]-1];
			}

			sum += (Math.pow(-1,inverseNum))*elementsProduct;
		}
		return sum;
	}

	private static List<int[]> orderSeq(int startNum,int size) {
		List<int[]> orderList = new ArrayList<int[]>();
		int[] orderSeq = new int[size];
		for(int i=0;i<size;i++) {
			orderSeq[i] = startNum+i;
		}
		orderList.add(orderSeq);
		return orderList;

	}
	/**
	 * compute the premutaion for a given ordinal.
	 *
	 * for example: we want to compute the [1 2 3]`s premuation.
	 * the operation as followed:
	 * 		1 2 3
	 * 			<- 1 exchange 2 -> 2 1 3
	 * 								 <- 1 exchange 3 -> 2 3 1
	 * 			<- 1 exchange 3 -> 3 2 1
	 * 								 <- 2 exchange 1 -> 3 1 2
	 * 			<-2 exchange 3 -> 1 3 2
	 *
	 * 	the premuation number is 3! = 3*2*1 = 6  :
	 * 	 {[1 2 3],[2 1 3],[2 3 1],[3 2 1],[3 1 2],[1 3 2]}
	 *
	 * @param initOrdinalList initial Ordinal Numbers
	 * @param startIndex default 0
	 * @param premutionCollector collecte the premuation value
	 */
	private static void premut(List<int[]> initOrdinalList,int startIndex,Set<int[]> premutionCollector){
		List<int[]> recurseList = new ArrayList<int[]>();
		int arrLength = 0;
		for(int i=0;i<initOrdinalList.size();i++) {
			int[] initOrdinal = initOrdinalList.get(i);
			arrLength = initOrdinal.length;
			if(startIndex<0 || startIndex >= arrLength) {
				throw new RuntimeException("the startIndex is out of bound !");
			}
			int temp;
			int[] cloneOrdinal = null;
			recurseList.add(initOrdinal);
			premutionCollector.add(initOrdinal);
			for(int j=startIndex+1;j<arrLength;j++){
				cloneOrdinal = initOrdinal.clone();
				// swap
				temp = cloneOrdinal[j];
				cloneOrdinal[j] = cloneOrdinal[startIndex];
				cloneOrdinal[startIndex] = temp;

				recurseList.add(cloneOrdinal);
			}
		}
		//recursive
		if(recurseList.size()>0 && ++startIndex < arrLength) {
			premut(recurseList,startIndex,premutionCollector);
		}

	}

	/**
	 * compute the array inverseOrderNum.
	 *  inverseOrder define as :
	 *  	if i < j ,but A[i] > A[j] ,then inverseOrderNum increase.
	 * for example:
	 * 		A = [5 4 3 8]
	 * the inverseOrderNum is : 1 + 2  = 3
	 * @param number
	 * @return
	 */
	private static int inverseOrderNum(int[] number){
		int reverseNum = 0;
		if(number==null) {
			throw new RuntimeException("the input param cant be null");
		}
		int length = number.length;
		for(int i = 0; i < length;i++){
			for(int j = 0; j < length;j++){
				if(i<j) {
					if(number[i]>number[j]){
						reverseNum++;
					}
				}

			}
		}
		return reverseNum;
	}

	/**
	 * check the matrix is square matrix
	 * @param matrix
	 * @return
	 */
	public static boolean isSquareMaxtrix(double[][] matrix){
		boolean isSquare = true;
		if(matrix!=null) {
			int xDim = matrix.length;
			for(int i=0;i<xDim;i++) {
				if(matrix[i].length != xDim)
					isSquare = false;
					break;
			}
		} else {
			throw new RuntimeException("matrix cant be null");
		}
		return isSquare;
	}
	/**
	 * compute the adjoint matrix for the given square matrix
	 * @param matrix
	 * @return
	 */
	public static double[][] adjointMatrix(double[][] matrix) {
		if(!isSquareMaxtrix(matrix)){
			throw new RuntimeException("matrix is not a square matrix");
		}
		int xDim = matrix.length;
		//algebraic complement
		List<Double> detMatrix;
		double[][] adjointMatrix = new double[xDim][xDim];
		for(int i=0;i<xDim;i++){
			for(int j=0;j<xDim;j++) {
				detMatrix = new LinkedList<Double>();
				for(int k=0;k<xDim;k++) {
					for(int m=0;m<xDim;m++){
						if(i!=k && j!=m){
							detMatrix.add(matrix[k][m]);
						}
					}
				}
				adjointMatrix[i][j] = Math.pow(-1,(i+j))*det(list2matrix(detMatrix,xDim-1,xDim-1));
			}
		}

		return transposedMatrix(adjointMatrix);
	}

	/**
	 * list to matrix
	 * @param list
	 * @param xDim
	 * @param yDim
	 * @return
	 */
	public static  double[][] list2matrix(List<Double> list,int xDim,int yDim){

		double[][] result = new double[xDim][yDim];
		if(list == null){
			throw new RuntimeException("list cant be null");
		}
		if(list.size() != xDim*yDim) {
			throw new RuntimeException("list size cant transform a "+xDim+" X "+yDim+" metrix!");
		}
		for(int i=0;i<xDim;i++){
			for(int j=0;j<yDim;j++) {
				result[i][j] = list.get(i*yDim+j);
			}
		}

		return result;
	}

	/**
	 * inverse of matrix
	 * 	inverse(A) = adjoint(A)/det(A)
	 * @param matrix
	 * @return
	 */
	public static double[][] inverseMatrix(double[][] matrix) {
		if(matrix == null){
			throw new RuntimeException("matrix cant be null");
		}
		if(!isSquareMaxtrix(matrix)){
			throw new RuntimeException("matrix must be a square matrix");
		}
		double det = det(matrix);
		if(det == 0) {
			throw new RuntimeException("matrix`s determinant equals zero ,so inverse of matrix does not exist");
		}
		int dim = matrix.length;
		double[][] inverseMatrix = matrixProduct(adjointMatrix(matrix),dim,dim,1/det);
		return inverseMatrix;

	}

	/**
	 * matrix product
	 * @param matrix
	 * @param xDim
	 * @param yDim
	 * @param number
	 * @return
	 */
	public static double[][] matrixProduct(double[][] matrix,int xDim,int yDim,double number){
		if(matrix == null){
			throw new RuntimeException("matrix cant be null");
		}
		double[][] productMatrix = new double[xDim][yDim];
		for(int i=0;i<xDim;i++){
			for(int j=0;j<yDim;j++) {
				productMatrix[i][j] = matrix[i][j]*number;
			}
		}
		return productMatrix;
	}
	/**
	 * compute the transposedMatrix
	 * @param matrix
	 * @return
	 */
	public static double[][] transposedMatrix(double[][] matrix){
		if(matrix == null){
			throw new RuntimeException("matrix cant be null");
		}
		double[][] transposedMatrix = new double[matrix.length][matrix.length];
		for(int i=0;i<matrix.length;i++) {
			for(int j=0;j<matrix[i].length;j++) {
				transposedMatrix[j][i] = matrix[i][j];
			}
		}
		return transposedMatrix;
	}
	/**
	 * print matrix
	 */
	public  static void printMatrix(double[][] matrix,String message){
		StringBuilder sBuilder = new StringBuilder(message).append(":").append("\n");
		for(int i=0;i<matrix.length;i++){
			for(int j=0;j<matrix[i].length;j++){
				sBuilder.append(matrix[i][j]);
				if(j==matrix[i].length-1){
					sBuilder.append("\n");
				} else {
					sBuilder.append("\t");
				}
			}
		}
		System.out.print(sBuilder.append("\n").toString());
	}

	/**
	 * compute the rank of the matrix using the method : minor matrix of order k
	 * @return
	 */
	public static int rank(double[][] matrix){
		if(matrix == null){
			throw new RuntimeException("matrix cant be null");
		}
		int xDim = matrix.length;
		int yDim = matrix[0].length;
		int highestOrderMinorMatrix = Math.min(xDim,yDim);
		return 0;
	}

	public static void main(String args[]) {
		int[] ordinal = {1,2,3,4,5,6,7};
		List<int[]> initList = new ArrayList<int[]>();
		Set<int[]> collector = new HashSet<int[]>();
		initList.add(ordinal);
		System.out.println("begin");
		MatrixUtil.premut(initList,0,collector);
		System.out.println("result size:"+collector.size());
		for(int[] value : collector) {
			System.out.print("[");
			for(int j=0;j<value.length;j++) {
				System.out.print(value[j] + " ");
			}
			System.out.println("] \n");

		}


		System.out.println(MatrixUtil.inverseOrderNum(new int[]{5,4,3,8}));

		double[][] matrix ={{1,2,3,4},{4,5,6,7},{7,8,9,10},{10,11,12,13}};
		System.out.println("matrix determinant:"+det(matrix));

		System.out.println(Math.pow(-1,0));

		//adjoint matrix
		double[][] matrix1 ={{1,2,3},{4,5,6},{6,8,9}};
		System.out.println("matrix determinant:"+det(matrix1));
		printMatrix(transposedMatrix(matrix1),"transposed matrix");
		printMatrix(adjointMatrix(matrix1), "adjoint matrix");

		printMatrix(inverseMatrix(matrix1),"inverse matrix");
	}

}
