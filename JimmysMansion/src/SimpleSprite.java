import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class SimpleSprite extends Sprite {
	
	
	private final double VELOCITY = 400;

	public SimpleSprite(double currentX, double currentY) {
		super();

		this.currentX = currentX;
		this.currentY = currentY;		

		try {
			this.defaultImage = ImageIO.read(new File("res/elonmusk.png"));
			this.IMAGE_HEIGHT = this.defaultImage.getHeight(null);
			this.IMAGE_WIDTH = this.defaultImage.getWidth(null);
		}
		catch (IOException e) {
			System.out.println(e.toString());
		}		
	}
	
	
	@Override
	public void update(KeyboardInput keyboard, long actual_delta_time) {

		double newX = currentX;
		double newY = currentY;
		
		//LEFT	
		if (keyboard.keyDown(37)) {
			newX -= actual_delta_time * 0.001 * VELOCITY;
		}
		//UP
		if (keyboard.keyDown(38)) {
			newY -= actual_delta_time * 0.001 * VELOCITY; //moves 50 pixels per second;
			
		}
		// RIGHT
		if (keyboard.keyDown(39)) {
			newX +=  actual_delta_time * 0.001 * VELOCITY;
		}
		// DOWN
		if (keyboard.keyDown(40)) {
			newY +=  actual_delta_time * 0.001 * VELOCITY;
		}
		
		if (checkCollisionWithBarrier(newX, newY) == false) {
			this.currentX = newX;
			this.currentY = newY;
		}
		
	}

	@Override
	public double getMinX() {
		return currentX;
	}

	@Override
	public double getMaxX() {
		return currentX + IMAGE_WIDTH;
	}

	@Override
	public double getMinY() {
		return currentY;
	}

	@Override
	public double getMaxY() {
		return currentY + IMAGE_HEIGHT;
	}

	@Override
	public long getHeight() {
		// TODO Auto-generated method stub
		return IMAGE_HEIGHT;
	}

	@Override
	public long getWidth() {
		// TODO Auto-generated method stub
		return IMAGE_WIDTH;
	}

	@Override
	public Image getImage() {
		return this.defaultImage;
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
		
		boolean colliding = false;

		for (Rectangle barrier : barriers) {
			boolean toLeft = (x + this.IMAGE_WIDTH) < barrier.getMinX();
			boolean toRight = x > barrier.getMaxX();
			boolean collidingX = !( toLeft || toRight);
			boolean above = (y + this.IMAGE_HEIGHT) < barrier.getMinY();
			boolean below = y > barrier.getMaxY();
			boolean collidingY = !( above || below);
			if (collidingX && collidingY) {
				colliding = true;
				break;
			}			
		}		
		return colliding;		
	}

}
