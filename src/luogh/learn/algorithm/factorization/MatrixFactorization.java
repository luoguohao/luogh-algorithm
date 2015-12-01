package luogh.learn.algorithm.factorization;

import java.util.Random;

/**
 * Created by hadoop on 2015/12/2 0002.
 */
public class MatrixFactorization {
    private static final int X_DIMENSIONS = 5;
    private static final int Y_DIMENSIONS = 4;
    private static final int K_DIMENSIONS = 2;
    private static final int ITERATE_TIMES = 5000;

    private static final float ALPHA_STEP = 0.0002F;
    private static final float BETA = 0.02F;


    public static void main(String args[]) {
        float[][] matrixR = initPrototypeMatrix();
        float[][] matrixP = initDecompositionMatrix(X_DIMENSIONS,K_DIMENSIONS);
        float[][] matrixQ = initDecompositionMatrix(K_DIMENSIONS,Y_DIMENSIONS);

        // print
        printMatrix(matrixR,"Matrix R");
        printMatrix(matrixP,"Matrix P");
        printMatrix(matrixQ,"Matrix Q");

        executeMatrixFactorization(matrixR,matrixP,matrixQ);
    }

    private static void executeMatrixFactorization(float[][] R,float[][] P,float[][] Q){

    }
    private static float[][] initPrototypeMatrix(){
        float[][] originalMatrix = new float[X_DIMENSIONS][Y_DIMENSIONS];
        originalMatrix[0][0] = 5.0F;
        originalMatrix[0][1] = 3.0F;
        originalMatrix[0][2] = 0.0F; //this value is going to predict
        originalMatrix[0][3] = 1.0F;
        originalMatrix[1][0] = 4.0F;
        originalMatrix[1][1] = 0.0F; //this value is going to predict
        originalMatrix[1][2] = 0.0F; //this value is going to predict
        originalMatrix[1][3] = 1.0F;
        originalMatrix[2][0] = 1.0F;
        originalMatrix[2][1] = 1.0F;
        originalMatrix[2][2] = 0.0F; //this value is going to predict
        originalMatrix[2][3] = 5.0F;
        originalMatrix[3][0] = 1.0F;
        originalMatrix[3][1] = 0.0F; //this value is going to predict
        originalMatrix[3][2] = 0.0F; //this value is going to predict
        originalMatrix[3][3] = 4.0F;
        originalMatrix[4][0] = 0.0F; //this value is going to predict
        originalMatrix[4][1] = 1.0F;
        originalMatrix[4][2] = 5.0F;
        originalMatrix[4][3] = 4.0F;

        return originalMatrix;
    }

    /**
     * the decompostionMatrix initial datas is simply make randomly
     * @param xDim
     * @param yDim
     * @return
     */
    private static  float[][] initDecompositionMatrix(int xDim,int yDim){
        final Random random = new Random();
        float[][] compositionMatrix = new float[xDim][yDim];
        for(int i=0;i<xDim;i++) {
            for(int j=0;j<yDim;j++) {
                compositionMatrix[i][j] = random.nextFloat()/10.0F;
            }
        }

        return compositionMatrix;
    }

    /**
     * print matrix
     */
    private static void printMatrix(float[][] matrix,String message){
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
}
