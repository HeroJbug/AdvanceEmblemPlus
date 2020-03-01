package AEP;

import BreezySwing.*;
import javax.swing.*;
import java.awt.*;

public class Sprite {
	//variables
	private ImageIcon[] images; //the images of the sprite
	private int originX; //the X origin of the sprite in reference to it's images
	private int originY; //the Y origin of the sprite in reference to it's images
	private Hitbox hitbox; //the hitbox of the object
	private int imageIdx = 0; //the current image that the sprite is displaying
    
    //constructors
    public Sprite(ImageIcon[] i, int orX, int orY, Hitbox hb)
    {
    	images = i;
    	originX = orX;
    	originY = orY;
    	hitbox = hb;
    }
    public Sprite(ImageIcon[] i, int orX, int orY, int bX1, int bY1, int bX2, int bY2)
    {
    	images = i;
    	originX = orX;
    	originY = orY;
    	hitbox = new Hitbox(bX1, bY1, bX2, bY2);
    }
    public Sprite(ImageIcon[] i, int orX, int orY)
    {
    	images = i;
    	originX = orX;
    	originY = orY;
    	//the following sets the boundaries of the sprite to the sides of the sprite's first image
    	hitbox = new Hitbox(0, 0, images[0].getIconWidth() - 1, images[0].getIconHeight() - 1);
    }
    public Sprite(ImageIcon[] i)
    {
    	images = i;
    	//the following sets the origin of the sprite to (0,0)
    	originX = 0;
    	originY = 0;
    	//the following sets the boundaries of the sprite to the sides of the sprite's first image
    	hitbox = new Hitbox(0, 0, images[0].getIconWidth() - 1, images[0].getIconHeight() - 1);
    }
    public Sprite()
    {
    	images = new ImageIcon[1];
    	images[0] = new ImageIcon(GraphicsDisplay.class.getResource("/resources/Art/nothing.png"));
    	//the following sets the origin of the sprite to (0,0)
    	originX = 0;
    	originY = 0;
    	//the following sets the boundaries of the sprite to the sides of the sprite's first image
    	hitbox = new Hitbox(0, 0, images[0].getIconWidth() - 1, images[0].getIconHeight() - 1);
    }
    
    //modifiers
    //originX
    public void setOriginX(int x)
    {
    	originX = x;
    }
    //originY
    public void setOriginY(int y)
    {
    	originY = y;
    }
    //resizeBoundaries
    public void resizeBoundaries(int bX1, int bY1, int bX2, int bY2)
    {
    	hitbox.setBX1(bX1);
    	hitbox.setBY1(bY1);
    	hitbox.setBX2(bX2);
    	hitbox.setBY2(bY2);
    }
    //update
    public void update(int idx)
    {
    	imageIdx = idx;
    }
    //accessors
    //originX
    public int getOriginX()
    {
    	return originX;
    }
    //originY
    public int getOriginY()
    {
    	return originY;
    }
    //hitbox
    public Hitbox getHitbox()
    {
    	return hitbox;
    }
    //images
    public void drawSprite(Graphics g, GBPanel panel, int x, int y)
    {
    	g.drawImage(images[imageIdx].getImage(), x, y, panel);
    }
    public boolean checkCollision(int xLoc, int yLoc, int x, int y)
    {
    	return hitbox.isInBounds(xLoc , yLoc, x, y);
    }
    //equals
    public boolean equals(Sprite other)
    {
    	if (this.images.equals(other.images))
    	{
    		return true;
    	}
    	return false;
    }
    //toString
    public String toString()
    {
    	return images.toString();
    }
}