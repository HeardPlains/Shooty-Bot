/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3648.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	
	private Shooter drive = new Shooter();
	//private DrivePresets drive = new DrivePresets("POLE_TM_PRACTICE");
	private String autoSelected;
	private SendableChooser<String> autoChooser = new SendableChooser<>();
	private SendableChooser<String> delayChooser = new SendableChooser<>();
	private float delayStart = 0f;
	//private Joystick controller = new Joystick(0);
	
	
	UsbCamera camera1;
	UsbCamera camera2;	 
	
	Thread m_visionThread;
	 
	@Override
	public void robotInit() {
    	m_visionThread = new Thread(() -> {
    		visionProcessing();
		});
		m_visionThread.setDaemon(true);
		m_visionThread.start();
		
	} 

	@Override
	public void autonomousInit() {
		autoSelected = autoChooser.getSelected(); 
		drive.startAuto(autoSelected);
	} 
	 
	@Override 
	public void autonomousPeriodic() {
		drive.runAuto(Integer.parseInt(delayChooser.getSelected()));
	}

	@Override
	public void teleopInit() {
		drive.startTeleop();
	}
	
	@Override
	public void teleopPeriodic() {
		drive.runTeleop();
	}
	

	
	public void visionProcessing() {
		UsbCamera camera1 = CameraServer.getInstance().startAutomaticCapture(0); 
		UsbCamera camera2 = CameraServer.getInstance().startAutomaticCapture(1);
		camera1.setResolution(320, 240);
		camera2.setResolution(320, 240);
		camera1.setExposureManual(20);
		camera2.setExposureAuto();
	}
}