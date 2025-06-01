class Solution:
    def distributeCandies(self, n: int, limit: int) -> int:
        def ways(candies: int) -> int:
            return comb(candies + 2, 2) if candies >= 0 else 0

        limit_plus_one = limit + 1
        total = ways(n)
        total -= 3 * ways(n - limit_plus_one)
        total += 3 * ways(n - 2 * limit_plus_one)
        total -= ways(n - 3 * limit_plus_one)
        return total
