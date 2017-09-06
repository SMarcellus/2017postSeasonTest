package org.usfirst.frc.team2228.robot;

public class TeleConfig {
	public static final double JOY_STICK_DEADBAND = 0.1;
	public static final double QUICK_TURN_SPEED = 0.7;
	
    public static final double MAX_DELTA_VELOCITY = 0.2;
    public static final double ZERO_DEAD_BAND = 0.1;
    public static final double kTurnSensitivityHighGain = 0.7;
    public static final double kTurnSensitivityLowGain = 0.0;
    public static final double kThrottleSensitivityLowGain = 0.7;
    public static final double kThrottleSensitivityHighGain = 0.7;
    public static final double kLowMaxSpeedFactor = 0.7;
    public static final double kHeadingTargetAngle = 0;
    public static final double kPHeadingGain = 0;
    public static final double kDHeadingGain = 0;
    public static final double kTestJogSpeed = 0.2;
    
	
	public static final int LOW_SMOOTH = 5;
	public static final int HIGH_SMOOTH = 35;
	public static final int TIME_PERIOD_SF = 35;
}
