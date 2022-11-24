package frc.robot.commands; 

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase; 
import frc.robot.subsystems.Vision; 
import frc.robot.subsystems.Drivetrain; 
import frc.robot.util.OrbitPID;
import frc.robot.util.TuningTable;



public class DriveToTarget extends CommandBase { 
    private Vision limelight; 
    private Drivetrain drive; 

    private TuningTable kP_drive = new TuningTable("kP_drive"); 
    private TuningTable kP_driveAlign = new TuningTable("kP_driveAlign"); 
    private TuningTable kI_drive = new TuningTable("kI_drive"); 
    private TuningTable kI_driveAlign = new TuningTable("kI_driveAlign"); 
    private TuningTable kD_drive = new TuningTable("kD_drive"); 
    private TuningTable kD_driveAlign = new TuningTable("kD_driveAlign"); 

    private OrbitPID driveSpeedPID; 
    private OrbitPID alignPID; 

    //private AlignRobotForShot align = new AlignRobotForShot(limelight, drive); 

    public DriveToTarget(Vision limelight, Drivetrain drive) {
        this.limelight = limelight; 
        this.drive = drive; 
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(limelight, drive); 
        kP_drive.setDefault(0.01); 
        kI_drive.setDefault(0.2); 
        kD_drive.setDefault(0.01); 

        kP_driveAlign.setDefault(0.033);
        kI_driveAlign.setDefault(0.035); 
        kD_driveAlign.setDefault(0.01);

            // Small kP b/c scaling limelight distance (10000 cm away sometimes)
        this.driveSpeedPID = new OrbitPID(kP_drive.get(), kI_drive.get(), kD_drive.get(), 0, 0, 0, 0.5, 0); 
            // Subtle alignment -- kP needs to be small 
        this.alignPID = new OrbitPID(kP_driveAlign.get(), kI_driveAlign.get(), kD_driveAlign.get(), 0, 0, 0, 0.45, 0); 
      }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() { 
        limelight.setVisionStatus(true);
        pastSpeed = 0; 
    }

    // Called every time the scheduler runs while the command is scheduled.
    double distAway;
    //double pastSpeed = 0;
    // 0.2 speed is stop
       
    OrbitPID acceleratePID = new OrbitPID(0.041, 0, 0.002); 
    double pastSpeed = 0; 
    double speed = 0; 
    double finalSpeed = 0; 
    @Override
    public void execute() { 

        if (kP_driveAlign.hasChanged() | kI_driveAlign.hasChanged() | kD_driveAlign.hasChanged()) {
            alignPID.configure(kP_driveAlign.get(), kI_driveAlign.get(), kD_driveAlign.get(), 0.0, 0.0, 0.1, 0.5, 0.0);
        } 

        if (kP_drive.hasChanged() | kI_drive.hasChanged() | kD_drive.hasChanged()) {
            driveSpeedPID.configure(kP_drive.get(), kI_drive.get(), kD_drive.get(), 0.0, 0.0, 0.1, 0.45, 0.0);
          }
        double amountAdjust = limelight.getX();

        if (amountAdjust < 3 && amountAdjust > -3) {

            distAway = limelight.getDistanceFromTarget(); 

            speed = driveSpeedPID.calculate(0, distAway);
            
            // If - PID creeps into +, calculate difference from 0 + number becomes
                if (speed >= 0) { 
                    speed = -speed; 
                }
            
            /*
            if (finalSpeed > speed) {
                */
            finalSpeed = acceleratePID.calculate(speed, pastSpeed); 
            pastSpeed += finalSpeed; 
            drive.tankDrive(-pastSpeed, pastSpeed);
             
            /*
            if (finalSpeed > speed) { 
                finalSpeed -= 0.01;
            }
            */
            //drive.tankDrive(-finalSpeed, finalSpeed);
            //drive.tankDrive(-speed, speed);
            
            SmartDashboard.putNumber("driveFwdSpeed", speed);
            SmartDashboard.putBoolean("Driving fwd", true); 

            //drive.tankDrive(-distAway / 150, (distAway / 150));
            //pastSpeed = speed; 
        }

        else { 
            //align.schedule();
            pastSpeed = 0; 
            SmartDashboard.putBoolean("Driving fwd", false); 
            //amountAdjust = limelight.getX(); 
        //double kP = -0.1; 

            double motorTurningSpeed = alignPID.calculate(0, amountAdjust); 
           
            if (distAway > 9400) { 
                motorTurningSpeed /= 3; 
            }
            

            if (Math.abs(amountAdjust) < 8) {
                motorTurningSpeed *= 3.2; 
            }
            /*
            if (Math.abs(amountAdjust) < 2.5) { 
                motorTurningSpeed = 0; 
            }
            */
            drive.tankDrive(motorTurningSpeed, motorTurningSpeed); 
            

        }
        
         
        SmartDashboard.putNumber("tx", amountAdjust); 
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
        return distAway < 50;
    }
    }