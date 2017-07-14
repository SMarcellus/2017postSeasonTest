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
	
	private RobotDrive driveStyle;
	private CANTalon right1;
	private CANTalon left1;
	private CANTalon right2;
	private CANTalon left2;
	
	// Constructor
	public void DriveBase()
	{
		right1 = new CANTalon(SRXConfig.RIGHT_ONE_DRIVE);
		right2 = new CANTalon(SRXConfig.RIGHT_TWO_DRIVE);
		left1 = new CANTalon(SRXConfig.LEFT_ONE_DRIVE);
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
		left1.changeControlMode(SRXConfig.left1Mode);
		left2.changeControlMode(SRXConfig.left2Mode);
	}
}
