package org.usfirst.frc.team2228.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.HIDType;

public class DriverConfig {

	public static DriverIF.DriveStyle myStyle = DriverIF.DriveStyle.chessyStyle;
	public static GenericHID.HIDType myControllerStyle = GenericHID.HIDType.kHIDGamepad;
	// maybe joystick/controller should be a singleton...
	
	
}
