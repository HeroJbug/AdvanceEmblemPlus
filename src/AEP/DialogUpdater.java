package AEP;

import BreezySwing.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class DialogUpdater {
	//variables
	private String text; //the dialog being input
	private int textIndex; //index on the array of strings that is being printed out
	private int linesDisplayed; //how many lines are displayed
	private int padding; //space between each line
	private Font font;
	ArrayList<String> dialog = new ArrayList<String>();
	
    //constructor
    public DialogUpdater(String txt, int space, int displayed) //creates new dialog
    {
    	linesDisplayed = displayed;
    	padding = space;
    	CreateDialogue(txt);
    }
    
    public DialogUpdater(String txt, int space)
    {
    	padding = space;
    	CreateDialogue(txt);
    }
    
    public DialogUpdater(String txt)
    {
    	padding = 20;
    	CreateDialogue(txt);
    }
    
    public DialogUpdater()
   	{
    	textIndex = 0;
    	linesDisplayed = 1;
    	padding = 20;
    	dialog.add("");
    }
    
    private void CreateDialogue(String txt)
    {
    	text = txt;
    	textIndex = 0;
    	linesDisplayed = 1;
    	String line = "";
    	int i = text.length() - 1;
    	while(i > 0)
    	{
    		if (i + 2 < text.length() - 1)
    		{
    			if (text.substring(i, i + 3).equals("{#}"))
    			{
    				line = text.substring(i + 3, text.length());
    				text = text.substring(0, i);
    				i = text.length() - 1;
    				for (int k = 0; k < line.length() - 3; k++)
    				{
    					if (line.substring(k, k + 3).equals("{#}"))
    					{
    						line = line.substring(0, k + 1);
    					}
    				}
    				dialog.add(line);
    			}
    			else
    			{
    				i--;
    			}
    		}
    		else
    		{
    			i--;
    		}
    	}
		dialog.add(text);
		textIndex = dialog.size() - 1;
    }
    
    //modifiers
    //textIndex
    public void nextLine()
    {
    	if (!isAtEnd())
    	{
    		textIndex -= linesDisplayed;
    	}
    }
    //setFont
    public void setFont(Font fnt)
    {
    	font = fnt;
    }
    //setPadding
    public void setPadding(int space)
    {
    	padding = space;
    }
    
    //accessors
    //text
    public void getText(Graphics g, GBPanel panel, int x, int y)
    {
    	FontMetrics fm = g.getFontMetrics();
    	int xLoc;
    	int yLoc;
    	for (int i = 0; i < linesDisplayed; i++)
    	{
    		if (textIndex - i >= 0)
    		{
    			xLoc = fm.stringWidth(dialog.get(textIndex - i)) / 2;
    			yLoc = fm.getAscent() / 2;
    			g.drawString(dialog.get(textIndex - i), x - xLoc, (y + padding) - yLoc);
    		}
    	}
    }
    //text
    public String getText()
    {
    	return (String)(dialog.get(textIndex));
    }
    //textIndex
    public int getTextIndex()
    {
    	return textIndex;
    }
    //toString
    public String toString()
    {
    	String txt = "";
    	for(Object s:dialog)
    	{
    		txt += s;
    	}
    	return txt;
    }
    //size
    public int getSize()
    {
    	return dialog.size();
    }
    //isAtEndOfDialog
    public boolean isAtEnd()
    {
    	if (textIndex - linesDisplayed < 0)
    	{
    		return true;
    	}
    	return false;
    }
}