import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class BouncingSprite extends Sprite {

	private final double VELOCITY = 200; //50 pixels per second
	private double velocityX = 300; //per second
	private double velocityY = 300; //per second
	private double accelarationY = 400; //per second per second
	private Bounce bounce = new Bounce();
	
	public BouncingSprite(double currentX, double currentY, double velocityX, double velocityY) {
		super();
		
		this.currentX = currentX;
		this.currentY = currentY;
		this.velocityX = velocityX;
		this.velocityY = velocityY;

		try {
			this.defaultImage = ImageIO.read(new File("res/247172_jesus_32x32.png"));
			this.IMAGE_HEIGHT = this.defaultImage.getHeight(null);
			this.IMAGE_WIDTH = this.defaultImage.getWidth(null);
		}
		catch (IOException e) {
			System.out.println(e.toString());
		}		
	}
	
	@Override
	public void update(KeyboardInput keyboard, long actual_delta_time) {
		
		bounce.updatePosition(this, barriers, velocityX, velocityY, actual_delta_time);
		this.currentX = bounce.getNewX();
		this.currentY = bounce.getNewY();
		this.velocityX = bounce.getNewVelocityX();
		this.velocityY = bounce.getNewVelocityY();			

		//this.velocityY = this.velocityY + 500 * 0.001 * actual_delta_time;

	}
	

	@Override
	public double getMinX() {
		// TODO Auto-generated method stub
		return currentX;
	}

	@Override
	public double getMaxX() {
		// TODO Auto-generated method stub
		return currentX + IMAGE_WIDTH;
	}

	@Override
	public double getMinY() {
		// TODO Auto-generated method stub
		return currentY;
	}

	@Override
	public double getMaxY() {
		// TODO Auto-generated method stub
		return currentY + IMAGE_HEIGHT;
	}

	@Override
	public long getHeight() {
		return IMAGE_HEIGHT;
	}

	@Override
	public long getWidth() {
		return IMAGE_WIDTH;
	}

	@Override
	public Image getImage() {
		// TODO Auto-generated method stub
		return defaultImage;
	}

	@Override
	public void setMinX(double currentX) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMinY(double currentY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBarriers(ArrayList<Rectangle> barriers) {
		this.barriers = barriers;
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

	@Override
	public boolean checkCollisionWithBarrier(double x, double y) {

		boolean isColliding = false;

		for (Rectangle barrier : barriers) {
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

