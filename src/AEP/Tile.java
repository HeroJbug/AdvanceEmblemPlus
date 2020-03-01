package AEP;

import BreezySwing.*;
import javax.swing.*;
import java.awt.*;

public class Tile {
	//variables
	ImageIcon image;
	int x;
	int y;

    //constructor
    public Tile(ImageIcon i, int xLoc, int yLoc) {
    	image = i;
    	x = xLoc;
    	y = yLoc;
    }
    
    //accessors
    //display
    public void display(Graphics g, GBPanel panel)
    {
    	g.drawImage(image.getImage(), x, y, panel);
    }
}