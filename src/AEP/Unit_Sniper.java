package AEP;

import javax.swing.*;

public class Unit_Sniper extends Unit{
	
    //constructor
    public Unit_Sniper(int x, int y, int side, int r, int c)
    {
    	super(UnitType.SNIPER, side, x, y, r, c, new ImageIcon[] {new ImageIcon(GraphicsDisplay.class.getResource("/resources/Art/p1sniper1.png")),
        		new ImageIcon(GraphicsDisplay.class.getResource("/resources/Art/p2sniper1.png")),
        		new ImageIcon(GraphicsDisplay.class.getResource("/resources/Art/inasniper1.png"))});
    	updateSprite(side);
    	maxHp = (int)(Math.random() * 9 + 10); //ranges from 10 - 18
    	hp = maxHp;
    	atk = (int)(Math.random() * 8 + 7); //ranges from 7 - 14
    	str = (int)(Math.random() * 11 + 3); //ranges 3 - 13
    	def = (int)(Math.random() * 7 + 3); //ranges from 3 - 9
    	crit = (int)(Math.random() * 44 + 87); //ranges from 87 - 130
    	luck = (int)(Math.random() * 46 * 50); //ranges from 50 - 95
    	mvt = 4;
    	minRng = 2;
    	maxRng = 2;
    }  	
}