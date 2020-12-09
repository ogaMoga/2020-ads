package ru.mail.polis.ads.graph1;

import ru.mail.polis.ads.SolveTemplate;

import java.io.*;
import java.util.*;

public final class Task2{

    static final int WHITE = 0,
            GRAY = 1,
            BLACK = 2;

    private static void solve(final FastScanner in, final PrintWriter out) {
        int n = in.nextInt();
        int m = in.nextInt();

        Map<Integer, Set<Integer>> graph = new HashMap<>();
        for (int i = 0; i <= n; i++) {
            graph.put(i, new HashSet<>());
        }

        for (int i = 0; i < m; i++) {
            int from = in.nextInt();
            int to = in.nextInt();
            graph.get(from).add(to);
            graph.get(to).add(from);
        }


        int[] color = new int[graph.size()];
        int minValue = Integer.MAX_VALUE;
        for (int i = 1; i < graph.size(); i++) {
            if (color[i] == WHITE) {
                int value = isCycle(graph, i, color, new Stack<Integer>(), Integer.MAX_VALUE);
                if (value != -1) {
                    if (value < minValue) minValue = value;
                }
            }
        }
        if (minValue == Integer.MAX_VALUE) {
            System.out.println("No");
        } else {
            System.out.println("Yes");
            System.out.println(minValue);
        }

    }

    private static int isCycle(Map<Integer, Set<Integer>> graph, int i, int[] color, Stack<Integer> stack, int previous) {
        int bestRes = -1;
        stack.push(i);
        color[i] = GRAY;
        for (Integer point : graph.get(i)) {
            if (point == previous) continue;

            if ((color[point] == GRAY)){
                int minValue = Integer.MAX_VALUE;
                int value;
                Stack<Integer> temp = new Stack<Integer>();
                do {
                    value = stack.pop();
                    temp.push(value);
                    if (value < minValue) minValue = value;
                } while(value != point);

                while(temp.size() > 0)
                    stack.push(temp.pop());

                if ((minValue != -1) && ((minValue < bestRes) || (bestRes == -1)))  bestRes = minValue;    // loop


            } else {
                int res = isCycle(graph, point, color, stack, i);
                if ((res != -1) && ((res < bestRes) || (bestRes == -1))) bestRes = res;    // loop
            }
        }
        color[i] = BLACK;
        stack.pop();
        return bestRes; // no loop
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