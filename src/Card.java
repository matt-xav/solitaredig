package src;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * @author Jason Miner and Matthew Smillie
 *
 */
public class Card extends Polygon
{
	private static final long serialVersionUID = -1948500488018695977L;

	private int cornerX;
	private int cornerY;
	private int rightX;
	private int bottomY;

	private int rank;
	private String suit;

	public boolean faceDown;

	public Image myCardImage;
	public Image backImage;

	private String changeCursed;
	public Image invertedImage;
	private boolean cursed;

	// The card's width and height.
	public static final int HEIGHT = 150;
	public static final int WIDTH = 110;

	public Card(int newRank, String newSuit)
	{
		this(newRank, newSuit, 0, 0);
	}

	public Card(int newRank, String newSuit, int x, int y)
	{
		changeCursed = "c";
		rank = newRank;
		suit = newSuit;
		setLocation(x, y);
		faceDown = true;
		try
		{
			myCardImage = ImageIO.read(new File(getFileName()));
			invertedImage = ImageIO.read(new File(getCursedFileName()));
			backImage = ImageIO.read(new File("../solitaredig/assets/cards/back.gif"));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cursed = false;
	}

	public void display(Graphics g) throws IOException
	{
		if (myCardImage == null)
		{
			g.setColor(Color.BLACK);
			g.drawRoundRect(cornerX, cornerY, WIDTH, HEIGHT, 10, 10);
		}
		else
		{

			if (faceDown)
			{
				// BufferedImage img = null;
				// try
				// {
				// img = ImageIO.read(new File("back.gif"));
				// }
				// catch (IOException e)
				// {
				// }
				g.drawImage(backImage, cornerX, cornerY, WIDTH, HEIGHT, null);
			}
			else
			{
				g.drawImage(myCardImage, cornerX, cornerY, WIDTH, HEIGHT, null);
				if (cursed)
				{
					g.drawImage(invertedImage, cornerX, cornerY, WIDTH, HEIGHT, null);
				}
			}
		}
	}

	public String getFileName()
	{
		if (rank == 10)
			return "../solitaredig/assets/cards/" + "t" + suit + ".gif";
		if (rank == 11)
			return "../solitaredig/assets/cards/" + "j" + suit + ".gif";
		if (rank == 12)
			return "../solitaredig/assets/cards/" + "q" + suit + ".gif";
		if (rank == 13)
			return "../solitaredig/assets/cards/" + "k" + suit + ".gif";
		if (rank == 1)
			return "../solitaredig/assets/cards/" + "a" + suit + ".gif";

		return "../solitaredig/assets/cards/" + rank + suit + ".gif";
	}

	public String getCursedFileName()
	{
		if (rank == 10)
			return "../solitaredig/assets/cards/" + "t" + suit + changeCursed + ".gif";
		if (rank == 11)
			return "../solitaredig/assets/cards/" + "j" + suit + changeCursed + ".gif";
		if (rank == 12)
			return "../solitaredig/assets/cards/" + "q" + suit + changeCursed + ".gif";
		if (rank == 13)
			return "../solitaredig/assets/cards/" + "k" + suit + changeCursed + ".gif";
		if (rank == 1)
			return "../solitaredig/assets/cards/" + "a" + suit + changeCursed + ".gif";

		return "../solitaredig/assets/cards/" + rank + suit + changeCursed + ".gif";
	}

	public boolean isRed()
	{
		return (suit.equals("d") || suit.equals("h"));
	}

	public int getRank()
	{
		return rank;
	}

	public String getSuit()
	{
		return suit;
	}

	public String getChangeCursed()
	{
		return changeCursed;
	}

	public int getX()
	{
		return cornerX;
	}

	public int getY()
	{
		return cornerY;
	}

	public int getRightX()
	{
		return rightX;
	}

	public int getBottomY()
	{
		return bottomY;
	}

	public boolean isCursed()
	{
		return cursed;
	}

	public void setCursed(boolean cursed)
	{
		this.cursed = cursed;
	}

	public void setLocation(int x, int y)
	{
		cornerX = x;
		cornerY = y;
		assignVertices();
	}

	/**
	 * Assigns the rightX and bottomY vertices based on the top left corner location
	 */
	private void assignVertices()
	{
		rightX = cornerX + WIDTH;
		bottomY = cornerY + HEIGHT;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + rank;
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (rank != other.rank)
			return false;
		if (suit == null)
		{
			if (other.suit != null)
				return false;
		}
		else
			if (!suit.equals(other.suit))
				return false;
		return true;
	}

	// added
	public String toString()
	{
		return String.format("Card: %s %s", getSuit(), getRank());// need to add special things for j, k, q, a
	}
}
