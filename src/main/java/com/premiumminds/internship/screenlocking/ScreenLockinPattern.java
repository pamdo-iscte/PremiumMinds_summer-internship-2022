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
    private static final Map<Integer, int[]> tabuleiroVizinhos = new HashMap<>();
    private static final Map<Integer, int[]> passarPorCima = new HashMap<>();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    static {
        tabuleiroVizinhos.put(1, new int[]{2, 4, 5, 6, 8});
        tabuleiroVizinhos.put(3, new int[]{2, 6, 5, 4, 8});
        tabuleiroVizinhos.put(9, new int[]{6, 8, 5, 4, 2});
        tabuleiroVizinhos.put(7, new int[]{4, 8, 5, 6, 2});
        tabuleiroVizinhos.put(2, new int[]{1, 3, 5, 4, 6, 7, 9});
        tabuleiroVizinhos.put(6, new int[]{3, 9, 5, 2, 8, 7, 1});
        tabuleiroVizinhos.put(8, new int[]{7, 9, 5, 4, 6, 3, 1});
        tabuleiroVizinhos.put(4, new int[]{1, 7, 5, 2, 8, 3, 9});
        tabuleiroVizinhos.put(5, new int[]{1, 2, 3, 4, 6, 7, 8, 9});

        passarPorCima.put(1, new int[]{3, 2, 9, 5, 7, 4});
        passarPorCima.put(3, new int[]{1, 2, 7, 5, 9, 6});
        passarPorCima.put(9, new int[]{3, 6, 1, 5, 7, 8});
        passarPorCima.put(7, new int[]{1, 4, 3, 5, 9, 8});
        passarPorCima.put(2, new int[]{8, 5});
        passarPorCima.put(8, new int[]{2, 5});
        passarPorCima.put(4, new int[]{6, 5});
        passarPorCima.put(6, new int[]{4, 5});
    }
 /**
  * Method to count patterns from firstPoint with the length
  * @param firstPoint initial matrix position
  * @param length the number of points used in pattern
  * @return number of patterns
  */
  public Future<Integer> countPatternsFrom(int firstPoint,int length) {
    return executor.submit(() -> {
        boolean[] visited = new boolean[9];
        return calcularNPadroes(visited, firstPoint, length);
    });
  }

    private int calcularNPadroes(boolean[] visitado, int firstPoint, int length) {
        if (length <= 0 || length > 9) return 0;
        if (length == 1) return 1;
        int resultado = 0;
        visitado[firstPoint - 1] = true;
        int[] vizinhos = tabuleiroVizinhos.get(firstPoint);
        for (int j = 0; j < vizinhos.length; ++j) {
            if (!visitado[vizinhos[j] - 1]) {
                resultado += calcularNPadroes(visitado, vizinhos[j], length - 1);
            }
        }
        int[] porCima = passarPorCima.get(firstPoint);
        if (porCima != null) {
            for (int k = 0; k < porCima.length / 2; k++) {
                int objetivo = porCima[k * 2];
                int condicao = porCima[k * 2 + 1];
                if (visitado[condicao - 1] && !visitado[objetivo - 1]) {
                    resultado += calcularNPadroes(visitado, objetivo, length - 1);
                }
            }
        }

        visitado[firstPoint - 1] = false;
        return resultado;
    }
}
