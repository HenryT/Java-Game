package peacenerd.generate;

import java.util.ArrayList;

import peacenerd.level.*;
import peacenerd.level.GridCell.GridCellType;
import peacenerd.utils.MathHelper;
import peacenerd.utils.MathHelper.Pair;

public class GridGenerator {
	private String wall;
	private String floor;
	
	public GridGenerator() {
		wall = "Resources/Images/Tiles/wall2.jpg"; 
		floor = "Resources/Images/Tiles/floor2.jpg";
	}
	
	public Grid generateGrid(String name, int rows, int cols) {
		Grid grid = new Grid(name, rows, cols);
		addWalls(grid);
		addRooms(grid);
		addFloor(grid);
		
		/*
		for (int i = 0; i < rows; i++)
			for(int j = 0; j < cols; j++)
				grid.setGridCell(i, j, new GridCell(floor, false));
		*/
		return grid;
	}
	
	/* Adds rooms within the grid */
	private void addRooms(Grid grid) {
		/* Parameters */
		//int subRooms = MathHelper.getRand(1, 4);
		
		/* Determine number of cross-room dividers */
		int numDivHorizontal = MathHelper.getRandWithBias(0, .1, 0, 4);
		int numDivVertical = MathHelper.getRandWithBias(0, .1, 0, 4);
		
		/* Add Horizontal Dividers */
		while (numDivHorizontal > 0) {
			int r = MathHelper.getRand(0, (grid.getRows())-1);
			int space = MathHelper.getRand(3, 6);
			if (isHorizontalSpace(grid, r, space)) {
				addHorizontalLine(grid, r, 0, grid.getCols());
				numDivHorizontal--;
			}
		}
		
		/* Add Vertical Dividers */
		while (numDivVertical > 0) {
			int c = MathHelper.getRand(0, (grid.getCols())-1);
			int space = MathHelper.getRand(3, 6);
			if (isVerticalSpace(grid, c, space)) {
				addVerticalLine(grid, 0, c, grid.getRows());
				numDivVertical--;
			}
		}
	}
	
	/* Returns whether there is 'space' units of empty grid above/below a certain row */
	private boolean isVerticalSpace(Grid grid, int row, int space) {
		for (int i = 0; i < space; i++) {
			if (row+i >= grid.getRows() || !grid.isEmpty(row+i, 1))
				return false;
		}
		return true;
	}
	
	/* Returns whether there is 'space' units of empty grid to the left/right of a certain column */
	private boolean isHorizontalSpace(Grid grid, int col, int space) {
		for (int i = 0; i < space; i++) {
			if (col+i >= grid.getCols() || !grid.isEmpty(1, col+i))
				return false;
		}
		return true;
	}
	
	/* Draws a straight vertical line of walls from one row to another */
	private void addVerticalLine(Grid grid, int r1, int c1, int r2) {
		for (int i = 0; i < Math.abs(r1-r2); i++) {
			GridCell c = new GridCell(wall, GridCellType.Wall);
			grid.setGridCell(r1+i, c1, c);
		}
	}
	
	/* Draws a straight horizontal line of walls from one column to another */
	private void addHorizontalLine(Grid grid, int r1, int c1, int c2) {
		for (int i = 0; i < Math.abs(c1-c2); i++) {
			GridCell c = new GridCell(wall, GridCellType.Wall);
			grid.setGridCell(r1, c1+i, c);
		}
	}
	
	private void addSubRoom(Grid grid) {
		
	}
	
	/* Adds walls around the perimeter of the grid */
	private void addWalls(Grid grid) {
		//left downward wall
		for (int i = 0; i < grid.getRows(); i++)
		{
			GridCell c = new GridCell(wall, GridCellType.Wall);
			grid.setGridCell(i, 0, c);
		}
		
		//right downward wall
		for (int i = 0; i < grid.getRows(); i++)
		{
			GridCell c = new GridCell(wall, GridCellType.Wall);
			grid.setGridCell(i, grid.getCols() - 1, c);
		}
		
		//top wall
		for (int i = 0; i < grid.getCols(); i++)
		{
			GridCell c = new GridCell(wall, GridCellType.Wall);
			grid.setGridCell(0, i, c);
		}
		
		//bottom wall
		for (int i = 0; i < grid.getCols(); i++)
		{
			GridCell c = new GridCell(wall, GridCellType.Wall);
			grid.setGridCell(grid.getRows() - 1, i, c);
		}
	}
	
	/* Adds Floors to all empty grid cells */
	private void addFloor(Grid grid) {
		for (int i = 0; i < grid.getRows(); i++)
			for (int j = 0; j < grid.getCols(); j++)
			{
				if (grid.getGridCell(i, j).isEmpty())
				{
					GridCell c = new GridCell(floor, GridCellType.Floor);
					grid.setGridCell(i, j, c);
				}
			}
	}
}
