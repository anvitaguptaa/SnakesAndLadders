/*
 * Purpose: Represents the game board for a game of Snakes and Ladders
 * Author: Anvita Gupta
 * Created On: 16/01/20
 */

public class GameBoard {

	private int[] tile;
	// Array that holds the players of the game
	private Opponent[] opponents;

	public GameBoard() {
		// Game board array is initialized, holding the lengths and locations of snakes and ladders
		tile= new int [100];
		tile[2]= 48;
		tile[5]= 21;
		tile[19]= 50;
		tile[24]= -20;
		tile[33]= -33;
		tile[35]= 19;
		tile[46]= -28;
		tile[62]= 32;
		tile[64]= -13;
		tile[67]= 30;
		tile[86]= -30;
		tile[90]= -30;
		tile[98]= -30;
	    
	    // Creates opponents
	    opponents = UserInterface.createOpponents();
	}
	
	// Represents the roll of a single six-sided die
	public int roll() {
		return (int)(6 * Math.random() + 1);
	}
	
	// Represents the play of a player's turn
	public void playTurn(Opponent opponent) {
		opponent.nextPlay();
		
		// Times the amount of time spent on the player's turn
		double startTime = System.currentTimeMillis();
		 
		// Outputs instructions to the player
		UserInterface.nextPlay(opponent);
		int rollNum = roll();
		
		// Displays roll results
		System.out.println(opponent.getName() + " you have rolled a " + rollNum + ". ");
		int currentLoc= opponent.currentLocation()+rollNum;
		 
		// Assesses if the player has landed past or on the winning space
		if(currentLoc >= 99) {
			System.out.println("You have landed on, or past, the 100th space.");
		  
			// Updates player location
			opponent.nextLocation(currentLoc);
			return;
		}
		
		// Checks if opponent has landed on snake or ladder
		if(tile[currentLoc] != 0) {
			// Prints information for user
			if(tile[currentLoc] > 0) {
				System.out.println("Congratulations, you landed on tile " + (currentLoc + 1) + ". This is a ladder tile. ");
			}
		     
			else if(tile[currentLoc] < 0) {
				System.out.println("You landed on tile " + (currentLoc + 1) + ". This is a snake tile. ");
			}
		      
			//Adjusts position if landed on ladder or snake tile
			currentLoc+=tile[currentLoc];
		}
		 
		// Prompts opponent with information of current location;
		System.out.println("You are now on tile " + (currentLoc+1) + ".");
		 
		// Calculates total time
		double totalTime = (System.currentTimeMillis() - startTime);
		    
		// Updates opponent information
		opponent.nextLocation(currentLoc);
		opponent.increaseTime(totalTime);
		System.out.println(opponent.getName() + " you have spent "  + opponent.getTurnTime() + " seconds playing so far.");
		System.out.println();
	}
	
	// The entire game runs until a player has won
	public int playGame(int currentOpponent, int turnNum) {
		// Resets opponent turn once everyone has played their turn
		if(currentOpponent >= opponents.length) {
			currentOpponent = 0;
			turnNum++;
		}
	    
		// Plays the sequential opponents turn
		playTurn(opponents[currentOpponent]);
	    
		// Checks if an opponent has won.
		if(opponents[currentOpponent].currentLocation() >= 99) {
			// Sets the points for all players
			for(Opponent opponent:opponents) {
				opponent.setPoints();
			}
	      
			// Returns the index of the winner
			return currentOpponent;
		}
	    
		// Starts the next player's turn
		return playGame(currentOpponent + 1, turnNum);
	}
	
	// Main method that manages the overall snakes and ladders game play
	public static void main(String[] args) {
		// Imports data stored in the XML
		OpponentXML.importXML(); 
	 
		// Prompts user with start menu
		UserInterface.start();
	    
		// Creates game board object
		GameBoard game= new GameBoard();
	    
	   // Calculates total game time
		double startTime=System.currentTimeMillis();
	   
	    // Gets the winning opponent
		int winner = game.playGame(0, 1);
		double totalTime=(System.currentTimeMillis()-startTime)/1000;
	    
		// Displays the results of the game
		UserInterface.showResults(game.opponents, winner, totalTime);
	    
		// Saves player data from the game played
		OpponentXML.addPlayers(game.opponents);
	}
}

