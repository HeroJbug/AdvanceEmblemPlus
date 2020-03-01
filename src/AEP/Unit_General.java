package AEP;

import javax.swing.*;

public class Unit_General extends Unit{
	
    //constructor
    public Unit_General(int x, int y, int side, int r, int c) 
    {
    	super(UnitType.GENERAL, side, x, y, r, c, new ImageIcon[] {new ImageIcon(GraphicsDisplay.class.getResource("/resources/Art/p1general1.png")),
        		new ImageIcon(GraphicsDisplay.class.getResource("/resources/Art/p2general1.png")),
        		new ImageIcon(GraphicsDisplay.class.getResource("/resources/Art/inageneral1.png"))});
    	updateSprite(side);
    	maxHp = (int)(Math.random() * 14 + 12); //ranges from 12 - 25
    	hp = maxHp;
    	atk = (int)(Math.random() * 7 + 6); //ranges from 6 - 12
    	str = (int)(Math.random() * 7 + 4); //ranges 4 - 10
    	def = (int)(Math.random() * 7 + 5); //ranges from 5 - 11
    	crit = (int)(Math.random() * 41 + 80); //ranges from 80 - 120
    	luck = (int)(Math.random() * 46 + 60); //ranges from 60 - 105
    	mvt = 5;
    	minRng = 1;
    	maxRng = 1;
    }
}