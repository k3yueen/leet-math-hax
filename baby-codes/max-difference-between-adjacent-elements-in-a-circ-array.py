class Solution:
    def maxAdjacentDistance(self, nums: list[int]) -> int:
        # Circular array: last and first are neighbors, remember that.
        n = len(nums)
        max_diff = abs(nums[0] - nums[-1])  # wrap-around pair

        for i in range(1, n):
            diff = abs(nums[i] - nums[i - 1])
            max_diff = max(max_diff, diff)

        return max_diff
