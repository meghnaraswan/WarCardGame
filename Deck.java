/**
 * A class that stores the information of all of the unique cards by
 * randomizing the cards for each player
 */

import java.util.LinkedList;
import java.util.Random;

public class Deck {
  /**member variable*/
  private LinkedList<Card> m_cards = null;

  /**default constructor: initializing member variable*/
  public Deck() {
    m_cards = new LinkedList<Card>();
    /**
    * making an array for CardValue and Suit
    * values() returns unmodifiable collection view of the property values
    * within the enums
    */
    CardValue allCarValues[] = CardValue.values();
    Suit allSuits[] = Suit.values();
    for(int i = 0; i < allSuits.length; i++) { //iterating through the suit array's size
      for(int j = 0; j < allCarValues.length; j++) { //iterating through the card value array's size
        m_cards.add(new Card(allSuits[i], allCarValues[j])); //adding the suit and the numeric display value in the linked list, m_cards, correspondingly in the linked LinkedList
      }
    }
    // for (int i = 0; i < m_cards.size(); i++) {
    //   /**System.out.println(*/m_cards.get(i)/**)*/;
    // }
  }

  /**
  * A public method, deal, that returns a Card data type
  * @return Card value representing a unique card that has been removed
  */
  public Card deal() {
    Card retCard = null; //initializing retCard
    if(m_cards.size() > 0) { //if the size of the linked list is greater than 0, remove randomized index in the linked list
      Random rand = new Random(); //new random number generator
      int randomInt = rand.nextInt(m_cards.size()); //randomInt represents the random index which is chosen from the size of the m_cards linked list
      retCard = m_cards.remove(randomInt); //the return will be that one random index removed from the linked list
    }
    return retCard;
  }

  /** Creates a string representation of the decks's information
  * @return A formatted string containing information about how many cards are
  * in the deck, the suit and its corresponding numeric display value
  */
  public String toString() {
    String retVal = "";
    retVal += String.format("Deck has %d Cards\n", m_cards.size()); //size of deck
    for (int i = 0; i < m_cards.size(); i++) { //for loop for displaying each card one by one
        retVal += String.format("#%d - %s %s\n", (i + 1), m_cards.get(i).getCardValue(), m_cards.get(i).getSuit()); //displaying cards from 1 to 52
    }
    return retVal;
  }
}
