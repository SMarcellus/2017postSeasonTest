package org.usfirst.frc.team2228.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.HIDType;

public class DriverConfig {
	
	// throttle choose from linear, sine, squared, etc - described in DriverIF.java
    public static DriverIF.ControllerSensitivity sensitivity = DriverIF.ControllerSensitivity.Linear;
    public static boolean turnSensitivityEnabled = false;
    public static boolean smoothMoveEnabled = false;
    
	// Driver class can get this button mapping, good place for all your button mapping
    // controller calls getRawAxis with this id for analog input (joysticks and triggers)
    public static int throttle = XBoxConfig.LEFT_STICK_Y_AXIS;
    public static int turn = XBoxConfig.RIGHT_STICK_X_AXIS;
    // controller calls getRawButton with id for button map
    public static int slowSpeedEnable = XBoxConfig.LEFT_TRIGGER;
    public static int highSpeedEnable = XBoxConfig.RIGHT_TRIGGER;
    // undefined
    public static int throttleSensitivityEnabled = 0;
    public static int enableChessyDrive = 0;
    public static int enableTankDrive = 0;
    public static int breakawayRight180 = 0;
    public static int breakawayLeft180 = 0;
    public static int quickTurnLeft = 0;
    public static int quickTurnRight = 0;

	public static DriverIF.DriveStyle myStyle = DriverIF.DriveStyle.chessyStyle;
	public static boolean isPIDEnabled = false;
	
	// define the DriveBase test/calibration controls
	public static int enableSquareWave = XBoxConfig.A_BUTTON;
}
