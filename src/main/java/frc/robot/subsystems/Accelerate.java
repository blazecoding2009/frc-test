package frc.robot.subsystems;

import frc.robot.util.OrbitPID;

public class Accelerate {
    
    private OrbitPID acceleratePID; 

    public Accelerate() { 
        this.acceleratePID = new OrbitPID(0.055, 0, 0.002);  
    }
    
    public double getAccelerationSpeed(double targetSpeed, double currentSpeed) { 
       //accelerate PID 
       double increment = acceleratePID.calculate(targetSpeed, currentSpeed); 
       currentSpeed += increment; 
       return currentSpeed; 
    }

}
