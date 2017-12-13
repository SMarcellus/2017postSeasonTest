package org.usfirst.frc.team2228.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TestMode {
	GenericHID controller;
    final String defaultTest = "Default";
    final String testButtons = "test buttons";
    String testSelected;
    SendableChooser<String> chooser;
	
	
	public TestMode(Joystick _stick) {
		controller = _stick;
        chooser = new SendableChooser<String>();
        chooser.addDefault("Default Test", defaultTest);
        chooser.addObject("test Buttons", testButtons);
        SmartDashboard.putData("Test choices", chooser);
	}
	
    public void testInit() {  
    	testSelected = (String)chooser.getSelected();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	String previousTest = testSelected;
    	testSelected = (String)chooser.getSelected();
    	if (previousTest != testSelected){
    		System.out.println("Test selected: " + testSelected);
    	}
    	switch (testSelected)
    	{
    	case testButtons:{
    		TestXBoxButtons();
    		break;
    	}
    	
    	default:
    		break;
    	}    		
    
    }
    
    private void TestXBoxButtons(){
    	double filterValue = 0.2;
    	double axis = controller.getRawAxis(XBoxConfig.LEFT_STICK_X_AXIS);
    	if (Math.abs(axis) > filterValue)
    	{
    		System.out.println("left x " + axis);
    	}
    	axis = controller.getRawAxis(XBoxConfig.LEFT_STICK_Y_AXIS);
    	if (Math.abs(axis) > filterValue)
    	{
    		System.out.println("left y " + axis);
    	}
    	axis = controller.getRawAxis(XBoxConfig.RIGHT_STICK_X_AXIS);
    	if (Math.abs(axis) > filterValue)
    	{
    		System.out.println("right x " + axis);
    	}
    	axis = controller.getRawAxis(XBoxConfig.RIGHT_STICK_Y_AXIS);
    	if (Math.abs(axis) > filterValue)
    	{
    		System.out.println("right y " + axis);
    	}
    	axis = controller.getRawAxis(XBoxConfig.LEFT_TRIGGER);
    	if (Math.abs(axis) > filterValue)
    	{
    		System.out.println("left trigger " + axis);
    	}
    	axis = controller.getRawAxis(XBoxConfig.RIGHT_TRIGGER);
    	if (Math.abs(axis) > filterValue)
    	{
    		System.out.println("right trigger " + axis);
    	}
    	
    	// buttons
    	boolean button = controller.getRawButton(XBoxConfig.A_BUTTON);
    	if (button)
    	{
    		System.out.println("A Button!!");
    	}
    	button = controller.getRawButton(XBoxConfig.B_BUTTON);
    	if (button)
    	{
    		System.out.println("B Button!!");
    	}
    	button = controller.getRawButton(XBoxConfig.X_BUTTON);
    	if (button)
    	{
    		System.out.println("X Button!!");
    	}
    	button = controller.getRawButton(XBoxConfig.Y_BUTTON);
    	if (button)
    	{
    		System.out.println("Y Button!!");
    	}
    	button = controller.getRawButton(XBoxConfig.LEFT_BUMPER);
    	if (button)
    	{
    		System.out.println("left bumper!!");
    	}
    	button = controller.getRawButton(XBoxConfig.RIGHT_BUMPER);
    	if (button)
    	{
    		System.out.println("right bumper!!");
    	}
    	button = controller.getRawButton(XBoxConfig.BACK_BUTTON);
    	if (button)
    	{
    		System.out.println("Back Button!!");
    	}
    	button = controller.getRawButton(XBoxConfig.START_BUTTON);
    	if (button)
    	{
    		System.out.println("Start Button!!");
    	}
    	button = controller.getRawButton(XBoxConfig.LEFT_STICK_BUTTON);
    	if (button)
    	{
    		System.out.println("left stick Button!!");
    	}
    	button = controller.getRawButton(XBoxConfig.RIGHT_STICK_BUTTON);
    	if (button)
    	{
    		System.out.println("right stick Button!!");
    	}
    	button = controller.getRawButton(XBoxConfig.SILVER_GUIDE_BUTTON);
    	if (button)
    	{
    		System.out.println("Silver Guide Button!!");
    	}

    	button = controller.getRawButton(XBoxConfig.UNKNOWN_BUTTON_11);
    	if (button)
    	{
    		System.out.println("unknown 11 Button!!");
    	}
    
    }

}
