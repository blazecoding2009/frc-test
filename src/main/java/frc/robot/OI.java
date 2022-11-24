/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * Add your docs here.
 */
public class OI {

  XboxController driver = new XboxController(Constants.OI.controllerChannelDriver);
  XboxController operator = new XboxController(Constants.OI.controllerChannelOperator);

  /*
  private double deadzone(double input, double deadzone) {
    return Math.abs(input) < deadzone? 0: input;
  }
  */
    
  /**
   * Scale the input by a constant slope based on the value of deadband.
   * This eliminates the "jump" when the input is beyond the deadband.
   * @param deadband
   * @param input
   * @return
   */
  private double deadbandCompensation(double input, double deadband) {
    // Linear function between (x, y): (deadband, 0) and (1, 1).
    double slope = 1 / (1 - deadband);    // m = rise / run
    double offset = -1 * (slope * 1 - 1); // b = -1 * (mx - y)

    // Scale the output.
    if (input < 0.0) {
      return Math.abs(input) > deadband ? (-1 * (slope * Math.abs(input) + offset)) : 0.0;
    } else if (input > 0.0) {
      return Math.abs(input) > deadband ? (slope * Math.abs(input) + offset) : 0.0;
    } else {
      return 0.0;
    }
  }

  public double getDriveTurn() {
    double d = getJoystickX(driver, Hand.kLeft);
    SmartDashboard.putNumber("Turn", d);
    return d;
  }

  public double getDriveThrottle() {
    //double d = getTrigger(driver, Hand.kRight) - getTrigger(driver, Hand.kLeft);
    
    
    double d = getJoystickY(driver, Hand.kLeft); 
    SmartDashboard.putNumber("Throttle", d);
    
    
    return d;
  }

  public double getTrigger(XboxController controller, Hand hand) {
    return deadbandCompensation(controller.getTriggerAxis(hand), Constants.OI.controllerDeadbandTrigger);
  }

  public double getJoystickX() {
    return deadbandCompensation(driver.getX(Hand.kLeft), Constants.OI.controllerDeadbandJoystick);
  }

  public double getJoystickY() {
    return deadbandCompensation(driver.getY(Hand.kLeft), Constants.OI.controllerDeadbandJoystick);
  }

  public double getJoystickX(XboxController controller, Hand hand) {
    return deadbandCompensation(controller.getX(hand), Constants.OI.controllerDeadbandJoystick);
  }

  public double getJoystickY(XboxController controller, Hand hand) {
    return deadbandCompensation(controller.getY(hand), Constants.OI.controllerDeadbandJoystick);
  }

  public Trigger getAlignButton() { return new Trigger(driver::getAButton);}

  public Trigger getDriveTargetButton() { return new Trigger(driver::getYButton);}

  public Trigger getLeftTurnBtn() { return new Trigger(driver::getXButton);}

  public Trigger getRightTurnBtn() { return new Trigger(driver::getBButton);}
}


