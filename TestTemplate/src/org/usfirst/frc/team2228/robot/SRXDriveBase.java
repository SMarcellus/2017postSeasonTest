package org.usfirst.frc.team2228.robot;
import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SRXDriveBase extends DriveBase {
	private RobotDrive drive;
	
	// drive motor controllers
	private CANTalon right1;
	private CANTalon left1;
	private CANTalon right2;
	private CANTalon left2;
	
	// test/calibration parameters
	private boolean isActiveHighTime = false;
	private double squareStartTime = 0;
	private short lastChoice = 0;
	
	// Constructor
	public SRXDriveBase(DriverIF _driver)
	{
		super(_driver);
			
		right1 = new CANTalon(SRXConfig.RIGHT_ONE_DRIVE); // master
		right2 = new CANTalon(SRXConfig.RIGHT_TWO_DRIVE);
		left1 = new CANTalon(SRXConfig.LEFT_ONE_DRIVE);  // master
		left2 = new CANTalon(SRXConfig.LEFT_TWO_DRIVE);
		
		if (SRXConfig.rightEncoderEnabled)
		{
			right1.setFeedbackDevice(SRXConfig.rightFeedback);
		}
		
		if (SRXConfig.leftEncoderEnabled)
		{
			left1.setFeedbackDevice(SRXConfig.leftFeedback);
		}
		
		right1.changeControlMode(SRXConfig.right1Mode);
		right2.changeControlMode(SRXConfig.right2Mode);
		if (SRXConfig.right2Mode == TalonControlMode.Follower) {
			right2.set(right1.getDeviceID());
			right2.enableControl();
		}
			
		left1.changeControlMode(SRXConfig.left1Mode);
		left2.changeControlMode(SRXConfig.left2Mode);
		if (SRXConfig.left2Mode == TalonControlMode.Follower) {
			left2.set(left1.getDeviceID());
			left2.enableControl();
		}		
		
		drive = new RobotDrive(right1, left1);
		
		// Set peak and nominal output voltage levels of motor controllers
		right1.configNominalOutputVoltage(+0.0f, -0.0f);
		right1.configPeakOutputVoltage(+12.0f, -12.0f);
		right2.configNominalOutputVoltage(+0.0f, -0.0f);
		right2.configPeakOutputVoltage(+12.0f, -12.0f);
		left1.configNominalOutputVoltage(+0.0f, -0.0f);
		left1.configPeakOutputVoltage(+12.0f, -12.0f);
		left2.configNominalOutputVoltage(+0.0f, -0.0f);
		left2.configPeakOutputVoltage(+12.0f, -12.0f);
		
		// Disable brake mode in all drive motors, motors will coast to a stop
		right1.enableBrakeMode(SRXConfig.brakeModeEnabled);
		right2.enableBrakeMode(SRXConfig.brakeModeEnabled);
		left1.enableBrakeMode(SRXConfig.brakeModeEnabled);
		left2.enableBrakeMode(SRXConfig.brakeModeEnabled);
	}


	public void autonomousInit() {
		// zeroYaw
		System.out.println("We are in AutoInit");
		right1.setPosition(0);
		left1.setPosition(0);
		
	}
	
	/*  indexDistanceIn is the requested distance to move in inches
	 *  indexTime is the timeout value the move must be made within
	 *  
	 */
	public void driveIndexRobot(double indexDistanceIn, int indexTime) {
		setCruiseAndAccelSetPoints(indexDistanceIn, indexTime);
		right1.setMotionMagicCruiseVelocity(rightDrvTrainCruiseVelSetPt);
		right1.setMotionMagicAcceleration(rightDrvTrainAccelSetPt);
		left1.setMotionMagicCruiseVelocity(leftDrvTrainCruiseVelSetPt);
		left1.setMotionMagicAcceleration(leftDrvTrainAccelSetPt);
		
	}
	
	public void teleopInit(){

		right1.changeControlMode(TalonControlMode.PercentVbus);
		right1.set(0);
		left1.changeControlMode(TalonControlMode.PercentVbus);	
		left1.set(0);
	}
	
	public void teleopPeriodic(){
		//double originalThrottle;
		//double originalTurn;
		
		switch (driver.GetDriveStyle()) {
		case chessyStyle: {
		//	originalThrottle = throttleValue = driver.GetThrottle();
		//	originalTurn = turnValue = driver.GetTurn();
			
			turnValue = CheckTurnSensitivityFilter(turnValue);
			throttleValue = CheckThrottleSensitivity(throttleValue);
			throttleValue = CheckSmoothMove(throttleValue);
			AdjustForControllerDeadBand();
			CheckForAdjustSpeedRequest();
			AdjustForDriveLimits();
			
			drive.arcadeDrive(throttleValue, turnValue, false);
			break;
		}
		default:
			break;
		}
	}/*
	* Test programs to calibrate the robot drive system
	*/
	/**
	* driveTestMtrSquareWave method
	* Team/Date/Author
	* By pressing and holding a button(left/Right) on the joystick a
	* square wave will be generated for PID tuning
	*/
    public void testInit() {  
    	SmartDashboard.putNumber("runTest", 0);
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	// some way to choose a test function 	
    	short choice = (short)SmartDashboard.getNumber("runTest", 0);
    	boolean newRequest =  (lastChoice != choice);
    	lastChoice = choice;
    	switch (choice) {
    	  case 1: {
    		  
 
    		  if (newRequest) {
    		   System.out.println("start choice 1");
    		  }
    	  break;
    	  }
    	  
    	  default: {
    		  if (newRequest){
       		   System.out.println("choice defaulted");
       		  }
    	  }
    		   
    	}
    }
    	

    	
    	public void driveTestMtrSquareWave(int driveTrainSide) {
    		if (driver.GetSquareWaveEnabled()){
    			
    		    if (!squareWaveActive) {
    			  isActiveHighTime = true;
    			  squareWaveActive = true;
    			  squareStartTime = Timer.getFPGATimestamp();
    		    }
    		
    		  if (isActiveHighTime){
    			if (squareStartTime + SRXConfig.kSQWaveHighTime > Timer.getFPGATimestamp()) {
    				// time to switch to low 
    				squareStartTime = Timer.getFPGATimestamp();
    				isActiveHighTime = false;
    			}
    		  } else {
    				if (squareStartTime + SRXConfig.kSQWaveLowTime > Timer.getFPGATimestamp()) {
    					// time to switch to high
    					squareStartTime = Timer.getFPGATimestamp();
    					isActiveHighTime = true;
    				}  
    		  }
    		  
    		  if (isActiveHighTime) {
    			if (driveTrainSide == SRXConfig.RIGHT_ONE_DRIVE) {
    				right1.set(SRXConfig.kRightSideHighSpeed);
    			} else {
    				left1.set(SRXConfig.kLeftSideHighSpeed);
    			}
    		  } else {
    			if (driveTrainSide == SRXConfig.RIGHT_ONE_DRIVE) {
    				right1.set(SRXConfig.kRightSideLowSpeed);
    			} else {
    				left1.set(SRXConfig.kLeftSideLowSpeed);
    			}
    		  }
    	  } else {
    		  squareWaveActive = false;
    	  }
    	}
    	
    	public void driveTestJog(int motorID){
    		
    	}
    	
    	public void driveTestStraightIndex(int motorID){
    		
    	}
    	 
    }
    


