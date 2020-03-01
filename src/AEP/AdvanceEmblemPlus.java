package AEP;

import BreezySwing.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class AdvanceEmblemPlus extends GBFrame{
	//variables
	boolean ready = false;
	int turn = 1;
	ArrayList<PanelButton> buttons = new ArrayList<PanelButton>();
	DialogUpdater dialog = new DialogUpdater();
	Space[][] spaces = new Space[10][10];
	Player[] players = new Player[2];
	Unit selectedUnit = null; //the currently selected Unit
	Unit viewedUnit = null; //the Unit whose stats are being displayed
	UnitType UnitType;
	int currentPlayer;
	GameStates state = GameStates.TITLE;
	GameActions gameAction = GameActions.MOVING;
	float xScale = 0;
	float yScale = 0;
	int windowWidth;
	int windowHeight;
	int gridOffsetX;
	int gridOffsetY;
	RangeCalculator rangeCalc;
	Unit otherUnit;
	int dialogCounter = 0;
	int actionsListX;
	int actionsListY;
	int tempRow;
	int tempColumn;
	int gameSpeed = 60/1000;
	javax.swing.Timer timer;
	
	//panels
	GraphicsDisplay draw = new GraphicsDisplay(this);
	GBPanel drawing = addPanel(draw, 1, 1, 1, 1);
	
	public AdvanceEmblemPlus()
	{
		ActionListener action = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (draw.mouseClicked){panelButtonClicked();}
				panelButtonHightlighted();
				int armyIdx;
				switch(state)
				{
					case TITLE: //title
						switch(ClickedButton())
						{
							case 0: //start game
								state = GameStates.CHOOSINGUNITS;
								buttons.clear();
								buttons.add(new PanelButton("Begin battle?", windowWidth / 2, windowHeight - 120, 150, 50)); //0
								buttons.add(new PanelButton("Add to Player 1's team?", windowWidth / 4, (int)(windowHeight * 0.65), 210, 50)); //1
								buttons.add(new PanelButton("Add to Player 2's team?", (windowWidth * 3) / 4, (int)(windowHeight * 0.65), 210, 50)); //2
								buttons.add(new PanelButton("Infantry", windowWidth / 2, (int)(windowHeight * 0.3), 100, 50)); //3
								buttons.add(new PanelButton("Sniper", windowWidth / 2, (int)(windowHeight * 0.3) + 50, 100, 50)); //4
								buttons.add(new PanelButton("Medic", windowWidth / 2, (int)(windowHeight * 0.3) + 100, 100, 50)); //5
								buttons.add(new PanelButton("Tank", windowWidth / 2, (int)(windowHeight * 0.3) + 150, 100, 50)); //6
								UnitType = UnitType.INFANTRY;
								break;
							case 1: //quit game
								System.exit(0);
								break;
							case 2: //showTutorial
								ShowTutorial();
								break;
						}
						break;
					case CHOOSINGUNITS: //when the players are choosing their Units
						armyIdx = 0;
						switch(ClickedButton())
						{
							case 0: //start game
								state = GameStates.GAME;
								ChangeGameAction(GameActions.SELECTING);
							
								for(int i = 0; i < spaces.length; i++)
								{
									for(int j = 0; j < spaces[0].length; j++)
									{
										spaces[i][j] = new Space(CalculateX(j) - 32, CalculateY(i) - 32, i, j, new Hitbox(0, 0, 64, 64));
									}
								}
								break;
							case 2: //adding to a player's army
								armyIdx++;
							case 1: 
								if (players[armyIdx].getArmySize() < 10)
								{
									addUnitToArmy(UnitType, armyIdx);
								}
								break;
							case 3: //Infantry
								UnitType = UnitType.INFANTRY;
								break;
							case 4: //sniper
								UnitType = UnitType.SNIPER;
								break;
							case 5: //medic
								UnitType = UnitType.MEDIC;
								break;
							case 6: //tank
								UnitType = UnitType.TANK;
								break;
						}
						break;
					case GAME: //game
						armyIdx = currentPlayer;
						switch(gameAction)
						{
							case SELECTING: //selecting
								setSpaceVisible();
								viewedUnit = getViewedUnit();
								if (draw.mouseClicked)
								{
									selectedUnit = getSelectedUnit(players[currentPlayer]);
									if (selectedUnit != null && selectedUnit.getActive())
									{
										rangeCalc.calculateRange(selectedUnit.getRow(), selectedUnit.getColumn(), 0, selectedUnit.getMvt());
										ChangeGameAction(GameActions.MOVING);
									}
								}
								else if (buttons.get(0).getActive())
								{
									changeControl();
								}
								break;
							case MOVING: //moving	
								setSpaceVisible();
									
								if (draw.mouseHeld)
								{
									selectedUnit.setX(draw.mouseX);
									selectedUnit.setY(draw.mouseY);
								}
								else
								{
									Space selectedSpace = getSelectedSpace();
									
									if (selectedSpace != null && isInRange()
									&& (getUnitAtGrid(players[currentPlayer], selectedSpace.getRow(), selectedSpace.getColumn()) == selectedUnit
									|| getUnitAtGrid(players[(currentPlayer + 1) % 2], selectedSpace.getRow(), selectedSpace.getColumn()) == null))
									{
										if (selectedSpace.getRow() != selectedUnit.getRow() || selectedSpace.getColumn() != selectedUnit.getColumn())
										{
											tempRow = selectedSpace.getRow();
											tempColumn = selectedSpace.getColumn();
										}
										else
										{
											tempRow = selectedUnit.getRow();
											tempColumn = selectedUnit.getColumn();
										}
										
										selectedUnit.setY(CalculateY(tempRow));
										selectedUnit.setX(CalculateX(tempColumn));
											
										//if (tempColumn == 0){actionsListX = selectedUnit.getX() + 37;}else{actionsListX = selectedUnit.getX() - 37;}
										//if (tempRow == 0){actionsListY = selectedUnit.getY() + 62;}else{actionsListY = selectedUnit.getY();}
										actionsListX = CalculateX(tempColumn);
										actionsListY = CalculateY(tempRow);
										ChangeGameAction(GameActions.DECIDING);											
									}
									else
									{
										selectedUnit.setY(CalculateY(selectedUnit.getRow()));
										selectedUnit.setX(CalculateX(selectedUnit.getColumn()));
										selectedUnit = null;
										ChangeGameAction(GameActions.SELECTING);
									}
								}
								break;
							case DECIDING: //deciding
								switch (ClickedButton())
								{
									case 0:
										selectedUnit.setY(CalculateY(selectedUnit.getRow()));
										selectedUnit.setX(CalculateX(selectedUnit.getColumn()));
										selectedUnit = null;
										ChangeGameAction(GameActions.SELECTING);
										break;
									case 1:
										SetSelectedUnitInactive();
										selectedUnit = null;
										ChangeGameAction(GameActions.SELECTING);
										break;
									case 2:
										if (selectedUnit.getType() == UnitType.MEDIC)
										{
											ChangeGameAction(GameActions.HEALING);
										}
										break;
									case 3:
										if (selectedUnit.getType() != UnitType.MEDIC)
										{
											ChangeGameAction(GameActions.ATTACKING);
										}
										break;
								}
								break;
							case ATTACKING: //attacking
								armyIdx = (armyIdx + 1) % 2;
							case HEALING: //healing
								viewedUnit = getViewedUnit();
								setSpaceVisible();
								if (draw.mouseClicked && isInRange())
								{
									otherUnit = getSelectedUnit(players[armyIdx]);
									if (otherUnit != null)
									{
										buttons.clear();
										state = GameStates.READINGTEXT;
									}
								}
								else if (buttons.get(0).getActive())
								{
									ChangeGameAction(GameActions.DECIDING);
								}
								break;
						}
						if (gameover())
						{
							dialog = new DialogUpdater("Player " + ((currentPlayer + 1) % 2 + 1) + "'s army has been defeated...{#}Player "
							+ (currentPlayer + 1) + " wins!{#}~GAME COMPLETE~{#}endGame");
							buttons.clear();
							state = GameStates.GAMEOVER;
						}
						break;
					case READINGTEXT: //reading text
						if (dialog.toString().equals(""))
						{
							int amt = 0;
							switch (gameAction)
							{
								case ATTACKING: //attacking
									amt = doBattle();
									break;
								case HEALING: //healing
									amt = doHeal();
									break;
							}
							otherUnit.modifyHp(amt);
						}
						if(!dialog.isAtEnd())
						{
							runDialog(360);
						}
						else
						{
							dialog = new DialogUpdater();
							SetSelectedUnitInactive();
							
							players[currentPlayer].getArmy().set(getUnitId(players[currentPlayer], selectedUnit), selectedUnit);
							if (gameAction == GameActions.ATTACKING)
							{
								int otherPlayer = (currentPlayer + 1) % 2;
								if (otherUnit.getHp() <= 0)
								{
									players[otherPlayer].getArmy().remove(otherUnit);
								}
							}
							
							selectedUnit = null;
							otherUnit = null;
							ChangeGameAction(GameActions.SELECTING);
							state = GameStates.GAME;
						}
						break;
					case GAMEOVER: //gameover
						runDialog(360);
						if (dialog.getText().equals("endGame"))
						{
							restartGame();
						}
						break;
				}
				if (noMoreActiveUnits(players[currentPlayer]))
				{
					changeControl();
				}
				
				draw.update();
			}
		};
		timer = new javax.swing.Timer(gameSpeed, action);
	}
	
	public void StartGame(int w, int h)
	{
		windowWidth = w;
    	windowHeight = h;
    	
    	gridOffsetX = (windowWidth / 2) - (64 * 5);
    	gridOffsetY = (windowHeight / 2) - (64 * 5);
    	xScale = (float)windowWidth / 1600;
    	yScale = (float)windowHeight / 900;
    	
		restartGame();

		timer.setInitialDelay(0);
		timer.start();
		ready = true;
	}
	
	public void ShowTutorial()
	{
		messageBox("STARTING THE GAME:"
				+ "\n\tTo start the game, first select all the Units you want in your army.\nThere are many Units to recruit!"
    			+ " some of which include Infantry, Snipers, Tanks, and Medics!\nOnce you got all of the Units that you want,"
    			+ " press the \"Start Game\" button to start the game!"
    			+ "\n\nUNIT TYPES AND DESCRIPTIONS"
    			+ "\n\tGeneral:\n\t\tGenerals are the leaders of the army. They can move farther"
    			+ "\n\t\t than anyone else and their well rounded stats are nothing to scoff at."
    			+ "\n\t\tComes as the starting Unit and players can't have more than one per army."
    			+ "\n\tInfantry:\n\t\tThe common soldier. Has the lowest opportUnity cost out all of the Unit types."
    			+ "\n\tSniper:\n\t\tCan attack from a distance with massive power but lack of defense."
    			+ "\n\tTank:\n\t\tMassive beasts of metal with Strong HP and Defense. Lack mobility."
    			+ "\n\tMedic:\n\t\tThe healer class. A glass wall who can't attack but is the only"
    			+ "\n\t\tUnit that heal other Units."
    			+ "\n\nHOW TO PLAY:"
    			+ "\n\tYou must beat the other player's army by taking out their troops\nor General! By simply dragging a Unit"
    			+ "\nto a new space, you can select from a list of actions to command your troops into battle!\nMove your Units with the move"
    			+ " command, strike the enemy with the attack command, heal their\nwounds with theheal command, and when"
    			+ " you're satsified hit the End turn button to hand it\nto other player to fight your cunning strategy!"
    			, 760, 600);
	}
	
	public int ClickedButton()
	{
		for(int i = 0; i < buttons.size(); i++)
		{
			if (buttons.get(i).getActiveUnSafe())
			{
				buttons.get(i).setActive(false);
				return i;
			}
		}
		
		return -1;
	}
	
	public void SetSelectedUnitInactive()
	{
		selectedUnit.setRow(tempRow);
		selectedUnit.setColumn(tempColumn);
		selectedUnit.setActive(false);
		selectedUnit.updateSprite(2);
	}
	
	public boolean isInRange()
	{
		Space selectedSpace = getSelectedSpace();
		
		if (selectedSpace != null)
		{
			return rangeCalc.isInRange(selectedSpace.getRow(), selectedSpace.getColumn());
		}
		
		return false;
	}
	
	public void addUnitToArmy(UnitType type, int i)
	{
		Player player = players[i];
		int r, c;
		r = 9 * (1 - i);
		do{
			c = (int)(Math.random() * 10);
		}while(!checkUnitPlacement(player, r, c));
		int x = CalculateX(c);
		int y = CalculateY(r);
		
		//adds the Unit to the player's army
		switch(type)
    	{
    		case INFANTRY: //if the Unit is an Infantry Unit
    			player.addUnit(new Unit_Infantry(x, y, i, r, c));
    			break;
    		case SNIPER: //if the Unit is a Sniper Unit
    			player.addUnit(new Unit_Sniper(x, y, i, r, c));
    			break;
    		case TANK: //if the Unit is a Tank Unit
    			player.addUnit(new Unit_Tank(x, y, i, r, c));
    			break;
    		case MEDIC: //if the Unit is a Medic Unit
    			player.addUnit(new Unit_Medic(x, y, i, r, c));
    			break;
    	}
	}
	
	public void ChangeGameAction(GameActions actionToChange)
	{
		gameAction = actionToChange;
		
		buttons.clear();
		switch(actionToChange)
		{
			case SELECTING:
				rangeCalc.clear();
			case MOVING:
				buttons.add(new PanelButton("End Turn", (windowWidth / 2) + (5 * 64) + 240, (windowHeight * 7) / 8, 75, 50));
				break;
			case DECIDING:
				rangeCalc.clear();
				buttons.add(new PanelButton("Cancel", actionsListX, actionsListY - 25, 75, 50)); //0
				buttons.add(new PanelButton("Wait", actionsListX, actionsListY - 75, 75, 50)); //1
				buttons.add(new PanelButton("Heal", actionsListX, actionsListY - 125, 75, 50)); //2
				buttons.add(new PanelButton("Attack", actionsListX, actionsListY - 175, 75, 50)); //3
				if (selectedUnit.getType() == UnitType.MEDIC)
				{
					buttons.get(3).textColor = Color.DARK_GRAY;
				}
				else
				{
					buttons.get(2).textColor = Color.DARK_GRAY;
				}
				break;
			case ATTACKING:
			case HEALING:
				rangeCalc.calculateRange(tempRow, tempColumn, selectedUnit.getMinRng(), selectedUnit.getMaxRng());
				buttons.add(new PanelButton("Cancel", CalculateX(tempColumn), CalculateY(tempRow), 64, 50));
				break;
		}
	}
	
	public void panelButtonClicked()
	{
		for(PanelButton b : buttons)
		{
			if (b.checkCollision(draw.mouseX, draw.mouseY))
			{
				b.setActive(true);
			}
		}
	}
	
	public void panelButtonHightlighted()
	{
		for(PanelButton b : buttons)
		{
			if (b.checkCollision(draw.mouseX, draw.mouseY))
			{
				b.setCursorIsOver(true);
			}
			else
			{
				b.setCursorIsOver(false);
			}
		}
	}
	
	public void runDialog(int counter)
	{
		if (dialogCounter == counter)
		{
			dialog.nextLine();
			dialogCounter = 0;
		}
		else
		{
			dialogCounter++;
		}
	}
	
	//noMoreActiveUnits
	public boolean noMoreActiveUnits(Player player)
	{
		for(Unit u : player.getArmy())
		{
			if (u.getActive())
			{
				return false;
			}
		}
		return true;
	}
	
	public boolean checkUnitPlacement(Player p, int r, int c)
	{
		for(Unit u : p.getArmy())
		{
			if (u.getRow() == r && u.getColumn() == c)
			{
				return false;
			}
		}
		return true;
	}
	
	public int doBattle()
	{
		int dmg = selectedUnit.attack(otherUnit);
		int playerAttacked = (currentPlayer + 1) % 2;
		String text = "Player " + (currentPlayer + 1) + "'s " + selectedUnit.getTypeString() + " Attacked!{#}"
			+ "Player " + (currentPlayer + 1) + "'s " + selectedUnit.getTypeString() + " dealt "
			+ Math.abs(dmg) + " damage to Player " + (playerAttacked + 1) + "'s " + otherUnit.getTypeString() + "!{#}";
		if (otherUnit.getHp() + dmg <= 0)
		{
			text += "Player " + (playerAttacked + 1) + "'s " + otherUnit.getTypeString() + " fled the battlefield!{#}";
		}
		dialog = new DialogUpdater(text + "end");
		return dmg;
	}
	
	public int doHeal()
	{
		Unit_Medic healer = (Unit_Medic)(selectedUnit);
		int amt = healer.heal(otherUnit);
		dialog = new DialogUpdater("Player " + (currentPlayer + 1) + "'s Medic healed their army's " + otherUnit.getTypeString() + "!{#}"
			+ "Player " + currentPlayer + "'s Medic " + " restored " + amt + " HP to Player " + currentPlayer + "'s " + otherUnit.getTypeString() + "!{#}"
			+ "end");
		return amt;
	}
	
	public Unit getViewedUnit()
	{
		int length = Math.max(players[0].getArmySize(), players[1].getArmySize());
		for (int i = 0; i < length; i++)
		{
			if (players[0].getArmySize() > i && players[0].getArmy().get(i).checkCollision(draw.mouseX, draw.mouseY))
			{
				return players[0].getArmy().get(i);
			}
			if (players[1].getArmySize() > i && players[1].getArmy().get(i).checkCollision(draw.mouseX, draw.mouseY))
			{
				return players[1].getArmy().get(i);
			}
		}
		return null;
	}
	
	public Unit getSelectedUnit(Player p)
	{
		for(Unit u : p.getArmy())
		{
			if (u.checkCollision(draw.mouseX, draw.mouseY))
			{
				return u;
			}
		}
		return null;
	}
	
	public void setSpaceVisible()
	{
		for(int i = 0; i < spaces.length; i++)
		{
			for(int j = 0; j < spaces[0].length; j++)
			{
				spaces[i][j].visible = spaces[i][j].checkCollision(draw.mouseX, draw.mouseY);
			}
		}
	}
	
	public Space getSelectedSpace()
	{
		for(int i = 0; i < spaces.length; i++)
		{
			for(int j = 0; j < spaces[0].length; j++)
			{
				if (spaces[i][j].visible)
				{
					return spaces[i][j];
				}
			}
		}
		return null;
	}
	
	public int getUnitId(Player p, Unit u)
	{
		int i = 0;
		for(Unit us : p.getArmy())
		{
			if (us.equals(u))
			{
				return i;
			}
			i++;
		}
		return -1;
	}
	
	public Unit getUnitAtGrid(Player p, int r, int c)
	{
		for(Unit us : p.getArmy())
		{
			if (us.getRow() == r && us.getColumn() == c)
			{
				return us;
			}
		}
		return null;
	}
	 
	//change controlling player
    public void changeControl()
    {
    	currentPlayer = (currentPlayer + 1) % 2;
    	
    	setAllActive(true);
    	if (currentPlayer == 0) { turn++; }
    }
    
    public void setAllActive(boolean status)
    {
    	int length = Math.max(players[0].getArmySize(), players[1].getArmySize());
    	for(int i = 0; i < length; i++)
    	{
    		if (players[0].getArmySize() > i)
    		{
    			players[0].getArmy().get(i).setActive(true);
    			players[0].getArmy().get(i).updateSprite(0);
    		}
    		if (players[1].getArmySize() > i)
    		{
    			players[1].getArmy().get(i).setActive(true);
    			players[1].getArmy().get(i).updateSprite(1);
    		}
    	}
    }
    
    //check to see if the game is over
    public boolean gameover()
    {
    	int otherPlayer = (currentPlayer + 1) % 2;
    	return !players[otherPlayer].hasGeneral();
    }
    
    //restart the game
    public void restartGame()
    {
    	turn = 1;
		buttons = new ArrayList<PanelButton>();
		dialog = new DialogUpdater();
		spaces = new Space[10][10];
		players = new Player[2];
		players[0] = new Player();
		players[1] = new Player();
		selectedUnit = null; //the currently selected Unit
		viewedUnit = null; //the Unit whose stats are being displayed
		UnitType = UnitType.INFANTRY;
		rangeCalc = new RangeCalculator(spaces.length, spaces[0].length);
		currentPlayer = 0;
		state = GameStates.TITLE;
		gameAction = GameActions.MOVING;
		otherUnit = null;
		dialogCounter = 0;
		actionsListX = 0;;
		tempRow = 0;
		tempColumn = 0;
		
		players[0].addUnit(new Unit_General(CalculateX(2), CalculateY(9), 0, 9, 2));
		players[1].addUnit(new Unit_General(CalculateX(7), CalculateY(0), 1, 0, 7));
		buttons.add(new PanelButton("Start game", 3 * windowWidth / 8, (int)(windowHeight * 0.85), 300, 50));
		buttons.add(new PanelButton("Quit game", 5 * windowWidth / 8, (int)(windowHeight * 0.85), 300, 50));
		buttons.add(new PanelButton("How to Play", 310, 60, 300, 50));
    }
    
    public int CalculateX(int col)
    {
    	return gridOffsetX + (64 * col) + 32;
    }
    
    public int CalculateY(int row)
    {
    	return gridOffsetY + (64 * row) + 32;
    }
    
    public static void main(String[] args) {
    	JFrame frm = new AdvanceEmblemPlus();
    	frm.setExtendedState(JFrame.MAXIMIZED_BOTH);
    	frm.setUndecorated(true);
    	frm.setTitle("Advance Emblem +");
    	frm.setVisible(true);
    	AdvanceEmblemPlus game = (AdvanceEmblemPlus)frm;
    	game.StartGame(frm.getWidth(), frm.getHeight());
    }
}