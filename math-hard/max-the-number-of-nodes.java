class Solution {
    public int[] maxTargetNodes(int[][] edges1, int[][] edges2) {
        int n = edges1.length + 1;
        int m = edges2.length + 1;

        List<Integer>[] graph1 = buildGraph(edges1, n);
        List<Integer>[] graph2 = buildGraph(edges2, m);

        boolean[] parity1 = new boolean[n];
        boolean[] parity2 = new boolean[m];

        int even1 = dfs(graph1, 0, -1, parity1, true);
        int even2 = dfs(graph2, 0, -1, parity2, true);

        int odd1 = n - even1;
        int odd2 = m - even2;

        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            int tree1Count = parity1[i] ? even1 : odd1;
            int tree2Count = Math.max(even2, odd2);
            result[i] = tree1Count + tree2Count;
        }

        return result;
    }

    private List<Integer>[] buildGraph(int[][] edges, int size) {
        List<Integer>[] graph = new ArrayList[size];
        for (int i = 0; i < size; i++)
            graph[i] = new ArrayList<>();
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1];
            graph[u].add(v);
            graph[v].add(u);
        }
        return graph;
    }

    private int dfs(List<Integer>[] graph, int node, int parent, boolean[] parity, boolean isEven) {
        int count = isEven ? 1 : 0;
        parity[node] = isEven;
        for (int neighbor : graph[node]) {
            if (neighbor != parent)
                count += dfs(graph, neighbor, node, parity, !isEven);
        }
        return count;
    }
}
