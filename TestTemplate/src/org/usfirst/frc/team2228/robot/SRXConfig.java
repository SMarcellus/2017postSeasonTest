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
		
		
		
}
