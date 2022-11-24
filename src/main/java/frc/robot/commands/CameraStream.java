package frc.robot.commands;

import java.io.BufferedInputStream;

import javax.imageio.stream.ImageInputStream;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.cameraserver.CameraServer;

import org.opencv.highgui.HighGui;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

public class CameraStream extends CommandBase { 
    
    //CameraServer camServer = new CameraServer(); // need 2022.4.1 WPILib

    VideoCapture vid = new VideoCapture();
    Mat frame; 
    //Canvas c = new Canvas(); 
    //Image cameraFeed;  
    //ImageInputStream imgInput = new ImageInputStream(//camera); 
    //BufferedInputStream bufStream = new BufferedInputStream(imgInput); 
    public CameraStream() {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(); 
      }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() { 
        
    }
    
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() { 
        //cameraFeed = c.getImage(); 
        
        vid.open(0); 
        vid.read(frame); 
        HighGui.imshow("display", frame); 
         

    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        vid.release();
        HighGui.destroyAllWindows();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }

}


