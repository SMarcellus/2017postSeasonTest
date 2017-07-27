package org.usfirst.frc.team2228.robot;

import org.usfirst.frc.team2228.robot.DriverConfig;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;

public class Driver implements DriverIF {
	XboxController xbox;
	Joystick joystick;
	GenericHID controller;   // left
	GenericHID controller2;  // right
	
	boolean useXbox;
	
	public Driver(XboxController _xbox) {
		controller = controller2 = xbox = _xbox;
		useXbox = true;
	}
	
	public Driver(Joystick _stick) {
		controller = controller2 = joystick = _stick;
		useXbox = false;
	}
	
	public Driver(Joystick leftStick,
			      Joystick rightStick) {
		controller = leftStick;
	    controller2 = rightStick;
		useXbox = true;
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
		return controller.getX();
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
		return joystick.getTwist();
	}

}
