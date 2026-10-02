package frc.robot.subsystems;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/** Owns the drivetrain motors and provides methods for commands to use. */
public class DriveSubsystem extends SubsystemBase {
  // Preserve the working configuration. These are roboRIO PWM ports,
  // not CAN device IDs.
  private static final int LEFT_MOTOR_PWM_PORT = 0;
  private static final int RIGHT_MOTOR_PWM_PORT = 1;
  private static final boolean LEFT_MOTOR_INVERTED = true;
  private static final boolean RIGHT_MOTOR_INVERTED = false;

  private final PWMSparkMax leftMotor = new PWMSparkMax(LEFT_MOTOR_PWM_PORT);
  private final PWMSparkMax rightMotor = new PWMSparkMax(RIGHT_MOTOR_PWM_PORT);

  // The helper combines forward and turn inputs into left/right outputs.
  // Each "::set" identifies the method used to send a motor's output.
  private final DifferentialDrive drive =
      new DifferentialDrive(leftMotor::set, rightMotor::set);

  public DriveSubsystem() {
    leftMotor.setInverted(LEFT_MOTOR_INVERTED);
    rightMotor.setInverted(RIGHT_MOTOR_INVERTED);
    SendableRegistry.addChild(drive, leftMotor);
    SendableRegistry.addChild(drive, rightMotor);

    // Dashboard outputs are software requests, not measured wheel speeds.
    // Dashboard actuator controls in Test are separate from Xbox driving.
    SmartDashboard.putData("Drivetrain", drive);
  }

  /** Inputs normally range from -1 to +1: forward/reverse and turning. */
  public void arcadeDrive(double forward, double turn) {
    // Explicitly preserve the original default: square input magnitudes
    // while keeping their signs, for gentler response near stick center.
    drive.arcadeDrive(forward, turn, true);
  }

  /** Stop both sides when movement is not requested or a command ends. */
  public void stop() {
    drive.stopMotor();
  }
}
