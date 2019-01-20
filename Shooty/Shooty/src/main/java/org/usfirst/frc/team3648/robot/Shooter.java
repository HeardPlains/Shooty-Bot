package org.usfirst.frc.team3648.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


import java.util.ArrayList;

public class Shooter {
	/*Joysticks*/
	private Joystick driveController = new Joystick(0);
	private float motorMod = -1;
	/*Sparks*/
	private Spark frontLeft = new Spark(0);
	private Spark frontRight = new Spark(1);
	private Spark Angle = new Spark(3);
	private Spark Shooter = new Spark(4);
	private Servo Latch = new Servo(9);
	private ADXRS450_Gyro gyro;
	//Timed Varibles
	private Timer latchTime = new Timer();
	private boolean Latch_Open = false;
	private DifferentialDrive drive;
	//Autonomous Setup
	ArrayList<Float> autoDelays = new ArrayList<Float>();
	private ArrayList<AutoStep> autoSteps = new ArrayList<AutoStep>();
	
	public Shooter() {
		gyro = new ADXRS450_Gyro();
		for (SPI.Port c : SPI.Port.values())
    		System.out.println(c);
		if(gyro != null)
			gyro.calibrate();
		
		frontLeft.setInverted(true);
		drive = new DifferentialDrive(frontLeft, frontRight);
	
	}
	
	public void startTeleop(){
		gyro.reset();
	}
	
	public void runTeleop(){
		System.out.print(gyro.isConnected()+", ");
		System.out.println(gyro.getAngle());
		
		//Drive Train Tanks
		drive.tankDrive(driveController.getRawAxis(1)*motorMod, driveController.getRawAxis(5)*motorMod);
	
	
		//Change the speed modifier based on the trigger being held. Lets us have a slow, normal, and fast mode
		if(driveController.	getRawAxis(2)>0.5){
			motorMod = -0.7f;
		}else if(driveController.getRawAxis(3)>0.5){
			motorMod = -1f;
		}else{
			motorMod = -0.85f;
		}

		//Fire Shooter
		if(driveController.getRawAxis(3)> .25f){
			Shooter.set(driveController.getRawAxis(3));
		}

		//Angle Shoot Up
		if(driveController.getPOV()==0){
			Angle.set(.2f);
		}

		//Angle Shooter Down
		if(driveController.getPOV()==180){
			Angle.set(-.2f);
		}

		//Open Latch Activates Once
		if (Latch_Open == true){}
		else{
			if(driveController.getRawButtonReleased(4)){
				Latch.set(.2f); 
				latchTime.delay(1); 
				Latch_Open = true;
			}
		}
		//Close Latch Activates Once
		if(Latch_Open == false){}
		else{
			if(driveController.getRawButtonReleased(5)){
			Latch.set(-.2f);
			latchTime.delay(1);
			Latch_Open = false;
			}
		}
	}
	
	public void startAuto(String position){}
	public void runAuto(float delay) {}

}