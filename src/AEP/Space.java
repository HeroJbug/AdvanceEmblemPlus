package AEP;

import BreezySwing.*;
import javax.swing.*;
import java.awt.*;

public class Space implements InteractableObject{
	//variables
	private Hitbox hitbox;
	private int x;
	private int y;
	private int row;
	private int column;
	public boolean visible = false;
	
    //constructor
    public Space(int _x, int _y, int _row, int _col, Hitbox _hb)
    {
    	row = _row;
    	column = _col;
    	hitbox = _hb;
    	x = _x;
    	y = _y;
    }
    
    //modifiers
    //setX
    public void setX(int loc)
    {
    	//does nothing
    }
    //setY
    public void setY(int loc)
    {
    	//does nothing
    }
    //accessors
    //getRow
    public int getRow()
    {
    	return row;
    }
    //getColumn
    public int getColumn()
    {
    	return column;
    }
    //checkCollision
    public boolean checkCollision(int xLoc, int yLoc)
    {
    	return hitbox.isInBounds(xLoc, yLoc, x, y);
    }
    //display
    public void display(Graphics g, GBPanel panel)
    {
    	if (visible)
    	{
    		ImageIcon image = new ImageIcon(GraphicsDisplay.class.getResource("/resources/Art/mouseSpot.png"));
    		g.drawImage(image.getImage(), x, y, panel);	
    	}
    }	
}