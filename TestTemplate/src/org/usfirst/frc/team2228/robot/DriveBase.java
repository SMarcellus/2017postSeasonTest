package org.usfirst.frc.team2228.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveBase {
	
	private RobotDrive drive;
	private DriverIF driver;
	
	// drive motor controllerss
	private CANTalon right1;
	private CANTalon left1;
	private CANTalon right2;
	private CANTalon left2;
	
	// class variables
	private double currentRobotAngle = 0;
	private double throttleValue = 0;
	private double turnValue = 0;
	private double deltaThrottleValue = 0;
	private double headingError = 0;
	private double previousHeadingError = 0;
	
	// driver request states
	private boolean lowSpeedEnabled = false;
	private boolean driveStraightEnabled = false;
	private boolean headingModuleEnabled = false;
	private boolean brakeEnabled = false;
	private boolean squareWaveActive = false;
	private boolean jogActive = false;
	private boolean moveXDistanceActive = false;
	
	// any toggle states
	
	// smooth move parameters
	private double previousEMAValue = 0; // -1 to 1
	private int    timePeriodSF = 35;
	
	
	// Constructor
	public void DriveBase(DriverIF _driver)
	{
		driver = _driver;
		right1 = new CANTalon(SRXConfig.RIGHT_ONE_DRIVE); // master
		right2 = new CANTalon(SRXConfig.RIGHT_TWO_DRIVE);
		left1 = new CANTalon(SRXConfig.LEFT_ONE_DRIVE);  // master
		left2 = new CANTalon(SRXConfig.LEFT_TWO_DRIVE);
		
		if (SRXConfig.rightEncoderEnabled)
		{
			right1.setFeedbackDevice(SRXConfig.rightFeedback);
		}
		
		if (SRXConfig.leftEncoderEnabled)
		{
			left1.setFeedbackDevice(SRXConfig.leftFeedback);
		}
		
		right1.changeControlMode(SRXConfig.right1Mode);
		right2.changeControlMode(SRXConfig.right2Mode);
		if (SRXConfig.right2Mode == TalonControlMode.Follower) {
			right2.set(right1.getDeviceID());
			right2.enableControl();
		}
			
		left1.changeControlMode(SRXConfig.left1Mode);
		left2.changeControlMode(SRXConfig.left2Mode);
		if (SRXConfig.left2Mode == TalonControlMode.Follower) {
			left2.set(left1.getDeviceID());
			left2.enableControl();
		}		
		
		drive = new RobotDrive(right1, left1);
		
		// Set peak and nominal output voltage levels of motor controllers
		right1.configNominalOutputVoltage(+0.0f, -0.0f);
		right1.configPeakOutputVoltage(+12.0f, -12.0f);
		right2.configNominalOutputVoltage(+0.0f, -0.0f);
		right2.configPeakOutputVoltage(+12.0f, -12.0f);
		left1.configNominalOutputVoltage(+0.0f, -0.0f);
		left1.configPeakOutputVoltage(+12.0f, -12.0f);
		left2.configNominalOutputVoltage(+0.0f, -0.0f);
		left2.configPeakOutputVoltage(+12.0f, -12.0f);
		
		// Disable brake mode in all drive motors, motors will coast to a stop
		right1.enableBrakeMode(SRXConfig.brakeModeEnabled);
		right2.enableBrakeMode(SRXConfig.brakeModeEnabled);
		left1.enableBrakeMode(SRXConfig.brakeModeEnabled);
		left2.enableBrakeMode(SRXConfig.brakeModeEnabled);
	}
	
	public void autonomousInit() {
		
	}
	
	/*  indexDistanceIn is the requested distance to move in inches
	 *  indexTime is the timeout value the move must be made within
	 *  return true when distance or time is reached
	 */
	public boolean driveIndexRobot(double indexDistanceIn, int indexTime) {
		return true;
	}
	
	/*  heading is the requested degrees of rotation
	 *   timeout value?
	 *  return true when rotation is complete
	 */
	public boolean rotateRobot(double heading) {
		return true;
	}
	
	public void teleopInit(){
		
	}
	
	public void teleopPeriodic(){
		double originalThrottle;
		double originalTurn;
		
		switch (driver.GetDriveStyle()) {
		case chessyStyle:
			originalThrottle = throttleValue = driver.GetThrottle();
			originalTurn = turnValue = driver.GetTurn();
			
			turnValue = CheckTurnSensitivityFilter(turnValue);
		}
	}
	
	public double CheckTurnSensitivityFilter( double _turn) {
		double fTurn = _turn;
		
		if (driver.GetTurnSensitivityEnabled()) {			
			if (driver.GetLowSpeedFactorEnabled()) {
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
	
	public double ApplySineFunction(double _turn) {
		double factor = Math.PI/2.0 * TeleConfig.kTurnSensitivityGain;
		return Math.sin(factor * _turn)/Math.sin(factor);
	}

}
