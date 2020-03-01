package AEP;

import BreezySwing.*;
import javax.swing.*;
import java.awt.*;

public class GraphicsDisplay extends GBPanel{
	//variables
	private AdvanceEmblemPlus game;
	private Tile bg;
	public int mouseX = 0;
	public int mouseY = 0;
	public boolean mouseClicked = false;
	public boolean mouseHeld = false;
	Color bg_col = Color.WHITE;
	private Tile title;
	private Tile bg1;
	private Tile field;
	
    //constructor
    public GraphicsDisplay(AdvanceEmblemPlus _g) {
    	game = _g;
    	ImageIcon titlepic = new ImageIcon(AEP.GraphicsDisplay.class.getResource("/resources/Art/title.png"));
    	title = new Tile(titlepic, (1600 / 2) - (titlepic.getIconWidth() / 2), (900 / 3) - (titlepic.getIconHeight() / 2));
    	bg1 = new Tile(new ImageIcon(GraphicsDisplay.class.getResource("/resources/Art/titlebg.png")), 0, 0);
    	field = new Tile(new ImageIcon(GraphicsDisplay.class.getResource("/resources/Art/field.png")), 0, 0);
    }
    
    //modifiers
    //paintComponent
    public void paintComponent(Graphics g)
	{
		super.paintComponents(g);
		if (!game.ready) { return; }
		g.clearRect(0, 0, game.windowWidth, game.windowHeight);
		g.setFont(new Font("Coder's Crux 2 Regular", Font.PLAIN, 16));
		if (bg != null)
		{
			bg.display(g, this);
		}
		
		switch(game.state)
		{
			case TITLE: //title
				bg = bg1;
				title.display(g, this);
				displayButtons(g);
				break;
			case CHOOSINGUNITS: //choosing Units
				displayUnitString(g, this);
				displayButtons(g);
				break;
			case GAME: //game
				bg = field;
				displayGrid(g);
				displaySpaces(g);
				
				switch(game.gameAction)
				{
					case SELECTING: //selecting
					case MOVING: //moving
					case DECIDING: //deciding
						displayRange(g, this, new ImageIcon(GraphicsDisplay.class.getResource("/resources/Art/moveable.png")));
						break;
					case ATTACKING: //attacking
					case HEALING: //healing
						displayRange(g, this, new ImageIcon(GraphicsDisplay.class.getResource("/resources/Art/attackable.png")));
						break;
				}
				
				displayViewedUnit(g, this);
				displayArmies(g);
				displayButtons(g);
				
				displayTurn(g, this);
				break;
			case READINGTEXT: //reading text
				displayGrid(g);
				
				displayArmies(g);
				
				displayDialogBox(g, this);
				displayTurn(g, this);
				break;
			case GAMEOVER: //gameover
				displayGrid(g);
				displayArmies(g);
				
				displayDialogBox(g, this);
				break;
		}
	}
	
