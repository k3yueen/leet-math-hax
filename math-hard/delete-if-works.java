class Solution {
    public int maxDifference(String s, int k) {
        int n = s.length();
        int ans = Integer.MIN_VALUE;

        //Enumerate all ordered pairs of distinct digits a≠b
        for (char a = '0'; a <= '4'; a++) {
            for (char b = '0'; b <= '4'; b++) {
                if (a == b) continue;

                //minDiff[parityA][parityB] = minimum delta (count_a - count_b) 
                //at a valid starting prefix with given parities
                int[][] minDiff = {
                    {Integer.MAX_VALUE / 2, Integer.MAX_VALUE / 2},
                    {Integer.MAX_VALUE / 2, Integer.MAX_VALUE / 2}
                };

                int countA = 0, countB = 0, left = 0;
                //prefix deltas stored for each index
                int[] prefixA = new int[n + 1];
                int[] prefixB = new int[n + 1];

                //Build prefix sums
                for (int i = 0; i < n; i++) {
                    prefixA[i + 1] = prefixA[i] + (s.charAt(i) == a ? 1 : 0);
                    prefixB[i + 1] = prefixB[i] + (s.charAt(i) == b ? 1 : 0);
                }

                for (int right = 0; right < n; right++) {
                    //Expand right
                    countA = prefixA[right + 1];
                    countB = prefixB[right + 1];

                    //Slide left until window invalid or minimal
                    while (right - left + 1 >= k
                        && prefixA[left] < countA
                        && prefixB[left] < countB) {
                        int pa = prefixA[left] % 2;
                        int pb = prefixB[left] % 2;
                        minDiff[pa][pb] = Math.min(minDiff[pa][pb], prefixA[left] - prefixB[left]);
                        left++;
                    }

                    //Attempt update ans using current suffix window ending here
                    int currDelta = countA - countB;
                    int paCurr = countA % 2;
                    int pbCurr = countB % 2;
                    ans = Math.max(ans, currDelta - minDiff[1 - paCurr][pbCurr]);
                }
            }
        }

        return ans;
    }
}
