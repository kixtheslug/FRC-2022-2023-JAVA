package frc.robot.commands.AutoGroups;

import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.GyroScope;
import frc.robot.commands.DriveTillPlatform;
import frc.robot.commands.ResetEncoders;
import frc.robot.commands.AutoBalance;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AutoGroup_BalanceDrive extends SequentialCommandGroup {
    //Variables
    public AutoGroup_BalanceDrive(Drivetrain drivetrain, GyroScope gyro){
        //Adding a drivetrain
        //Adding Order of commands

        addCommands(
            new ResetEncoders(drivetrain),

            // balance
            new AutoBalance(drivetrain, gyro)

        );
    }
}