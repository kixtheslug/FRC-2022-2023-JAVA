package frc.robot.commands;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.GyroScope;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TurnAngle extends CommandBase{

    private Drivetrain m_drivetrain;
    private GyroScope m_gyro;
    private float startDes;
    private float turnDestination;
    private float m_turnAmount;
    private boolean isf;
    private int direction;
    private float errorRange = 5.0f;
    private boolean status;
    /*drivetrain - the drivetrain being used
    gyro - the gyro being used
    turnAmount - the amount in degrees being turned
    isTurnningBy - wether or not the turnAmount is a destination, or we the amount we are adding to
    our current angle*/
    public TurnAngle(Drivetrain drivetrain, GyroScope gyro, float turnAmount, boolean isTurnningBy){
        m_drivetrain = drivetrain;
        m_gyro = gyro;
        System.out.println("\n==============================LoL==============================\n");

        addRequirements(m_drivetrain);
        addRequirements(m_gyro);
        isf = isTurnningBy;
        status = false;
        // Getting what the angle should be at the end
        // m_turnAmount = turnAmount;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        System.out.println("Init");
        startDes = m_gyro.getAngleZ();
        if(startDes < 0){
            startDes = 360 + startDes;
        }

        status = false;
        // if(isf){
            m_turnAmount = startDes + 90.0f;
        // }
        if(m_turnAmount > 180.0f){
            m_turnAmount = -1*(180-(m_turnAmount%180));
        }
    }
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        System.out.println("Destination : " + m_turnAmount);
        System.out.println("Start : " + startDes);
        System.out.println("Z : " + m_gyro.getAngleZ());
        /*If statements to check the distance it needs to go and if it is within a small margin or not
        and based of that, checks to see the direction to turn to (if it has already reached its destination,
        it will not move*/
        if(m_turnAmount-m_gyro.getAngleZ()>errorRange){
            direction = 1;
        }else if(m_turnAmount-m_gyro.getAngleZ() < -1*errorRange){
            direction = 1;
        }else{
            direction = 0;
        }
        //Direction should only be 0 when the robot reaches the desired angle
        status = (direction == 0);
        m_drivetrain.arcadeDrive(0.0, Constants.SPEED*direction);
    }
  
  
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
      direction = 0;
      System.out.println("Fin");
      m_drivetrain.arcadeDrive(direction, direction);
    }
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return status;
    }
}