	private void displayGrid(Graphics g)
	{
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, (game.windowWidth / 2) - (5 * 64), 900);
		g.fillRect((game.windowWidth / 2) + (5 * 64), 0, 1600, 900);
		g.setColor(Color.BLACK);
		for (int i = 0; i <= 10; i++)
		{
			g.drawLine(game.gridOffsetX, game.gridOffsetY + (64 * i), game.windowWidth - game.gridOffsetX, game.gridOffsetY + (64 * i));
			g.drawLine(game.gridOffsetX + (64 * i), game.gridOffsetY, game.gridOffsetX + (64 * i), (game.windowHeight - game.gridOffsetY));
		}
	}
	
	private void displaySpaces(Graphics g)
	{
		for(int i = 0; i < game.spaces.length; i++)
		{
			for(int j = 0; j < game.spaces[0].length; j++)
			{
				game.spaces[i][j].display(g, this);
			}
		}
	}
	
	private void displayArmies(Graphics g)
	{
		int length = Math.max(game.players[0].getArmySize(), game.players[1].getArmySize());
		for(int i = 0; i < length; i++)
		{
			if (game.players[0].getArmySize() > i)
    		{
				game.players[0].getArmy().get(i).display(g, this);
    		}
    		if (game.players[1].getArmySize() > i)
    		{
    			game.players[1].getArmy().get(i).display(g, this);
    		}
		}
	}
	
	private void displayButtons(Graphics g)
	{
		for(PanelButton b : game.buttons)
		{
			b.display(g, this);
		}
	}
	
	//displayRanges
	//mvt
	private void displayRange(Graphics g, GBPanel panel, ImageIcon image)
	{
		if (game.selectedUnit != null)
		{
			Object[] objs = game.rangeCalc.GetRangeHashed();
			for(Object obj : objs)
			{
				Integer val = (Integer)obj;
				
				int x = game.gridOffsetX + (64 * game.rangeCalc.DehashColumn(val));
    			int y = (game.rangeCalc.DehashRow(val) * 64) + game.gridOffsetY;
				
				g.drawImage(image.getImage(), x, y, panel);
			}
		}
	}
	
	//displayViewedUnit
	public void displayViewedUnit(Graphics g, GBPanel panel)
	{
		int xOffset = (game.windowWidth / 2) + (5 * 64) + 240;
		int yOffset = (game.windowHeight / 6);
		FontMetrics fm = g.getFontMetrics();
    	int xLoc = fm.stringWidth("~Viewed Unit~") / 2;
    	int yLoc = fm.getAscent() / 2;
    	g.setColor(Color.YELLOW);
		g.drawString("~Viewed Unit~", xOffset - xLoc, yOffset - yLoc);
		if (game.viewedUnit != null)
		{
			xLoc = fm.stringWidth("Type: " + game.viewedUnit.getType() + " Side: " + game.viewedUnit.getSide()) / 2;
			yLoc = fm.getAscent() / 2;
			g.drawString("Type: " + game.viewedUnit.getType() + " Side: " + game.viewedUnit.getSide(), xOffset - xLoc, (yOffset - yLoc) + 25);
			xLoc = fm.stringWidth("HP: " + game.viewedUnit.getHp() + "/" + game.viewedUnit.getMaxHp() + "     ATK: " + game.viewedUnit.getAtk()) / 2;
			yLoc = fm.getAscent() / 2;
			g.drawString("HP: " + game.viewedUnit.getHp() + "/" + game.viewedUnit.getMaxHp() + "     ATK: " + game.viewedUnit.getAtk(), xOffset - xLoc, (yOffset - yLoc) + 50);
			xLoc = fm.stringWidth("STR: " + game.viewedUnit.getStr() + "        DEF: " + game.viewedUnit.getDef()) / 2;
			yLoc = fm.getAscent() / 2;
			g.drawString("STR: " + game.viewedUnit.getStr() + "        DEF: " + game.viewedUnit.getDef(), xOffset - xLoc, (yOffset - yLoc) + 75);
			xLoc = fm.stringWidth("MVT: " + game.viewedUnit.getMvt() + "         ACTRNG: " + game.viewedUnit.getMinRng() + "-" + game.viewedUnit.getMaxRng()) / 2;
			yLoc = fm.getAscent() / 2;
			g.drawString("MVT: " + game.viewedUnit.getMvt() + "         ACTRNG: " + game.viewedUnit.getMinRng() + "-" + game.viewedUnit.getMaxRng(), xOffset - xLoc, (yOffset - yLoc) + 100);
		}
		else
		{
			xLoc = fm.stringWidth("~NO DATA~") / 2;
			yLoc = fm.getAscent() / 2;
			g.drawString("~NO DATA~", xOffset - xLoc, (yOffset + 50) - yLoc);
		}
	}
	//displayTurn
	public void displayTurn(Graphics g, GBPanel panel)
	{
		int xOffset = (game.windowWidth / 2) - (5 * 64) - 240;
		int yOffset = (game.windowHeight / 6);
		FontMetrics fm = g.getFontMetrics();
    	int xLoc = fm.stringWidth("Turn: " + game.turn) / 2;
    	int yLoc = fm.getAscent() / 2;
    	g.setColor(Color.YELLOW);
    	g.drawString("Turn: " + game.turn, xOffset - xLoc, yOffset - yLoc);
    	xLoc = fm.stringWidth("Current Player: " + game.currentPlayer) / 2;
    	yLoc = fm.getAscent() / 2;
    	g.drawString("Current Player: " + (game.currentPlayer + 1), xOffset - xLoc, (yOffset - yLoc) + 25);
	}
	//displayDialogBox
	public void displayDialogBox(Graphics g, GBPanel panel)
	{
		g.setColor(Color.DARK_GRAY);
		g.fillRect((game.windowWidth / 2) - 250, (game.windowHeight / 2) - 12, 500, 50);
		g.setColor(Color.GRAY);
		g.drawRect((game.windowWidth / 2) - 250, (game.windowHeight / 2) - 12, 500, 50);
		g.setColor(Color.WHITE);
		game.dialog.getText(g, this, game.windowWidth / 2, game.windowHeight / 2);
	}
	//displayUnitString
	private void displayUnitString(Graphics g, GBPanel panel)
	{
		FontMetrics fm = g.getFontMetrics();
    	int xLoc = fm.stringWidth("Currently Selected Unit: " + game.UnitType.name()) / 2;
    	int yLoc = fm.getAscent() / 2;
    	g.setColor(Color.BLACK);
    	g.drawString("Currently Selected Unit: " + game.UnitType.name(), (game.windowWidth / 2) - xLoc, (game.windowHeight / 4) - yLoc);
    	xLoc = fm.stringWidth("Size of Player 1's Army: " + game.players[0].getArmySize()) / 2;
    	yLoc = fm.getAscent() / 2;
    	g.drawString("Size of Player 1's Army: " + game.players[0].getArmySize(), (game.windowWidth / 4) - xLoc, (int)(game.windowHeight * 0.7) - yLoc);
    	xLoc = fm.stringWidth("Size of Player 2's Army: " + game.players[1].getArmySize()) / 2;
    	yLoc = fm.getAscent() / 2;
    	g.drawString("Size of Player 2's Army: " + game.players[1].getArmySize(), ((3 * game.windowWidth) / 4) - xLoc, (int)(game.windowHeight * 0.7) - yLoc);
	}
	//mousePressed
	public void mousePressed(int x, int y)
	{
		mouseClicked = true;
		mouseHeld = true;
	}
	//mouseDragged
	public void mouseDragged(int x, int y)
	{
		mouseX = x;
		mouseY = y;
		mouseHeld = true;
	}
	//mouseReleased
	public void mouseReleased(int x, int y)
	{
		mouseHeld = false;
	}
	//mouseMoved
	public void mouseMoved(int x, int y)
	{
		mouseX = x;
		mouseY = y;
	}
	//repaint the the screen
	private void clear()
	{
		this.repaint();
	}
	//updates the GraphicsDisplay by taking in important data from JDH_FinalProjectCHHS.java
	public void update()
	{
		mouseClicked = false;
		clear();
	}
}