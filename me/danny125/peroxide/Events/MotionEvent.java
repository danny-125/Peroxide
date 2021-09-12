package me.danny125.peroxide.Events;

public class MotionEvent extends Event<MotionEvent> {
		  public double x;
		  
		  public double y;
		  
		  public double z;
		  
		  public float yaw;
		  
		  public float pitch;
		  
		  public boolean onGround;
		  
		  public MotionEvent(double x, double y, double z, float yaw, float pitch, boolean onGround) {
		    this.x = x;
		    this.y = y;
		    this.z = z;
		    this.yaw = yaw;
		    this.pitch = pitch;
		    this.onGround = onGround;
		  }
		  
		  public double getX() {
		    return this.x;
		  }
		  
		  public void setX(double x) {
		    this.x = x;
		  }
		  
		  public double getY() {
		    return this.y;
		  }
		  
		  public void setY(double y) {
		    this.y = y;
		  }
		  
		  public double getZ() {
		    return this.z;
		  }
		  
		  public void setZ(double z) {
		    this.z = z;
		  }
		  
		  public float getYaw() {
		    return this.yaw;
		  }
		  
		  public void setYaw(float yaw) {
		    this.yaw = yaw;
		  }
		  
		  public float getPitch() {
		    return this.pitch;
		  }
		  
		  public void setPitch(float pitch) {
		    this.pitch = pitch;
		  }
		  
		  public boolean isOnGround() {
		    return this.onGround;
		  }
		  
		  public void setOnGround(boolean onGround) {
		    this.onGround = onGround;
		  }
}
