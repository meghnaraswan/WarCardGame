/**
 * A class that keeps track of the statistics of the game
 */

import java.util.LinkedList;
import java.util.Arrays;

public class Game {
  /**member variable*/
  private Deck m_deck;
  private Player m_playerOne;
  private Player m_playerTwo;
  private int m_numBattles;
  private int m_numWars;
  private int m_numDoulbeWars;
  // private int m_playerOneWon;
  // private int m_playerTwoWon;

  /**default constructor*/
  public Game() {
    m_numBattles = 0;
    m_numWars = 0;
    m_numDoulbeWars = 0;
    m_deck = new Deck(); //creating deck object
    m_playerOne = new Player(); //creating player object
    m_playerTwo = new Player(); //creating player object

    /**creating linked list of cards for players 1 and 2*/
    LinkedList<Card> playerOneCards = new LinkedList<Card>();
    LinkedList<Card> playerTwoCards = new LinkedList<Card>();
    for (int i = 0; i < 26; i++) { //this will distribute the cards evenly to 26 cards for each player
      Card c1 = m_deck.deal(); //calling the deal() method from Deck class to remove card from the deck
      playerOneCards.add(c1); //adding the removed card to the list playerOneCards
      Card c2 = m_deck.deal(); //calling the deal() method from Deck class to remove card from the deck
      playerTwoCards.add(c2); //adding the removed card to the list playerTwoCards
    }
    /**calling the collect() method in te Player class to take the collection of the cards and add it to their own list of cards*/
    m_playerOne.collect(playerOneCards);
    m_playerTwo.collect(playerTwoCards);
  }

  /**accessor for number of battles*/
  public int getNumBattles() {
    return m_numBattles;
  }

  /**accessor for number of wars*/
  public int getNumWars() {
    return m_numWars;
  }

  /**accessor for number of double wars*/
  public int getNumDoulbeWars() {
    return m_numDoulbeWars;
  }

  /**accessor for player 1*/
  public Player getPlayerOne() {
    return m_playerOne;
  }

  /**accessor for player 2*/
  public Player getPlayerTwo() {
    return m_playerTwo;
  }

  /**
  * A public method, play, that does not return a value
  * It carries out the rules of the game of war until one of the players has won
  */
  public void play() {
    int retVal = 0; //initializing retVal as 0
    LinkedList<Card> tableCards = new LinkedList<Card>(); //creating a list of cards, tableCards, to hold the cards that were flipped during the game
    // battle
    while (true) {
      int outcome = this.battle(tableCards); //calling the battle() method in the Game class (method is indicated below), for this instance
      if(outcome == -1) { //if the outcome is -1, this means that the 2 player's medians were the same so the war() method is called
        outcome = this.war(tableCards, true);
        if(outcome == -1) { //if the outcome is still -1, meaning that the player's draw again, the result will be a double war
          outcome = this.war(tableCards, false);
        }
      }
      if(outcome == 0) { //if the outcome is 0, this means that player 1 has the higher median value
        if(m_playerOne.hasWon()) { //if player 1 has won, calling the hasWon() method in the Player class, then player 1 has won
          System.out.println("Player 1 has Won. Game Over!!!");
          break;
        } else {
          continue;
        }
      } else if(outcome == 1){ //if the outcome is 1, this means that player 2 has the higher median value
        if(m_playerTwo.hasWon()) { //if player 2 has won, calling the hasWon() method in the Player class, then player 2 has won
          System.out.println("Player 2 has Won. Game Over!!!");
          break;
        } else {
          continue;
        }
      } else { //if the outcome is -1 and still a draw after 2 wars and the tableCards has all of the cards, we need to distribute the cards evenly again to the players
         /**creating linked list of cards for players 1 and 2*/
         LinkedList<Card> pOneCards = new LinkedList<Card>();
         LinkedList<Card> pTwoCards = new LinkedList<Card>();
         for (int i = 0; i < tableCards.size(); i++) { //for every card in table cards, the cards will distribute evenly and alternatively to each player
           if(i % 2 == 0) { //for every even position in the list, pOneCards will obtain those cards
             pOneCards.add(tableCards.get(i));
           } else { //for every even position in the list, pTwoCards will obtain those cards
             pTwoCards.add(tableCards.get(i));
           }
         }
         /**calling the collect() method in te Player class to take the collection of the cards and add it to their own list of cards*/
         m_playerOne.collect(pOneCards);
         m_playerTwo.collect(pTwoCards);
         continue;
      }
    }
  }

