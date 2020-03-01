package AEP;

public class Hitbox {
	//variables
	private int boundX1; //the X value of the top lefthand corner of the hitbox
	private int boundY1; //the Y value of the top lefthand corner of the hitbox
	private int boundX2; //the X value of the bottom righthand corner of the hitbox
	private int boundY2; //the Y value of the bottom righthand corner of the hitbox
	
    //constructor
    public Hitbox(int bX1, int bY1, int bX2, int bY2) {
    	boundX1 = bX1;
    	boundY1 = bY1;
    	boundX2 = bX2;
    	boundY2 = bY2;
    }
    
    //modifiers
    //boundX1
    public void setBX1(int x)
    {
    	boundX1 = x;
    }
    //boundY1
    public void setBY1(int y)
    {
    	boundY1 = y;
    }
    //boundX2
    public void setBX2(int x)
    {
    	boundX2 = x;
    }
    //boundY2
    public void setBY2(int y)
    {
    	boundY2 = y;
    }
    //accessors
    //getWidth
    public int getWidth()
    {
    	return boundX2 - boundX1;
    }
    //getHeight
    public int getHeight()
    {
    	return boundY2 - boundY1;
    }
    //getBoundX1
    public int getBX1()
    {
    	return boundX1;
    }
    //getBoundX2
    public int getBX2()
    {
    	return boundX2;
    }
    //getBoundY1
    public int getBY1()
    {
    	return boundY1;
    }
    //getBoundY2
    public int getBY2()
    {
    	return boundY2;
    }
    //isInBounds
    public boolean isInBounds(int x, int y, int boxLocX, int boxLocY)
    {
    	return (x >= boxLocX + boundX1 && x <= boxLocX + boundX2
    			&& y >= boxLocY + boundY1 && y <= boxLocY + boundY2);
    }
}