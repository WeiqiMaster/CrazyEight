/**
 * Crazy Eights
 * Author: Peter Pan
 * 29/07/2018
 */
package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

/**
 * The game class
 *
 */
public class Game
{
	//the initial card stack, which will only be created once. So it does need to be a separate class.
	private ArrayList<Card> cardStack=new ArrayList<Card>();
	private Card topCard;//the last card a player has played.
	private int cardsDrawThisTurn=0;//the number of cards a player draws this turn.
	private Player player1;//create the variable  player1 but have not instantiate it.
	private Player player2;//create the variable  player2 but have not instantiate it.
	
	public Game() throws InterruptedException
	{
		Scanner scanner = new Scanner(System.in);  
		
		System.out.println("Creating a deck which has 52 unique cards in it...");
		createDeck();//Creating a deck which has 52 cards in it
		
		Thread.sleep(1000);//wait for 1 second
		
		//create two players and ask users for their names
		System.out.println("Please enter the first player's name: ");
		player1=new Player(scanner.nextLine());//instantiate the player1 and get its name.
		System.out.println("Please enter the second player's name: ");
		player2=new Player(scanner.nextLine());//instantiate the player2 and get its name.
		System.out.println("Assigning 8 cards to each player...");
		
		//hand put 8 cards to each player.
		handoutCard(player1.getCardsInHand());
		handoutCard(player2.getCardsInHand());
		
		Thread.sleep(1000);
		System.out.println("The top card is:");
		
		//get the top card and show it on the screen.
		topCard=cardStack.get(cardStack.size()-1);
		cardStack.remove(cardStack.size()-1);
		topCard.showCardOnScreen();

        while (true)
		{
        	if (player1.getifWin())//player1 wins
			{
        		System.out.println("Congratulations, "+player1.getName()+" wins!");
        		break;
			}else if (player2.getifWin()) //player2 wins
			{
				System.out.println("Congratulations, "+player2.getName()+" wins!");
				break;
			}else 
			{
				//judge who takes turn
	        	if (Player.getifTakeTurn())
				{
	        		//player1's turn
	            	takeTurn(player1, scanner);
				}else {
					//player2's turn
					takeTurn(player2, scanner);
				}
			}
		}
		
        Thread.sleep(10000);//show the result for 10 seconds
        //close the scanner
		scanner.close();
	}
	
	//take a turn for a player
	private void takeTurn(Player player , Scanner scanner)
	{
		cardsDrawThisTurn=0;//set the number of cards draw this turn to 0
		System.out.println(player.getName()+"'s turn:");
		//keep looping until the player plays a card or draw three cards.
		while ((player==player1&&Player.getifTakeTurn()) || (player==player2 && !Player.getifTakeTurn()))
		{
			if (!player.ifHasCardToPlay(topCard))
			{
				System.out.println("You have no card to play. You have to draw card(s) (up to 3 in a turn).");
			}
			showOption();//show the player's option
			char inputOption=scanner.next().charAt(0);//get the player's input
			switch (inputOption)
			{
				case '1':
					player.showCards();
					break;
				case '2':
					System.out.println("Please input the suit (clove, spade, diomn, heart):");
					String dumpSuit=scanner.next();
					System.out.println("Please input the value:");
					int dumpValue=scanner.nextInt();
					//get the index of this card in player's hand 
					int indexOfCard=player.indexOfCardInHand(dumpSuit, dumpValue);
					if (indexOfCard!=-1 )//if the player does not have this card
					{
						if (player.getCardsInHand().get(indexOfCard) instanceof Card8) //if this card is an 8
						{
							System.out.println("This card has a value of 8. Please input the suit you want to change to (clove, spade, diomn, heart):");
							topCard=new Card8(scanner.next());//ask player to choose a suit to change
							player.getCardsInHand().remove(indexOfCard);//remove the card 8 from player's hand
							System.out.println("You have successfully dumped this card. Now the top card is:");
							topCard.showCardOnScreen();
							passTurn(player);//pass the turn to the other player.
						}else if ((dumpSuit.equals(topCard.getSuit()) || dumpValue == topCard.getValue() ))//judge if the player can play this card
						{
							topCard=player.getCardsInHand().get(indexOfCard);//change the top card
							player.getCardsInHand().remove(indexOfCard);//remove this card from the player's hand
							System.out.println("You have successfully dumped this card. Now the top card is:");
							topCard.showCardOnScreen();
							passTurn(player);//pass the turn to the other player.
						} else 
						{
							System.out.println("Sorry, the card you select does not match the value or the suit of the top card.");
						}
					}else if(indexOfCard==-1)
					{
					    System.out.println("Sorry, you do not have this card in your hand.");
					}
					break;
				case '3'://Sort by value
					player.sortByValue();
					player.showCards();
					break;
				case '4'://sort by suit
					player.sortBySuit();
					player.showCards();
					break;
				case '5'://show the top card
					topCard.showCardOnScreen();
					break;
				case '6'://draw a card (up to three in a turn)
					if (cardsDrawThisTurn<3)
					{
						cardsDrawThisTurn++;
						player.getCardsInHand().add(cardStack.get(cardStack.size()-1));//add a card to the player's hand.
						System.out.println("The card you draw is:");
						cardStack.get(cardStack.size()-1).showCardOnScreen();//show the card drawn on the screen
						cardStack.remove(cardStack.size()-1);//remove this card from the deck.
					}else
					{//pass to the other player
						System.out.println("You have drawn 3 cards in your turn. You can not draw more cards.");
						System.out.println();
					}
					break;
			}
			if (inputOption=='7')
			{
				System.exit(0);//exit the program
			}
			
			//judge if the player wins the game.
			if (player.getCardsInHand().isEmpty())
			{
				player.setifWin(true);
			}
			
		}
	}
	
	//pass the turn to the other player.
	private void passTurn(Player player)
	{
		if (player==player1)
		{
			Player.setifTakeTurn(false);//pass the turn to the player2.
		}else {
			Player.setifTakeTurn(true);//pass the turn to the player1.
		}
	}
	
	//show player's option on the screen.
	private void showOption()
	{
		System.out.println();
		System.out.println("Your option (input number to choose):");
		System.out.println("1. Show Your Card(s) in Hand");
		System.out.println("2. Dump Card(s)");
		System.out.println("3. Order Card(s) by Value");
		System.out.println("4. Order Card(s) by Suit");
		System.out.println("5. Show the top card");
		System.out.println("6. Draw a card (you can only draw cards up to 3 in a turn)");
		System.out.println("7. Quit the program");
	}
	

	//create the initial deck which contains 52 cards.
	private void createDeck()
	{
		for (int i = 1; i < 14; i++)
		{
			if (i==8)
			{
				//add card8.
				cardStack.add(new Card8("spade"));
				cardStack.add(new Card8("diomn"));
				cardStack.add(new Card8("heart"));
				cardStack.add(new Card8("clove"));
			}else 
			{
				//add other cards
				cardStack.add(new Card(i,"spade"));
				cardStack.add(new Card(i,"diomn"));
				cardStack.add(new Card(i,"heart"));
				cardStack.add(new Card(i,"clove"));
			}
		}
		Collections.shuffle(cardStack);//shuffle the deck
	
	}
	
	//Hand out 8 cards to the player
	private void handoutCard(ArrayList<Card> deck)
	{
		for (int i = 0; i < 8; i++)
		{
			deck.add(cardStack.get(cardStack.size()-1));
			cardStack.remove(cardStack.size()-1);
		}
	}
	
}
