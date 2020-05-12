import java.util.LinkedList;

/**
* An enum with 4 enumerators belonging to an enumerated type named Suit
*/
enum Suit {
  Clubs, Spade, Diamonds, Hearts;
};

/**
* An enum with 13 enumerators belonging to an enumerated type named CardValue
* reassigning the values of the enumerators to its actual card values in a deck
*/
enum CardValue {
   Two(2), Three(3), Four(4), Five(5), Six(6), Seven(7), Eight(8), Nine(9), Ten(10), J(11), Q(12), K(13), A(14);

    /**member variable*/
    private int value;

    /**private constructor for the enum
    * @param val int representing the value, or number,  of the card
    * to play
    */
    private CardValue(int val) {
        value = val;
    }

    /**accessor for value*/
    public int getValue() {
        return this.value; //this refers to the current object within the instance method
    }

    /**
    * A public method, toString, that returns a String, which is the
    * corresponding numbers or letters for the individual cards
    * @return a String value representing the value of the card
    */
    public String toString() {
      String displayValue = ""; //initializing to empty string
      switch(value) { //using switch statement for corresponding each integer value that is represented as a letter in cards, to an letter card value
        case 11: //if the value is 11, then the display value should be J
            displayValue = "J";
            break;
        case 12: //if the value is 12, then the display value should be Q
            displayValue = "Q";
            break;
        case 13: //if the value is 13, then the display value should be K
            displayValue = "K";
            break;
        case 14: //if the value is 14, then the display value should be A
            displayValue = "A";
            break;
        default: //otherwise the numeric value of the cards will be represented by the same numeric number when displayed in a string format
          displayValue = String.valueOf(value); //valueOf returns the enum constant of the specified string value, or the card vlaue in this case; and since the value is an integer, we need to convert it into a String
      }
      return displayValue;
    }
}

/**
 * A class that stores the information of a card and its attributes
 */
public class Card {
  /**member variables*/
  private CardValue m_cardValue;
  private Suit m_suit;

  /** Overloaded Card constructor
  * @param suit Suit representing the suit of the card
  * @param cardVal CardValue representing the numeric value of the card
  */
  public Card(Suit suit, CardValue cardVal) {
    m_cardValue = cardVal;
    m_suit = suit;
  }

  /**accessor: access the numric value of the card*/
  public int getValue(){
    return m_cardValue.getValue(); //calling the getValue() method above in the CardValue enum; this will allow the member variable to obtain the display value of the card
  }

  /**member accessor*/
  public CardValue getCardValue(){
    return m_cardValue;
  }

  /**member accessor*/
  public Suit getSuit(){
    return m_suit;
  }

  /** Creates a string representation of the card's information
  * @return A formatted string containing the card display value and the suit
  */
  public String toString() {
    String retVal = String.format("%s %s", m_cardValue, m_suit);
    return retVal;
  }
}
