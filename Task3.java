package ru.mail.polis.ads.graph1;

import ru.mail.polis.ads.SolveTemplate;

import java.io.*;
import java.util.*;

public final class Task3 {


    private static void solve(final FastScanner in, final PrintWriter out) {
        int n = in.nextInt();
        int m = in.nextInt();
        int start = 1;
        int[][] cost = new int[n + 1][n + 1];
        int[] distance = new int[n+1];
        for (int i = 0; i < n + 1; i++) {
            Arrays.fill(cost[i], 30000);
        }
        Arrays.fill(distance, 30000);
        int[] last = new int[n + 1];
        last[start] = -1;
        Map<Integer, Set<Integer>> graph = new HashMap<>();
        for (int i = 1; i < n + 1; i++) {
            graph.put(i, new HashSet<>());
        }
        distance[start] = 0;
        for (int i = 0; i < m; i++) {
            int from = in.nextInt();
            int to = in.nextInt();
            int curCost = in.nextInt();
            graph.get(from).add(to);
            cost[from][to] = Math.min(curCost, cost[from][to]);
        }

        for (int i = 0; i < n - 1; i++) {
            for (Integer current : graph.keySet()) {
                for (int nextElem : graph.get(current)) {
                    distance[nextElem] = Math.min(distance[nextElem], distance[current] + cost[current][nextElem]);
                }
            }
        }

        if (cost[1][1] < distance[1])
            distance[1] = cost[1][1];

        for (int i = 1; i <= n; i++) {
            System.out.print(distance[i] + " ");
        }

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
