package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class Solenoid extends CommandBase {
    
    private Piston piston;

    public Solenoid(Piston piston) { 

        this.piston = piston; 
        addRequirements(piston); 
    }

    public void extend() { 
        piston.set(true)
    }

}