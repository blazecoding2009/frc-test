package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Gyro;
import frc.robot.util.OrbitPID;

public class GyroDrive extends CommandBase { 

    private Drivetrain dt; 
    private Gyro gyro; 
    private OI oi = new OI(); 

    private double joyX;
    private double joyY;  
    

    public GyroDrive(Drivetrain dt, Gyro gyro) {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(dt, gyro); 
      }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() { 
        //gyro.resetYaw();
    }
    
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() { 
        
        /*
        double yaw = gyro.getYaw(); 
        SmartDashboard.putNumber("Gyro yaw", yaw); 
        */

        joyX = oi.getJoystickX(); 
        joyY = oi.getJoystickY();

        //SmartDashboard.putNumber("joyX", joyX); 
        //SmartDashboard.putNumber("joyY", joyY); 

        double heading = calculateHeading(joyX, joyY); 
        //SmartDashboard.putNumber("heading", heading); 

        /*
        OrbitPID turnPID = new OrbitPID(0.3, 0.001, 0.05); 
        double turn = turnPID.calculate(gyro.getYaw(), heading); 
        */
        

    } 

    double turnDeg = 0; 
    public double calculateHeading(double x, double y) { 
        if (Math.abs(x) + Math.abs(y) > 0.9) {
            if (y <= 0) { 
                turnDeg = x * 90 + 360; 
                turnDeg = turnDeg >= 360 ? turnDeg % 360 : turnDeg; 
        }
            else { 
                turnDeg = 180 - x * 90; 
            }
    }
        return turnDeg; 
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {

    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
    
}
