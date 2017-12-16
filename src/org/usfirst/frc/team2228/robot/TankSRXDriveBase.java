package org.usfirst.frc.team2228.robot;
import com.ctre.CANTalon;

import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/*
 *   Tank drive base refers to how the hardware is configured, with
 *   two master controllers, each one drives one side of the robot.
 */
public class TankSRXDriveBase extends DriveBase {
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
	private short loggerIterations = 0;
	private short loggerThreshold = 20;
	
	// Constructor
	public TankSRXDriveBase(DriverIF _driver)
	{
		super(_driver);
		right1 = new CANTalon(RobotMap.CAN_ID_1); // master
		right2 = new CANTalon(RobotMap.CAN_ID_2);
		left1 = new CANTalon(RobotMap.CAN_ID_3);  // master
		left2 = new CANTalon(RobotMap.CAN_ID_4);
		
		if (SRXConfig.RIGHT_ENCODER_ENABLED)
		{
			right1.setFeedbackDevice(SRXConfig.RIGHT_FEEDBACK);
		}
		
		if (SRXConfig.LEFT_ENCODER_ENABLED)
		{
			left1.setFeedbackDevice(SRXConfig.LEFT_FEEDBACK);
		}
		LiveWindow.addActuator("rtMaster", "RtTalonMaster", right1);
		LiveWindow.addActuator("rtFollower", "RtTalonFollower", right2);
		LiveWindow.addActuator("lftMaster", "LtTalonMaster", left1);
		LiveWindow.addActuator("lftFollower", "LtTalonFollower", left2);
		
		right1.changeControlMode(SRXConfig.RIGHT_1_MODE);
		right1.enableControl();
		right2.changeControlMode(SRXConfig.RIGHT_2_MODE);
		if (SRXConfig.RIGHT_2_MODE == TalonControlMode.Follower) {
			right2.set(right1.getDeviceID());
			right2.enableControl();
		}
			
		left1.changeControlMode(SRXConfig.LEFT_1_MODE);
		left1.enableControl();
		left2.changeControlMode(SRXConfig.LEFT_2_MODE);
		if (SRXConfig.LEFT_2_MODE == TalonControlMode.Follower) {
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
		right1.enableBrakeMode(SRXConfig.BRAKE_MODE_ENABLED);
		right2.enableBrakeMode(SRXConfig.BRAKE_MODE_ENABLED);
		left1.enableBrakeMode(SRXConfig.BRAKE_MODE_ENABLED);
		left2.enableBrakeMode(SRXConfig.BRAKE_MODE_ENABLED);
		
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
	}
	
	public void teleopPeriodic(){
		double originalThrottle;
		double originalTurn;
		
		// assume chessy style drive for now, may need other class
		// implementations for tank, arcade, mecanum, etc.
		
		
		originalThrottle = throttleValue = driver.GetThrottle();
		originalTurn = turnValue = driver.GetTurn();
			
			turnValue = CheckTurnSensitivityFilter(limit(turnValue));
			throttleValue = CheckThrottleSensitivity(limit(throttleValue));
			throttleValue = CheckSmoothMove(limit(throttleValue));
			AdjustForControllerDeadBand();
			CheckForAdjustSpeedRequest();
			
			drive.arcadeDrive(throttleValue, turnValue, false);
			
			loggerIterations++;
			if (loggerIterations >= loggerThreshold)
			{
				/*
				DebugLogger.log(originalThrottle + 
						"," + originalTurn + 
				        "," + throttleValue + 
				        "," + turnValue);*/
			}

	}
	/*
	* Test programs to calibrate the robot drive system
	*/
	/**
	* driveTestMtrSquareWave method
	* Team/Date/Author
	* By pressing and holding a button(left/Right) on the joystick a
	* square wave will be generated for PID tuning
	*/
    public void testInit() {  
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	// some way to choose a test function 
    }
    	

    	
    	public void driveTestMtrSquareWave(int driveTrainSide) {
    		if (driver.GetSquareWaveEnabled()){
    			
    		    if (!squareWaveActive) {
    			  isActiveHighTime = true;
    			  squareWaveActive = true;
    			  squareStartTime = Timer.getFPGATimestamp();
    		    }
    		
    		  if (isActiveHighTime){
    			if (squareStartTime + SRXConfig.K_SQ_WAVE_HIGH_TIME > Timer.getFPGATimestamp()) {
    				// time to switch to low 
    				squareStartTime = Timer.getFPGATimestamp();
    				isActiveHighTime = false;
    			}
    		  } else {
    				if (squareStartTime + SRXConfig.K_SQ_WAVE_LOW_TIME > Timer.getFPGATimestamp()) {
    					// time to switch to high
    					squareStartTime = Timer.getFPGATimestamp();
    					isActiveHighTime = true;
    				}  
    		  }
    		  
    		  if (isActiveHighTime) {
    			if (driveTrainSide == RobotMap.CAN_ID_1) {
    				right1.set(SRXConfig.K_RIGHT_HIGH_SPEED);
    			} else {
    				left1.set(SRXConfig.K_LEFT_HIGH_SPEED);
    			}
    		  } else {
    			if (driveTrainSide == RobotMap.CAN_ID_1) {
    				right1.set(SRXConfig.K_RIGHT_LOW_SPEED);
    			} else {
    				left1.set(SRXConfig.K_LEFT_LOW_SPEED);
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
    



