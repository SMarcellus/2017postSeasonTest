package org.usfirst.frc.team2228.robot;


import java.io.File;
import java.net.URISyntaxException;
import java.util.Date;

import org.usfirst.frc.team2228.robot.DriverIF.ControllerSensitivity;


public class DriveBase {
	protected DriverIF driver;
	
	// class variables
	protected double currentRobotAngle = 0;
	protected double throttleValue = 0;
	protected double turnValue = 0;
	protected double deltaThrottleValue = 0;
	protected double headingError = 0;
	protected double previousHeadingError = 0;
	
	// driver request states
	protected boolean lowSpeedEnabled = false;
	protected boolean driveStraightEnabled = false;
	protected boolean headingModuleEnabled = false;
	protected boolean brakeEnabled = false;
	protected boolean squareWaveActive = false;
	protected boolean jogActive = false;
	protected boolean moveXDistanceActive = false;
	
	// any toggle states
	protected boolean lowSpeedFactorEnabled = false;
	protected boolean lowSpeedTriggered = false;
	protected boolean highSpeedTriggered = false;
	
	// smooth move parameters
	public double previousEMAValue = 0.0; // -1 to 1
	public int timePeriodSF =  TeleConfig.HIGH_SMOOTH;
	
	// auto parameters
	protected double autoIndexDistance = 0;
	protected int autoIndexTime = 0;
	protected double calibratedRightDistance = 0;
	protected double calibratedLeftDistance = 0;
	protected double rightDrvTrainCruiseVelSetPt = 0;
	protected double leftDrvTrainCruiseVelSetPt = 0;
	protected double rightDrvTrainAccelSetPt = 0;
	protected double leftDrvTrainAccelSetPt = 0;
	protected double rightDrvTrainTargetPosSetPt = 0;
	protected double leftDrvTrainTargetPosSetPt = 0;
	protected double kCalibratedRgtWheelCircum = SRXConfig.WHEEL_DIAMETER * Math.PI;
	protected double kCalibratedLftWheelCircum = SRXConfig.WHEEL_DIAMETER * Math.PI;
	protected double kRgtDistanceCalibration = 0;  // ?
	protected double kLftDistanceCalibration = 0;  // ?
	protected double kInchesPerCount = (SRXConfig.WHEEL_DIAMETER * Math.PI)/SRXConfig.COUNTS_PER_REV;

	public Date buildDate;
	

	// Constructor
	public DriveBase(DriverIF _driver)
	{
      driver = _driver;
      
      // keep its build date for reference
      try {
		buildDate = new Date(
			new File(getClass().getClassLoader().getResource(
				getClass().getCanonicalName().replace('.', '/')+".class").toURI()).lastModified());
	  } catch (URISyntaxException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	  }
	}
	
	public DriveBase() {
		// TODO Auto-generated constructor stub
	}
	
	/*  indexDistanceIn is the requested distance to move in inches
	 *  indexTime is the timeout value the move must be made within
	 *  
	 */
	public void setCruiseAndAccelSetPoints(double indexDistanceIn, int indexTime) {
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
		
	}
	
	/*  heading is the requested degrees of rotation
	 *   timeout value?
	 *  return true when rotation is complete
	 */
	public boolean rotateRobot(double heading) {
		return true;
	}
	
