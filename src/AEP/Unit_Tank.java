package AEP;

import javax.swing.*;

public class Unit_Tank extends Unit {
	
    //constructor
    public Unit_Tank(int x, int y, int side, int r, int c)
    {
    	super(UnitType.TANK, side, x, y, r, c, new ImageIcon[] {new ImageIcon(GraphicsDisplay.class.getResource("/resources/Art/p1tank1.png")),
        		new ImageIcon(GraphicsDisplay.class.getResource("/resources/Art/p2tank1.png")),
        		new ImageIcon(GraphicsDisplay.class.getResource("/resources/Art/inatank1.png"))});
    	updateSprite(side);
    	maxHp = (int)(Math.random() * 9 + 10); //ranges from 10 - 18
    	hp = maxHp;
    	atk = (int)(Math.random() * 9 + 8); //ranges from 8 - 16
    	str = (int)(Math.random() * 11 + 6); //ranges 6 - 16
    	def = (int)(Math.random() * 11 + 10); //ranges from 10 - 20
    	crit = (int)(Math.random() * 44 + 47); //ranges from 47 - 90
    	luck = (int)(Math.random() * 40 * 40); //ranges from 40 - 80
    	mvt = 3;
    	minRng = 1;
    	maxRng = 2;
    }
}