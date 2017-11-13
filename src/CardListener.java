package src;


import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

/**
 * CardListener.java
 */
public class CardListener extends MouseInputAdapter
{
	private Solitaire panel;
	
	private Deck deck;
	
	private Pile[] tableaPiles;
	private Pile[] foundationPiles;
	
	private Pile deckPile;
	private Pile origPile;
	
	private int lastX, lastY;
	
	/** Constructor for a Card Listener */
	public CardListener(Solitaire panel)
	{
		this.panel = panel;
		deck = panel.getDeck();
		tableaPiles = panel.getTableauPiles();
		foundationPiles = panel.getFoundationPiles();
		deckPile = panel.getDeckPile();
		lastX = 0;
		lastY = 0;
		origPile = null;
	}
	
	/** Selects a card when it is clicked */
	public void mousePressed(MouseEvent e)
	{
		panel.selectedPile = getPileClicked(e);
		if (panel.selectedPile != null)
		{
			lastX = e.getX();
			lastY = e.getY();
		}
		else
		{ // if no pile was clicked, check if the deck was
			if (deck.hasBeenClicked(e))
			{
				if (deck.size() == 0)
				{
					deck.addToDeck(deckPile);
				}
				else
				{
					for (int i = 0; i < 3; i++)
					{
						Card c = deck.getCardOnTop();
						if (c != null)
						{
							deckPile.addToPile(c);
							deck.removeCardOnTop();
						}
					}
					deckPile.turnAllCardsUp();
				}
			}
		}
		panel.repaint();
	}
	
	/** Moves the card as it is dragged by the mouse */
	public void mouseDragged(MouseEvent e)
	{
		if (panel.selectedPile != null)
		{
			int newX = panel.selectedPile.getX() + (e.getX() - lastX);
			int newY = panel.selectedPile.getY() + (e.getY() - lastY);
			panel.selectedPile.setLocation(newX, newY);
			lastX = e.getX();
			lastY = e.getY();
		}
		panel.repaint();
	}
	
	/**
	 * Drops a card on a pile only if it has the right face and color
	 * 
	 * got help from a friend named Stanley Munson
	 */
	public void mouseReleased(MouseEvent e)
	{
		Pile p = panel.selectedPile;
		if (p != null)
		{
			boolean validDrop = false;
			// check to see if the selectedPile has been dropped on a main pile
			for (int i = 0; i < tableaPiles.length; i++)
			{
				if (tableaPiles[i].droppedOnPile(p))
				{
					if (tableaPiles[i].isEmpty())
					{
						if (p.getCardOnBottom().getRank() == 13)
						{
							tableaPiles[i].addToPile(p);
							origPile.turnTopCardUp();
							validDrop = true;
						}
					}
					else
					{
						// if not empty only add if the colors are NOT the same
						if (!p.getCardOnBottom().isRed() == tableaPiles[i].getCardOnTop().isRed())
						{
							// now ensure the faces are descending
							if (p.getCardOnBottom().getRank() + 1 == tableaPiles[i].getCardOnTop().getRank())
							{
								tableaPiles[i].addToPile(p);
								origPile.turnTopCardUp();
								validDrop = true;
								break;
							}
						}
					} // end isEmpty() condition
				}
			}
			// if the drop is still invalid, check if it's been dropped on a suit pile
			// instead
			if (!validDrop)
			{
				for (int i = 0; i < foundationPiles.length; i++)
				{
					if (foundationPiles[i].droppedOnPile(p))
					{
						if (foundationPiles[i].isEmpty())
						{
							if (p.size() == 1)
							{
								if (p.getCardOnBottom().getRank() == 1)
								{
									foundationPiles[i].addToPile(p.getCardOnBottom());
									origPile.turnTopCardUp();
									validDrop = true;
								}
							}
						}
						else
						{
							if (p.size() == 1)
							{
								// only single cards can be added to suit piles the suits must be the same for
								// cards being added
								if (p.getCardOnBottom().getSuit() == foundationPiles[i].getCardOnTop().getSuit())
								{
									// the faces must be in ascending order
									if (p.getCardOnBottom().getRank() == foundationPiles[i].getCardOnTop().getRank()
											+ 1)
									{
										foundationPiles[i].addToPile(p.getCardOnBottom());
										origPile.turnTopCardUp();
										validDrop = true;
									}
								}
							}
						} // end isEmpty() condition
					}
				}
			}
			if (!validDrop)
			{
				if (p.size() == 1)
				{
					origPile.addToPile(p.getCardOnBottom());
				}
				else
				{
					origPile.addToPile(p);
				}
			}
		}
		panel.selectedPile = null;
		origPile = null;
		panel.repaint();
	}
	
	public void mouseClicked(MouseEvent e)
	{
	}
	
	/** Returns the card that was clicked or null if no card was clicked */
	private Pile getPileClicked(MouseEvent e)
	{
		Pile clicked = null;
		origPile = null;
		// check the main piles and then the suit piles
		for (int i = 0; i < tableaPiles.length; i++)
		{
			if ((clicked = tableaPiles[i].pileHasBeenClicked(e)) != null)
			{
				origPile = tableaPiles[i];
				return clicked;
			}
		}
		for (int i = 0; i < foundationPiles.length; i++)
		{
			if ((clicked = foundationPiles[i].pileHasBeenClicked(e)) != null)
			{
				origPile = foundationPiles[i];
				return clicked;
			}
		}
		if ((clicked = deckPile.pileHasBeenClicked(e)) != null)
		{
			origPile = deckPile;
		}
		return clicked;
	}
}
