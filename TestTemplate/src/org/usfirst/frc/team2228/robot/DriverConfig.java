package org.usfirst.frc.team2228.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.HIDType;

public class DriverConfig {
	
    public static DriverIF.ControllerSensitivity sensitivity = DriverIF.ControllerSensitivity.High;
    
	// define the DriveBase teleop controls
    public static int throttle = XBoxConfig.LEFT_STICK_Y_AXIS;
    public static int turn = XBoxConfig.RIGHT_STICK_X_AXIS;
    public static int slowSpeedEnable = XBoxConfig.LEFT_TRIGGER;
    public static int highSpeedEnable = XBoxConfig.RIGHT_TRIGGER;
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
	public static int enableSquareWave = XBoxConfig.A_BUTTON;
}
