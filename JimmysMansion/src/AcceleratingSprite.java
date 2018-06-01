import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class AcceleratingSprite extends Sprite {

	protected Image image_straight;
	
	protected double velocityX = 000;        	//PIXELS PER SECOND
	protected double velocityY = 0;          	//PIXELS PER SECOND
	protected double accelerationX = 0;          			//PIXELS PER SECOND PER SECOND 
	protected double accelerationY = 0;          		//PIXELS PER SECOND PER SECOND 
	
	public AcceleratingSprite(double currentX, double currentY) {

		super();
		this.currentX = currentX;
		this.currentY = currentY;		

		try {
			this.defaultImage = ImageIO.read(new File("res/obama.png"));
			this.IMAGE_HEIGHT = this.defaultImage.getHeight(null);
			this.IMAGE_WIDTH = this.defaultImage.getWidth(null);
		}
		catch (IOException e) {
			System.out.println(e.toString());
		}		
	}
	
	@Override
	public void setSprites(ArrayList<Sprite> staticSprites) {
		// TODO Auto-generated method stub
		this.sprites = staticSprites;
		
	}

	@Override
	public void setKeyboard(KeyboardInput keyboard) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean checkCollisionWithSprites(double x, double y) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void setBarriers(ArrayList<Rectangle> barriers) {
		this.barriers = barriers;
	}
	
	public void setPlayers(ArrayList<Sprite> players) {
		this.sprites = players;
	}
	

	@Override
	public long getHeight() {
		return IMAGE_HEIGHT;
	}

	@Override
	public long getWidth() {
		return IMAGE_WIDTH;
	}	
	
	public double getMinX() {
		return currentX;
	}

	@Override
	public double getMaxX() {
		// TODO Auto-generated method stub
		return currentX + IMAGE_WIDTH;
	}

	public void setMinX(double currentX) {
		this.currentX = currentX;
	}

	public void setMinY(double currentY) {
		this.currentY = currentY;
	}

	public double getMinY() {
		return currentY;
	}

	@Override
	public double getMaxY() {
		// TODO Auto-generated method stub
		return currentY + IMAGE_HEIGHT;
	}
	

	@Override
	public Image getImage() {
		return defaultImage;
	}
		
	@Override
	public void update(KeyboardInput keyboard, long actual_delta_time) {

		accelerationX = 0;
		accelerationY = 0;
		//LEFT	
		if (keyboard.keyDown(37)) {
			velocityX = velocityX - 2;	
		}
		//UP
		if (keyboard.keyDown(38)) {
			velocityY = velocityY - 2;
		}
		// RIGHT
		if (keyboard.keyDown(39)) {
			velocityX = velocityX + 2;  
		}
		// DOWN
		if (keyboard.keyDown(40)) {
			velocityY = velocityY + 2;	
		}
						
		//calculate new position assuming there are no changes in direction
	    double movement_x = (this.velocityX * actual_delta_time * 0.001);
	    double movement_y = (this.velocityY * actual_delta_time * 0.001);
	    double new_x = this.currentX + movement_x;
	    double new_y = this.currentY + movement_y;
			
		if (checkCollisionWithBarrier(new_x, new_y) == false) {
			this.currentX = new_x;
			this.currentY = new_y;
		}
				
	}			
	

	@Override
	public boolean checkCollisionWithBarrier(double x, double y) {
		boolean isColliding = false;
		
		for (Rectangle barrier : barriers) {			
			//colliding in x dimension?	
			if ( !( (x + this.IMAGE_WIDTH) < barrier.getMinX() || x > barrier.getMaxX())) {			
				//colliding in y dimension?	
				if ( !( (y + this.IMAGE_HEIGHT) < barrier.getMinY() || y > barrier.getMaxY())) {								
					isColliding = true;
					break;
				}
			}
		}
		
		return isColliding;		
	}
}
