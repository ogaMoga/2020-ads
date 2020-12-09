package ru.mail.polis.ads.graph1;

import ru.mail.polis.ads.SolveTemplate;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public final class Task1 {

    static final int WHITE = 0,
                GRAY = 1,
                BLACK = 2;

    private static void solve(final FastScanner in, final PrintWriter out) {
        int n = in.nextInt();
        int m = in.nextInt();

        List<List<Integer>> graph = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            int from = in.nextInt();
            int to = in.nextInt();
            graph.get(from - 1).add(to - 1);
        }

        int[] color = new int[n];

        if(hasCycle(graph)) {
            System.out.println(-1);
        } else {
            Stack<Integer> result = new Stack<Integer>();

            for (int i = 0; i < n; i++) {
                if(color[i] == WHITE) {
                    topSort(graph, color, i, result);
                }
            }

            for (int i = 0; i < n; i++) {
                System.out.print(result.pop() + " ");
            }

        }


    }

    private static void topSort(List<List<Integer>> graph, int[] color, int i, Stack<Integer> result) {
        for (Integer point : graph.get(i)) {
            if (color[point] == WHITE) topSort(graph, color, point, result);
        }
        color[i] = BLACK;
        result.push(i + 1);
    }

    private static boolean hasCycle(List<List<Integer>> graph) {
        int[] color = new int[graph.size()];
        for (int i = 0; i < graph.size(); i++) {
            if ((color[i] == WHITE) && (isCycle(graph, i, color)))
                return true;
        }

        return false;
    }

    private static boolean isCycle(List<List<Integer>> graph, int i, int[] color) {
        color[i] = GRAY;
        for (Integer point : graph.get(i)) {
            if (color[point] == GRAY) return true;
            boolean res = isCycle(graph, point, color);
            if (res) {
                color[i] = BLACK;
                return res;
            }
        }
        color[i] = BLACK;
        return false;
    }


    private static class FastScanner {
        private final BufferedReader reader;
        private StringTokenizer tokenizer;

        FastScanner(final InputStream in) {
            reader = new BufferedReader(new InputStreamReader(in));
        }

        String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return tokenizer.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }

    public static void main(final String[] arg) {
        final FastScanner in = new FastScanner(System.in);
        try (PrintWriter out = new PrintWriter(System.out)) {
            solve(in, out);
        }
    }
}
