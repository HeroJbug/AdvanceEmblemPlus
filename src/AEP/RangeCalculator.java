package AEP;

import java.util.*;
import java.awt.*;

public class RangeCalculator {
	private HashSet<Integer> range;
	private int width;
	private int height;
	
	private class RangeNode
	{
		public RangeNode(int _row, int _col, int _cost, int _val)
		{
			row = _row;
			col = _col;
			cost = _cost;
			val = _val;
		}
		
		public int row;
		public int col;
		public int cost;
		public int val;
	}

    public RangeCalculator(int w, int h) {
    	range = new HashSet<Integer>();
    	width = w;
    	height = h;
    }
    
    public void calculateRange(int row, int column, int minRng, int maxRng)
    {
    	range.clear();
    	
    	ArrayList<RangeNode> queue = new ArrayList<RangeNode>();
    	HashSet<Integer> visited = new HashSet<Integer>();
    	queue.add(new RangeNode(row, column, 0, HashFunction(row, column)));
    	visited.add(HashFunction(row, column));
    	
    	while (!queue.isEmpty())
    	{
    		RangeNode node = queue.remove(0);
    		
    		if (node.cost >= minRng && node.cost <= maxRng) { range.add(node.val); }
    		
    		addRange(queue, visited, node, 0, 1);
    		addRange(queue, visited, node, 1, 0);
    		addRange(queue, visited, node, 0, -1);
    		addRange(queue, visited, node, -1, 0);
    	}
    }
    
    private void addRange(ArrayList<RangeNode> queue, HashSet<Integer> visited, RangeNode node, int rowOffset, int columnOffset)
    {
		int row = node.row + rowOffset;
    	int column = node.col + columnOffset;
    	int val = HashFunction(row, column);
    	
    	if (!visited.contains(val) && row >= 0 && row < height && column >= 0 && column < width)
    	{
    		int cost = node.cost + 1;
    		queue.add(new RangeNode(row, column, cost, val));
    		visited.add(val);
    	}
    }
    
    public void clear()
    {
    	range.clear();
    }
    
    public boolean isInRange(int row, int col)
    {
    	int val = HashFunction(row, col);
    	
    	return range.contains(val);
    }
    
    public Object[] GetRangeHashed()
    {
    	return range.toArray();
    }
    
    private int HashFunction(int row, int column)
    {
    	return row * width + column;
    }
    
    public int DehashRow(int val)
    {
    	return val / width;
    }
    
    public int DehashColumn(int val)
    {
    	return val % width;
    }
    
    private RangeNode DehashFunction(int val)
    {
    	int row = DehashRow(val);
    	int col = DehashColumn(val);
    	
    	return new RangeNode(row, col, 0, val);
    }
}