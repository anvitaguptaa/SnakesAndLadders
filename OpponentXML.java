/*
 * Purpose: Stores the highscores of all players who play the game
 * Author: Anvita Gupta
 * Created On: 16/01/20
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;
import nu.xom.Serializer;

// Stores and sorts player high scores.
public class OpponentXML {
	private static Elements elements;
	private static Element leaderboard;
	private static Document doc;
  
	//Adds opponents to XML file
	public static void addPlayers(Opponent[] opponents) {
		//Loops for every opponent
		for(Opponent opponent:opponents) {
			boolean done = false;
     
			// Updates elements
			elements = leaderboard.getChildElements();
      
			//Loops elements
			int index=0;
			while(index < elements.size()) {
				//Checks if the player data is already in XML
				if(opponent.getName().equals(elements.get(index).getFirstChildElement("name").getValue())) {
					//Does not save game data if new high score does not exceed old high score
					if(opponent.getPoints() <= Double.parseDouble(elements.get(index).getFirstChildElement("highscore").getValue())) {
						done = true;
					}
					
					//Removes old high score if it is lower than the new game score
					else {
						leaderboard.removeChild(index);
					}
				}
				
				index++;
			}
      
			// Updates the elements
			elements = leaderboard.getChildElements();
      
			// Loops through the XML while checking if it is complete
			for(int i=0; i < elements.size() && !done; i++) {
   
				// Checks if the opponent's score is higher than the element being assessed
				if(opponent.getPoints() >= Double.parseDouble(elements.get(i).getFirstChildElement("highscore").getValue())) {
					// Inserts the opponent data at the correct index in the XML
					Element name = new Element("name");
					name.appendChild(opponent.getName());
					
					Element highScore= new Element("highscore");
					highScore.appendChild(String.valueOf(opponent.getPoints()));
					
					Element person= new Element("person");
					Element time= new Element("time");
					time.appendChild(String.valueOf(opponent.getTurnTime()));
					person.appendChild(name);
					person.appendChild(highScore);
					person.appendChild(time);
					
					leaderboard.insertChild(person, i);

					// XML is complete
					done = true;
				}
			}
      
			// Adds an opponent player if XML was empty or player had the lowest score
			if(elements.size() == 0 || !done) {
				Element name = new Element("name");
				name.appendChild(opponent.getName());
				
				Element highScore= new Element("highscore");
				highScore.appendChild(String.valueOf(opponent.getPoints()));
				
				Element person= new Element("person");
				Element time= new Element("time");
				time.appendChild(String.valueOf(opponent.getTurnTime()+" seconds"));
				person.appendChild(name);
				person.appendChild(highScore);
				person.appendChild(time);
				
				leaderboard.appendChild(person);
			}
		}
		
		// Saves the XML
		save();
	}
  
	// Saves the XML
	public static void save() {
		// Writes the document to a new XML file
		try {
			FileWriter xmlfile = new FileWriter("leaderboard.xml");
			BufferedWriter writer = new BufferedWriter(xmlfile);
			writer.write(doc.toXML());
			writer.close();
		}
    
		// Catches the exception
		catch (IOException e) {
			
		}
    
		// Displays the XML
		display();
	}
  
	// XML is displayed with serializer
	public static void display() {
		// Informs the user that the XML leaderboard is printed
		System.out.println("The leaderboard with the high scores of all players "
							+ "who have played is printed here:");
		System.out.println();
    
		// Prints XML with a specified style
		try {
			Serializer serializer = new Serializer(System.out);
			serializer.setIndent(4);
			serializer.setMaxLength(64);
			serializer.write(doc);
		}
  
		// Catches and prints exception
		catch (IOException ex) {
			System.err.println(ex);
		}
	}
  
	// Imports the XML
	public static void importXML() {
		// Creates the XML file and builder object
		File leaderboardFile = new File("leaderboard.xml");
		Builder builder= new Builder();
    
		// Finds the leaderboard.xml to get elements
		try {
			doc = builder.build(leaderboardFile);
			leaderboard= doc.getRootElement();
			elements = leaderboard.getChildElements();
		}
    
		//Creates XML file if it does not already exist
		catch(IOException e) {
			leaderboard= new Element("leaderboard");
			doc= new Document(leaderboard);
			elements = leaderboard.getChildElements();
		}
    
		// Catches parsing exception
		catch(ParsingException e) {
			
		}   
	}
}

