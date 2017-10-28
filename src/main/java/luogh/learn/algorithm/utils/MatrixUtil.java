package luogh.learn.algorithm.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Kaola on 2015/12/23. this util is uesed to compute the matrix metric such as matrix
 * Rank, Inverse Matrix,Adjoint Matrix, the Determinant of Square Matrix ,etc .
 */
public class MatrixUtil {

  /**
   * compute the Determinant of the given matrix.
   */
  public static double det(double[][] matrix) {
    int xDim = 0;
    //the matrix must be Square Matrix
    if (matrix != null) {
      xDim = matrix.length;
      if (!isSquareMaxtrix(matrix)) {
        throw new RuntimeException("the matrix must be a Square Matrix!");
      }
    }
    //first,compute the premutation of the ordinal
    Set<int[]> premutNums = new HashSet<int[]>();
    premut(orderSeq(1, xDim), 0, premutNums);

    //sum
    double sum = 0;
    for (int[] premuteNum : premutNums) {
      //inverseOrderNum
      int inverseNum = inverseOrderNum(premuteNum);

      double elementsProduct = 1;
      for (int i = 0; i < xDim; i++) {
        elementsProduct *= matrix[i][premuteNum[i] - 1];
      }

      sum += (Math.pow(-1, inverseNum)) * elementsProduct;
    }
    return sum;
  }

  private static List<int[]> orderSeq(int startNum, int size) {
    List<int[]> orderList = new ArrayList<>();
    int[] orderSeq = new int[size];
    for (int i = 0; i < size; i++) {
      orderSeq[i] = startNum + i;
    }
    orderList.add(orderSeq);
    return orderList;

  }

  /**
   * compute the premutaion for a given ordinal.
   *
   * for example: we want to compute the [1 2 3]`s premuation. the operation as followed: 1 2 3 <- 1
   * exchange 2 -> 2 1 3 <- 1 exchange 3 -> 2 3 1 <- 1 exchange 3 -> 3 2 1 <- 2 exchange 1 -> 3 1 2
   * <-2 exchange 3 -> 1 3 2
   *
   * the premuation number is 3! = 3*2*1 = 6  : {[1 2 3],[2 1 3],[2 3 1],[3 2 1],[3 1 2],[1 3 2]}
   *
   * @param initOrdinalList initial Ordinal Numbers
   * @param startIndex default 0
   * @param premutionCollector collecte the premuation value
   */
  private static void premut(List<int[]> initOrdinalList, int startIndex,
      Set<int[]> premutionCollector) {
    List<int[]> recurseList = new ArrayList<int[]>();
    int arrLength = 0;
    for (int i = 0; i < initOrdinalList.size(); i++) {
      int[] initOrdinal = initOrdinalList.get(i);
      arrLength = initOrdinal.length;
      if (startIndex < 0 || startIndex >= arrLength) {
        throw new RuntimeException("the startIndex is out of bound !");
      }
      int temp;
      int[] cloneOrdinal = null;
      recurseList.add(initOrdinal);
      premutionCollector.add(initOrdinal);
      for (int j = startIndex + 1; j < arrLength; j++) {
        cloneOrdinal = initOrdinal.clone();
        // swap
        temp = cloneOrdinal[j];
        cloneOrdinal[j] = cloneOrdinal[startIndex];
        cloneOrdinal[startIndex] = temp;

        recurseList.add(cloneOrdinal);
      }
    }
    //recursive
    if (recurseList.size() > 0 && ++startIndex < arrLength) {
      premut(recurseList, startIndex, premutionCollector);
    }

  }

  /**
   * compute the array inverseOrderNum. inverseOrder define as : if i < j ,but A[i] > A[j] ,then
   * inverseOrderNum increase. for example: A = [5 4 3 8] the inverseOrderNum is : 1 + 2  = 3
   */
  private static int inverseOrderNum(int[] number) {
    int reverseNum = 0;
    if (number == null) {
      throw new RuntimeException("the input param cant be null");
    }
    int length = number.length;
    for (int i = 0; i < length; i++) {
      for (int j = 0; j < length; j++) {
        if (i < j) {
          if (number[i] > number[j]) {
            reverseNum++;
          }
        }

      }
    }
    return reverseNum;
  }

  /**
   * check the matrix is square matrix
   */
  public static boolean isSquareMaxtrix(double[][] matrix) {
    boolean isSquare = true;
    if (matrix != null) {
      int xDim = matrix.length;
      for (int i = 0; i < xDim; i++) {
        if (matrix[i].length != xDim) {
          isSquare = false;
        }
        break;
      }
    } else {
      throw new RuntimeException("matrix cant be null");
    }
    return isSquare;
  }

