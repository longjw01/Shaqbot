package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.commands.DriveCommand;
import frc.robot.subsystems.DriveSubsystem;

/** Connects subsystems, controls, and commands during startup. */
public class RobotContainer {
  // Controller USB slot in Driver Station, not a roboRIO port.
  private static final int DRIVER_CONTROLLER_PORT = 0;

  private final DriveSubsystem drivetrain = new DriveSubsystem();
  private final XboxController driverController =
      new XboxController(DRIVER_CONTROLLER_PORT);

  public RobotContainer() {
    // Assign the command used when the drivetrain is available.
    // Assigning a command does not immediately execute it.
    drivetrain.setDefaultCommand(new DriveCommand(drivetrain, driverController));
    configureBindings();
  }

  /** Add button-to-command connections here as the robot gains features. */
  private void configureBindings() {
    // ShaqBot uses only stick axes. Their command is assigned above.
  }

  /** ShaqBot currently holds still throughout Autonomous. */
  public Command getAutonomousCommand() {
    // "drivetrain::stop" means "call drivetrain.stop() when the action runs."
    // Commands.run repeats until canceled. The drivetrain argument declares
    // a requirement, preventing the default drive command from running.
    return Commands.run(drivetrain::stop, drivetrain)
        .withName("Autonomous: Hold Stopped");
  }
}
