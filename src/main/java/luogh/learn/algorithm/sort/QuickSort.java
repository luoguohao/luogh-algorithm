package luogh.learn.algorithm.sort;

import java.util.Arrays;

public class QuickSort {
	private static int iter_counter = 0;
	public static void main(String args[]) {
//		int[] arrays = {6,1,2,7,9,3,4,5,10,8};
		int[] arrays = {4,1,2,5};
		sort(arrays,0,arrays.length-1);
	}
	
	public static void sort(int[] arrays,int beginIndex,int endIndex){
		if(beginIndex >= endIndex) return ;
		int refPosition = beginIndex;
		int endPosition = endIndex;
		int refValue = arrays[beginIndex]; // set reference value 
		while(beginIndex != endIndex) {
			//6,1,2,7,9,3,4,5,10,8
			// 第一个循环必须从右边开始，如果从左边开始，去查询大于参考值的值获取到的索引可能直接到了endIndex的位置。比如 4 1 2 5 
			// 此时的beginIndex = endIndex == 3.那么这样的话，第二个循环此时无法执行，于是序列变为了 5 1 2 4,此时
			while(arrays[endIndex] >= refValue && beginIndex < endIndex){
				endIndex --;
			}
						
			while(arrays[beginIndex] <= refValue && beginIndex < endIndex){
				beginIndex ++;
			}
			
			if(arrays[beginIndex] > arrays[endIndex]){
				//swap 
				int temp = arrays[beginIndex];
				arrays[beginIndex] = arrays[endIndex];
				arrays[endIndex] = temp;
				
				System.out.println("Change value in position:["+beginIndex+","+endIndex+"]" +" Arrays Value:"+Arrays.toString(arrays));
			}
		}
		
		// reset reference value position in array,for the next iterate
		int temp = arrays[beginIndex];
		arrays[beginIndex] = arrays[refPosition];
		arrays[refPosition] = temp;
		
		System.out.println("iter Times:"+(++iter_counter)+" Arrays:"+Arrays.toString(arrays));
		System.out.println("refValue:"+refValue+" refPosition:"+refPosition+ " endPostion:"+endPosition);
		sort(arrays,refPosition,beginIndex-1);
		sort(arrays,beginIndex+1,endPosition);
		
	}
	
}
