class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates);   
        backtrack(candidates, target, 0, new ArrayList<>(), 0, result);
        return result;
    }
    private void backtrack(int[] candidates, int target, int startIndex, 
                           List<Integer> currentList, int currentSum, List<List<Integer>> result) {
        if (currentSum == target) {
            result.add(new ArrayList<>(currentList));
            return;
        }
        for (int i = startIndex; i < candidates.length; i++) {
            if (currentSum + candidates[i] > target) {
                break;
            }
            currentList.add(candidates[i]);
            backtrack(candidates, target, i, currentList, currentSum + candidates[i], result);
            currentList.remove(currentList.size() - 1);
        }
    }
}