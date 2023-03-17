package frc.robot.commands.PositioningGroups;
import frc.robot.commands.extend.ExtenderSetPositionWaitForComplete;
import frc.robot.commands.pivot.PivotMoveToAngle;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.ExtensionSub;
import frc.robot.subsystems.PivotSub;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


public class Group_RetractAll extends SequentialCommandGroup {
    

    public Group_RetractAll(PivotSub m_pivotMotor, ExtensionSub m_extensionMotor ){
        
        addCommands(
            new ExtenderSetPositionWaitForComplete(m_extensionMotor, 0),
            new PivotMoveToAngle(m_pivotMotor, 20)
         );
    }
}