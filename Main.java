/**
* Lead Author(s):
* @author Charles Des-Bordes
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
* Version: 2025-11-04
*/
package tictactoe;

/**
 * Entry point for the Tic-Tac-Toe application.
 * This class launches the graphical user interface (GUI) on the
 * Swing Event Dispatch Thread (EDT) to ensure thread safety
 * when creating and interacting with Swing components.
 */
public class Main {
	/**
     * Main method, invoked when the program starts.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        // Start GUI safely on Swing thread
        javax.swing.SwingUtilities.invokeLater(() -> {
            new TicTacToeGUI(); // Initialize and display the GUI
        });
    }
}