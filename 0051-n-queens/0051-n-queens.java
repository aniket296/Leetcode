class Solution {
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> result = new ArrayList<>();
        if (n <= 0) {
            return result;
        }
        int[] colPlacements = new int[n];
        boolean[] cols = new boolean[n];
        boolean[] diag1 = new boolean[2 * n];
        boolean[] diag2 = new boolean[2 * n];
        backtrack(0, n, colPlacements, cols, diag1, diag2, result);
        return result;
    }
    private void backtrack(int row, int n, int[] colPlacements, boolean[] cols, 
                          boolean[] diag1, boolean[] diag2, List<List<String>> result) {
        if (row == n) {
            result.add(buildBoard(colPlacements, n));
            return;
        }
        for (int col = 0; col < n; col++) {
            int d1 = row - col + n;
            int d2 = row + col;
            if (cols[col] || diag1[d1] || diag2[d2]) {
                continue;
            }
            colPlacements[row] = col;
            cols[col] = true;
            diag1[d1] = true;
            diag2[d2] = true;
            backtrack(row + 1, n, colPlacements, cols, diag1, diag2, result);
            cols[col] = false;
            diag1[d1] = false;
            diag2[d2] = false;
        }
    }
    private List<String> buildBoard(int[] colPlacements, int n) {
        List<String> board = new ArrayList<>(n);
        for (int row = 0; row < n; row++) {
            char[] rowChars = new char[n];
            Arrays.fill(rowChars, '.');
            rowChars[colPlacements[row]] = 'Q';
            board.add(new String(rowChars));
        }
        return board;
    }
}