	public double CheckTurnSensitivityFilter( double _turn) {
		/*  Described in Drive Train Class Specification as
		 *  Sensitivity High -- used on chessy turn control
		 *  Apply a sin function that is scaled
		 */
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
		
		//  I don't have this one right yet...
		

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
	


	/** 
	* TippingFilter aka SmoothMove
	* The tipping filter follows the actions of the driver with respect
	* to the motion of the throttle joystick. If the driver exceeds the 
	* limit of robot accel/decel capability, the tipping filter slows the 
	* response of the throttle to protect the robot. If the filter is activated,
	* it will return to driver control as soon as the driver is controlling
	* within robot limits. Determination of kMaxDeltaVelocity is determined by testing.
	* @param value, is the value of the throttle joystick 
	*/
	public double CheckSmoothMove( double _throttle) {
		double fThrottle = _throttle;
		double deltaValue = fThrottle - previousEMAValue;
		int timePeriodSF = 0;
		
		if (driver.GetSmoothMoveEnabled()) {
			// check if the requested throttle is going in the same previous direction
			if (Math.signum(fThrottle) == Math.signum(previousEMAValue)) {
							
				// prevent tipping if delta is too big apply a larger limit
				if (Math.abs(deltaValue) > TeleConfig.MAX_DELTA_VELOCITY) {
					timePeriodSF = TeleConfig.HIGH_SMOOTH;
					System.out.println("delta value >" + TeleConfig.MAX_DELTA_VELOCITY);
				} else {
					timePeriodSF = TeleConfig.LOW_SMOOTH;
				}			
			} else { // driver has switched directions, we're tipping!
				fThrottle = 0;
				timePeriodSF = TeleConfig.HIGH_SMOOTH;
				System.out.println("tipping?");
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
			double smoothFactor = 2.0 / (timePeriodSF + 1);
			fThrottle = previousEMAValue + (smoothFactor) * (deltaValue);
			previousEMAValue = fThrottle;
			
			// If we are within the zero speed dead band set the gain for a high
			// response filter(low smoothing) and set the speed to zero <-- ?
			if (Math.abs(previousEMAValue) < TeleConfig.ZERO_DEAD_BAND) {
		      timePeriodSF = TeleConfig.LOW_SMOOTH;
		      System.out.println("previousEMA" + previousEMAValue + "<" + TeleConfig.ZERO_DEAD_BAND);
			}
			
		}
		return fThrottle;
	}
	/** 
	* TippingFilter
	* Team/Date/Author:
	* The tipping filter follows the actions of the driver with respect
	* to the motion of the throtle joystick. If the driver exceeds the 
	* limit of robot accel/decel capability the tipping filter slows the 
	* response of the throtle to protect the robot. 
	*
	* There are four changes in value from the last joystick value:
	* 1) Transistion from one side of zero to the other side of zero
	* 2) The positive side of zero
	* 3) The negative side of zero
	* 4) Within the joystick deadband
	*
	* Determination of kMaxDeltaVel is determined by testing.
	*
	* @parm _value, is the value of the throtle joystick 
	*/
	public double CheckTippingFilter(double _value) {
		double value = _value;
		// determine change for last joystick read
		double deltaValue = value - previousEMAValue;
		double smoothFactor = 1.0;
		
		// Check joystick value transition from one side of zero to the other side of zero
		if (Math.signum(value) != Math.signum(previousEMAValue)){
			
			// If joystick change is large enough to cause a wheelie or cause the
			// robot to start to tip - the robot intervenes to see that this does
			// not occur The following limits the change in joystick movement
			if (Math.abs(deltaValue) > TeleConfig.MAX_DELTA_VELOCITY){
				smoothFactor = TeleConfig.HIGH_SMOOTH;
			} else {	
			
				// If driver behaves
				smoothFactor = TeleConfig.LOW_SMOOTH;
			}
		}
		
		// Determine if the sign of value and oldEMA are the same
		else if (Math.signum(value) == Math.signum(previousEMAValue)){
				
				// If joystick change is large enough to cause a wheelie or cause the
				// robot to start to tip - the robot intervenes to see that this does
				// not occur The following limits the change in joystick movement
				if (Math.abs(deltaValue) > TeleConfig.MAX_DELTA_VELOCITY){
					smoothFactor = TeleConfig.HIGH_SMOOTH;
				} else {	
				
					// If driver behaves
					smoothFactor = TeleConfig.LOW_SMOOTH;
				}
		}
		
		// Check if the smoothing filter is within the joystick deadband and put filter in high response gain
		else if (Math.abs(previousEMAValue) < TeleConfig.ZERO_DEAD_BAND) {
			deltaValue = 0;
			previousEMAValue = 0;
			smoothFactor = TeleConfig.LOW_SMOOTH;
		} 
		
		// Run through smoothing filter	
		/* 
		*Expoential Avg Filter (EMA) is a recursive low pass filter that
		* can change it's gain to address filter response
		* 
		* Range of smoothFactor is 0 to 1; where smoothFactor = 0 (no smoothing)
		* smoothFactor = .99999 high smoothing
		* Typical smoothFactor = 1-(2.0 / (timePeriodSF + 1)) where user decides
		* on aprox number of cycles for output = input. Time period on
		* iterative robot class is aprox 20ms
		*/
		// This is the value of the joystick speed after going through the
		// tipping filter
		value = previousEMAValue + (1-smoothFactor) * (deltaValue);
		previousEMAValue = value;
		return value;
	}
	
	
	public double ApplySineFunction(double _turn) {
		double factor = Math.PI/2.0 * TeleConfig.kTurnSensitivityHighGain;
		return Math.sin(factor * _turn)/Math.sin(factor);
	}
	
	/*
	 *  Prevent reaction to very small movements to the joystick
	 *  "deadband" usually set to 0.1 as an input 
	 */
	public void AdjustForControllerDeadBand() {
		if (Math.abs(throttleValue) < TeleConfig.JOY_STICK_DEADBAND) {
			throttleValue = 0;
		}
		if (Math.abs(turnValue) < TeleConfig.JOY_STICK_DEADBAND) {
			turnValue = 0;
		}
	
	}
	
	/*
	 * 
	 */
	public void AdjustForDriveLimits() {
		if (Math.abs(throttleValue) > 1.0) {
			throttleValue = Math.signum(throttleValue)*1;
		}
		if (Math.abs(turnValue) > 1.0) {
			turnValue = Math.signum(turnValue)*1;
		}
	
	}
	
	/*
	* Check for low max speed operation and limit speed by low speed factor
	*/	
	public void CheckForAdjustSpeedRequest() {
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
	

}
