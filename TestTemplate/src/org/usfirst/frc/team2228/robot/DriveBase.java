package org.usfirst.frc.team2228.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveBase {
	
	private RobotDrive drive;
	private DriverIF driver;
	
	// drive motor controllerss
	private CANTalon right1;
	private CANTalon left1;
	private CANTalon right2;
	private CANTalon left2;
	
	// class variables
	private double currentRobotAngle = 0;
	private double throttleValue = 0;
	private double turnValue = 0;
	private double deltaThrottleValue = 0;
	private double headingError = 0;
	private double previousHeadingError = 0;
	
	// driver request states
	private boolean lowSpeedEnabled = false;
	private boolean driveStraightEnabled = false;
	private boolean headingModuleEnabled = false;
	private boolean brakeEnabled = false;
	private boolean squareWaveActive = false;
	private boolean jogActive = false;
	private boolean moveXDistanceActive = false;
	
	// any toggle states
	private boolean lowSpeedFactorEnabled = false;
	private boolean lowSpeedTriggered = false;
	private boolean highSpeedTriggered = false;
	
	// smooth move parameters
	private double previousEMAValue = 0; // -1 to 1
	
	// auto parameters
	private double autoIndexDistance = 0;
	private int autoIndexTime = 0;
	private double calibratedRightDistance = 0;
	private double calibratedLeftDistance = 0;
	private double rightDrvTrainCruiseVelSetPt = 0;
	private double leftDrvTrainCruiseVelSetPt = 0;
	private double rightDrvTrainAccelSetPt = 0;
	private double leftDrvTrainAccelSetPt = 0;
	private double rightDrvTrainTargetPosSetPt = 0;
	private double leftDrvTrainTargetPosSetPt = 0;
	private double kCalibratedRgtWheelCircum = SRXConfig.WHEEL_DIAMETER * Math.PI;
	private double kCalibratedLftWheelCircum = SRXConfig.WHEEL_DIAMETER * Math.PI;
	private double kRgtDistanceCalibration = 0;  // ?
	private double kLftDistanceCalibration = 0;  // ?
	private double kInchesPerCount = (SRXConfig.WHEEL_DIAMETER * Math.PI)/SRXConfig.COUNTS_PER_REV;
	
	// test/calibration parameters
	private boolean isActiveHighTime = false;
	private double squareStartTime = 0;
	private short lastChoice = 0;
	
	

	// Constructor
	public DriveBase(DriverIF _driver)
	{
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
	
	public DriveBase() {
		// TODO Auto-generated constructor stub
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
		// check for change or no? in calibrated distance
		//if ((autoIndexDistance != indexDistanceIn) && (autoIndexTime != indexTime)) {
			calibratedRightDistance = indexDistanceIn * kRgtDistanceCalibration;
			rightDrvTrainCruiseVelSetPt = (1.5*(calibratedRightDistance / indexTime)*60)
					/ kCalibratedRgtWheelCircum;
			rightDrvTrainAccelSetPt = ((1.5*(indexDistanceIn / indexTime)*60)
					/ kCalibratedRgtWheelCircum)
					/ (indexTime * .33333);
			rightDrvTrainTargetPosSetPt = calibratedRightDistance / kInchesPerCount;
			
			calibratedLeftDistance = indexDistanceIn * kRgtDistanceCalibration;
			leftDrvTrainCruiseVelSetPt = (1.5*(calibratedLeftDistance / indexTime)*60)
					/ kCalibratedLftWheelCircum;
			leftDrvTrainAccelSetPt = ((1.5*(indexDistanceIn / indexTime)*60)
					/ kCalibratedLftWheelCircum)
					/ (indexTime * .33333);
			leftDrvTrainTargetPosSetPt = calibratedLeftDistance / kInchesPerCount;
		//}
		right1.setMotionMagicCruiseVelocity(rightDrvTrainCruiseVelSetPt);
		right1.setMotionMagicAcceleration(rightDrvTrainAccelSetPt);
		left1.setMotionMagicCruiseVelocity(leftDrvTrainCruiseVelSetPt);
		left1.setMotionMagicAcceleration(leftDrvTrainAccelSetPt);
		
	}
	
	/*  heading is the requested degrees of rotation
	 *   timeout value?
	 *  return true when rotation is complete
	 */
	public boolean rotateRobot(double heading) {
		return true;
	}
	
	public void teleopInit(){

		right1.changeControlMode(TalonControlMode.PercentVbus);
		right1.set(0);
		left1.changeControlMode(TalonControlMode.PercentVbus);	
		left1.set(0);
	}
	
	public void teleopPeriodic(){
		double originalThrottle;
		double originalTurn;
		
		switch (driver.GetDriveStyle()) {
		case chessyStyle:
			originalThrottle = throttleValue = driver.GetThrottle();
			originalTurn = turnValue = driver.GetTurn();
			
			turnValue = CheckTurnSensitivityFilter(turnValue);
			throttleValue = CheckThrottleSensitivity(throttleValue);
			throttleValue = CheckSmoothMove(throttleValue);
			AdjustForControllerDeadBand();
			CheckForAdjustSpeedRequest();
			AdjustForDriveLimits();
			drive.arcadeDrive(throttleValue, turnValue, false);
		}
	}
	
	public double CheckTurnSensitivityFilter( double _turn) {
		double fTurn = _turn;
		
		if (driver.GetTurnSensitivityEnabled()) {			
			if (lowSpeedFactorEnabled) {
			  fTurn = ApplySineFunction(fTurn);
			  fTurn = ApplySineFunction(fTurn);
			} else {
				  fTurn = ApplySineFunction(fTurn);
				  fTurn = ApplySineFunction(fTurn);
				  fTurn = ApplySineFunction(fTurn);				
			}
			
		}
		return fTurn;
	}

	
	public double CheckThrottleSensitivity( double _throttle) {
		double fThrottle = _throttle;
		int exp = 3;
		
		if (driver.GetThrottleSensitivityEnabled()) {			
			if (lowSpeedFactorEnabled) {
				fThrottle = TeleConfig.kThrottleSensitivityLowGain*(Math.pow(fThrottle, exp)) +
						(1 - TeleConfig.kThrottleSensitivityLowGain*fThrottle);
			} else {	
				fThrottle = TeleConfig.kThrottleSensitivityHighGain*(Math.pow(fThrottle, exp)) +
						(1 - TeleConfig.kThrottleSensitivityHighGain*fThrottle);		
			}
			
		}
		return fThrottle;
	}
	



	// This calculates the value of the joystick speed after going through the
	// tipping filter
	public double CheckSmoothMove( double _throttle) {
		double fThrottle = _throttle;
		double deltaValue = 0;
		int smoothFactor = 0;
		
		if (driver.GetSmoothMoveEnabled()) {
			if (Math.signum(fThrottle) == Math.signum(previousEMAValue)) {
				deltaValue = fThrottle - previousEMAValue;				
				// prevent tipping if delta is too big
				if (Math.abs(deltaValue) > TeleConfig.kMaxDeltaVelocity) {
					smoothFactor = TeleConfig.kHighSmooth;
				} else {
					smoothFactor = TeleConfig.kLowSmooth;
				}			
			} else { // driver has switched directions, we're tipping!
				fThrottle = 0;
				smoothFactor = TeleConfig.kHighSmooth;
			}
			/*
			* Exponential Moving Average Filter (EMA) is a recursive low pass filter
			* that can change its gain to address filter response
			* Range of smoothFactor is 0 to 1; where smoothFactor = 0 (no smoothing)
			* smoothFactor = .99999 high smoothing
			* Typically: smoothFactor = 1-(2.0 / (timePeriodSF + 1)) where user
			* decides on approx number of cycles(timePeriodSF) for output = input. 
			* Time period on iterative robot is approx 20ms
			*/
			fThrottle = previousEMAValue + (1-smoothFactor) * (deltaValue);
			
			// If we are within the zero speed dead band set the gain for a high
			// response filter(low smoothing) and set the speed to zero
			if (Math.abs(previousEMAValue) < TeleConfig.kZeroSpeedDeadBand) {
			  smoothFactor = TeleConfig.kLowSmooth;
			  fThrottle = 0;
			}
			previousEMAValue = fThrottle;
		}
		return fThrottle;
	}
	
	public double ApplySineFunction(double _turn) {
		double factor = Math.PI/2.0 * TeleConfig.kTurnSensitivityGain;
		return Math.sin(factor * _turn)/Math.sin(factor);
	}
	
	public void AdjustForControllerDeadBand() {
		if (Math.abs(throttleValue) < TeleConfig.JOY_STICK_DEADBAND) {
			throttleValue = 0;
		}
		if (Math.abs(turnValue) < TeleConfig.JOY_STICK_DEADBAND) {
			turnValue = 0;
		}
	
	}
	
	public void AdjustForDriveLimits() {
		if (Math.abs(throttleValue) > 1.0) {
			throttleValue = Math.signum(throttleValue)*1;
		}
		if (Math.abs(turnValue) > 1.0) {
			turnValue = Math.signum(turnValue)*1;
		}
	
	}
	
	public void CheckForAdjustSpeedRequest() {
		/*
		* Check for low max speed operation and limit speed by low speed factor
		*/
		boolean currentLowSpeedTriggered = driver.GetLowSpeedTriggered();
		
		if (currentLowSpeedTriggered && !lowSpeedTriggered  // this is a change
		    && !lowSpeedFactorEnabled) {  // it's now enabled!
			  lowSpeedFactorEnabled = true;

		} else if (currentLowSpeedTriggered && !lowSpeedTriggered  // this is change
		           && lowSpeedFactorEnabled) {  // it's now disabled
			 lowSpeedFactorEnabled = false;

		}
		lowSpeedTriggered = currentLowSpeedTriggered;
		
		// If enabled - limit the robot top speed
		if (lowSpeedFactorEnabled) {
		  throttleValue *= TeleConfig.kLowMaxSpeedFactor;
		  turnValue *= TeleConfig.kLowMaxSpeedFactor;
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
