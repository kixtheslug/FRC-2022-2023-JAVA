package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PivotMotor;
import frc.robot.subsystems.ExtensionMotor;


public class StopAllMotors extends CommandBase{
    private PivotMotor m_pivot;
    private ExtensionMotor m_extender;

    public StopAllMotors(PivotMotor pivot, ExtensionMotor extender){
        m_pivot = pivot;
        m_extender = extender;
        addRequirements(m_pivot);
        addRequirements(m_extender);
    }
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        m_pivot.emergencyStop();
        m_extender.emergencyStop();    
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return true;
    }
}
