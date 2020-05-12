/**
 * A class that stores the information of all of the players and the cards
 * they obtain
 */

import java.util.LinkedList;

public class Player {
  /**member variable*/
  private LinkedList<Card> m_cards = null;

  /**default constructor: initializing member variable*/
  public Player() {
    m_cards = new LinkedList<Card>();
  }

  /**
  * A public method, flip, that returns a Card data type
  * @return Card value representing the cards that are removed and returned
  * at the front of the list
  */
  public Card flip() {
    Card retVal = null;
    if (m_cards.size() > 0) { //if the size of the linked list is greater than 0, remove the card at postion 0, or at the front of the list
      retVal = m_cards.remove(0);
      System.out.println("Flipped card: " + retVal);
    }
    return retVal;
  }

  /**
  * A public method, collect, that takes a collection of cards as a parameter
  * and adds it to the list of owned cards, with no return type
  * @param cards LinkedList<Card> representing the list of cards
  */
  public void collect(LinkedList<Card> cards) {
    m_cards.addAll(cards); //adds cards to the m_cards list
  }

  /**
  * A public method, hasWon, that returns a boolean data type
  * It would return true if a player owned all 52 cards, and false otherwise
  * @return Card value representing if a player has won, obtaining all 52 cards
  */
  public boolean hasWon() {
    boolean won = false; //default initialization to false
    if(m_cards.size() == 52){ //if the size of the indiviual player's deck, or the m_cards list, is 52, then the player has won the cam, returning true
      won = true;
    }
    return won;
  }

  /** Creates a string representation of the player's cards information
  * @return A formatted string containing information about how many cards each
  * player has, the suit and its corresponding numeric display value
  */
  public String toString() {
    String retVal = "";
    retVal += String.format("Player has %d Cards\n", m_cards.size()); //the number of cards the player has after the deck has been evenly distributed
    for (int i = 0; i < m_cards.size(); i++) {
        retVal += String.format("#%d - %s %s\n", (i+1), m_cards.get(i).getCardValue(), m_cards.get(i).getSuit());
    }
    return retVal;
  }

  public static void main(String[] args) {
    Player p = new Player();
    System.out.println(p);
  }
}