  /**
   * compute the adjoint matrix for the given square matrix
   */
  public static double[][] adjointMatrix(double[][] matrix) {
    if (!isSquareMaxtrix(matrix)) {
      throw new RuntimeException("matrix is not a square matrix");
    }
    int xDim = matrix.length;
    //algebraic complement
    List<Double> detMatrix;
    double[][] adjointMatrix = new double[xDim][xDim];
    for (int i = 0; i < xDim; i++) {
      for (int j = 0; j < xDim; j++) {
        detMatrix = new LinkedList<Double>();
        for (int k = 0; k < xDim; k++) {
          for (int m = 0; m < xDim; m++) {
            if (i != k && j != m) {
              detMatrix.add(matrix[k][m]);
            }
          }
        }
        adjointMatrix[i][j] =
            Math.pow(-1, (i + j)) * det(list2matrix(detMatrix, xDim - 1, xDim - 1));
      }
    }

    return transposedMatrix(adjointMatrix);
  }

  /**
   * list to matrix
   */
  public static double[][] list2matrix(List<Double> list, int xDim, int yDim) {

    double[][] result = new double[xDim][yDim];
    if (list == null) {
      throw new RuntimeException("list cant be null");
    }
    if (list.size() != xDim * yDim) {
      throw new RuntimeException("list size cant transform a " + xDim + " X " + yDim + " metrix!");
    }
    for (int i = 0; i < xDim; i++) {
      for (int j = 0; j < yDim; j++) {
        result[i][j] = list.get(i * yDim + j);
      }
    }

    return result;
  }

  /**
   * inverse of matrix inverse(A) = adjoint(A)/det(A)
   */
  public static double[][] inverseMatrix(double[][] matrix) {
    if (matrix == null) {
      throw new RuntimeException("matrix cant be null");
    }
    if (!isSquareMaxtrix(matrix)) {
      throw new RuntimeException("matrix must be a square matrix");
    }
    double det = det(matrix);
    if (det == 0) {
      throw new RuntimeException(
          "matrix`s determinant equals zero ,so inverse of matrix does not exist");
    }
    int dim = matrix.length;
    double[][] inverseMatrix = matrixProduct(adjointMatrix(matrix), dim, dim, 1 / det);
    return inverseMatrix;

  }

  /**
   * matrix product
   */
  public static double[][] matrixProduct(double[][] matrix, int xDim, int yDim, double number) {
    if (matrix == null) {
      throw new RuntimeException("matrix cant be null");
    }
    double[][] productMatrix = new double[xDim][yDim];
    for (int i = 0; i < xDim; i++) {
      for (int j = 0; j < yDim; j++) {
        productMatrix[i][j] = matrix[i][j] * number;
      }
    }
    return productMatrix;
  }

