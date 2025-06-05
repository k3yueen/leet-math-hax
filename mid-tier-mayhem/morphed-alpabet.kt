class Solution {
    private val parent = IntArray(26) { it } // Each char initially points to itself

    private fun find(x: Int): Int {
        if (parent[x] != x) {
            parent[x] = find(parent[x]) // Path compression
        }
        return parent[x]
    }

    private fun union(x: Int, y: Int) {
        val rootX = find(x)
        val rootY = find(y)
        //smallest letter = root of power. others? submit. (union.find().zen)
        if (rootX != rootY) {
            if (rootX < rootY) {
                parent[rootY] = rootX
            } else {
                parent[rootX] = rootY
            }
        }
    }

    fun smallestEquivalentString(s1: String, s2: String, baseStr: String): String {
        // phase 1: align the alphabet energies into harmony groups 
        for (i in s1.indices) {
            val a = s1[i] - 'a'
            val b = s2[i] - 'a'
            union(a, b)
        }

        // Step 2: flex the lex mojo by swapping chars with their tiniest vibe twins
        val result = StringBuilder()
        for (ch in baseStr) {
            val smallest = find(ch - 'a')
            result.append('a' + smallest)
        }

        return result.toString()
    }
}
