import java.awt.Rectangle;
import java.util.ArrayList;

public class Bounce {
	
	private boolean didBounce = false;
	private double newVelocityX = 0;
	private double newVelocityY = 0;
	private double newX = 0;
	private double newY = 0;
	private double newLocaton = 0;
	private boolean justBounced = false;

	public double getNewVelocityX() {
		return newVelocityX;
	}

	public void setNewVelocityX(double newVelocityX) {
		this.newVelocityX = newVelocityX;
	}

	public double getNewVelocityY() {
		return newVelocityY;
	}

	public void setNewVelocityY(double newVelocityY) {
		this.newVelocityY = newVelocityY;
	}

	public double getNewX() {
		return newX;
	}

	public void setNewX(double newX) {
		this.newX = newX;
	}

	public boolean getDidBounce() {
		return didBounce;
	}

	public double getNewY() {
		return newY;
	}
	
	public Bounce() {
		
	}
	
	public void updatePosition(Sprite sprite, ArrayList<Rectangle> barriers, double velocityX, double velocityY, long actual_delta_time ) {
		
		//calculate new position assuming there are no changes in direction
	    double movement_x = (velocityX * actual_delta_time * 0.001);
	    double movement_y = (velocityY * actual_delta_time * 0.001);
	    
	    this.newVelocityX = velocityX;
	    this.newVelocityY = velocityY;
	    this.newX = sprite.currentX + movement_x;
	    this.newY = sprite.currentY + movement_y;
	    this.didBounce = false;

	    long this_top = Math.round(sprite.currentY);
	    long this_bottom = this_top + sprite.IMAGE_HEIGHT;
	    long this_left = Math.round(sprite.currentX);
	    long this_right = this_left + sprite.IMAGE_WIDTH;
		 
	    for (Rectangle barrier : barriers) {
			//colliding with top edge of barrier?
	        //?moving down (can only collide if sprite is moving down)
	        if (movement_y > 0) {
	            //?is the this to the left || right of the barrier? (can only collide if this is not the case) 
	            if (!( (this_left > barrier.getMaxX()) || (this_right < barrier.getMinX()))) {
	                this.calculateBounce(this_bottom, movement_y, barrier.getMinY());
	                if (this.justBounced) {
	                	this.newY = this.newLocaton - sprite.IMAGE_HEIGHT;
	                	this.newVelocityY = -velocityY;
	                	this.didBounce = true;
	                }
	            }
	        }

	        //colliding with bottom edge of barrier?
	        //?moving up (can only collide if sprite is moving up)
	        if (movement_y < 0) {
	            //is the this to the left || right of the barrier? (can only collide if this is not the case) 
	            if (! ((this_left > barrier.getMaxX()) || (this_right < barrier.getMinX()))) {
	                this.calculateBounce(this_top, movement_y, barrier.getMaxY());
	                if (this.justBounced) {
	                	this.newY = this.newLocaton;
	                	this.newVelocityY = -velocityY;
	                	this.didBounce = true;
	                }
	            }
	        }

	        //colliding with left edge of barrier?
	        //?moving right (can only collide if sprite is moving to right)
	        if (movement_x > 0) {
	            //?is the this above || below the barrier? (can only collide if this is not the case) 
	            if (!( (this_top > barrier.getMaxY()) || (this_bottom < barrier.getMinY()))) {
	                this.calculateBounce(this_right, movement_x, barrier.getMinX());
	                if (this.justBounced) {
	                	this.newX = this.newLocaton - sprite.IMAGE_WIDTH;
	                	this.newVelocityX = -velocityX;
	                	this.didBounce = true;
	                }
	            }
	        }

	        //colliding with right edge of barrier?
	        //?moving left (can only collide if sprite is moving to left)
	        if (movement_x < 0) {
	            //?is the this above || below the barrier? (can only collide if this is not the case) 
	            if (!( (this_top > barrier.getMaxY()) || (this_bottom < barrier.getMinY()))) {
	                this.calculateBounce(this_left, movement_x, barrier.getMaxX());
	                if (this.justBounced) {
	                	this.newX = this.newLocaton;
	                	this.newVelocityX = -velocityX;
	                	this.didBounce = true;
	                }
	            }
	        }
	    }
	    
                    
	}
	
	private void calculateBounce(double location, double distanceToTravel, double boundary) {

		double distanceToBoundary = 0;
			
	    if ( (distanceToTravel > 0) && (location <= boundary) && ((location + distanceToTravel) >= boundary)) {
	        distanceToBoundary = (boundary - location);
	        this.newLocaton = boundary - (distanceToTravel - distanceToBoundary);
//	        this.newVelocity = - distanceToTravel;
	        this.justBounced = true;
	    }
	    else if (distanceToTravel < 0 && (location >= boundary) && ((location + distanceToTravel) <= boundary)) {
	        distanceToBoundary = (location - boundary);
	        this.newLocaton = boundary - (distanceToTravel + distanceToBoundary);
//	        this.newVelocity = - distanceToTravel;
	        this.justBounced = true;
	    }
	    else {
	    	this.newLocaton = location + distanceToTravel;
//	    	this.newVelocity = distanceToTravel;
	    	this.justBounced = false;
	    }
	    
	}	
}
