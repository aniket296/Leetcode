class Solution {
    private static final long INF = (long) 1e15;
    public long minOperations(int[] nums, int x, int k) {
        int n = nums.length;
        long[] windowCosts = getWindowCosts(nums, x);
        Long[][] memo = new Long[n + 1][k + 1];
        return dp(0, k, x, windowCosts, n, memo);
    }
    private long dp(int i, int count, int x, long[] windowCosts, int n, Long[][] memo) {
        if (count == 0) return 0;
        if (i > n - count * x) return INF;
        if (memo[i][count] != null) return memo[i][count];
        long skip = dp(i + 1, count, x, windowCosts, n, memo);
        long pick = windowCosts[i] + dp(i + x, count - 1, x, windowCosts, n, memo);
        return memo[i][count] = Math.min(skip, pick);
    }
    private long[] getWindowCosts(int[] nums, int x) {
        int n = nums.length;
        long[] costs = new long[n - x + 1];
        TreeMap<Integer, Integer> lower = new TreeMap<>();
        TreeMap<Integer, Integer> upper = new TreeMap<>();
        long lowerSum = 0, upperSum = 0;
        int lowerSize = 0, upperSize = 0;
        for (int i = 0; i < n; i++) {
            if (lower.isEmpty() || nums[i] <= lower.lastKey()) {
                lower.merge(nums[i], 1, Integer::sum);
                lowerSum += nums[i];
                lowerSize++;
            } else {
                upper.merge(nums[i], 1, Integer::sum);
                upperSum += nums[i];
                upperSize++;
            }
            if (i >= x) {
                int outNum = nums[i - x];
                if (lower.containsKey(outNum)) {
                    lower.merge(outNum, -1, Integer::sum);
                    if (lower.get(outNum) == 0) lower.remove(outNum);
                    lowerSum -= outNum;
                    lowerSize--;
                } else {
                    upper.merge(outNum, -1, Integer::sum);
                    if (upper.get(outNum) == 0) upper.remove(outNum);
                    upperSum -= outNum;
                    upperSize--;
                }
            }
            if (lowerSize < upperSize) {
                int val = upper.firstKey();
                upper.merge(val, -1, Integer::sum);
                if (upper.get(val) == 0) upper.remove(val);
                upperSum -= val;
                upperSize--;
                lower.merge(val, 1, Integer::sum);
                lowerSum += val;
                lowerSize++;
            } else if (lowerSize - upperSize > 1) {
                int val = lower.lastKey();
                lower.merge(val, -1, Integer::sum);
                if (lower.get(val) == 0) lower.remove(val);
                lowerSum -= val;
                lowerSize--;
                upper.merge(val, 1, Integer::sum);
                upperSum += val;
                upperSize++;
            }
            if (i >= x - 1) {
                int median = lower.lastKey();
                costs[i - x + 1] = (long) median * lowerSize - lowerSum + upperSum - (long) median * upperSize;
            }
        }
        return costs;
    }
}