package AEP;

import java.util.*;

public class Player {
	//variables
	private ArrayList<Unit> Units = new ArrayList<Unit>(); //the list of Units in the player's army
	private int rallyPoints = 0; //the rally points that the player has; these can be used to give their army buffs
	private int rallyLevel = 0; //the rally level the player is at; increase the potency of rally buffs
	
	//constructor
    public Player() {
		//does nothing
    }
    
    //modifiers
    //addUnit
    public void addUnit(Unit u)
    {
    	Units.add(u);
    }
    //removeUnit
    public void removeUnit(Unit u)
    {
    	Units.remove(u);
    }
    //setArmy
    public void setArmy(ArrayList<Unit> a)
    {
    	Units = a;
    }
    //accessors
    //getArmy
    public ArrayList<Unit> getArmy()
    {
    	return Units;
    }
    //getArmySize
    public int getArmySize()
    {
    	return Units.size();
    }
    //checks to see if the the player has a general
    public boolean hasGeneral()
    {
    	if (Units.size() == 0) { return false; }
    	
    	return Units.get(0).getType() == UnitType.GENERAL;
    }
}