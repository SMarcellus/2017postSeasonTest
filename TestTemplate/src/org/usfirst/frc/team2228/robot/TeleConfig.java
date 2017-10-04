package org.usfirst.frc.team2228.robot;

import org.usfirst.frc.team2228.robot.DriverIF.ControllerSensitivity;

public class TeleConfig {
	public static final double JOY_STICK_DEADBAND = 0.1;
	public static final double QUICK_TURN_SPEED = 0.7;
	
    public static final double MAX_DELTA_VELOCITY = 0.2;
    public static final double ZERO_DEAD_BAND = 0.1;
    
    public static final double kTurnSensitivityHighGain = 0.7; // 0.1 to 1.0 used for chezy turn control
    
    public static final DriverIF.ControllerSensitivity throttleSensitivity = DriverIF.ControllerSensitivity.Squared;
    public static final double kThrottleCubedGain = 1.0; // 0 to 1; 0 is linear (output==input) 1 is cubed (output=input**3)
    public static final double kThrottleSensitivityHighGain = 0.7;
    public static final double kLowMaxSpeedFactor = 0.7;
    public static final double kHeadingTargetAngle = 0;
    public static final double kPHeadingGain = 0;
    public static final double kDHeadingGain = 0;
    public static final double kTestJogSpeed = 0.2;
    
	
	public static final int kLowSmoothPeriod = 5;
	public static final int kHighSmoothPeriod = 35;
	// smoothFactor is 0 to 1, where 0 is no smooth, 0.99999 is high smoothing
	// Typical smooth factor = 2.0/ (timePeriodSF + 1) where user decides
	// on approx number of cycles for output == input.  Time period on 
	// iterative robot class is 20+ ms
	public static final double klowSmoothFactor = 0.3333;
	public static final double kHighSmoothFactor = 0.05556;
	public static final double kTransitionSmoothFactor = 0.05556;
	
	// determination of max delta values are determined by testing
	public static final double kMaxDeltaVelocity = 0.2;
	public static final double kTransitionMaxDelta = 0.8;
}
