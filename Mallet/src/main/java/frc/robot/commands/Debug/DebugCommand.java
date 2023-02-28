package frc.robot.commands.Debug;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DebugCommand extends CommandBase{
  private GyroScope m_gyro;
    public DebugCommand(GyroScope gyro){
      m_gyro = gyro;
    }
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
      System.out.println(m_gyro.isCalibrating());
    }
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

    }
  
  
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return true;
    }
}
