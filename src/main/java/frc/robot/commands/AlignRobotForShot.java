package frc.robot.commands; 

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase; 
import frc.robot.subsystems.Vision; 
import frc.robot.subsystems.Drivetrain; 
import frc.robot.util.OrbitPID;
import frc.robot.util.TuningTable;


public class AlignRobotForShot extends CommandBase { 
    private Vision limelight; 
    private Drivetrain drive; 

    private TuningTable kP = new TuningTable("kP_Align"); 
    private TuningTable kI = new TuningTable("kI_Align"); 
    private TuningTable kD = new TuningTable("kD_Align"); 

    private OrbitPID alignPID; 


    public AlignRobotForShot(Vision limelight, Drivetrain drive) {
        this.limelight = limelight; 
        this.drive = drive; 
        kP.setDefault(0.037); 
        kI.setDefault(0.035);
        kD.setDefault(0.018);

        this.alignPID = new OrbitPID(kP.get(), kI.get(), kD.get(), 0, 0, 0.1, 0.45, 0); 
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(limelight, drive); 
      }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() { 
        limelight.setVisionStatus(true);
    }
    boolean driving = false; 
    boolean aligning = true;  
    double amountAdjust; 

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() { 
        if (kP.hasChanged() | kI.hasChanged() | kD.hasChanged() ) {
            alignPID.configure(kP.get(), kI.get(), kD.get(), 0.0, 0.0, 0.1, 0.45, 0.0);
          }
        amountAdjust = limelight.getX(); 
        //double kP = -0.1; 

        double motorTurningSpeed = alignPID.calculate(0, amountAdjust); 
        //kP * amountAdjust; 

        /*
        if (driving) { 
            double distAway = limelight.getDistanceFromTarget(); 
            if (distAway > 50) { 
                distAway = 50; 
            }
            drive.tankDrive(-distAway / 100, (distAway / 100));

            if (distAway < 20) { 
                driving = false; 
            }
            }
            else {
                
                if (motorTurningSpeed > 0.5) { 
                    motorTurningSpeed = 0.5; 
                } 
                */
                drive.tankDrive(motorTurningSpeed, motorTurningSpeed); 
                    
/*
        if (amountAdjust < 3) { 
            driving = true; 
        } 
        else { 
            driving = false; 
        }
        */


        SmartDashboard.putNumber("tx", limelight.getX()); 
        SmartDashboard.putNumber("ty", limelight.getY()); 
        SmartDashboard.putNumber("tvert", limelight.getVertHeight()); 

        SmartDashboard.putNumber("distance", limelight.getDistanceFromTarget());
        
        
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        limelight.setVisionStatus(false); 
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
    }