  /**
  * A private method, battle, that returns an int (0 if player 1 wins, 1 if
  * player 2 wins, -1 if is a draw)
  * It takes a list of cards, more specifically the table cards and returns the
  * outcome condition
  * @return int value representing the oucome of the battle
  * @param tableCards LinkedList<Card> representing the cards flipped on the
  * table during the game
  */
  private int battle(LinkedList<Card> tableCards) {
    System.out.println("Start Battle.");
    int retVal = -1; //initializing retVal as -1
    /**creating 2 arrays of integers for the flipped cards for players 1 and 2*/
    int[] playerOneVals = new int[3];
    int[] playerTwoVals = new int[3];

    Card[] playerOneCards = new Card[3];
    Card[] playerTwoCards = new Card[3];
    /**initializing the flipped cards as 0 (where there are no cards flipped)*/
    int numCardsFlippedOne = 0;
    int numCardsFlippedTwo = 0;
    for (int i = 0; i < 3; i++) { //flip every card until there are at most 3 flipped cards for each player
      Card playerOneCard = m_playerOne.flip();
      playerOneCards[i] = playerOneCard;
      if(playerOneCard != null) { //if the card for player 1 is not null, then the array will obtain the card, incrementing number of cards flipped by 1
        playerOneVals[i] = playerOneCard.getValue();
        numCardsFlippedOne += 1;
        tableCards.add(playerOneCard); //the cards on the table will add the card to its pile
      }
      Card playerTwoCard = m_playerTwo.flip();
      playerTwoCards[i] = playerTwoCard;
      if(playerTwoCard != null) { //if the card for player 2 is not null, then the array will obtain the card, incrementing number of cards flipped by 1
        playerTwoVals[i] = playerTwoCard.getValue();
        numCardsFlippedTwo += 1;
        tableCards.add(playerTwoCard); //the cards on the table will add the card to its pile
      }
    }

    //after a battle is dealt
    WarLogger.getInstance().logBattle(m_numBattles+1,WarLogger.P1, playerOneCards);
    WarLogger.getInstance().logBattle(m_numBattles+1,WarLogger.P2, playerTwoCards);

    /**calliing the medianValue() method on the 2 arrays of integers with the flipped cards and the number of cards flipped per player*/
    int medianOne = medianValue(playerOneVals, numCardsFlippedOne);
    int medianTwo = medianValue(playerTwoVals, numCardsFlippedTwo);

    if (medianOne > medianTwo) { //if player 1's meadian is greater than player 2's, the return value will be 0 and player 1 will collect all the cards on the table, clearing the cards on the table
      retVal = 0;
      m_playerOne.collect(tableCards);
      tableCards.clear();
      WarLogger.getInstance().logBattleOutcome(m_numBattles+1, WarLogger.P1); //after a battle is won
    } else if (medianOne < medianTwo) { //if player 2's meadian is greater than player 1's, the return value will be 1 and player 2 will collect all the cards on the table, clearing the cards on the table
      retVal = 1;
      m_playerTwo.collect(tableCards);
      tableCards.clear();
      WarLogger.getInstance().logBattleOutcome(m_numBattles+1, WarLogger.P2); //after a battle is won
    } else { //otherwise, if player 1's median is the same as player 2's median, this will return -1 and the tableCards will be returned by the return parameter
      retVal = -1; //tableCards will not be cleared as it will hold the cards to be colelcted later.
      WarLogger.getInstance().logBattleOutcome(m_numBattles+1, WarLogger.WAR); //after a battle is won
    }
    m_numBattles++; //when the battle finishes, the number of battles will iincrement by 1
    System.out.println(String.format("Battle Done. Battle number: %d", m_numBattles));
    return retVal;
  }

