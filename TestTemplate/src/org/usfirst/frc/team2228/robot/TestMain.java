package org.usfirst.frc.team2228.robot;


public class TestMain {
	public static DriveBase driveBase;
	
	public static void TestTurnSensitivity(){
		double [] input = {
				-1.0, -0.95, -0.9, -0.8, -0.7, -0.6, -0.5, -0.4, -0.3, -0.2, -0.1, 0.0,
				0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0, 1.0, 1.0 
		};
		double output = 0.0;
		int size = input.length;
		for (int i = 0;  i < size; i++){
			output = driveBase.CheckTurnSensitivityFilter(input[i]);
			System.out.println(input[i] + "-->" + output);
		}
	}

	public static void main(String[] args){
		SimDriver driver = new SimDriver();
        driveBase = new DriveBase((DriverIF)driver);
        TestTurnSensitivity();
	}
}
