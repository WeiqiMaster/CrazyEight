/**
 * 
 */
package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * The Player class
 *
 */
public class Player
{
	private String name;//name property
	private ArrayList<Card> cardsInHand;//arraylist contains the cards of a player
	static private boolean ifTakeTurn=true;//static property used to decide who should take turn.
	private boolean ifWin;//used to decide if this player win the game.
	
	//Constructor for this class Player
	public  Player(String name)
	{
		this.name=name;
		this.cardsInHand=new ArrayList<Card>();
		this.ifWin=false;
	}
	
	//Get method for the arraylist cardsInHand.
	ArrayList<Card> getCardsInHand()
	{
		return cardsInHand;
	}
	
	//Get method for the name property
	String getName()
	{
		return this.name;
	}
	
	//Get method for the property ifTakeTurn
	static boolean getifTakeTurn()
	{
		return ifTakeTurn;
	}
	
	//Set method for the property ifTakeTurn
	static void setifTakeTurn(boolean bool)
	{
		ifTakeTurn=bool;
	}
	
	//Get method for the property ifWin.
	boolean getifWin()
	{
		return this.ifWin;
	}
	
	//Set method for the property ifWin.
	void setifWin(boolean bool)
	{
		this.ifWin=bool;
	}
	
	//show all the cards in this player's hand on the screen
	void showCards()
	{
		for (int i = 0; i < this.cardsInHand.size(); i++)
		{
			this.cardsInHand.get(i).showCardOnScreen();
		}
	}
	
	//judge if the player had card to play.
	boolean ifHasCardToPlay(Card topCard)
	{
		for (int i = 0; i < this.cardsInHand.size(); i++)
		{
			if (this.cardsInHand.get(i) instanceof Card8)
			{
				return true;
			}
		}
		
		return this.cardsInHand.contains(topCard);
		
	}
	
	//look for the index of a specific card on this player's hand. If the card can not be found, the return -1.
	int indexOfCardInHand(String suit, int value)
	{
		for (int i = 0; i < this.cardsInHand.size(); i++)
		{
			if (this.cardsInHand.get(i).getSuit().equals(suit)&&this.cardsInHand.get(i).getValue()==value)
			{
				return i;
			}
		}
		return -1;
	}
	
	//Sort the player's cards in hand by suit
	void sortBySuit()
	{
		Collections.sort(this.cardsInHand, new Comparator<Card>()
				{
					@Override
					public int compare(Card card1, Card card2)
					{
						return card1.getSuit().compareTo(card2.getSuit());
					}
				});
	}
	
	//Sort the player's cards in hand by value using bubble sort.
	void sortByValue()
	{
		boolean ifSwap = true;
		for (int j = 0; j < this.cardsInHand.size() - 1; j++)
		{
			if (ifSwap)
			{
				ifSwap = false;
				for (int i = 0; i <= this.cardsInHand.size() - 2 - j; i++)
				{
					if (this.cardsInHand.get(i).getValue() > this.cardsInHand.get(i+1).getValue())
					{
						Collections.swap(this.cardsInHand, i, i+1);
						ifSwap = true;
					}
				}
			} else
			{
				break;
			}
		}
	}
	
}
