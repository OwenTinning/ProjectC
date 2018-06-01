import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class JumpingSprite extends Sprite {

	protected Image image_straight;
	
	protected double velocityX = 000;        	//PIXELS PER SECOND
	protected double velocityY = 0;          	//PIXELS PER SECOND
	protected double accelerationX = 0;          			//PIXELS PER SECOND PER SECOND 
	protected double accelerationY = 0;          		//PIXELS PER SECOND PER SECOND 
	protected final int VELOCITY = 50;
	protected boolean isJumping = false;
	protected long jumpTime = 0;
	protected double jumpBase = 0;
	protected final double INITIAL_JUMP_VELOCITY = 200; //pixels / second
	protected final double GRAVITY = -600; //pixels / second / second
		
	public JumpingSprite(double currentX, double currentY) {

		super();
		this.currentX = currentX;
		this.currentY = currentY;		

		try {
			this.defaultImage = ImageIO.read(new File("res/kim_jong_un_by_jlr758-d649mib.png"));
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
		
		double newX = this.currentX;
		double newY = this.currentY;
		
		if (isJumping) {
			//continue jump
			newY = jumpBase - calculateJumpHeight(actual_delta_time);
			if (newY > jumpBase) {
				//end jump
				newY = jumpBase;
				isJumping = false;
			}
		}
		else if (keyboard.keyDown(32)) {
			startJump();	
		}
		//LEFT	
		if (keyboard.keyDown(37)) {
			newX -= actual_delta_time * 0.001 * VELOCITY;
		}
		//UP
		if (keyboard.keyDown(38)) {
			newY -= actual_delta_time * 0.001 * velocityY; //moves 50 pixels per second;
			
		}
		// RIGHT
		if (keyboard.keyDown(39)) {
			newX +=  actual_delta_time * 0.001 * VELOCITY;
		}
		// DOWN
		if (keyboard.keyDown(40)) {
			newY +=  actual_delta_time * 0.001 * VELOCITY;
		}
		
		this.currentY = newY;
		this.currentX = newX;
		
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
	
	private double calculateJumpHeight(long actual_delta_time) {
		//h = (v0 * t) + (a * (t*t) ÷ 2)
		if (isJumping) {
			jumpTime += actual_delta_time;
			double jumpTimeInSeconds = ((double)jumpTime) / 1000;			
			return (INITIAL_JUMP_VELOCITY * jumpTimeInSeconds ) + ( 0.5 * GRAVITY * jumpTimeInSeconds * jumpTimeInSeconds) ;
		}
		else {
			//just for safety
			return jumpBase;
		}
	}
	
	public void startJump() {
		if ((isJumping == false)) {
			isJumping = true;
			jumpTime = 0;
			jumpBase = this.currentY;
		}
	}
}
