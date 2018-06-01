
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BlackBackground extends Background {

    private Image background;
    private int backgroundWidth = 0;
    private int backgroundHeight = 0;
    private int maxCols = 0;
    private int maxRows = 0;
    
    private int map[][] = new int[][] { 
		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		{1,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0},
		{1,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0},
		{1,0,0,0,0,1,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0},
		{1,0,0,0,0,1,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0},
		{1,0,0,0,0,1,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0},
		{1,0,0,0,0,1,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0},
		{1,0,0,0,0,1,1,1,1,1,0,0,1,0,0,1,0,0,0,0,0,0,0},
		{1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0},
		{1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0},
		{1,1,1,1,1,1,1,1,1,1,0,0,1,0,0,1,0,0,0,0,0,0,0},
		{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0},
		{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0},
		{1,0,0,0,0,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0},
		{1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0},
		{1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0},
		{1,0,0,0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0},
		{1,1,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0},
		{1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0},
		{1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0},
		{1,0,0,0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0},
		{1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0},
		{1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0},
		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0}
	};
	
    public BlackBackground() {
    	try {
    		this.background = ImageIO.read(new File("res/blackbackground.png"));
    		backgroundWidth = background.getWidth(null);
    		backgroundHeight = background.getHeight(null);
    		
    	}
    	catch (IOException e) {
    		//System.out.println(e.toString());
    	}	
    	maxRows = map.length - 1;
    	maxCols = map[0].length - 1;
    }
	
	public Tile getTile(int col, int row) {
		//row is an index of tiles, with 0 being the at the origin
		//col is an index of tiles, with 0 being the at the origin
		
		Image image = null;
		
		if (map[row][col] == 0) {
			image = background;
		}else{
			image = null;
		}
		
		int x = (col * backgroundWidth);
		int y = (row * backgroundHeight);
		
		Tile newTile = new Tile(image, x, y, backgroundWidth, backgroundHeight, false);
		
		return newTile;
	}
	
	public int getCol(int x) {
		//which col is x sitting at?
		int col = 0;
		if (backgroundWidth != 0) {
			col = (x / backgroundWidth);
			if (x < 0) {
				return col - 1;
			}
			else {
				return col;
			}
		}
		else {
			return 0;
		}
	}
	
	public int getRow(int y) {
		//which row is y sitting at?
		int row = 0;
		
		if (backgroundHeight != 0) {
			row = (y / backgroundHeight);
			if (y < 0) {
				return row - 1;
			}
			else {
				return row;
			}
		}
		else {
			return 0;
		}
	}
	
}
