class Solution {
    func maxTargetNodes(_ edges1: [[Int]], _ edges2: [[Int]], _ k: Int) -> [Int] {

      // Build an adjacency list from an edge list
        func buildGraph(_ edges: [[Int]]) -> [[Int]] {
            let n = edges.count + 1
            var graph = Array(repeating: [Int](), count: n)
            for edge in edges {
                let u = edge[0], v = edge[1]
                graph[u].append(v)
                graph[v].append(u)
            }
            return graph
        }

  // Count number of nodes reachable within a given depth using DFS
        func dfs(_ graph: [[Int]], _ node: Int, _ parent: Int, _ depth: Int) -> Int {
            if depth < 0 {
                return 0
            }
            var count = 1
            for neighbor in graph[node] {
                if neighbor != parent {
                    count += dfs(graph, neighbor, node, depth - 1)
                }
            }
            return count
        }


  // Build both trees as adjacency lists
        let graph1 = buildGraph(edges1)
        let graph2 = buildGraph(edges2)

  // Find the maximum number of nodes reachable in tree2 within (k-1) distance 
        var maxReachableInTree2 = 0
        if k > 0 {
            for i in 0..<graph2.count {
                maxReachableInTree2 = max(maxReachableInTree2, dfs(graph2, i, -1, k - 1))
            }
        }

// For each node in tree1 need to calculate reachable nodes within distance k &
// and add the best from tree2 to get total target nodes
        var result = [Int]()
        for i in 0..<graph1.count {
            let reachableInTree1 = dfs(graph1, i, -1, k)
            result.append(reachableInTree1 + maxReachableInTree2)
        }

        return result
    }
}
