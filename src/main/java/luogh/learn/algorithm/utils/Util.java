package luogh.learn.algorithm.utils;

import static java.util.stream.Collectors.joining;

import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @author luogh
 */
public class Util {

  public static void swap(int[] arr, int i, int j) {
    Preconditions.checkState(i >= 0 && i < arr.length && j >= 0 && j < arr.length, "Invalid index");
    int tmp = arr[i];
    arr[i] = arr[j];
    arr[j] = tmp;
  }

  public static void logWithSort(int[] arr, Consumer<int[]> consumer) {
    Stopwatch tick = Stopwatch.createStarted();
    StringBuilder str = new StringBuilder()
        .append("Origin data:")
        .append(Arrays.stream(arr).mapToObj(String::valueOf).collect(joining(",")))
        .append("\n");

    consumer.accept(arr);

    long cost = tick.stop().elapsed(TimeUnit.MILLISECONDS);
    str.append("Sorted data:")
        .append(Arrays.stream(arr).mapToObj(String::valueOf).collect(joining(",")))
        .append("\n")
        .append("Cost time ")
        .append(cost)
        .append(" ms");
    System.out.println(str.toString());
  }
}
