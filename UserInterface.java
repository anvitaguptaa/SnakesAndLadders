/*
 * Purpose: Manages program interactions with the players
 * Author: Anvita Gupta
 * Created On: 16/01/20
 */

import java.util.Scanner;

// Manages interactions with the opponents
public class UserInterface {
	// Constructs scanner
	private static Scanner s  = new Scanner(System.in);
	
	// Creates opponents based on user input
	public static Opponent[] createOpponents() {
		// Makes a opponent array of the size of the inputted number of players
		System.out.println("Please print the number of opponents that will be playing the game,"
							+ " and press the \"Enter\" key");
		int numOpponents = s.nextInt();
		s.nextLine();
		Opponent[] opponents = new Opponent[numOpponents];
		
		// Asks for the name of each opponent
		String name;
		for(int n = 0; n < numOpponents; n++) {
			System.out.println("Please type only the name of opponent " + (n + 1));
			name = s.nextLine();
			opponents[n] = new Opponent(name);
		}
		
		// Returns the opponent array
		return opponents;
	}
	
	// Prompts the opponent to start their turn
	public static void nextPlay(Opponent opponent) {
		System.out.println("It is now " + opponent.getName() + "'s turn");
	    System.out.println("Please press the \"Enter\" key to roll the die");
	    s.nextLine();
	}
	
	// Displays the results of the game
	public static void showResults(Opponent[] opponents, int winner, double totalTime) {
		// Displays score and time
		System.out.println("Congratulations! " + opponents[winner].getName() 
							+ " has won the game with a score of " + opponents[winner].getPoints() + "!" );
	    System.out.println(opponents[winner].getName()  + " took a total of " + opponents[winner].getTurnTime() + " seconds"
	        				+ " to win, out of the total game time of " + totalTime + " seconds.");
	    s.close();
	}
	
	// Prompts user input for the starting menu
	public static int startupMenu() {
	    System.out.println("Enter 1 to play Snakes and Ladders");
	    System.out.println("Enter 2 to see the game instructions");
	    System.out.println("Press 3 to see the game leaderboard");
	    return s.nextInt();
	}
	
	// Performs user selected option
	public static void start() {
		switch(startupMenu()) {
			// Continues to Snakes and Ladders game
			case 1:
				break;
				
			// Displays instructions then prompts the menu again
			case 2:
				gameInstructions();
				start();
				break;
				
			// Displays the user leader board then prompts the menu again
			case 3:
				OpponentXML.display();
				start();
				break;
		}
	}
	
	// Displays game instructions
	public static void gameInstructions()
	{
		System.out.println();
		System.out.println("The objective of the game is to be the first opponent to land on, or pass, the 100th tile of the gameboard.\n" 
				+ "Players will take turns rolling a die.\n" 
				+ "The number rolled on the die indicates the number of spaces a player must move on their turn.\n" 
				+ "The first opponent to land on, or past, tile 100 is the winner; other opponents lose and the game ends.\n" 
				+ "There are no levels in this game, only one round until an opponent wins.\n" 
				+ "In moving along the board, there is an exception when you land on a snake or ladder tile.\n" 
				+ "Snake tiles will drop you to a lower tile, depending on how long the snake is.\n" 
				+ "Ladder tiles will allow you to climb to a higher tile, depending on the length of the ladder.\n" 
				+ "Your final score will represent the average amount of tiles you moved per turn.\n" 
				+ "Each of your turns will be individually timed, along with the total time of game play.\n" 
				+ "Good Luck!\n");
	}
}

