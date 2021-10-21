package video02;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;

public class PickFruitsWithInvokeAll {
    public static void main(String[] args) {
        AppleTree[] appleTrees = AppleTree.newAppleTree(6);

        Callable<Void> applePicker1 = createApplePicker(appleTrees, 0, 2, "Alex");
        Callable<Void> applePicker2 = createApplePicker(appleTrees, 2, 4, "Bob");
        Callable<Void> applePicker3 = createApplePicker(appleTrees, 4, 6, "Carol");

        ForkJoinPool.commonPool().invokeAll(Arrays.asList(applePicker1, applePicker2, applePicker3));

        System.out.println();
        System.out.println("All fruits collected!");
    }

    public static Callable<Void> createApplePicker(AppleTree[] appleTrees, int fromIndexInclusive, int toIndexExclusive,
                                                   String workerName) {
        return () -> {
            for (int i = fromIndexInclusive; i < toIndexExclusive; i++) {
                appleTrees[i].pickApples(workerName);
            }
            return null;
        };
    }
}
