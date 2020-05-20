import java.awt.Color;

import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

public class aBall extends Thread {
	
private GOval myBall;
	
	private static final int WIDTH = 1200;
	private static final int HEIGHT = 600;
	private static final int OFFSET = 200;
	private static final double XMAX = 100;
	private static final double YMAX = 100;
	
	// parameters for the arguments
	private  double Xi,Yi,Vo,bSize,theta,bLoss;
	private Color bColor;
		
//conversion from simulation coordinates to pixels 
	private static final double SCALE = HEIGHT/XMAX;
	
	/**
	 * The constructor specifies the parameters for simulation They are
	 * 
	 * @param Xi double the initial X position of the center of the ball in meters
	 * @param Yi double The initial Y position of the center of the ball in meters
	 * @param Vo double The initial velocity of the ball at launch
	 * @param theta double Launch angle (with the horizontal plane)	
	 * @param bColor Color The initial color of the ball
	 * @param bLoss double Fraction [0,1] the energy lost on each bounce
	 */


	
	public aBall(double Xi,double Yi, double Vo, double theta,double bSize, Color bColor, double bLoss) {
	
		//get simulation parameters
		this.Xi =Xi;
		this.Yi = Yi;
		this.Vo = Vo;
		this.bSize= bSize; 
		this.theta= theta;
		this.bColor = bColor;
		this.bLoss =bLoss;

	
		//the instance of aBall
		myBall = new GOval ((Xi-bSize)*SCALE,HEIGHT-(Yi+bSize)*SCALE,2*bSize*SCALE,2*bSize*SCALE); //the ball is scaled into screen coordinates
		myBall.setFilled(true);
		myBall.setFillColor( bColor);	
		
		
		}	
		
//to access GOval in bSim
	public GOval getBall() {	
		return myBall;
		}
		
	// Initializing parameters for Simulation
	double time = 0.0;
	
	
	//defining simulation variables
	private static final double g= 9.8;
	private static final double m = 1.0; //mass of ball
	private static final double Pi= 3.141592654;
	private static final double TICK = 0.1; // delta t 
	private static final double ETHR = 0.01;
	private static final boolean TEST = true;
	private static final double k = 0.0001;

/** 
 * The run method implements the simulation loop from Assignment 1
 * Once the start method is called on the aBall instance' the 
 * code in the run method is executed concurrently with the main 
 * program
 * @param void
 * @return void
 */

			
public void run()  {
	
	//Initializing variables
	double Vt = (m*g)/(4*Pi*bSize*bSize*k);	
	double Vox = Vo*Math.cos(theta*Pi/180);
	double Voy=Vo*Math.sin(theta*Pi/180);
	
	double X = (WIDTH/2)*SCALE;
	double Y = HEIGHT-(Yi)*SCALE; //to get the ball to start at the HEIGHT = RADIUS
	

	double Xlast = 0;
	double Ylast = 0;
	double Vx = Vox;
	double Vy = Voy;
	
	
	//kinetic energy parameters for Simulation
	
	double KEx = 0.5*Vx*Vx*(1-bLoss); //kinetic energy of ball in the X direction AFTER collision
	double KEy = 0.5*Vy*Vy*(1-bLoss); //kinetic energy of ball in the Y direction AFTER collision
	double Ktotal = KEx + KEy; 
	double Klast = Ktotal;


	while(true) {
			X =Vox*Vt/g*(1-Math.exp(-g*time/Vt))+Xi; // must add initialX so that we can reinitialize Xlast as our new starting point
			Y= ((bSize))+Vt/g*(Voy+Vt)*(1-Math.exp(-g*time/Vt))-Vt*time ; 
			
			Vx = (X-Xlast)/TICK;
			Vy = (Y-Ylast)/TICK;
	
			if (TEST)
			System.out.printf("t: %.2f X: %.2f Y:%.2f Vx: %.2f Vy: %.2f\n", time, Xi+X, Y,KEx, KEy, Vx, Vy);


			// loop when there is a collision
				if (Y <= bSize && Vy <0 ) { 
					KEx = 0.5*Vx*Vx*(1-bLoss); //Kinetic Energy in X direction after collision
					KEy = 0.5*Vy*Vy*(1-bLoss); // Kinetic Energy in Y direction after collision
					Ktotal = KEx+KEy;
					
		
				if((Ktotal < ETHR) && Ktotal < Klast) break; 
					
			// loop to make balls shift toward the left		
				if (theta <= 90) {
					Vox = Math.sqrt(2*KEx);
				}
				else {
					Vox = -Math.sqrt(2*KEx);
							
				}
				
				Voy = Math.sqrt(2*KEy);
				
				//reinitializing everything
					time = 0.0; // must re-initialize time 
					Xi = X; //reinitializing the start point
					Y= Yi; //limit the ball movement between bSize<=Y<= 100	
					
					Xlast = X;
					Ylast = 0;
					Ktotal = Klast; 
				
				if ((KEy <= ETHR)) break;	
					}
				

		
	//calculating the position of the ball
	int ScrX = (int) ((X-bSize)*SCALE);  //converting X position in pixel coordinates
	int ScrY = (int)(HEIGHT - (Y+bSize)*SCALE); //converting Y position in pixel coordinates
	myBall.setLocation(ScrX,ScrY);
	
	
	//pause for 50ms 
		try {
				Thread.sleep(50);
			}		catch (InterruptedException e) {
				e.printStackTrace(); 
				}
	
			time += TICK;
			Xlast = X;
			Ylast = Y;
			
		

			}		
	}
}

