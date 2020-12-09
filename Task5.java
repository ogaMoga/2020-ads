package ru.mail.polis.ads.graph1;

import ru.mail.polis.ads.SolveTemplate;

import java.io.*;
import java.util.*;

public final class Task5 {


    private static void solve(final FastScanner in, final PrintWriter out) {
        int n = in.nextInt();
        int m = in.nextInt();
        int start = in.nextInt();
        int finish = in.nextInt();

        Map<Integer, Set<Integer>> graph = new HashMap<>();
        for (int i = 1; i < n + 1; i++) {
            graph.put(i, new HashSet<>());
        }

        for (int i = 0; i < m; i++) {
            int from = in.nextInt();
            int to = in.nextInt();
            graph.get(from).add(to);
            graph.get(to).add(from);
        }

        Queue<Integer> que = new LinkedList<>();
        que.add(start);

        boolean[] visited = new boolean[n + 1];
        int[] last = new int[n + 1];
        last[start] = -1;

        visited[start] = true;
        int current = start;
        while((!que.isEmpty()) && (current != finish)){
            current = que.poll();
            for (int nextElem : graph.get(current)) {
                if(!visited[nextElem]) {
                    que.add(nextElem);
                    last[nextElem] = current;
                    visited[nextElem] = true;
                }

            }
        }
        if(!visited[finish]) {
            System.out.println(-1);
        } else {
            current = finish;
            List<Integer> res = new ArrayList<>();
            for (int i = finish; i != -1; i=last[i]) {
                res.add(i);

            }
            System.out.println(res.size() - 1);

            for (int i = res.size() - 1; i >= 0; i--) {
                System.out.print(res.get(i) + " ");
            }
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
