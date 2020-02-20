/**
 * 
 */
package main;

/**
 * The Card class
 *
 */
public class Card
{
	//two properties of this class
	private int value;
	private String suit;
	
	public Card(int value, String suit)//Constructor for this class Card
	{
		this.value=value;
		this.suit=suit;
	}
	
	//Override the method in the parent class Object so that I can use the arraylist.contains(obj o) in the method 
	//"ifHasCardToPlay"  in the class Player even when the two objects are not exactly equal in the memory block.
	//This is the demonstration of polymorphism.
	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Card) {   
            Card u = (Card) obj;   
        return this.suit.equals(u.suit) || (this.value==u.value);   
        }   
        return false;
	}
	
	int getValue()//return the value of the card
	{
		return value;
	}
	
	String getSuit()//return the suit of the card
	{
		return suit;
	}
	
	void showCardOnScreen()//Output the card on the console interface
	{
		System.out.println("*********");
		System.out.println("*    "+this.value+"    *");
		System.out.println("* "+this.suit+" *");
		System.out.println("*********");
	}
	
}
