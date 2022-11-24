// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.TeleopDrive;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Gyro;
import frc.robot.subsystems.Test;
import frc.robot.subsystems.Vision; 
import frc.robot.commands.AlignRobotForShot;
import frc.robot.commands.DriveToTarget;
import frc.robot.commands.GyroDrive;
import frc.robot.commands.LeftTurn;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final OI oi = new OI();
  private final Drivetrain drivetrain = new Drivetrain();
  private final Vision limelight = new Vision(); 
  private final Gyro gyro = new Gyro(); 
  private final TeleopDrive teleopDrive = new TeleopDrive(drivetrain, () -> oi.getDriveThrottle(), () -> oi.getDriveTurn());
  private final AlignRobotForShot alignment = new AlignRobotForShot(limelight, drivetrain);
  private final Test testfilething = new Test();
  private final DriveToTarget driveTarget = new DriveToTarget(limelight, drivetrain); 
  private final GyroDrive gyroDrive = new GyroDrive(drivetrain, gyro); 
  private final LeftTurn lTurn = new LeftTurn(drivetrain); 

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    setDefaultCommands();
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    
    oi.getAlignButton().whileActiveContinuous(alignment);   
    oi.getDriveTargetButton().whileActiveContinuous(driveTarget); 
    oi.getLeftTurnBtn().whenActive(lTurn); 
    

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    //return m_autoCommand;
    return testfilething();
  }

  public void setDefaultCommands() {
    //setDefaultCommand(alignment);
    drivetrain.setDefaultCommand(teleopDrive);
  }

  public TeleopDrive getTeleopDrive() { 
    return teleopDrive; 
  }

  public Drivetrain getDriveTrain() { 
    return drivetrain; 
  }

  public GyroDrive getGyroDrive() { 
    return gyroDrive; 
  }
}
