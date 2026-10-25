class Solution {
    public int lengthOfLongestSubstring(String s) {
        int[] lastIndex = new int[128];
        Arrays.fill(lastIndex, -1);
        int maxLength = 0;
        int left = 0;
        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);
            if (lastIndex[currentChar] >= left) {
                left = lastIndex[currentChar] + 1;
            }
            lastIndex[currentChar] = right;
            maxLength = Math.max(maxLength, right - left + 1);
        }
        return maxLength;
    }
}