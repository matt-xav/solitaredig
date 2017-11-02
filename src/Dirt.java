/**
 * 
 */

package src;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Dirt extends JPanel implements MouseListener, MouseMotionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame myFrame;
	private int WIDTH;
	private int HEIGHT;
	private int xOffset = 0;
	private int yOffset = 0;
	
	private final Color BROWN = new Color(185, 122, 87);
	
	private int[][] dirtLocations;
	private final int digRadius = 50;
	
	private Random myRand;
	
	public Dirt(JFrame frame){
		super();
		WIDTH = frame.getWidth();
		HEIGHT = frame.getHeight();
		myFrame = frame;
		
		//methodology borrowed from Steve Harper's dolpin code
		int borderWidth = (WIDTH - frame.getContentPane().getWidth())/2;
		xOffset = borderWidth;
        yOffset = HEIGHT - frame.getContentPane().getHeight()-borderWidth; // assume side border = bottom border; ignore title bar

		
		myRand = new Random();
		dirtLocations = new int[WIDTH][HEIGHT];// initialize dirt locations
		for(int i=0; i<WIDTH; i++) {
			//System.out.printf("%3d ", i);
			for(int j=0; j<HEIGHT; j++) {
				//System.out.printf("%3d ", j);
				dirtLocations[i][j] = myRand.nextInt(2);//randomly pick locations for dirt to be drawn
			}
			//System.out.print("\n");
		}
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		for(int i = 0; i<WIDTH; i++) {
			//System.out.printf("%3d ", i);
			for(int j=0; j<HEIGHT; j++) {
				if(dirtLocations[i][j]==1) {
					//System.out.printf("%3d ", j);
					g.setColor(BROWN);
					g.drawRect(i, j, 0, 0);// draws a dirt particle
				}
			}
			//System.out.print("\n");
		}
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		Point p = e.getLocationOnScreen();
		p.setLocation(p.getX() - myFrame.getX()- xOffset, p.getY() - myFrame.getY() - yOffset);
		int px = (int) (p.getX()); int py = (int) (p.getY());
		int xlim = px + digRadius;
		int ylim = py + digRadius;
		for(int i = px - digRadius; i < xlim; i++) {
			for(int j = py - digRadius; j<ylim; j++) {
				if(i >= 0 && i < WIDTH && j >= 0 && j < HEIGHT) {//ensure we don't go out of bounds
					if(Math.sqrt((px-i)*(px-i) + (py-j)*(py-j)) <= digRadius) {
						dirtLocations[i][j] = 0;
						/*if(Math.sqrt((px-i)*(px-i) + (py-j)*(py-j)) == 0) {
							dirtLocations[i][j] = 1;
						}*/
					}
				}
			}
		}
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseClicked(e);
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		mouseClicked(arg0);
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	//main method for testing purposes
	public static void main(String[] args) {
		JFrame thisFrame = new JFrame();
		thisFrame.setTitle("Dirt");
		thisFrame.setSize(1080, 700); //height, width
		thisFrame.setVisible(true);
		thisFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dirt myPanel = new Dirt(thisFrame);
		thisFrame.add(myPanel);
	}

}