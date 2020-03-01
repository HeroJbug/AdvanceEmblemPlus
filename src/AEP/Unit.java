package AEP;

import BreezySwing.*;
import javax.swing.*;
import java.awt.*;

public class Unit implements InteractableObject{
	//variables
	protected UnitType type; //the type of Unit
	protected int maxHp; //the maximum health points of the Unit
	protected int hp; //the health points of the Unit
	protected int atk; //the Unit's attack power; will be used to create a variety between attacks
	protected int str; //the strength of the Unit
	protected int def; //the Unit's defense
	protected int mvt; //the range of movement of the Unit
	protected int minRng; //the minimum attack range of the Unit
	protected int maxRng; //the maximum attack range of the Unit
	protected int side; //the side that the Unit is on
	protected int crit; //the chance that the Unit will perform a critical
	protected int luck; //the chance that the Unit's enemy won't attack with a critical
	private boolean active = true; //states whether the Unit is active or not
	private Sprite sprite; //the sprite of the object
	private int x; //the x coordinate of the object
	private int y; //the y coordinate of the object
	private int row; //the row that the Unit is in
	private int column; //the column that the Unit is in
	
    //constructor
    public Unit(UnitType t, int s, int xLoc, int yLoc, int r, int c, ImageIcon[] images)
    {
    	sprite = new Sprite(images, 32, 32);
    	type = t;
    	side = s;
    	setX(xLoc);
    	setY(yLoc);
    	row = r;
    	column = c;
    }
    
    //modifiers
    //attack
    public int attack(Unit other)
    {
    	int amt; //the amount of damage dealt to the other Unit
    	amt = -(Math.max(1,
    			(int)(Math.random() * (this.getAtk() + 1) + this.getStr()) - other.getDef())
    			);
    	return amt;
    }
    //hp
    public void modifyHp(int amt)
    {
    	while (hp + amt > maxHp)
		{
			amt--;
		}
		hp += amt;
    }
    //setActive
    public void setActive(boolean a)
    {
    	active = a;
    }
    //setX
    public void setX(int loc)
    {
    	x = loc - sprite.getOriginX();
    }
    //setY
    public void setY(int loc)
    {
    	y = loc - sprite.getOriginY();
    }
    //updateSprite
   	public void updateSprite(int idx)
   	{
   		sprite.update(idx);
   	}
   	//setRow
   	public void setRow(int r)
   	{
   		row = r;
   	}
   	//setColumn
   	public void setColumn(int c)
   	{
   		column = c;
   	}
   	//setSprite
   	public void setSprite(Sprite spr)
   	{
   		sprite = spr;
   	}
    //accessors
    //display
    public void display(Graphics g, GBPanel panel)
    {
    	sprite.drawSprite(g, panel, x, y);
    }
    //x
    public int getX()
    {
    	return x;
    }
    //y
    public int getY()
    {
    	return y;
    }
    //getRow
    public int getRow()
    {
    	return row;
    }
    //getColumn
    public int getColumn()
    {
    	return column;
    }
    //type
    public UnitType getType()
    {
    	return type;
    }
    public String getTypeString()
    {
    	String str = "";
    	
    	switch(type)
    	{
    		case INFANTRY: //if the Unit is an Infantry Unit
    			str = "Infantry";
    			break;
    		case SNIPER: //if the Unit is a Sniper Unit
    			str = "Sniper";
    			break;
    		case TANK: //if the Unit is a Tank Unit
    			str = "Tank";
    			break;
    		case MEDIC: //if the Unit is a Medic Unit
    			str = "Medic";
    			break;
    		case GENERAL: //if the Unit is an infantry Unit
    			str = "General";
    			break;
    	}
    	
    	return str;
    }
    //hp
    public int getHp()
    {
    	return hp;
    }
    //maxHp
    public int getMaxHp()
    {
    	return maxHp;
    }
    //atk
    public int getAtk()
    {
    	return atk;
    }
    //str
    public int getStr()
    {
    	return str;
    }
    //def
    public int getDef()
    {
    	return def;
    }
    //crit
    public int getCrit()
    {
    	return crit;
    }
    //luck
    public int getLck()
    {
    	return luck;
    }
    //mvt
    public int getMvt()
    {
    	return mvt;
    }
    //minRng
    public int getMinRng()
    {
    	return minRng;
    }
    //maxRng
    public int getMaxRng()
    {
    	return maxRng;
    }
    //side
    public int getSide()
    {
    	return side;
    }
    //active
    public boolean getActive()
    {
    	return active;
    }
    //isInBounds
    public boolean checkCollision(int xLoc, int yLoc)
    {
    	return sprite.checkCollision(xLoc, yLoc, x, y);
    }
    //equals
    public boolean equals(Unit other)
    {
    	if (this.getType().equals(other.getType()) && this.getHp() == other.getHp() && this.getAtk() == other.getAtk()
    		&& this.getStr() == other.getStr() && this.getDef() == other.getDef() && this.getMvt() == other.getMvt()
    		&& this.getMinRng() == other.getMinRng() && this.getMaxRng() == other.getMaxRng() && this.getSide() == other.getSide()
    		&& this.getRow() == other.getRow() && this.getColumn() == other.getColumn())
    	{
    		return true;
    	}
    	return false;
    }
    //toString
    public String toString()
    {
    	return "Type: " + this.getTypeString() + " - HP: " + this.getHp();  
    }
}