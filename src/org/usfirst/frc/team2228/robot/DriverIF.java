package org.usfirst.frc.team2228.robot;

import org.usfirst.frc.team2228.robot.DriverIF.ControllerSensitivity;
import org.usfirst.frc.team2228.robot.DriverIF.DriveStyle;
/*
 * This class provides a generic interface for the driver's intent to the drive base code. 
 * The goal is the isolate what changes year to year with each driver's preference from
 * the basic, generic library intent driving code.  The driver and game specific changes
 *  will be the class that extends this interface as well as the configuration files.
 *  
 */
public interface DriverIF {

	// 
	public enum DriveStyle {
		tankStyle,   // two joysticks - each driving one side of the robot
		chessyStyle, // two joysticks - one driving throttle, the other driving turn
		arcadeStyle, // one joystick - one dimension for throttle, one for turn, or using twist 
		// mecanumStyle  This is really more a build configuration than a driver's choice.
	}

/*  Sensitivity choice for throttle  
 *  - Linear: action is linear for the operator, output == input
    - Sine: provides a larger "do little around" zero and near full speeds, like a elevator uses.
    - Squared: slows down the response of the robot to fast moves on the part of the operator.
    - Cubed: even greater slows down the response of the robot to fast moves
 */
	public enum ControllerSensitivity {
		Linear,
		Sine,
		Squared,
		Cubed
	}
	 /**
	   * return the DriveStyle from the Driver config
	   */
	public abstract DriveStyle GetDriveStyle();
	
	 /**
	   * intended for tank drive
	   */
	public abstract double GetLeftX();
	public abstract double GetRightX();
	
	 /**
	   * intended for chessy drive
	   */
	public abstract double GetThrottle();
	public abstract double GetTurn();
	
	 /**
	   * intended for arcade drive
	   */
	public abstract double GetX();
	public abstract double GetY();
	public abstract double GetTwist();
	
	public abstract boolean QuickTurnLeft();
	public abstract boolean QuickTurnRight();
	
	public abstract ControllerSensitivity GetThrottleSensitivity();
	
	public abstract boolean GetTurnSensitivityEnabled();
	public abstract boolean GetThrottleSensitivityEnabled();
	
	public abstract boolean GetSmoothMoveEnabled();
	public abstract boolean GetLowSpeedTriggered();
	public abstract boolean GetHighSpeedTriggered();
	
	 /**
	   * testing and calibration
	   */
	public abstract boolean GetSquareWaveEnabled();
}