  /**
   * compute the transposedMatrix
   */
  public static double[][] transposedMatrix(double[][] matrix) {
    if (matrix == null) {
      throw new RuntimeException("matrix cant be null");
    }
    double[][] transposedMatrix = new double[matrix.length][matrix.length];
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        transposedMatrix[j][i] = matrix[i][j];
      }
    }
    return transposedMatrix;
  }

  /**
   * print matrix
   */
  public static void printMatrix(double[][] matrix, String message) {
    StringBuilder sBuilder = new StringBuilder(message).append(":").append("\n");
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        sBuilder.append(matrix[i][j]);
        if (j == matrix[i].length - 1) {
          sBuilder.append("\n");
        } else {
          sBuilder.append("\t");
        }
      }
    }
    System.out.print(sBuilder.append("\n").toString());
  }

  /**
   * compute the rank of the matrix using Gaussian Elimination method
   */
  public static int rank(double[] matrix, int xDim, int yDim) {
    if (matrix == null) {
      throw new RuntimeException("matrix cant be null");
    }

    printMatrix(array2Matrix(matrix, xDim, yDim), "Original Matrix");

    guassianEstimate(matrix, xDim, yDim, 0);

    printMatrix(array2Matrix(matrix, xDim, yDim), "Row-reduced echelon matrix");

    return nonZeroRowCnt(matrix, xDim, yDim);
  }

  public static int nonZeroRowCnt(double[] matrix, int xDim, int yDim) {
    if (matrix == null) {
      throw new RuntimeException("matrix cant be null");
    }
    int nonZeroRow = 0;
    for (int i = 0; i < xDim; i++) {
      for (int j = 0; j < yDim; j++) {
        if (matrix[i * yDim + j] != 0) {
          nonZeroRow++;
          break;
        }
      }
    }
    return nonZeroRow;
  }

  /**
   * using Guassian Estimate method to convert matrix to a row-reduced echelon matrix so we can use
   * the echelon matrix computing the rank ,etc.
   */
  public static void guassianEstimate(double[] matrix, int xDim, int yDim, int startColumnIndex) {
    //first,choose the row number which first value is nonzero and max and before the column postion,all value is zero.
    //for example: if we want be the column 2 ,so [0 0 1 3] match the condition ,but [0 1 1 3] does not match.
    double maxValue = 0;
    double tempValue;
    int maxRowIndex = 0;
    for (int i = 0; i < xDim; i++) {
      tempValue = Math.abs(matrix[i * yDim + startColumnIndex]);
      // we also need to check the column before startColumnIndex elem is all zero ,if not ,skip it.
      boolean allZeroBefore = true;
      for (int j = 0; j < startColumnIndex; j++) {
        if (matrix[i * yDim + j] != 0) {
          allZeroBefore = false;
          break;
        }
      }
      if (tempValue > maxValue && allZeroBefore) {
        maxValue = tempValue;
        maxRowIndex = i;
      }
    }
    //all the first elem in each row is zero,we should consider the next column value.
    if (maxValue == 0) {
      // current column`s max element is zero.
//			System.out.println("current column "+startColumnIndex+" `s max element is zero!");
      if ((++startColumnIndex) <= yDim - 1) {
        guassianEstimate(matrix, xDim, yDim, startColumnIndex);
      }
    } else {
      int maxElemPosition = maxRowIndex * yDim + startColumnIndex;
      int mxDim = maxElemPosition / yDim;
      int myDim = maxElemPosition % yDim;

//			System.out.println("max value : "+maxValue +" and it`s position is :("+mxDim+","+myDim+")");

      //obtain other row with the relevant non-zero column
      double tmp;
      double factor;
      for (int i = 0; i < xDim; i++) {
        tmp = matrix[i * yDim + myDim];
        if (i != mxDim && tmp != 0) { //the non-zero row
          factor = (-1) * tmp / maxValue;
          for (int j = myDim; j < yDim; j++) {
            matrix[i * yDim + j] += (matrix[mxDim * yDim + j] * factor);
          }
        }
      }

      if ((++startColumnIndex) <= yDim - 1) {
        guassianEstimate(matrix, xDim, yDim, startColumnIndex);
      } else {
        //simplest the matrix such as :[0 0 7 0 ; 0 0 0 8;6 0 0 0] ,the simplest form is :[0 0 1 0; 0 0 0 1;1 0 0 0]
        double[] rowVector;
        for (int i = 0; i < xDim; i++) {
          rowVector = new double[yDim];
          for (int j = 0; j < yDim; j++) {
            rowVector[j] = matrix[i * yDim + j];
          }
          double mxGcd = maxGCD(rowVector);
          for (int j = 0; j < yDim; j++) {
            matrix[i * yDim + j] = matrix[i * yDim + j] / mxGcd;
          }
        }
      }
    }
  }

  /**
   * find the greatest common divisor (GCD)
   */
  public static double maxGCD(double[] array) {
    if (array == null) {
      throw new RuntimeException("array cant be null");
    }
    double minest = Double.MAX_VALUE;
    boolean allZero = true;
    for (int i = 0; i < array.length; i++) {
      if (array[i] != 0) {
        minest = Math.min(array[i], minest);
        allZero = false;
      }
    }
    if (allZero) {
      return 1;
    }
    Double[] divisors = findDivisors(minest); //divisor has been sorted desc

    double maxGCD = 1;
    for (int k = 0; k < divisors.length; k++) {
      boolean isMaxPosition = true;
      for (int j = 0; j < array.length; j++) {
        if (array[j] == 0) {
          continue;
        } else if (array[j] % divisors[k] != 0) {
          isMaxPosition = false;
        }
      }
      if (isMaxPosition) {
        maxGCD = divisors[k];
        break;
      }
    }
    return maxGCD;
  }

  /**
   * find divisor
   */
  public static Double[] findDivisors(double number) {
    List<Double> divisors = new ArrayList<Double>();
    if (number == 0) {
      return divisors.toArray(new Double[]{});
    }
    divisors.add(number);
    for (int i = 1; i <= Math.ceil(number / 2); i++) {
      if (number % i == 0) {
        divisors.add(new Double(i));
      }
    }
    // sort desc
    Collections.sort(divisors);
    Collections.reverse(divisors);

    return divisors.toArray(new Double[]{});
  }

  /**
   * find the max element which display in the matrix first
   */
  private static int maxElemPositionChoose(double[] matrix, int xDim, int yDim,
      int startColumnIndex) {
    double maxValue = 0;
    double tempValue;
    int maxRowIndex = 0;
    for (int i = 0; i < xDim; i++) {
      tempValue = Math.abs(matrix[i * yDim + startColumnIndex]);
      if (tempValue > maxValue) {
        maxValue = tempValue;
        maxRowIndex = i;
      }
    }
    //all the first elem in each row is zero,we should consider the next column value.
    if (maxValue == 0) {
      if ((++startColumnIndex) <= yDim - 1) {
        return maxElemPositionChoose(matrix, xDim, yDim, startColumnIndex);
      } else {
        // all columns max value is zero. that means its a zero matrix.
        return -1;
      }
    } else {
      System.out.println("max value : " + matrix[maxRowIndex * yDim + startColumnIndex]);
      return maxRowIndex * yDim + startColumnIndex;
    }
  }

  /**
   * array to matrix form
   */
  public static double[][] array2Matrix(double[] array, int xDim, int yDim) {
    if (array == null) {
      throw new RuntimeException("array cant be null");
    }
    double[][] matrix = new double[xDim][yDim];
    for (int i = 0; i < xDim; i++) {
      for (int j = 0; j < yDim; j++) {
        matrix[i][j] = array[i * yDim + j];
      }
    }
    return matrix;
  }

  public static void main(String args[]) {
    int[] ordinal = {1, 2, 3, 4, 5, 6, 7};
    List<int[]> initList = new ArrayList<int[]>();
    Set<int[]> collector = new HashSet<int[]>();
    initList.add(ordinal);
    System.out.println("begin");
    MatrixUtil.premut(initList, 0, collector);
    System.out.println("result size:" + collector.size());
    for (int[] value : collector) {
      System.out.print("[");
      for (int j = 0; j < value.length; j++) {
        System.out.print(value[j] + " ");
      }
      System.out.println("] \n");

    }

    System.out.println(MatrixUtil.inverseOrderNum(new int[]{5, 4, 3, 8}));

    double[][] matrix = {{1, 2, 3, 4}, {4, 5, 6, 7}, {7, 8, 9, 10}, {10, 11, 12, 13}};
    System.out.println("matrix determinant:" + det(matrix));

    System.out.println(Math.pow(-1, 0));

    //adjoint matrix
    double[][] matrix1 = {{1, 2, 3}, {4, 5, 6}, {6, 8, 9}};
    System.out.println("matrix determinant:" + det(matrix1));
    printMatrix(transposedMatrix(matrix1), "transposed matrix");
    printMatrix(adjointMatrix(matrix1), "adjoint matrix");

    printMatrix(inverseMatrix(matrix1), "inverse matrix");

    //calculate where is the max elements position(x,y) in matrix
//        double[] matrix2 = {0,0,-1,8,0,0,2,-11,0,0,2,-3};
    double[] matrix2 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    int index = maxElemPositionChoose(matrix2, 3, 4, 0);
    System.out.println("ROW:" + ((index / 4)) + " COLUMN:" + (index % 4));

//		double[] matrix3 = {1,2,3,4,5,6,6,8,9};
//		double[] matrix3 = {0,0,1,8,0,0,2,1,0,0,2,3,
//							1,2,5,4,2,5,6,7,4,3,2,4,
//							4,3,3,2,5,6,3,3,4,3,3,3};
    double[] matrix3 = {0, 0, 1, 8, 0, 0,
        1, 2, 5, 4, 2, 5,
        4, 3, 3, 2, 5, 6};
//		guassianEstimate(matrix3,3,12,0);
//		printMatrix(array2Matrix(matrix3,3,12),"row-reduced echelon matrix");
//
//		Double[] divisors = findDivisors(8);
//		for(int i =0;i<divisors.length;i++){
//			System.out.print(divisors[i]+",");
//		}
//		System.out.println();
//		System.out.println(maxGCD(new double[]{0, 0, 8, 0}));

    System.out.println("rank : " + rank(matrix3, 3, 6));
  }

}
