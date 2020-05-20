import java.awt.Color;


import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import acm.util.*;
import acm.graphics.*;

public class bSim  extends GraphicsProgram{
	

	//parameters
	private static final int WIDTH = 1200;
	private static final int HEIGHT = 600;
	private static final int OFFSET = 200;
	
	private static double SCALE = HEIGHT/100;
	
	private static final int NUMBALLS = 100;
	
	private static final double MINSIZE = 1.0; //the minimum ball radius in meters
	private static final double MAXSIZE = 10.0; //the maximum ball radius in meters
	private static final double EMIN = 0.1; //minimum loss coefficient
	private static final double EMAX = 0.6; //maximum loss coefficient
	private static final double VoMIN = 40.0; //minimum velocity in meters/sec
	private static final double VoMAX = 50.0; //maximum velocity in meters/sec
	private static final double ThetaMIN = 80.0; //minimum launch angle (degrees)
	private static final double ThetaMAX = 100.0; //
	
	

	
public void run() {
	
// creating a random generator class
 RandomGenerator rgen = RandomGenerator.getInstance();
 rgen.setSeed((long)0.12345);
 
 // resizing the display window
	this.resize(WIDTH, HEIGHT+OFFSET);
	
	//adding the ground
	GRect myGround = new GRect (0,600,1200,3);
	myGround.setFilled(true);
	myGround.setColor(Color.BLACK);
	add(myGround);
	
	
	// loop to create 100 RANDOM balls
	for(int i = 0; i < NUMBALLS; i++) {	
		
			//scaling parameters
			double Xi = (WIDTH/2)/SCALE;
	
			double iSize = rgen.nextDouble(MINSIZE,MAXSIZE );
			Color iColor = rgen.nextColor();
		double iLoss = rgen.nextDouble(EMIN,EMAX);
		double iVel = rgen.nextDouble(VoMIN,VoMAX);
		double iTheta = rgen.nextDouble(ThetaMIN,ThetaMAX);
	
	
	
		aBall iBall= new aBall(Xi,iSize,iVel, iTheta, iSize, iColor, iLoss);
		add(iBall.getBall());
		iBall.start();
	
		} 
	}
}
		

		
	
		
		
		
		
		
		
		
		
		
		
	
		
		
				
	
			
	
	


