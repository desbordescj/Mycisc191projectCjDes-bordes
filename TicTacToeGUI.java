/**
* Lead Author(s):
* @author cj; student ID
* @author Full name; student ID
* <<Add additional lead authors here>>
*
* Other Contributors:
* Full name; student ID or contact information if not in class
* <<Add additional contributors (mentors, tutors, friends) here, with contact information>>
*
* References:
* Morelli, R., & Walde, R. (2016).
* Java, Java, Java: Object-Oriented Problem Solving
* https://open.umn.edu/opentextbooks/textbooks/java-java-java-object-oriented-problem-solving
*
* <<Add more references here>>
*
* Version: 2025-11-13
*/
package tictactoe;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

/**
 * Graphical User Interface for the Tic-Tac-Toe game.
 * Provides a 3x3 grid of buttons for human interaction, displays game status,
 * allows undoing moves, and manages scores for wins, losses, and ties.
 * Integrates with {@link HumanPlayer} and {@link ComputerPlayer}.
 */
public class TicTacToeGUI extends JFrame {

    private static final long serialVersionUID = 1L;

    // GUI components
    private JButton[][] buttons = new JButton[3][3]; // 3x3 grid buttons
    private JLabel statusLabel;                     // Displays turn status
    private JLabel scoreLabel;                      // Displays score
    private JButton undoButton;                     // Undo last move button

    // Game logic
    private GameBoard board = new GameBoard();
    private MoveHistory history = new MoveHistory();
    private HumanPlayer human = new HumanPlayer('X');
    private ComputerPlayer computer = new ComputerPlayer('O');
    private boolean xTurn = true;                   // True if it's human's turn

    // Score tracking
    private int wins = 0, losses = 0, ties = 0;

    /**
     * Constructs and initializes the GUI components, layouts, and listeners.
     */
    public TicTacToeGUI() {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 1000));
        setLayout(new BorderLayout());

        // Status label
        statusLabel = new JLabel("Your turn (X)", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(statusLabel, BorderLayout.NORTH);

        // Difficulty selector for computer player
        String[] options = {"Easy", "Medium", "Hard"};
        JComboBox<String> difficultyBox = new JComboBox<>(options);
        difficultyBox.setSelectedItem("Medium");
        difficultyBox.addActionListener(e -> computer.setDifficulty(
                (String) difficultyBox.getSelectedItem()));
        add(difficultyBox, BorderLayout.NORTH);

        // Game grid panel
        JPanel grid = new JPanel(new GridLayout(3, 3));
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                JButton btn = new JButton("");
                btn.setFont(new Font("Arial", Font.BOLD, 40));
                final int row = r;
                final int col = c;
                btn.addActionListener(e -> handleClick(row, col));
                buttons[r][c] = btn;
                grid.add(btn);
            }
        }
        add(grid, BorderLayout.CENTER);

        // Bottom panel for scores and undo button
        JPanel bottomPanel = new JPanel(new GridLayout(2, 1));

        scoreLabel = new JLabel("Wins: 0 | Losses: 0 | Ties: 0", SwingConstants.CENTER);
        bottomPanel.add(scoreLabel);

        undoButton = new JButton("Undo Last Move");
        undoButton.setEnabled(false);
        undoButton.addActionListener(e -> undoLastMove());
        bottomPanel.add(undoButton);

        add(bottomPanel, BorderLayout.SOUTH);

        // New Game button
        JButton newGame = new JButton("New Game");
        newGame.setFont(new Font("Arial", Font.BOLD, 28));
        newGame.setPreferredSize(new Dimension(250, 80));
        newGame.addActionListener(e -> resetGame());
        add(newGame, BorderLayout.EAST);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Handles a human player's click on the grid.
     * @param row the row clicked
     * @param col the column clicked
     */
    private void handleClick(int row, int col) {
        if (!xTurn) return; // Ignore clicks if not human's turn

        try {
            if (board.placeMove(row, col, human.getSymbol())) {
                buttons[row][col].setText(String.valueOf(human.getSymbol()));
                history.addMove(row, col, human.getSymbol());
                undoButton.setEnabled(history.size() >= 1);

                if (checkWinner(human.getSymbol())) {
                    JOptionPane.showMessageDialog(this, "You win!");
                    wins++;
                    updateScore();
                    xTurn = false;
                    return;
                } else if (board.isTie()) {
                    JOptionPane.showMessageDialog(this, "It's a tie!");
                    ties++;
                    updateScore();
                    xTurn = false;
                    return;
                }

                xTurn = false;
                statusLabel.setText("Computer thinking...");
                // Schedule computer's turn with a short delay
                javax.swing.Timer t = new javax.swing.Timer(500, e -> cpuTurn());
                t.setRepeats(false);
                t.start();
            }
        } catch (InvalidMoveException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    /**
     * Handles the computer player's turn.
     */
    private void cpuTurn() {
        int[] move = computer.chooseMove(board);
        if (move != null) {
            try {
                board.placeMove(move[0], move[1], computer.getSymbol());
                buttons[move[0]][move[1]].setText(String.valueOf(computer.getSymbol()));
                history.addMove(move[0], move[1], computer.getSymbol());
                undoButton.setEnabled(history.size() >= 1);

                if (checkWinner(computer.getSymbol())) {
                    JOptionPane.showMessageDialog(this, "Computer wins!");
                    losses++;
                    updateScore();
                    xTurn = false;
                    return;
                } else if (board.isTie()) {
                    JOptionPane.showMessageDialog(this, "It's a tie!");
                    ties++;
                    updateScore();
                    xTurn = false;
                    return;
                }

                xTurn = true;
                statusLabel.setText("Your turn (X)");

            } catch (InvalidMoveException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Checks if a given symbol has won the game.
     *
     * @param symbol the symbol to check
     * @return true if the symbol has won, false otherwise
     */
    private boolean checkWinner(char symbol) {
        return board.checkWinner() == symbol;
    }

    /**
     * Undoes the last move made by both the computer and the human player.
     */
    private void undoLastMove() {
        if (history.size() < 2) return; // Need both moves to undo

        // Undo computer move
        int[] aiMove = history.removeLastMove();
        board.removeMove(aiMove[0], aiMove[1]);
        buttons[aiMove[0]][aiMove[1]].setText("");

        // Undo human move
        int[] playerMove = history.removeLastMove();
        board.removeMove(playerMove[0], playerMove[1]);
        buttons[playerMove[0]][playerMove[1]].setText("");

        xTurn = true;
        statusLabel.setText("Your turn (X)");
        undoButton.setEnabled(history.size() >= 1);
    }

    /**
     * Resets the game to its initial state.
     */
    private void resetGame() {
        board.resetBoard();
        history.clear();
        xTurn = true;
        statusLabel.setText("Your turn (X)");
        undoButton.setEnabled(false);

        for (int r = 0; r < 3; r++)
            for (int c = 0; c < 3; c++)
                buttons[r][c].setText("");
    }

    /**
     * Updates the score label with current wins, losses, and ties.
     */
    private void updateScore() {
        scoreLabel.setText("Wins: " + wins + " | Losses: " + losses + " | Ties: " + ties);
    }

    /**
     * Main method — starts the GUI on the Swing Event Dispatch Thread.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicTacToeGUI::new);
    }
}
