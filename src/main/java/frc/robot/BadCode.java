public class BadCode extends CommandBase { 
    
    private Drivetrain dt;

    private Solenoid sol;

    //private final CANSparkMax leftMotor = new CANSparkMax(31, MotorType.kBrushless); // <-- parameters are just id/port, type of motor, use the same for your program
    
    //private final CANSparkMax rightMotor = new CANSparkMax(41, MotorType.kBrushless); 
        
    public void execute() { 
        dt.driveFwds();
    
        if (dt.getLeftEncoder() >= 5 && dt.getRightEncoder() >= 5) { 
            dt.stop();
            sol.extend();
        }
    }
} 