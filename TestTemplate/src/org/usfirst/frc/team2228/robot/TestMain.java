package org.usfirst.frc.team2228.robot;


public class TestMain {

	public static void main(String[] args){
		SimDriver driver = new SimDriver();
        DriveBase driveBase = new DriveBase(driver);
        double param = 0.65;
        double becomes = driveBase.CheckTurnSensitivityFilter(param);
		System.out.println(becomes);
	}
}
