package AEP;

import javax.swing.*;

public class Unit_Medic extends Unit{
	
    //constructor
    public Unit_Medic(int x, int y, int side, int r, int c) {
    	super(UnitType.MEDIC, side, x, y, r, c,new ImageIcon[] {new ImageIcon(GraphicsDisplay.class.getResource("/resources/Art/p1medic1.png")),
        		new ImageIcon(GraphicsDisplay.class.getResource("/resources/Art/p2medic1.png")),
        		new ImageIcon(GraphicsDisplay.class.getResource("/resources/Art/inamedic1.png"))});
    	updateSprite(side);
    	maxHp = (int)(Math.random() * 14 + 12); //ranges from 12 - 25
    	hp = maxHp;
    	atk = 0; //doesn't attack
    	str = 0; //doesn't attack
    	def = (int)(Math.random() * 8 + 3); //ranges from 3 - 10
    	crit = 0; //can't perform a critical
    	luck = (int)(Math.random() * 36 + 75); //ranges from 75 - 110
    	mvt = 4;
    	minRng = 1;
    	maxRng = 1;
    }
    //modifiers
    //heal
    public int heal(Unit other)
    {
    	int amt; //the amount healed to the other Unit
    	amt = (int)(Math.random() * 13 + 3);
    	while (other.getHp() + amt > other.getMaxHp())
    	{
    		amt--;
    	}
    	return amt;
    }
}