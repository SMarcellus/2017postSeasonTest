package org.usfirst.frc.team2228.robot;

import edu.wpi.first.wpilibj.Joystick;

public class SimDriver implements DriverIF {
	
	// test program can write to these directly for testing
	public DriverIF.DriveStyle myStyle = DriverIF.DriveStyle.chessyStyle;
	
	public double leftX = 0;
	public double righttX = 0;
	public double throttle = 0;
	public double turn = 0;
	public double getX = 0;
	public double getY = 0;
	public double getTwist = 0;
	
	public boolean quickTurnLeft = false;
	public boolean quickTurnRight = false;
	public DriverIF.ControllerSensitivity turnSensitivity = DriverIF.ControllerSensitivity.Normal;
	public DriverIF.ControllerSensitivity throttleSensitivity = DriverIF.ControllerSensitivity.Normal;
	public boolean  smoothMoveOn = false;
	public boolean  lowSpeedTriggered = false;
	public boolean  highSpeedTriggered = false;
	public boolean  squareWaveEnabled = false;
	
	public SimDriver() {
		
	}

	@Override
	public DriveStyle GetDriveStyle() {
		return myStyle;
	}

	@Override
	public double GetLeftX() {
		return getX;
	}

	@Override
	public double GetRightX() {
		return getY;
	}

	@Override
	public double GetThrottle() {
		return throttle;
	}

	@Override
	public double GetTurn() {
		return turn;
	}

	@Override
	public double GetX() {
		return getX;
	}

	@Override
	public double GetY() {
		return getY;
	}

	@Override
	public double GetTwist() {
		return getTwist;
	}

	@Override
	public boolean QuickTurnLeft() {
		return quickTurnLeft;
	}

	@Override
	public boolean QuickTurnRight() {
		return quickTurnRight;
	}

	@Override
	public ControllerSensitivity GetTurnSensitivity() {
		return turnSensitivity;
	}

	@Override
	public ControllerSensitivity GetThrottleSensitivity() {
		return throttleSensitivity;
	}

	
	public boolean GetTurnSensitivityEnabled() {
		return false;
	}
	
	public boolean GetThrottleSensitivityEnabled() {
		return  false;
	}
	
	@Override
	public boolean GetSmoothMoveEnabled() {
		return smoothMoveOn;
	}

	@Override
	public boolean GetLowSpeedTriggered() {
		return lowSpeedTriggered;
	}

	@Override
	public boolean GetHighSpeedTriggered() {
		return highSpeedTriggered;
	}

	@Override
	public boolean GetSquareWaveEnabled() {
		return squareWaveEnabled;
	}

}
