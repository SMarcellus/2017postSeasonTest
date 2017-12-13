package org.usfirst.frc.team2228.robot;

import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

public class SRXConfig {

	// CANTalon ID's
		
		public static boolean RIGHT_ENCODER_ENABLED = false;
		public static FeedbackDevice RIGHT_FEEDBACK = FeedbackDevice.QuadEncoder;
		
		public static boolean LEFT_ENCODER_ENABLED = false;
		public static FeedbackDevice LEFT_FEEDBACK = FeedbackDevice.QuadEncoder;
		
		public static TalonControlMode RIGHT_1_MODE = TalonControlMode.PercentVbus;
		public static TalonControlMode RIGHT_2_MODE = TalonControlMode.Follower;
		public static TalonControlMode LEFT_1_MODE = TalonControlMode.PercentVbus;
		public static TalonControlMode LEFT_2_MODE = TalonControlMode.Follower;
		
		public static boolean BRAKE_MODE_ENABLED = false;
		
		// closed loop parameters
		// Proportional, Integration, Differential, Feedfoward
		// ramp rate is maximum change in voltage volts/sec
		public static int default_profile = 0;
		public static double RIGHT_P = 0.2;
		public static double RIGHT_I = 0;
		public static double RIGHT_D = 0;
		public static double RIGHT_FF = 0;
		public static int RGT_IZONE_COUNTS = 50;
		
		//public static double closeLoopRampRate = 0;
		public static double LEFT_P = 0.2;
		public static double LEFT_I = 0;
		public static double LEFT_D = 0;
		public static double LEFT_FF = 0;
		public static int LFT_IZONE_COUNTS = 50;
		
		public static int RGT_ALLOWABLE_CLOSELOOP_ERR = 200;
		public static int LFT_ALLOWABLE_CLOSELOOP_ERR = 200;
		
		// CTRE CIMCoder magnetic quadrature (4) encoder 20 pulse per turn
		//  AndyMark tough box mini 14:50 to 16:48 
		// 857 = (50/14)*(48/16)*20*4
		//public static final double COUNTS_PER_REV = 857.0;
		// gear box changes to 14:50 19:45 
		public static final double COUNTS_PER_REV = 676.0;
		public static final double WHEEL_DIAMETER = 6.0; // inches
		
		public static final double COUNTS_INCH = COUNTS_PER_REV / (WHEEL_DIAMETER * Math.PI);
		
		//testing and calibration
		public static final double K_RIGHT_HIGH_SPEED = 0.75;
		public static final double K_LEFT_HIGH_SPEED = 0.75;
		public static final double K_RIGHT_LOW_SPEED = 0.25;
		public static final double K_LEFT_LOW_SPEED = 0.25;
		public static final double K_SQ_WAVE_HIGH_TIME = 500.0;
		public static final double K_SQ_WAVE_LOW_TIME = 500.0;
}
	