  // returns 0 - if PlayerOne won the battle, 1 if playerTeo won the battle, -1 if it was draw
  /**
  * A private method, war, that returns an int (0 if player 1 wins, 1 if
  * player 2 wins, -1 if is a draw)
  * It takes a list of cards, more specifically the table cards and returns the
  * outcome condition, and also a boolean value of the war
  * @return int value representing the oucome of the battle
  * @param tableCards LinkedList<Card> representing the cards flipped on the
  * table during the game
  * @param firstWar boolean representing whether the game is a war(true) or
  * double war(false)
  */
  private int war(LinkedList<Card> tableCards, boolean firstWar) {
    int retVal = -1; //initializing retVal as -1
    int playerOneVal = 0; //initializing playerOneVal as 0
    Card playerOneCard = m_playerOne.flip();
    if(playerOneCard != null) { //if the card for player 1 is not null, then the playerOneCard will obtain the value of the card flipped,
      playerOneVal = playerOneCard.getValue();
      tableCards.add(playerOneCard); //this will then get added to the cards on the table
    }
    int playerTwoVal = 0; //initializing playerTwoVal as 0
    Card playerTwoCard = m_playerTwo.flip();
    if(playerTwoCard != null) { //if the card for player 1 is not null, then the playerTwoCard will obtain the value of the card flipped,
      playerTwoVal = playerTwoCard.getValue();
      tableCards.add(playerTwoCard); //this will then get added to the cards on the table
    }
    int medianOne = playerOneVal; //reassigning playerOneVal to medianOne
    int medianTwo = playerTwoVal; //reassigning playerTwoVal to medianTwo
    if (medianOne > medianTwo) { //if player 1's meadian is greater than player 2's, the return value will be 0 and player 1 will collect all the cards on the table, clearing the cards on the table
      retVal = 0;
      m_playerOne.collect(tableCards);
      tableCards.clear();
      WarLogger.getInstance().logWarOutcome(m_numWars+1, WarLogger.P1); //after a war is won
    } else if (medianOne < medianTwo) { //if player 2's meadian is greater than player 1's, the return value will be 1 and player 2 will collect all the cards on the table, clearing the cards on the table
      retVal = 1;
      m_playerTwo.collect(tableCards);
      tableCards.clear();
      WarLogger.getInstance().logWarOutcome(m_numWars+1, WarLogger.P2); //after a war is won
    } else { //otherwise, if player 1's median is the same as player 2's median, this will return -1 and the tableCards will be returned by the return parameter
      retVal = -1; //tableCards will not be cleared as it will hold the cards to be colelcted later.
      WarLogger.getInstance().logWarOutcome(m_numWars+1, WarLogger.WAR); //after a war is won
    }
    if(firstWar == true) { //if it is only 1 war, the number of wars increments by 1
      m_numWars++; //battle finished
    } else { //otherwise, if it is 2 wars, the number of double wars increments by 1
      m_numDoulbeWars++;
    }
    System.out.println(String.format("War Done. Number of wars: %d, number of double wars: %d", m_numWars, m_numDoulbeWars));
    return retVal;
  }

  /**
  * A private method, medianValue, that returns an int
  * It takes an array of integers (card values), and the number of cards and
  * returns the median value of the cards
  * @return int value representing the median of the flipped cards
  * @param usrValue[] int representing the array of flipped cards
  * @param n int representing number of cards flipped
  */
  private int medianValue(int usrValue[], int n) {
    int retVal = 0; //initializing retVal as 0
    Arrays.sort(usrValue); //the array of integers are sorted in ascending order
    if (n == 1) { //if there is only 1 card, the card is the median, located in the 0th index
      retVal = usrValue[0];
    } else if (n == 2 || n == 3) { //if there are 2 or 3 cards, the 2nd position, or index 2, will be the median
      retVal = usrValue[1];
    }
    return retVal;
  }

  /** Creates a string representation of the game
  * @return A formatted string containing information about how the game,
  * outputting the deck, players, number of battles, wars and double wars
  */
  public String toString() {
    String retVal = "";
    retVal += "Deck: \n" + m_deck;
    retVal += "Player One: \n" + m_playerOne;
    retVal += "Player Two: \n" + m_playerTwo;
    retVal += String.format("m_numBattles: %d\n", m_numBattles);
    retVal += String.format("m_numWars: %d\n", m_numWars);
    retVal += String.format("m_numDoulbeWars: %d\n", m_numDoulbeWars);
    return retVal;
  }
}
