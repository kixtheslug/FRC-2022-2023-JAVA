package frc.robot.commands.extend;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ExtensionSub;


public class ExtenderMoveToPosition extends CommandBase{
    // Required Subsystems
    private ExtensionSub m_extender;
    private double m_finalAngle;

    // Creation Function of the Class
    public ExtenderMoveToPosition(ExtensionSub ext){
        m_extender = ext;
        m_finalAngle = m_extender.getDesiredAngle();
        addRequirements(m_extender);
    }
    public ExtenderMoveToPosition(ExtensionSub ext, double angle){
        m_extender = ext;
        m_finalAngle = angle;
        addRequirements(m_extender);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        m_extender.setAngle(m_finalAngle);
    }

    // Called every time the scheduler runs while the command is scheduled.
    // Tells the extender motor to either move to it's angle or stablize
    @Override
    public void execute() {
        m_extender.moveMotors();
    }

    // Called once the command ends or is interrupted.
    // Nothing is called here as it is covered already in the subsystem to stop the motor.
    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return Math.abs(m_extender.getCurentAngle()-m_extender.getDesiredAngle()) < 2;
    }
}
