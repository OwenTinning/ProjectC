import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class RotatingSprite extends Sprite {
	
	private double currentAngle = 0;
	private int currentImageAngle = 0;
	private double ROTATION_SPEED = 180;	//degrees per second
	protected Image rotatedImage;
	protected Image[] rotatedImages = new Image[360];
			
	public RotatingSprite(double currentX, double currentY) {

		super();
		this.currentX = currentX;
		this.currentY = currentY;		

		try {
			this.defaultImage = ImageIO.read(new File("res/trump.png"));
			this.rotatedImage = this.defaultImage;
			
			IMAGE_WIDTH = defaultImage.getWidth(null);
			IMAGE_HEIGHT = defaultImage.getHeight(null);
			
			System.out.println(System.currentTimeMillis());
			for (int i = 0; i < 360; i++) {
				rotatedImages[i] = ImageRotator.rotate(defaultImage, i);
			}
			System.out.println(System.currentTimeMillis());
		}
		catch (IOException e) {
		}
		
		
		
	}
		
	public void setBarriers(ArrayList<Rectangle> barriers) {
		this.barriers = barriers;
	}
	
	public void setPlayers(ArrayList<Sprite> players) {
		this.sprites = players;
	}
	

	@Override
	public long getHeight() {
		return rotatedImages[(int)currentAngle].getHeight(null);
	}

	@Override
	public long getWidth() {
		return rotatedImages[(int)currentAngle].getWidth(null);
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
		return rotatedImages[(int)currentAngle];
//		return rotatedImage;
	}
		
	@Override
	public void update(KeyboardInput keyboard, long actual_delta_time) {

		double newX = this.currentX;
		double newY = this.currentY;
				
		currentAngle -= (ROTATION_SPEED * (actual_delta_time * 0.001));
	    if (currentAngle >= 360) {
	    	currentAngle -= 360;
	    }
	    if (currentAngle < 0) {
	    	currentAngle += 360;
	    }		
	}			

	public boolean checkCollisionWithPlayers(double x, double y) {
		boolean isColliding = false;
		return isColliding;		
	}

	@Override
	public boolean checkCollisionWithBarrier(double x, double y) {
		boolean isColliding = false;
		return isColliding;		
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

}
