package AEP;

import BreezySwing.*;
import javax.swing.*;
import java.awt.*;

public class PanelButton implements InteractableObject {
	//variables
	Hitbox hitbox;
	private int x;
	private int y;
	private String text;
	public Color fillColor = Color.BLACK;
	public Color borderColor = Color.BLACK;
	public Color textColor = Color.YELLOW;
	public Color highlightColor = Color.YELLOW; 
	private boolean active = false;
	private boolean cursorIsOver = false;
	
	//constructors
    public PanelButton(String txt, int xLoc, int yLoc, int width, int height)
    {
    	hitbox = new Hitbox(0, 0 , width, height);
    	x = xLoc;
    	y = yLoc;
    	text = txt;
    }
    
    //modifiers
    //setX
    public void setX(int loc)
    {
    	x = loc;
    }
    //setY
    public void setY(int loc)
    {
    	y = loc;
    }
    //setString
    public void setString(String txt)
    {
    	text = txt;
    }
    //setActive
    public void setActive(boolean status)
    {
    	active = status;
    }
    //cursorIsOver
    public void setCursorIsOver(boolean status)
    {
    	cursorIsOver = status;
    }
    //accessors
    //isActive
    public boolean getActive()
    {
    	boolean a = active;
    	active = false;
    	return a;
    }
    
    public boolean getActiveUnSafe()
    {
    	return active;
    }
    
    //isInBounds
    public boolean checkCollision(int xLoc, int yLoc)
    {
    	return hitbox.isInBounds(xLoc, yLoc, x - (hitbox.getWidth() / 2), y - (hitbox.getHeight() / 2));
    }
    //display
    public void display(Graphics g, GBPanel panel)
    {
    	FontMetrics fm = g.getFontMetrics();
    	int xLoc = fm.stringWidth(text) / 2;
    	int yLoc = fm.getAscent() / 2;
    	g.setColor(fillColor);
    	g.fillRect(x - (hitbox.getWidth() / 2), y - (hitbox.getHeight() / 2), hitbox.getWidth(), hitbox.getHeight());
    	g.setColor(borderColor);
    	g.drawRect(x - (hitbox.getWidth() / 2), y - (hitbox.getHeight() / 2), hitbox.getWidth() , hitbox.getHeight());
    	g.setColor(textColor);
    	g.drawString(text, x - xLoc, y + yLoc);
    	if (cursorIsOver)
    	{
    		g.setColor(highlightColor);
    		g.drawRect(x - (hitbox.getWidth() / 2), y - (hitbox.getHeight() / 2), hitbox.getWidth(), hitbox.getHeight());
    		g.drawRect(x - (hitbox.getWidth() / 2) + 1, y - (hitbox.getHeight() / 2) + 1, hitbox.getWidth() - 2, hitbox.getHeight() - 2);
    		g.drawRect(x - (hitbox.getWidth() / 2) + 2, y - (hitbox.getHeight() / 2) + 2, hitbox.getWidth() - 3, hitbox.getHeight() - 3);
    	}
    }
}