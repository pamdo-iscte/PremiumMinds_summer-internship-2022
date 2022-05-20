package com.premiumminds.internship.screenlocking;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by aamado on 05-05-2022.
 */
class ScreenLockinPattern implements IScreenLockinPattern {
    private static final Map<Integer, int[]> neighbours = new HashMap<>();
    private static final Map<Integer, int[]> hasToPassOver = new HashMap<>();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    static {
        neighbours.put(1, new int[]{2, 4, 5, 6, 8});
        neighbours.put(2, new int[]{1, 3, 5, 4, 6, 7, 9});
        neighbours.put(3, new int[]{2, 6, 5, 4, 8});
        neighbours.put(4, new int[]{1, 7, 5, 2, 8, 3, 9});
        neighbours.put(5, new int[]{1, 2, 3, 4, 6, 7, 8, 9});
        neighbours.put(6, new int[]{3, 9, 5, 2, 8, 7, 1});
        neighbours.put(7, new int[]{4, 8, 5, 6, 2});
        neighbours.put(8, new int[]{7, 9, 5, 4, 6, 3, 1});
        neighbours.put(9, new int[]{6, 8, 5, 4, 2});

        hasToPassOver.put(1, new int[]{3, 2, 9, 5, 7, 4});
        hasToPassOver.put(2, new int[]{8, 5});
        hasToPassOver.put(3, new int[]{1, 2, 7, 5, 9, 6});
        hasToPassOver.put(4, new int[]{6, 5});
        hasToPassOver.put(6, new int[]{4, 5});
        hasToPassOver.put(7, new int[]{1, 4, 3, 5, 9, 8});
        hasToPassOver.put(8, new int[]{2, 5});
        hasToPassOver.put(9, new int[]{3, 6, 1, 5, 7, 8});

    }

 /**
  * Method to count patterns from firstPoint with the length
  * @param firstPoint initial matrix position
  * @param length the number of points used in pattern
  * @return number of patterns
  */
  public Future<Integer> countPatternsFrom(int firstPoint,int length) {
    return executor.submit(() -> {
        boolean[] checkIfVisited = new boolean[9];
        return calculateNPatterns(checkIfVisited, firstPoint, length);
    });
  }

    private int calculateNPatterns(boolean[] checkIfVisited, int firstPoint, int length) {
        if (length <= 0 || length > 9) return 0;
        if (length == 1) return 1;
        int valueToBeReturned = 0;
        checkIfVisited[firstPoint - 1] = true;
        int[] adjacent = neighbours.get(firstPoint);
        for (int i : adjacent) {
            if (!checkIfVisited[i - 1]) {
                valueToBeReturned += calculateNPatterns(checkIfVisited, i, length - 1);
            }
        }
        int[] overRide = hasToPassOver.get(firstPoint);
        if (overRide != null) {
            for (int k = 0; k < overRide.length / 2; k++) {
                int demand = overRide[k * 2 + 1];
                int goal = overRide[k * 2];
                if (!checkIfVisited[goal - 1] && checkIfVisited[demand - 1]) {
                    valueToBeReturned += calculateNPatterns(checkIfVisited, goal, length - 1);
                }
            }
        }
        checkIfVisited[firstPoint - 1] = false;
        return valueToBeReturned;
    }
}
