package AEP;

import javax.swing.*;

public class Unit_Infantry extends Unit{
	
    //constructor
    public Unit_Infantry(int x, int y, int side, int r, int c)
    {
    	super(UnitType.INFANTRY, side, x, y, r, c, new ImageIcon[] {new ImageIcon(GraphicsDisplay.class.getResource("/resources/Art/p1infantry1.png")),
        		new ImageIcon(GraphicsDisplay.class.getResource("/resources/Art/p2infantry1.png")),
        		new ImageIcon(GraphicsDisplay.class.getResource("/resources/Art/inaInfantry1.png"))});
    	updateSprite(side);
    	maxHp = (int)(Math.random() * 9 + 10); //ranges from 10 - 18
    	hp = maxHp;
    	atk = (int)(Math.random() * 7 + 6); //ranges from 6 - 12
    	str = (int)(Math.random() * 7 + 4); //ranges 4 - 10
    	def = (int)(Math.random() * 7 + 5); //ranges from 5 - 11
    	crit = (int)(Math.random() * 21 + 70); //ranges from 70 - 110
    	luck = (int)(Math.random() * 51 + 50); //ranges from 50 - 100
    	mvt = 4;
    	minRng = 1;
    	maxRng = 1;
    }
}