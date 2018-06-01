import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ShootingSprite extends Sprite {

	protected Image image_straight;
	private AudioPlayer thrustSound;
	
	protected double velocityX = 000;        	//PIXELS PER SECOND
	protected double velocityY = 0;          	//PIXELS PER SECOND
	protected double reloadTime = 0;
	protected Image rotatedImage;
	protected Image[] rotatedImages = new Image[360];

	protected double ACCELERATION = 400;          			//PIXELS PER SECOND PER SECOND 
	private double ROTATION_SPEED = 180;	//degrees per second	
	private double currentAngle = 0;
	private int currentImageAngle = 0;

	public ShootingSprite(double currentX, double currentY) {

		super();
		this.currentX = currentX;
		this.currentY = currentY;		

		try {
			this.defaultImage = ImageIO.read(new File("res/justin-trudeau.png"));
			this.IMAGE_HEIGHT = this.defaultImage.getHeight(null);
			this.IMAGE_WIDTH = this.defaultImage.getWidth(null);
		}
		catch (IOException e) {
			System.out.println(e.toString());
		}
		
		System.out.println(System.currentTimeMillis());
		for (int i = 0; i < 360; i++) {
			rotatedImages[i] = ImageRotator.rotate(defaultImage, i + 270);			
		}
		System.out.println(System.currentTimeMillis());
				
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
	}
		
	@Override
	public void update(KeyboardInput keyboard, long actual_delta_time) {

		//LEFT	
		if (keyboard.keyDown(37)) {
			currentAngle -= (ROTATION_SPEED * (actual_delta_time * 0.001));
		}
		// RIGHT
		if (keyboard.keyDown(39)) {
			currentAngle += (ROTATION_SPEED * (actual_delta_time * 0.001));
		}
		//UP
		if (keyboard.keyDown(38)) {
			if (thrustSound == null || thrustSound.playCompleted) {
				System.out.println("play thrust");
//				thrustSound = AudioPlayer.playAsynchronous("res/thrust.wav");
			}
			double angleInRadians = Math.toRadians(currentAngle);
			velocityX += Math.cos(angleInRadians) * ACCELERATION * actual_delta_time * 0.001;
			velocityY += Math.sin(angleInRadians) * ACCELERATION * actual_delta_time * 0.001;
		}		
		// DOWN
		if (keyboard.keyDown(40)) {
		}
		//SPACE
		if (keyboard.keyDown(32)) {
			shoot();	
		}

	    if (currentAngle >= 360) {
	    	currentAngle -= 360;
	    }
	    if (currentAngle < 0) {
	    	currentAngle += 360;
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
		
		reloadTime -= actual_delta_time;
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

	public void shoot() {
		
		if (reloadTime <= 0) {
			double currentVelocity = Math.sqrt((velocityX * velocityX) + (velocityY * velocityY));
			double bulletVelocity = 500; // + currentVelocity;
			double ratio = (bulletVelocity / currentVelocity);
//			 = ratio * velocityX + velocityX;
//			double bulletVelocityY = ratio * velocityY + velocityY;
			double angleInRadians = Math.toRadians(currentAngle);
			double bulletVelocityX = Math.cos(angleInRadians) * bulletVelocity + velocityX;
			double bulletVelocityY = Math.sin(angleInRadians) * bulletVelocity + velocityY;
			
			double bulletCurrentX = (this.currentX + (this.IMAGE_WIDTH / 2));
			double bulletCurrentY = (this.currentY + (this.IMAGE_HEIGHT / 2));

			BulletSprite bullet = new BulletSprite(bulletCurrentX, bulletCurrentY, bulletVelocityX, bulletVelocityY);
			System.out.println("shoot!");
			sprites.add(bullet);
			AudioPlayer.playAsynchronous("res/missile.wav");
			
			reloadTime = 100;
			
		}
	}

	
}
