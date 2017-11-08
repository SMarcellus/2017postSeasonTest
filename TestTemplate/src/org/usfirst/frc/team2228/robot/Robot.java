
package org.usfirst.frc.team2228.robot;

import java.io.File;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    final String defaultAuto = "Default";
    final String customAuto = "My Auto";
    String autoSelected;
    SendableChooser chooser;
    private Joystick joystick;
    private Driver driver;
    private TankSRXDriveBase driveBase;
    
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", defaultAuto);
        chooser.addObject("My Auto", customAuto);
        SmartDashboard.putData("Auto choices", chooser);

        // put axis and button mapping in DriverConfig

		File _logDirectory = new File("/home/lvuser/log");
		if (!_logDirectory.exists()) {
			_logDirectory.mkdir();
		}
		DebugLogger.init("/home/lvuser/log/Debug_");
		joystick = new Joystick(1/*RobotMap.RIGHT_SIDE_JOYSTICK_ONE*/);
		driver = new Driver(joystick);
		// generic drive base
		driveBase = new TankSRXDriveBase(driver);
    }
    
	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString line to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the switch structure below with additional strings.
	 * If using the SendableChooser make sure to add them to the chooser code above as well.
	 */
    public void autonomousInit() {
    	autoSelected = (String) chooser.getSelected();
//		autoSelected = SmartDashboard.getString("Auto Selector", defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
		//  auto.autonomousInit(autoSelected);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    //  auto.autonomousPeriodic();
    }

    /*   
     * *Initialization code for teleop mode should go here
     * @see edu.wpi.first.wpilibj.IterativeRobot#teleopInit()
     */
    public void teleopInit() {
    	driveBase.teleopInit();    	
    }
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
		driveBase.teleopPeriodic();
    }
    /*   
     * *Initialization code for test mode should go here
     * @see edu.wpi.first.wpilibj.IterativeRobot#testInit()
     */
    public void testInit() {
    	driveBase.testInit();    	
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	driveBase.testPeriodic();  
    
    }
    
}
