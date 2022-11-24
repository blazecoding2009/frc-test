/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class TeleopDrive extends CommandBase {

  Drivetrain dt;
  DoubleSupplier turn;
  DoubleSupplier drive;


  /**
   * Creates a new TeleopDrive.
   */
  public TeleopDrive(Drivetrain dt, DoubleSupplier turn, DoubleSupplier drive) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.dt = dt;
    this.turn = turn;
    this.drive = drive;

    addRequirements(dt);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    /*
    double left, right; 
    double speed = drive.getAsDouble(); 
    double rotate = turn.getAsDouble(); 

    if (rotate > 0.0) { 
      left = speed + rotate; 
      right = speed + rotate; 
    }
    else if (rotate < 0.0) { 
      left = speed - rotate; 
      right = speed - rotate; 
    }
    else { 
      left = -speed; 
      right = speed; 
    }

    dt.tankDrive(left, right);
    */

    

    dt.arcadeDriveWF(drive.getAsDouble(), turn.getAsDouble());

    SmartDashboard.putBoolean("Driving", true); 
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    dt.tankDrive(0.0, 0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
