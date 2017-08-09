package org.usfirst.frc.team2228.robot;

import org.usfirst.frc.team2228.robot.DriverConfig;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;

public class Driver implements DriverIF {
	GenericHID controller;   // left
	GenericHID controller2;  // right
	
	
	public Driver(Joystick _stick) {
		controller = controller2 = _stick;
	}
	
	public Driver(Joystick leftStick,
			      Joystick rightStick) {
		controller = leftStick;
	    controller2 = rightStick;
	}

	@Override
	public DriveStyle GetDriveStyle() {
		return DriverConfig.myStyle;
	}

	@Override
	public double GetLeftX() {
		return controller.getX();
	}

	@Override
	public double GetRightX() {
		return controller2.getX();
	}

	@Override
	public double GetThrottle() {
		return controller.getRawAxis(DriverConfig.throttle);
	}

	@Override
	public double GetTurn() {
		return controller.getY();
	}

	@Override
	public double GetX() {
		return controller.getX();
	}

	@Override
	public double GetY() {
		return controller.getY();
	}

	@Override
	public double GetTwist() {
		return controller.getY();  //twist
	}
	
	public boolean QuickTurnLeft() {
		return controller.getRawButton(DriverConfig.quickTurnLeft);
	}
	public boolean QuickTurnRight() {
		return controller.getRawButton(DriverConfig.quickTurnRight);
	}

	
	public boolean GetTurnSensitivityEnabled() {
		return false;
	}
	
	public boolean GetThrottleSensitivityEnabled() {
		return false;
	}
	
	public boolean GetSmoothMoveEnabled() {
		return true;
	}
	
	public boolean GetLowSpeedTriggered(){
		return controller.getRawButton(DriverConfig.slowSpeedEnable);
	}
	
	public boolean GetHighSpeedTriggered(){
		return controller.getRawButton(DriverConfig.highSpeedEnable);
	}
	
	public boolean GetSquareWaveEnabled(){
		return controller.getRawButton(DriverConfig.enableSquareWave);		
	}
}
