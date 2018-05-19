package lechats;

import controlP5.Button;
import controlP5.CallbackEvent;
import controlP5.CallbackListener;
import controlP5.ControlP5;
import processing.core.PApplet;

/*
 * Progress Check: 
 * - Milestone #1 is completed; the current state is a rectangle that spins continuously on a fixed fulcrum. I did
 *   encounter a problem with the entire grid rotating, but this was solved by using the functions pushMatrix() and 
 *   popMatrix(), which create and stop a temporary coordinate system respectively. The next step is to make the seesaw
 *   stop and reverse direction if a number (Q) is less than the condition. It might be useful to make some kind of function
 *   that handles drawing the seesaw's position given an angle, then feed that different values depending on the condition. 
 *   Also the lines background and stroke and fill shouldn't be deleted as these are the "redraw" functions basically.
 *   Also something to keep in mind as we progress is that we need to make sure to redraw the reaction and Q and also fixed 
 *   displays in the draw() function because they are cleared each time the seesaw changes position. 
 */
public class LeChats extends PApplet {
	 double angle = 1.0;
	 ControlP5 gui; 
	 Button startLeChats; 
	 final int MAX_ANGLE = 20; // due to the geometry of the seesaw, 45 turned out to be way too steep
	 double q = 80;  
	 double k = 100; 
	 public static void main(String[] args) {
	        // TODO Auto-generated method stub
		 PApplet.main("lechats.LeChats");//required for program to run; calls super of PApplet	
	    }
	public void settings() {
		size((int) (displayWidth*0.6), (int)(displayHeight*0.9));  
	}
	 
	public void setup() {
		 gui = new ControlP5(this);
		rectMode(CENTER); //this unfortunately only exists for rects :( makes the position of the rect relative to its center
		rect((float) (width*0.5), (float) (height*0.50), (float) (width*0.6), (float) (height*0.05));
		//^x, y, width, height
		triangle((float)(width*0.45), (float)(height*0.61), (float)(width*0.5),(float)(height*0.525), (float)(width*0.55), (float)(height*0.61));
		//x1, y1, x2, y2, x3, y3
		
		startLeChats = gui.addButton("Le Chats").setPosition((float)(width*0.90), (float)(height*0.875)).setWidth((int)(0.1*width)).setHeight((int)(0.1*height));
		startLeChats.addCallback(new CallbackListener() {
			@Override
			public void controlEvent(CallbackEvent theEvent) {
				switch (theEvent.getAction()) {
					case(ControlP5.ACTION_CLICK): {
						double currAngle = angle; 
						double initialQ = q;
						System.out.println("Q - K = "+(initialQ-k));
						while (Math.abs(q-k)>0.5) {
							if (q < k) {
								q+=1;  
								rotateTo(0);
							}
							else if (q > k)
							{
								q-=1; 
								rotateTo(0); 
							}
							else
							{
								System.out.println("Q = K"); 
								System.out.println("ANGLE: " + angle);
								//rotateClockwise(0); 
								angle = 0; 
							}
						}
					}
				}	
			}
			
		}); 
		
	}

	public void draw() {
		// Controls the rotation of the seesaw based on q v.s. k
		if (q < k && Math.abs(q-k)>0.5) rotateCounterClockwise(0.1); 
		else if (q > k && Math.abs(q-k)>0.5) rotateClockwise(0.1);
		else if(Math.abs(q-k)<0.5) { rotateTo(0); }  
	}
	
	public void rotateTo(double goalAngle)
	{
		if (angle < goalAngle - 0.05)
		{
			rotateClockwise(0.1);
		}
		else if (angle > goalAngle + 0.05) 
		{
			rotateCounterClockwise(0.1); 
		}
		else if (Math.abs(angle-goalAngle)<0.05)
		{
			//rotateClockwise(0); 
			angle = 0; 
		}
	}
	public void rotateClockwise(double speed)
	{
		System.out.println("Q: " + q);
		System.out.println("ANGLE: " + angle);
		System.out.println(); 
		if (speed != 0)
		{
			if (angle < MAX_ANGLE) angle+=speed;
			background(255); 
			//clears the background
			stroke(0); 
			//clears the strokes, i think
			fill(175);
			//fills the rectangle with this color (dark gray-ish)
			pushMatrix(); //starts a separate set of transformations; basically creates a new temporary coordinate system
				translate(width/2, height/2); 
				//translates the origin of the grid itself to the center of the rect
				rotate((float) radians((float)angle)); 
				//rotates the entire grid by the angle
				rectMode(CENTER); 
				rect(0,0, (float) (width*0.6), (float) (height*0.05)); 
				//since the new origin is the center of the old grid, the new position is (0,0) for the rect
			popMatrix();
			//reverted back to old system
			fill(175);
			triangle((float)(width*0.45), (float)(height*0.61), (float)(width*0.5),(float)(height*0.525), (float)(width*0.55), (float)(height*0.61));
		}	
	}
	
	public void rotateCounterClockwise(double speed) 
	{		
		System.out.println("Q: " + q);
		System.out.println("ANGLE: " + angle);
		System.out.println(); 
		if (speed != 0)
		{
			if (angle > -MAX_ANGLE) angle-=speed;
			background(255); 
			//clears the background
			stroke(0); 
			//clears the strokes, i think
			fill(175);
			//fills the rectangle with this color (dark gray-ish)
			pushMatrix(); //starts a separate set of transformations; basically creates a new temporary coordinate system
				translate(width/2, height/2); 
				//translates the origin of the grid itself to the center of the rect
				rotate((float) radians((float)angle)); 
				//rotates the entire grid by the angle
				rectMode(CENTER); 
				rect(0,0, (float) (width*0.6), (float) (height*0.05)); 
				//since the new origin is the center of the old grid, the new position is (0,0) for the rect
			popMatrix();
			//reverted back to old system
			fill(175);
			triangle((float)(width*0.45), (float)(height*0.61), (float)(width*0.5),(float)(height*0.525), (float)(width*0.55), (float)(height*0.61));
		}		
	}

}
