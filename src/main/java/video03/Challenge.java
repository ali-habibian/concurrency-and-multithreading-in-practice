package video03;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

public class Challenge {
    public static void main(String[] args) {
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        ForkJoinPool pool = ForkJoinPool.commonPool();

        SumTask task = new SumTask(numbers, 0, numbers.length - 1);
        Integer result = pool.invoke(task);

        System.out.println("Result of sum: " + result);
    }

    public static class SumTask extends RecursiveTask<Integer> {
        private final int[] numbers;
        private final int startInclusive;
        private final int endInclusive;

        private final int taskThreadsHold = 4;

        public SumTask(int[] numbers, int startInclusive, int endInclusive) {
            this.numbers = numbers;
            this.startInclusive = startInclusive;
            this.endInclusive = endInclusive;
        }

        @Override
        protected Integer compute() {
            if (endInclusive - startInclusive < taskThreadsHold) {
                return doCompute();
            }

            int midPoint = startInclusive + (endInclusive - startInclusive) / 2;
            SumTask leftTask = new SumTask(numbers, startInclusive, midPoint);
            SumTask rightTask = new SumTask(numbers, midPoint + 1, endInclusive);

            rightTask.fork();
            return leftTask.compute() + rightTask.join();
        }

        protected Integer doCompute() {
            return IntStream.rangeClosed(startInclusive, endInclusive)
                    .map(i -> numbers[i])
                    .sum();
        }
    }
}
