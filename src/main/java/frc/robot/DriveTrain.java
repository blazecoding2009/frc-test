/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;
import frc.robot.util.OrbitPID;




public class Drivetrain extends SubsystemBase {
  
  public Spark driveLeft;
  public Spark driveRight;

  //private OrbitPID drivePID; 

  private Accelerate accel; 
  

  public Drivetrain() {
    driveLeft = new Spark(Constants.Drivetrain.pwmChannelDriveLeft);
    driveRight = new Spark(Constants.Drivetrain.pwmChannelDriveRight);

    driveLeft.setInverted(Constants.Drivetrain.directionDriveLeft);
    driveRight.setInverted(Constants.Drivetrain.directionDriveRight);

    this.accel = new Accelerate();
  }

  double leftSpeed = 0;  
  double rightSpeed = 0; 

  public void tankDrive(double left, double right) {
    SmartDashboard.putNumber("Left Drive", left);
    SmartDashboard.putNumber("Right Drive", right);

    driveLeft.setSpeed(left);
    driveRight.setSpeed(right);
  }

  public void stop() {
    driveLeft.setSpeed(0);
    driveRight.setSpeed(0);
  }

  public void driveFwds() {
    driveLeft.setSpeed(0.5);
    driveRight.setSpeed(0.5);
  }

  public void getLeftEncoder() {
    return driveLeft.getEncoder();
  }

  public void getRightEncoder() {
    return driveRight.getEncoder();
  }

  @Override
  public void periodic() {
  }
}