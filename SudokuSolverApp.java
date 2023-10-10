import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuSolverApp {
    private JFrame frame;
    private JTextField[][] grid;
    private JButton solveButton;
    private JButton clearButton;

    public SudokuSolverApp() {
        frame = new JFrame("Sudoku Solver");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        grid = new JTextField[9][9];
        JPanel sudokuPanel = new JPanel(new GridLayout(9, 9));

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                grid[row][col] = new JTextField(1);
                sudokuPanel.add(grid[row][col]);
            }
        }

        solveButton = new JButton("Solve");
        clearButton = new JButton("Clear");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(solveButton);
        buttonPanel.add(clearButton);

        frame.add(sudokuPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[][] puzzle = new int[9][9];

                for (int row = 0; row < 9; row++) {
                    for (int col = 0; col < 9; col++) {
                        try {
                            String text = grid[row][col].getText();
                            if (!text.isEmpty()) {
                                puzzle[row][col] = Integer.parseInt(text);
                            } else {
                                puzzle[row][col] = 0;
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(frame, "Invalid input in the Sudoku grid.");
                            return;
                        }
                    }
                }

                if (solveSudoku(puzzle)) {
                    for (int row = 0; row < 9; row++) {
                        for (int col = 0; col < 9; col++) {
                            grid[row][col].setText(Integer.toString(puzzle[row][col]));
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "No solution found for the Sudoku puzzle.");
                }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int row = 0; row < 9; row++) {
                    for (int col = 0; col < 9; col++) {
                        grid[row][col].setText("");
                    }
                }
            }
        });

        frame.pack();
        frame.setVisible(true);
    }

    public static boolean solveSudoku(int[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (isSafe(board, row, col, num)) {
                            board[row][col] = num;
                            if (solveSudoku(board)) {
                                return true;
                            }
                            board[row][col] = 0; // Backtrack
                        }
                    }
                    return false;
                }
            }
        }
        return true; // Sudoku solved
    }

    private static boolean isSafe(int[][] board, int row, int col, int num) {
        return !usedInRow(board, row, num) &&
               !usedInCol(board, col, num) &&
               !usedInBox(board, row - row % 3, col - col % 3, num);
    }

    private static boolean usedInRow(int[][] board, int row, int num) {
        for (int col = 0; col < 9; col++) {
            if (board[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    private static boolean usedInCol(int[][] board, int col, int num) {
        for (int row = 0; row < 9; row++) {
            if (board[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    private static boolean usedInBox(int[][] board, int startRow, int startCol, int num) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row + startRow][col + startCol] == num) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SudokuSolverApp();
            }
        });
    }
}
