package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveSubsystem;

/** Reads the driver's sticks repeatedly and requests drivetrain movement. */
public class DriveCommand extends Command {
  // This command scales stick inputs only. WPILib's DifferentialDrive
  // later applies its default 0.02 deadband, input squaring, and mixing.
  // Do not repeat those calculations here: that would change drive response.
  private static final double INPUT_SCALE = 0.6;

  private final DriveSubsystem drivetrain;
  private final XboxController controller;

  public DriveCommand(DriveSubsystem drivetrain, XboxController controller) {
    // Use the existing objects rather than creating duplicate hardware.
    this.drivetrain = drivetrain;
    this.controller = controller;

    // Every command controlling the drivetrain must declare this requirement
    // so the scheduler can coordinate competing drivetrain commands.
    addRequirements(drivetrain);
  }

  @Override
  public void execute() {
    // Default commands can run in other enabled modes. Allow stick driving
    // only in enabled Teleop.
    if (!DriverStation.isTeleopEnabled()) {
      drivetrain.stop();
      return;
    }

    // Preserve the original axes and signs so driving feels the same.
    double forward = -controller.getLeftY() * INPUT_SCALE;
    double turn = -controller.getRightX() * INPUT_SCALE;
    drivetrain.arcadeDrive(forward, turn);
  }

  @Override
  public void end(boolean interrupted) {
    // Clear our motor request when canceled or replaced by another command.
    drivetrain.stop();
  }

  @Override
  public boolean isFinished() {
    // Continue driver control until canceled or interrupted.
    return false;
  }
}
