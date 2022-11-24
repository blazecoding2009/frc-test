
package frc.robot.subsystems;


import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance; 


import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants; 

//import frc.robot.util.OrbitPID;


public class Vision extends SubsystemBase {
  private NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight"); 
  private NetworkTableEntry tx, ty, ta, thor, tvert, ts, tv; 
  private boolean visionActivated = false; 
  
  public Vision() { 
      this.tx = table.getEntry("tx");
      this.ty = table.getEntry("ty");
      this.ta = table.getEntry("ta");
      this.thor = table.getEntry("thor");
      this.tvert = table.getEntry("tvert");
      this.tv = table.getEntry("tv");
      this.ts = table.getEntry("ts");
  }   

  // Basic methods to receive values 

  // Use this method to turn the vision "on" (get limelight data) and "off" (stop receiving)
  public void setVisionStatus(boolean activated) { 
    visionActivated = activated; 
  }

  public boolean isTargetFound() { 
    return this.tv.getDouble(0.0) == 1.0; 
  } 
  
  public double getX() { 
    return this.tx.getDouble(0.0); 
  }
  public double getY() {
    return this.ty.getDouble(0.0);  
  }  

  public double getVertHeight() {
    return tvert.getDouble(Double.NaN);
  }

  // Advanced methods of calculations / robot actions and movement  

   public double getDistanceFromTarget() { 
    double angleFromGroundToTarget = Constants.Vision.cameraMountAngle + getY(); 
    double distanceFromCamera = (Constants.Vision.targetStructureHeight - Constants.Vision.cameraMountHeight) / Math.tan(Math.toRadians(angleFromGroundToTarget)); 
    double distance = distanceFromCamera - Constants.Vision.camToFrameDis; 
    
    if (getY() >= 0.0) { 
      double maxDis = Constants.Vision.maxDistance; 
      //Last time distance was positive plus tbe amount of distance adjusted in the negatives
      distance = maxDis + (maxDis + distance); 
    }
    return distance;  
  } 
  
  /*public double getAlignAdjustValue() { 
    // With PID Control altered values 
    double errorFromTarget = getX(); 

    //OrbitPID alignment = new OrbitPID(Constants.Vision.targetAlignmentGains); 
    // Target is 0 (robot is fully aligned) and input is the current deviation 
    double adjustValue = alignment.calculate(0, errorFromTarget);  

    return adjustValue; 
  }*/

  @Override
  public void periodic() {
    // This method will be called once per scheduler run 
  }

}
