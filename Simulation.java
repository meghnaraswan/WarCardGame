/**
 *  A class that claculates for the total number of games played, avergage
 * battles, wars and doube wars played, and the minumum and maximum scores of
 * battles and wars played
 * @author Meghna Raswan
 */

import java.util.LinkedList;
import java.util.Arrays;

public class Simulation {
  /**member variables*/
  private LinkedList<Game> m_games = null;
  private int m_totalGames;
  private double m_avgBattles;
  private double m_avgWars;
  private double m_avgDoubleWars;
  private int m_maxBattles;
  private int m_minBattles;
  private int m_maxWars;
  private int m_minWars;

  /** Overloaded simulation constructor
  * @param numGames int representing the number of games the user decides
  * to play
  */
  public Simulation(int numGames) {
    m_totalGames = numGames;
    m_games = new LinkedList<Game>();
    m_avgBattles = 0.0;
    m_avgWars = 0.0;
    m_avgDoubleWars = 0.0;
    m_maxBattles = 0;
    m_minBattles = 0;
    m_maxWars = 0;
    m_minWars = 0;
  }

  /**default constructor*/
  public Simulation() {
      m_games = new LinkedList<Game>();
      m_totalGames = 1;
      m_avgBattles = 0.0;
      m_avgWars = 0.0;
      m_avgDoubleWars = 0.0;
      m_maxBattles = 0;
      m_minBattles = 0;
      m_maxWars = 0;
      m_minWars = 0;
  }

  /**
  * A public method, simulate, that does not return a value
  * It plays the specified number of games
  */
  public void simulate() {
    for (int i = 0; i < m_totalGames; i++) { //iterate over the total number of games played to play each game one by one
      Game g = new Game(); //creates a Game object
      g.play(); //calls the play() method onto the object
      m_games.add(g); //adds the object into the m_games LinkedList, starting from the 0th index to the total amount of games played index (so the list is as large as the number of games played)
      if (g.getPlayerOne().hasWon()){
        WarLogger.getInstance().logGameOutcome(i + 1, WarLogger.P1); //after a game is won
      }
      else if (g.getPlayerTwo().hasWon()){
        WarLogger.getInstance().logGameOutcome(i + 1, WarLogger.P2); //after a game is won
      }
    }
  }

