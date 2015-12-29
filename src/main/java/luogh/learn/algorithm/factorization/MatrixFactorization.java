package luogh.learn.algorithm.factorization;

import java.util.Random;

/**
 * Created by hadoop on 2015/12/2 0002.
 */
public class MatrixFactorization {
    private static final int X_DIMENSIONS = 5;
    private static final int Y_DIMENSIONS = 4;
    private static final int K_DIMENSIONS = 3;
    private static final int ITERATE_TIMES = 5000000;

    private static final float ALPHA_STEP = 0.0002F;
    private static final float BETA = 0.0003F;
    private static final float LOSS_RATE = 0.03F;


    public static void main(String args[]) {
        float[][] matrixR = initPrototypeMatrix();
        float[][] matrixP = initDecompositionMatrix(X_DIMENSIONS,K_DIMENSIONS);
        float[][] matrixQ = initDecompositionMatrix(K_DIMENSIONS,Y_DIMENSIONS);

        // print
        printMatrix(matrixR,"Matrix R");
        printMatrix(matrixP,"Matrix P");
        printMatrix(matrixQ,"Matrix Q");

        System.out.println("begin to execute MatrixFactorization.");
        long startTime = System.currentTimeMillis();
        executeMatrixFactorization(matrixR,matrixP,matrixQ,K_DIMENSIONS,ITERATE_TIMES);
        long endTime = System.currentTimeMillis();
        System.out.println("execute MatrixFactorization succeed,cost time:"+(endTime-startTime)/1000.00f+"s");
    }

    private static void executeMatrixFactorization(float[][] R,float[][] P,float[][] Q,int kDims,long iterTimes){

        while(iterTimes>0){

            float tempVariance = 0.00f;
            for(int i=0;i<R.length;i++){
                for(int j=0;j<R[i].length;j++){

                    if(R[i][j]>0){ //original valid value
                        float tempR= 0.00f;
                        //calculate tempR=SUM(PikQkj)
                        for(int k=0;k<kDims;k++){
//                          System.out.println("P[i][k]:"+P[i][k]+" Q[k][j]:"+Q[k][j]);
                            tempR += P[i][k]*Q[k][j];
                            tempVariance += 0.5*BETA*(Math.pow(P[i][k],2)+Math.pow(Q[k][j],2));
                        }
                        // caculate Variance
                        tempVariance += Math.pow((R[i][j] - tempR),2);
                        //update p`,q`
                        for(int m=0;m<kDims;m++){
                            P[i][m] += ALPHA_STEP*(2*(R[i][j] - tempR)*Q[m][j] - BETA*P[i][m]);
                            Q[m][j] += ALPHA_STEP*(2*(R[i][j] - tempR)*P[i][m] - BETA*Q[m][j]);
                        }

                    }
                }
            }

            //after every iterate , check variance loss is expected
            if(tempVariance<LOSS_RATE){
                float[][] finalR = matrixMulti(P,Q);
                System.out.println("we alrealy get the final answer");
                printMatrix(P,"\t the final matrix P is:");
                printMatrix(Q,"\t the final matrix Q is:");
                printMatrix(finalR,"\t the final matrix R is:");
                break;
            } else {
                if(iterTimes % 100 == 0) {
                    System.out.println("final answer still not find yet, current iterateTimes is:"+(ITERATE_TIMES-iterTimes)+" and current" +
                            "Variance is :"+tempVariance);
                }

            }
            iterTimes--;
        }

    }

    private static float[][] matrixMulti(float[][] P,float [][] Q){
        int iDim = P.length;
        int kQDim = P[0].length;
        int kPDim = Q.length;
        int jDim = Q[0].length;
        float[][] R = new float[iDim][jDim];
        if(kPDim != kQDim){
            throw new RuntimeException("this two Matrix cant not multilate ,as its Dim is not equals");
        }

        for(int i=0;i<iDim;i++){
            for(int j=0;j<jDim;j++){
                for(int k=0;k<kQDim;k++){
                    R[i][j] += P[i][k]*Q[k][j];
                }
				R[i][j] = Math.abs(R[i][j]); // for the sake of negative number
            }
        }

        return R;
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
     * 0~10
     * @param xDim
     * @param yDim
     * @return
     */
    private static  float[][] initDecompositionMatrix(int xDim,int yDim){
        final Random random = new Random(1L);
        float[][] compositionMatrix = new float[xDim][yDim];
        for(int i=0;i<xDim;i++) {
            for(int j=0;j<yDim;j++) {
                compositionMatrix[i][j] = random.nextFloat()*10;
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
