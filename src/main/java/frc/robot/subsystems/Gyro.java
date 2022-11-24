package frc.robot.subsystems;


import com.kauailabs.vmx.AHRSJNI;


import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Gyro extends SubsystemBase {

    private AHRSJNI gyro; 
    private byte[] data;

    public Gyro() { 
        gyro = new AHRSJNI(); 
    }
    /*
    public void resetYaw() { 
        gyro.reset();
    }
    */

    private byte starting = 4; 
    private byte length = 4; 

    public void getYaw() { 
        gyro.ReadConfigurationData(starting, data, length); 
        SmartDashboard.putRaw("gyro", data); 
    }
    


    @Override
    public void periodic() {
      // This method will be called once per scheduler run
  
    }
}