  /**
  * A public method, calculate, that does not return a value
  * It computes the aggregate statistics from all games
  */
  public void calculate() {
    double sumBattles = 0.0; //initialize the total number of battles played in every game
    double sumWars = 0.0; //initialize the total number of wars played in every game
    double sumDoubleWars = 0.0; //initialize the total number of double wars played in every game
    int minBattles = 1000000; //initialize the minimum amount of battles played in each game to an obsurely large number so that it will be easier to compare with the actual statistics of the number of battles played in each game and find the minimum
    int maxBattles = -1; //initialize the maximum amount of battles played in each game to an obsurely large number so that it will be easier to compare with the actual statistics of the number of battles played in each game and find the maximum
    int minWars = 1000000; //initialize the minimum amount of wars played in each game to an obsurely large number so that it will be easier to compare with the actual statistics of the number of wars played in each game and find the minimum
    int maxWars = -1; //initialize the maximum amount of wars played in each game to an obsurely small number so that it will be easier to compare with the actual statistics of the number of wars played in each game and find the maximum
    for (int i = 0; i < m_games.size(); i++) { //iterate through the linkedlist size, m_games, to calculate for the total number of battle, wars and double wars, and the min/max for both battles and wars, in each game
      Game g = m_games.get(i); //using the .get() method for the Game, g, object to get the element present in this list at a given specific index of the list, m_games
      sumBattles += g.getNumBattles(); //calling the getNumBattles() method in the Game class to add the number of battles in the current game to the sumBattles variable
      sumWars += g.getNumWars(); //calling the getNumWars() method in the Game class to add the number of wars in the current game to the sumWars variable
      sumDoubleWars += g.getNumDoulbeWars(); //calling the getNumDoulbeWars() method in the Game class to add the number of double wars in the current game to the sumDoubleWars variable
      if(g.getNumBattles() < minBattles) { //if the number of battles in the current game is less than the initialized minBattles of 1000000, (or the other battles in other games during this iteration), then minBattles is reassigned to the current number of battles in the game
        minBattles = g.getNumBattles();
      }
      if(g.getNumBattles() > maxBattles) { //if the number of battles in the current game is more than the initialized maxBattles of -1, (or the other battles in other games during this iteration), then maxBattles is reassigned to the current number of battles in the game
        maxBattles = g.getNumBattles();
      }
      if(g.getNumWars() < minWars) { //if the number of wars in the current game is less than the initialized minBattles of 1000000, (or the other wars in other games during this iteration), then minWars is reassigned to the current number of wars in the game
        minWars = g.getNumWars();
      }
      if(g.getNumWars() > maxWars) { //if the number of battles in the current game is more than the initialized maxWars of -1, (or the other battles in other games during this iteration), then maxWars is reassigned to the current number of wars in the game
        maxWars = g.getNumWars();
      }

    }
    m_avgBattles = sumBattles/m_games.size(); //average number of battles played is calculated by the the total number of battles played in every game, which we previously calculated for, divided by the m_games linkedlist size(whcih is the number of games played)
    m_avgWars = sumWars/m_games.size(); //average number of wars played is calculated by the the total number of wars played in every game, which we previously calculated for, divided by the m_games linkedlist size(whcih is the number of games played)
    m_avgDoubleWars = sumDoubleWars/m_games.size(); //average number of double wars played is calculated by the total number of double wars played in every game, which we previously calculated for, divided by the m_games linkedlist size(whcih is the number of games played)
    m_minBattles = minBattles; //assigning the member variables to the calculated minimum battles out of all of the games played
    m_maxBattles = maxBattles; //assigning the member variables to the calculated maximum battles out of all of the games played
    m_minWars = minWars; //assigning the member variables to the calculated minimum wars out of all of the games played
    m_maxWars = maxWars; //assigning the member variables to the calculated maximum wars out of all of the games played
  }

  /**
  * A public method, report, that does not return a value
  * It prints the required statistics to the screen in a nicely-formatted manner
  */
  public void report() {
    System.out.println(String.format("Total number of games: %d", m_totalGames));
    System.out.println(String.format("Average number of battles per game: %.2f", m_avgBattles));
    System.out.println(String.format("Average number of wars per game: %.2f", m_avgWars));
    System.out.println(String.format("Average number of double wars per game: %.2f", m_avgDoubleWars));
    System.out.println(String.format("Max number of battles in a game: %d", m_maxBattles));
    System.out.println(String.format("Min number of battles in a game: %d", m_minBattles));
    System.out.println(String.format("Max number of wars in a game: %d", m_maxWars));
    System.out.println(String.format("Min number of wars in a game: %d", m_minWars));
  }

  /**
  * Simulation class serves as a driver class to the Card, Deck, Player, Game,
  * and WarLogger classes
  * @author Meghna Raswan
  * @param args command-line arguments
  * @see Simulation#simulate()
  * @see Simulation#calculate()
  * @see Simulation#report()
  */
  public static void main(String[] args) {
    int n = 0; //n is the number of games
    //helps take a command line argument, n, and converts it into int type
    if (args.length > 0) {
      n = Integer.parseInt(args[0]);
    }

    System.out.println(String.format("Running simulation with %d Games...", n)); //shows the amount of games the program will run
    Simulation simulation = new Simulation(n); //creating a simulation object, passing the number of games, n, as a parameter
    simulation.simulate(); //calling the simulate() method
    simulation.calculate(); //calling the calculate() method
    simulation.report(); //calling the report() method

    WarLogger.getInstance().release(); //closes the logger and cleans up.
  }
}
