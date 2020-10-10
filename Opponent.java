/**
 * Purpose: Represents an opponent in a game of Snakes and Ladders
 * Author: Anvita Gupta
 * Created On: 16/01/20
 */

public class Opponent 
{
	// Fields are declared. 
	private int location;
	private String oppName;
	private double points;
	private double turnTime;
	private int plays;
	
	// Constructs a name given by the opponent.
	public Opponent(String str)
	{
		oppName = str;
		location = 0;
		points = 0;
		plays = 1;
	}
	
	// Returns the opponent's current position.
	public int currentLocation()
	{
		return location;
	}
	
	// Increases the play.
	public void nextPlay()
	{
		plays++;
	}
	
	// Updates opponent's location.
	public void nextLocation(int newLoc)
	{
		location = newLoc;
	}
	
	// Returns opponent's name.
	public String getName()
	{
		return oppName;
	}
	
	// Calculates opponent's points as the average number of turns per play.
	public void setPoints()
	{
		if(location > 99)
	    {
	      points = 99;
	    }
	    
	    points = (currentLocation()+1)/(double)plays;
	}
	
	// Returns points.
	public double getPoints()
	{
		return points;
	}
	
	// Returns play time in seconds.
	public double getTurnTime()
	{
		return turnTime / 1000;
	}
	
	// Adds the turn time to total play time. 
	public void increaseTime(double time)
	{
		turnTime += time;
	}
}
