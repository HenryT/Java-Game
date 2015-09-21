package peacenerd.level;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import peacenerd.character.GameCharacter;

public class Grid {
	private GridCell[][] cells;
	private int rows;
	private int cols;
	private String name;
	
	//If it is a tileSheet, this room has a pre-designed image. Otherwise, it is randomly generated.
	private boolean isTileSheet;
	private TiledMap image;
	
	/* Constructor for randomly generated rooms */
	public Grid(String name, int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		cells = new GridCell[rows][cols];
		this.image = null;
		this.isTileSheet = false;
		this.name = name;
		
		for(int i = 0; i < rows; i++)
		{
			for(int j = 0; j < cols; j++)
			{
				cells[i][j] = new GridCell();
			}
		}
	}
	
	/* Constructor for pre-set image */
	public Grid(String name, String tileSheetFile) throws SlickException {
		this.image = new TiledMap(tileSheetFile);
		this.isTileSheet = true;
		this.name = name;
		this.rows = this.image.getHeight();
		this.cols = this.image.getWidth();
		cells = new GridCell[rows][cols];
		
		System.out.println(this.rows + ", " + this.cols);
		for(int i = 0; i < rows; i++)
		{
			for(int j = 0; j < cols; j++)
			{
				cells[i][j] = new GridCell();
			}
		}
	}
	
	/* Grid Cells */
	public GridCell getGridCell(int row, int col) {
		return this.cells[row][col];
	}
	
	public void setGridCell(int row, int col, GridCell cell) {
		cell.setCoords(row, col);
		this.cells[row][col] = cell;
	}
	
	/* Occupied */
	public boolean isOccupied(int row, int col) {
		return this.cells[row][col].isOccupied();
	}
	
	public void setOccupied(boolean occupied, int row, int col) {
		this.cells[row][col].setOccupied(occupied);
	}
	
	/* Blocking */
	public boolean isBlocking(int row, int col) {
		return this.cells[row][col].isBlocking();
	}
	
	public void setBlocking(boolean blocking, int row, int col) {
		this.cells[row][col].setBlocking(blocking);
	}
	
	/* Empty */
	public boolean isEmpty(int row, int col) {
		return this.cells[row][col].isEmpty();
	}
	
	/* GameCharacters */
	public GameCharacter getCharAt(int row, int col) {
		return this.cells[row][col].getChar();
	}
	
	public void placeChar(GameCharacter character, int row, int col) {
		this.cells[row][col].placeChar(character);
	}
	
	public void removeChar(int row, int col) {
		this.cells[row][col].placeChar(GameCharacter.nullChar);
	}
	
	/* Rows and Columns */
	public int getRows() {
		return rows;
	}
	
	public int getCols() {
		return cols;
	}
	
	/* Drawing */
	public void draw() {
		if (this.isTileSheet) {
			this.image.render(0,0);
		}
		else {
			for (int i = 0; i < rows; i++)
				for(int j = 0; j < cols; j++)
					cells[i][j].draw();
		}
	}
}
