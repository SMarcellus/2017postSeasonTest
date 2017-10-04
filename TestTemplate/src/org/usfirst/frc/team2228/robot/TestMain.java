package org.usfirst.frc.team2228.robot;

public class TestMain {
	public static SimDriver driver;
	public static DriveBase driveBase;
	
	public static void TestTurnSensitivity(DriverIF.ControllerSensitivity turnS){
		driver.turnSensitivity = turnS;
		System.out.println("testing " + turnS);
		System.out.println("********************");
		double [] input = {
				-1.0, -0.95, -0.9, -0.8, -0.7, -0.6, -0.5, -0.4, -0.3, -0.2, -0.1, 0.0,
				0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0, 1.0, 1.0 
		};
		double output = 0.0;
		int size = input.length;
		for (int i = 0;  i < size; i++){
			output = driveBase.CheckTurnSensitivityFilter(input[i]);
			System.out.println(input[i] + "-->" + output);
		}
		System.out.println("********************");
	}
	
	public static void TestSmoothMove(){
		
		System.out.println("******** CHECK SMOOTH MOVE ************");
		double [] input = {0.0, -0.2 ,-0.4, -0.6, -0.8,
				-1.0, -1.0, -1.0, -1.0, -1.0, -1.0, 
				-1.0, -1.0, -1.0, -1.0, -1.0, -1.0,
				-1.0, -1.0, -1.0, -1.0, -1.0, -1.0,
				-1.0, -1.0, -1.0, -1.0, -1.0, -1.0,
				-1.0, -1.0, -1.0, -1.0, -1.0, -1.0,
				-1.0, -1.0, -1.0, -1.0, -1.0, -1.0,
				 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
				 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
				 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
				 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
				 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
				 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
				 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 
				 0, 0, 0, 0 
		};
		double output = 0.0;
		driveBase.previousEMAValue = 0.0;
		int size = input.length;
		for (int i = 0;  i < size; i++){
			output = driveBase.CheckSmoothMove(input[i]);
			System.out.println(input[i] + "-->" + output);
			DebugLogger.log(i + ": " + input[i] + " smooth " + output);
		}
		System.out.println("********************");
	}
	
	public static void TestTippingFilterMove(){
		
		System.out.println("******** CHECK TIPPING FILTER  ************");
		double [] input = {0.0, -0.2 ,-0.4, -0.6, -0.8,
				-1.0, -1.0, -1.0, -1.0, -1.0, -1.0, 
				-1.0, -1.0, -1.0, -1.0, -1.0, -1.0,
				-1.0, -1.0, -1.0, -1.0, -1.0, -1.0,
				-1.0, -1.0, -1.0, -1.0, -1.0, -1.0,
				-1.0, -1.0, -1.0, -1.0, -1.0, -1.0,
				-1.0, -1.0, -1.0, -1.0, -1.0, -1.0,
				 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
				 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
				 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
				 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
				 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
				 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
				 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 
				 0, 0, 0, 0 
		};
		double output = 0.0;
		driveBase.previousEMAValue = 0.0;
		int size = input.length;
		for (int i = 0;  i < size; i++){
			output = driveBase.CheckTippingFilter(input[i]);
			System.out.println(input[i] + "-->" + output);
			DebugLogger.log(i + ": " + input[i] + " tipping " + output);
		}
		System.out.println("********************");
	}
	
	//  CHESTER THIS TEST IS FOR YOU!!!!!
	public static void TestFloatVsDouble(){
		double dMultiplier = 7/6;                  // BAD divides then casts to float
		System.out.println(dMultiplier + " vs. ");
		float ftop = 7;
		float fbottom = 6;
		dMultiplier = ftop/ftop;                  // BAD
		System.out.println(dMultiplier + " vs. ");
		int itop = 7;
		int ibottom = 6;
		dMultiplier = itop/ibottom;                  // BAD
		System.out.println(dMultiplier + " vs. ");
		double dtop = 7;
		double dbottom = 6;
		dMultiplier = dtop/dbottom;                 // GOOD knows its dividing doubles
		System.out.println(dMultiplier + " vs. ");
		dMultiplier = (double)7/(double)6;          // GOOD knows its dividing doubles
		System.out.println(dMultiplier + " vs. ");
		dMultiplier = 7.0/6.0;                      // GOOD knows its dividing doubles
		System.out.println(dMultiplier);
	}

	public static void main(String[] args){
		DebugLogger.init("C:/temp/test/Debug_");
	    driver = new SimDriver();
        driveBase = new DriveBase((DriverIF)driver);
        System.out.println(driveBase.buildDate);
        
        TestSmoothMove();
        TestTippingFilterMove();
        
        /*
         *  
        TestFloatVsDouble();
        TestTurnSensitivity(DriverIF.ControllerSensitivity.High);
        TestTurnSensitivity(DriverIF.ControllerSensitivity.Normal);
        TestTurnSensitivity(DriverIF.ControllerSensitivity.Sine);
        TestTurnSensitivity(DriverIF.ControllerSensitivity.Low);
        */
	}
}
