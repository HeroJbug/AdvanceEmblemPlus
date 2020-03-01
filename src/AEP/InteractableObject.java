package AEP;

import BreezySwing.*;
import java.awt.*;

public interface InteractableObject {
	
	//modifiers
	//setX
	public void setX(int loc);
	//setY
    public void setY(int loc);
    //accessors
    //isInBounds
    public boolean checkCollision(int x, int y);
    //display
    public void display(Graphics g, GBPanel panel);
}