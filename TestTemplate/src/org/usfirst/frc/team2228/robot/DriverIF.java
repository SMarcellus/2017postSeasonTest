package org.usfirst.frc.team2228.robot;

public interface DriverIF {

	public enum DriveStyle {
		tankStyle,
		chessyStyle,
		arcadeStyle,
		mecanumStyle
	}


	public enum ControllerSensitivity {
		High,
		Normal,
		Sine,
		Low
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
	   * intended for arcade and mecanum drive
	   */
	public abstract double GetX();
	public abstract double GetY();
	public abstract double GetTwist();
	
	public abstract boolean QuickTurnLeft();
	public abstract boolean QuickTurnRight();
	
	public abstract ControllerSensitivity GetTurnSensitivity();
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
