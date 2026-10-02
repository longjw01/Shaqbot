// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/** Handles startup and mode transitions. Driving behavior lives in commands. */
public class Robot extends TimedRobot {
  private RobotContainer robotContainer;
  private Command autonomousCommand;

  @Override
  public void robotInit() {
    // Create and connect the hardware, controls, and commands once.
    robotContainer = new RobotContainer();
  }

  @Override
  public void robotPeriodic() {
    // WPILib provides the scheduler. Process one cycle every 20 ms,
    // including while disabled so it can handle command cancellation.
    CommandScheduler.getInstance().run();
  }

  @Override
  public void autonomousInit() {
    autonomousCommand = robotContainer.getAutonomousCommand();
    if (autonomousCommand != null) {
      autonomousCommand.schedule();
    }
  }

  @Override
  public void teleopInit() {
    // Release the drivetrain so its default command can run.
    if (autonomousCommand != null) {
      autonomousCommand.cancel();
    }
  }

  @Override
  public void testInit() {
    // Clear existing commands. The default command may restart, but its
    // mode check prevents joystick driving in Test.
    CommandScheduler.getInstance().cancelAll();
  }
}
