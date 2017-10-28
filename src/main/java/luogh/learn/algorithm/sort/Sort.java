package luogh.learn.algorithm.sort;

import static luogh.learn.algorithm.utils.Util.swap;

import luogh.learn.algorithm.utils.Util;

/**
 * @author luogh
 */
public class Sort {

  public static void main(String[] args) throws Exception {
    Util.logWithSort(new int[]{10, 9, 8, 7, 6, 5, 4, 3, 2, 1}, Sort::quickSort);
    Util.logWithSort(new int[]{10, 9, 8, 7, 6, 5, 4, 3, 2, 1}, Sort::bubbleSort);
    Util.logWithSort(new int[]{10, 9, 8, 7, 6, 5, 4, 3, 2, 1}, Sort::insertionSort);
  }

  /**
   * 快速排序
   */
  public static void quickSort(int[] arr) {
    for (int i = 0; i < arr.length - 1; i++) {
      int min = i;
      for (int j = i + 1; j < arr.length; j++) {
        if (arr[min] > arr[j]) {
          min = j;
        }
      }
      if (min != i) {
        swap(arr, i, min);
      }
    }
  }

  /**
   * 冒泡排序
   */
  public static void bubbleSort(int[] arr) {
    for (int i = 0; i < arr.length - 1; i++) {
      boolean swapped = false;
      for (int j = 0; j < arr.length - i - 1; j++) {
        if (arr[j] > arr[j + 1]) {
          swap(arr, j, j + 1);
          swapped = true;
        }
      }
      if (!swapped) { // 如果一轮下来没有交换，说明顺序已经是排好序，则表示已经完成了排序
        break;
      }
    }
  }

  /**
   * 直接插入排序
   */
  public static void insertionSort(int[] arr) {
    for (int i = 1; i < arr.length; i++) {
      /*
       只在当前要插入的值小于已插入值序列的最大值的情况下需要循环比较，否则不用循环比较插入,当前值直接作
       为已排序队列中的最大值放置在最后即可
       */
      if (arr[i - 1] > arr[i]) {
        for (int j = i; j > 0; j--) {
        /*
          这里只能是倒序比较, 正序比较不能满足;
          如往 7 10 序列中插入5，如果从左往右两两比较，则结果为7 5 10, 而如果是逆序比较，则是5 7 10;
         */
          if (arr[j] < arr[j - 1]) {
            swap(arr, j, j - 1);
          }
        }
      }
    }
  }
}
