package lechats;

import com.sun.corba.se.spi.orbutil.fsm.Action;

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
	 double angle = 0.0;
	 ControlP5 gui; 
	 Button startLeChats; 
	 Button increaseReactConc; 
	 Button decreaseReactConc; 
	 Button increaseProdConc; 
	 Button decreaseProdConc; 
	 final int MAX_ANGLE = 20; // due to the geometry of the seesaw, 45 turned out to be way too steep
	 double q = 1;  
	 double k = 1; 
	 float reactantSize = (float) 0.14;
	 float productSize = (float) 0.14;
	 float change = (float) 0.0; 
	 float initReactantSize = (float) 0;
	 float initProductSize = (float) 0;
		boolean lechats = false;
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
		rect((float)(width*0.2), (float)(height*0.50), (float)(width*0.05), (float)(height*0.05));
		rect((float)(width*0.8), (float)(height*0.50), (float) (width*0.05), (float)(height*0.05));
		
		startLeChats = gui.addButton("Le Chats").setPosition((float)(width*0.85), (float)(height*0.875)).setWidth((int)(0.1*width)).setHeight((int)(0.1*height));
		startLeChats.addCallback(new CallbackListener() {
			@Override
			public void controlEvent(CallbackEvent theEvent) {
//				switch(theEvent.getAction()) {
//				case ControlP5.ACTION_:
					System.out.println("LE CHATS ACTIVATED");
					lechats = true;
					double currAngle = angle; 
					double initialQ = q;
					System.out.println("Q - K = "+(initialQ-k));
					if(Math.abs(q-k)>0.005) {
						if (q < k) {
							change = (float) (Math.pow(initReactantSize, 2) - Math.pow(initProductSize, 2))/(2*(initProductSize + initReactantSize));
							System.out.println("CHANGE: " + change);
							rotateTo(0);
							
						}
						else if (q > k)
						{
							//q-=1; 
							rotateTo(0); 
						}
						else
						{
							System.out.println("Q = K"); 
							System.out.println("ANGLE: " + angle);
							//rotateClockwise(0); 
							lechats = false;
							angle = 0; 
						}
					}
					//lechats=false;
				//}
			}
		}); 
		
		increaseReactConc = gui.addButton("Increase Reactants").setPosition((float)(0.48*width), (float)(0.88*height)).setWidth((int)(0.1*width)).setHeight((int)(0.1*height));
		increaseReactConc.addCallback(new CallbackListener(){
			@Override
			public void controlEvent(CallbackEvent arg0) {
				//float n = (float) 0.001;
				reactantSize += 0.001;
				initReactantSize = reactantSize;
				initProductSize = productSize;
			}
		}); 
		
		decreaseReactConc = gui.addButton("Decrease Reactants").setPosition((float)(0.60*width), (float)(0.88*height)).setWidth((int)(0.1*width)).setHeight((int)(0.1*height)); 
		decreaseReactConc.addCallback(new CallbackListener(){

			@Override
			public void controlEvent(CallbackEvent arg0) {
				reactantSize -= 0.001; 
			}
			
		});
		
	}

	public void draw() {
		q = (Math.pow(productSize, 2))/(Math.pow(reactantSize, 2));
		// Controls the rotation of the seesaw based on q v.s. k
		if(!lechats)
		{
			if (q < k && Math.abs(q-k)>0.05) rotateCounterClockwise(0.1); 
			else if (q > k && Math.abs(q-k)>0.05) rotateClockwise(0.1);
			else if(Math.abs(q-k)<0.05) { rotateTo(0); } 
		}
	}
	
	public void rotateTo(double goalAngle)
	{
		if (Math.abs(angle-goalAngle)<0.05) 
		{
			rotateClockwise(0);
		}
		else if (angle > goalAngle) 
		{
			rotateCounterClockwise(0.1); 
		}
		else if (angle < goalAngle)
		{
			rotateClockwise(0.1);
		}
	}
	public void rotateClockwise(double speed)
	{	System.out.println("ROTATE CLOCKWISE");
		System.out.println("Q: " + q);
		System.out.println("ANGLE: " + angle);
		System.out.println("REACTANT SIZE: " + reactantSize);
		System.out.println("PRODUCT SIZE: " + productSize);
		System.out.println(); 
		if (speed != 0)
		{
			if (angle < MAX_ANGLE) {
				angle+=speed;
			    productSize += change/200; 
			    reactantSize-= change/200;
			}
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
				rect((float)(width*-0.2), (float)(height*(-reactantSize/2-0.025)), (float)(width*reactantSize), (float)(height*reactantSize));
				rect((float)(width*0.2), (float)(height*(-productSize/2-0.025)), (float) (width*productSize), (float)(height*productSize));
				//since the new origin is the center of the old grid, the new position is (0,0) for the rect
			popMatrix();
			//reverted back to old system
			fill(175);
			triangle((float)(width*0.45), (float)(height*0.61), (float)(width*0.5),(float)(height*0.525), (float)(width*0.55), (float)(height*0.61));
		}
		else{
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
				rect((float)(width*-0.2), (float)(height*(-reactantSize/2-0.025)), (float)(width*reactantSize), (float)(height*reactantSize));
				rect((float)(width*0.2), (float)(height*(-productSize/2-0.025)), (float) (width*productSize), (float)(height*productSize));
				//since the new origin is the center of the old grid, the new position is (0,0) for the rect
			popMatrix();
			//reverted back to old system
			fill(175);
			triangle((float)(width*0.45), (float)(height*0.61), (float)(width*0.5),(float)(height*0.525), (float)(width*0.55), (float)(height*0.61));
		}
	}
	
	public void rotateCounterClockwise(double speed) 
	{	System.out.println("ROTATE COUNTERCLOCKWISE");	
		System.out.println("Q: " + q);
		System.out.println("ANGLE: " + angle);
		System.out.println("REACTANT SIZE: " + reactantSize);
		System.out.println("PRODUCT SIZE: " + productSize);
		System.out.println(); 
		if (speed != 0)
		{
			if (Math.abs(angle + MAX_ANGLE) > 0.05) {
				angle-=speed;
			    productSize -= change; 
			    reactantSize+= change;
			}
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
				rect((float)(width*-0.2), (float)(height*(-reactantSize/2-0.025)), (float)(width*reactantSize), (float)(height*reactantSize));
				rect((float)(width*0.2), (float)(height*(-productSize/2-0.025)), (float) (width*productSize), (float)(height*productSize));
				//since the new origin is the center of the old grid, the new position is (0,0) for the rect
			popMatrix();
			//reverted back to old system
			fill(175);
			triangle((float)(width*0.45), (float)(height*0.61), (float)(width*0.5),(float)(height*0.525), (float)(width*0.55), (float)(height*0.61));
		}		
	}

}
