package org.usfirst.frc.team2228.robot;

import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

public class SRXConfig {

	// CANTalon ID's
		public static int RIGHT_ONE_DRIVE = 1;														
		public static int RIGHT_TWO_DRIVE = 2;
		public static int LEFT_ONE_DRIVE = 3;
		public static int LEFT_TWO_DRIVE = 4;
		
		public static boolean rightEncoderEnabled = true;
		public static FeedbackDevice rightFeedback = FeedbackDevice.QuadEncoder;
		
		public static boolean leftEncoderEnabled = true;
		public static FeedbackDevice leftFeedback = FeedbackDevice.QuadEncoder;
		
		public static TalonControlMode right1Mode = TalonControlMode.PercentVbus;
		public static TalonControlMode right2Mode = TalonControlMode.Follower;
		public static TalonControlMode left1Mode = TalonControlMode.PercentVbus;
		public static TalonControlMode left2Mode = TalonControlMode.Follower;
		
		public static boolean brakeModeEnabled = false;
		
		// CTRE CIMCoder magnetic quadrature (4) encoder 20 pulse per turn
		//  AndyMark tough box mini 14:50 to 16:48 
		// 857 = (50/14)*(48/16)*20*4
		//public static final double COUNTS_PER_REV = 857.0;
		// gear box changes to 14:50 19:45 
		public static final double COUNTS_PER_REV = 676.0;
		public static final double WHEEL_DIAMETER = 6.0; // inches
		
		public static final double COUNTS_INCH = COUNTS_PER_REV / (WHEEL_DIAMETER * Math.PI);
		
		//testing and calibration
		public static final double kRightSideHighSpeed = 0.75;
		public static final double kLeftSideHighSpeed = 0.75;
		public static final double kRightSideLowSpeed = 0.25;
		public static final double kLeftSideLowSpeed = 0.25;
		public static final double kSQWaveHighTime = 500.0;
		public static final double kSQWaveLowTime = 500.0;
}
	
