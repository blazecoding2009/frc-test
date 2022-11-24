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
  

  //private final DifferentialDrive drive = new DifferentialDrive(driveL, driveRight);
  /*
   * public Drivetrain(int leftID, int rightID, int leftSID, int rightSID,
   * MotorType type) { leftM = new CANSparkMax(leftID, type); rightM = new
   * CANSparkMax(rightID, type);
   * 
   * leftS = new CANSparkMax(leftSID, type); rightS = new CANSparkMax(rightSID,
   * type);
   * 
   * TeleopDriveConfig.config(leftM, rightM, leftS, rightS); }
   */

   /**
   * Creates a new Drivetrain.
   * 
   * @return
   */
  public Drivetrain() {
    driveLeft = new Spark(Constants.Drivetrain.pwmChannelDriveLeft);
    driveRight = new Spark(Constants.Drivetrain.pwmChannelDriveRight);

    driveLeft.setInverted(Constants.Drivetrain.directionDriveLeft);
    driveRight.setInverted(Constants.Drivetrain.directionDriveRight);

    this.accel = new Accelerate(); 
    //this.drivePID = new OrbitPID(0.005, 0, 0.05); 

    //driveLeft.setIdleMode(IdleMode.kCoast); 
  }

  double leftSpeed = 0;  
  double rightSpeed = 0; 

  public void tankDrive(double left, double right)
  {
    SmartDashboard.putNumber("Left Drive (%)", left * 100.0);
    SmartDashboard.putNumber("Right Drive (%)", right * 100.0);
/*
    left = drivePID.calculate(left, // encoder value )
    
    */

    leftSpeed = accel.getAccelerationSpeed(left, leftSpeed); 
    rightSpeed = accel.getAccelerationSpeed(right, rightSpeed);
  
    driveLeft.setSpeed(leftSpeed); 
    driveRight.setSpeed(rightSpeed);

    /*
    driveLeft.setSpeed(left);
    driveRight.setSpeed(right);
    */

    
    //.getSpeed()? 
    SmartDashboard.putNumber("PWM Left", driveLeft.getRaw()); 
    SmartDashboard.putNumber("PWM Right", driveRight.getRaw()); 

  }

  public void arcadeDriveWF(double speed, double turn)
  {
    // speed is from 0 to 1
    // + turn is left, - is right
    double left;
    double right;

    if (turn > 0.0) {
      left = (speed) + ((fastExp(Constants.Drivetrain.turnWeightFactor * turn) * -turn));
      right = (speed) + ((fastExp(Constants.Drivetrain.turnWeightFactor * turn) * turn));
    } else if (turn < 0.0) {
        left = (speed) + ((fastExp(Constants.Drivetrain.turnWeightFactor * -turn) * -turn));
        right = (speed) + ((fastExp(Constants.Drivetrain.turnWeightFactor * -turn) * turn));
    } else {
        left = -speed;
        right = -speed;
    }

    tankDrive(left, right);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

  }

  // Reference: https://www.javamex.com/tutorials/math/exp.shtml
  /**
   * Exponential function (e^x) approximation.
   * @param x : The value to use in e^x.
   * @return The result.
   */
  private double fastExp(double x) {
    x = 1d + x / 256d;

    for (int i = 0; i < 8; i++) {
      x *= x;
    }
    
    return x;
  }
}
