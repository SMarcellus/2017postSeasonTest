package org.usfirst.frc.team2228.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.HIDType;

public class DriverConfig {
	
	// addressing of the xbox controller
	// getRawAxis(int)
	public static int XBOX_LEFT_STICK_X_AXIS = 0;
	public static int XBOX_LEFT_STICK_Y_AXIS = 1;
	public static int XBOX_LEFT_TRIGGER = 2;
	public static int XBOX_RIGHT_TRIGGER = 3;
	public static int XBOX_RIGHT_STICK_X_AXIS = 4;
	public static int XBOX_RIGHT_STICK_Y_AXIS = 5;
	// dunno the raw button numbers ids...
	// getRawButton(int)
	public static int XBOX_A_BUTTON = 1;
	public static int XBOX_B_BUTTON = 2;
	public static int XBOX_X_BUTTON = 3;
	public static int XBOX_Y_BUTTON = 4;
	public static int XBOX_LEFT_BUMPER = 5;
	public static int XBOX_RIGHT_BUMPER = 6;
	public static int XBOX_BACK_BUTTON = 7;
	public static int XBOX_START_BUTTON = 8;
	public static int XBOX_LEFT_STICK_BUTTON = 9;
	public static int XBOX_RIGHT_STICK_BUTTON = 10;
	public static int XBOX_SILVER_GUIDE_BUTTON = 13;
	// pov
	public static int XBOX_D_PAD_UP = 4;
	public static int XBOX_D_PAD_RIGHT = 4;
	public static int XBOX_D_PAD_DOWN = 4;
	public static int XBOX_D_PAD_LEFT = 4;
	
	// define the DriveBase teleop controls
    public static int throttle = XBOX_LEFT_STICK_Y_AXIS;
    public static int turn = XBOX_RIGHT_STICK_X_AXIS;
    public static int slowSpeedEnable = XBOX_LEFT_TRIGGER;
    public static int highSpeedEnable = XBOX_RIGHT_TRIGGER;
    public static int turnSensitivityEnabled = 0;
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
	public static int enableSquareWave = XBOX_A_BUTTON;
}
