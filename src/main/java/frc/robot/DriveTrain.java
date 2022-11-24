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
  
  public CANSparkMax driveLeft;
  public CANSparkMax driveRight;

  public final AHRS gyro;

  private Accelerate accel; 
  
  private final DifferentialDriveOdometry odometry;

  public Drivetrain() {
    gyro = new AHRS(Port.kMXP);

    driveLeft = new CANSparkMax(31, MotorType.kBrushless);
    driveRight = new CANSparkMax(41, MotorType.kBrushless);

    driveLeft.setInverted(Constants.Drivetrain.directionDriveLeft);
    driveRight.setInverted(Constants.Drivetrain.directionDriveRight);

    odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(getHeading()));

    this.accel = new Accelerate();
  }

  public void tankDrive(double left, double right) {
    SmartDashboard.putNumber("Left Drive", left);
    SmartDashboard.putNumber("Right Drive", right);

    driveLeft.setSpeed(left);
    driveRight.setSpeed(right);
  }

  public void rotate(double angle) {
    if (angle < 180) {
      tankDrive(0.5, -0.5);
    } else if (angle >= 180) {
      tankDrive(-0.5, 0.5);
    }

    double getangle = getHeading();

    if (getangle == angle) {
      stop();
    } else if (getangle > angle) {
      tankDrive(-0.1, 0.1);
    } else if (getangle < angle) {
      tankDrive(0.1, -0.1);
    }
  }

  public void arcadeDrive(double speed, double turn)
  {
    double left;
    double right;

    left = speed + turn;
    right = speed + -turn;

    tankDrive(left, right);
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

  //access pose
  public Pose2d getPose() {
    return odometry.getPoseMeters();
  }
  
  //reset pose
  public void resetOdometry(Pose2d pose) {
    resetEncoders();
    odometry.resetPosition(pose, Rotation2d.fromDegrees(getHeading()));
  }

  public void resetEncoders() {
    driveLeft.getEncoder().setPosition(0.0);
    driveRight.getEncoder().setPosition(0.0);
  }

  public void zeroHeading() {
    gyro.reset();
  }

  public double getHeading() {
    return gyro.getYaw();
  }

  @Override
  public void periodic() {
  }
}