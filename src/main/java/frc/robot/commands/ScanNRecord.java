/*
package frc.robot.commands; 

import java.util.ArrayList;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain; 
import frc.robot.subsystems.Vision; 


public class ScanNRecord extends CommandBase {
    
    private Drivetrain dt;
    private Vision ll;

    private ArrayList<Pose2d> targetPoses = new ArrayList<>(); 

    public ScanNRecord(Drivetrain dt, Vision ll) { 
        this.dt = dt; 
        this.ll = ll; 

        addRequirements(dt, ll);
    }

    public void initialize() {
        
    }

    double turnSpeed = 20; 
    double mvtSpeed = 20; 
    boolean findNew = true; 
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        //double startRotatePos = dt.getHeading(); 

        // Routine to find targets 
        if (!this.ll.isTargetFound() || findNew) { 
            dt.arcadeDriveWF(mvtSpeed, turnSpeed);
            findNew = false; 
        }

        else { 

            // Drive to target

            // Targets (balls) are on ground, therefore target height is 0
            double alignTurn = this.ll.getAlignValue(); 
            dt.arcadeDriveWF(0, alignTurn); 

            if (alignTurn < 1) { 
                double distAway = this.ll.getDistanceFromTarget(); 
                // Speed robot heading to target is scaled to distance, so will stop when 0
                dt.arcadeDriveWF(distAway, 0); 

                if (distAway == 0) { 
                    // Record target poses 
                    targetPoses.add(dt.getPose()); 
                    findNew = true; 
                }
            }
        }

  
    }
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
      dt.tankDrive(0, 0);
    }
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return false;
    }




}

*/
