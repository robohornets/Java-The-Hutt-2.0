// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  private final boolean UseLimelight = false;

  @Override
  public void robotInit() {
    m_robotContainer = new RobotContainer();

    m_robotContainer.drivetrain.getDaqThread().setThreadPriority(99);
  }

  // int timer = 0;
  // int timer2 = 0;
  // boolean changeTimer = false;

  // double lastSwing = 0.0;
  // double currentSwing = 0.0;
  int resetTimer = 0;
  boolean rotationSet = false;

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();

    /**
     * This example of adding Limelight is very simple and may not be sufficient for on-field use.
     * Users typically need to provide a standard deviation that scales with the distance to target
     * and changes with number of tags available.
     *
     * This example is sufficient to show that vision integration is possible, though exact implementation
     * of how to use vision should be tuned per-robot and to the team's specification.
     */

    //  if (timer2 % 4 == 0) {
    //     lastSwing = RobotContainer.wiiRemote.getRawAxis(8);
    //  } else if (timer2 % 2 == 0) {
    //     currentSwing = RobotContainer.wiiRemote.getRawAxis(8);
    //  }

    //  timer2++;

    //  if (RobotContainer.wiiRemote.getRawAxis(8) == 1 || RobotContainer.wiiRemote.getRawAxis(8) == -1) {
    //   changeTimer = true;
    //   //RobotContainer.rotationManager = RobotContainer.wiiRemote.getRawAxis(8);
    //   if (lastSwing != 0.004 || currentSwing != 0.004) {
    //     if (lastSwing < currentSwing) {
    //       RobotContainer.rotationManager = -1;
    //     }
    //     else {
    //       RobotContainer.rotationManager = 1;
    //     }
    // }

    //  }

    //  if (timer % 10 == 0 && timer != 0) {
    //   RobotContainer.rotationManager = 0;
    //   changeTimer = false;
    //   timer = 0;
    //  }

    //  if (changeTimer) {
    //   timer++;
    //  }

    // Variables

// double lastSwing = 0.0;


// // Inside robotPeriodic or similar periodic method
// // Assuming RobotContainer.wiiRemote.getRawAxis(8) retrieves the joystick position
// double currentSwing = RobotContainer.wiiRemote.getRawAxis(8);

// // Detect a significant change in joystick position
// if (!rotationSet && Math.abs(currentSwing - lastSwing) > 0.01) {
//     // Determine the direction of the change and set rotation manager accordingly
//     if (currentSwing > lastSwing) {
//         RobotContainer.rotationManager = 1;
//     } else if (currentSwing < lastSwing) {
//         RobotContainer.rotationManager = -1;
//     }
//     rotationSet = true;
//     resetTimer = 0; // Reset the timer for resetting the rotation manager
// }

// // Update lastSwing for the next cycle
// lastSwing = currentSwing;

// // Reset rotation manager after 100ms
// if (rotationSet) {
//     resetTimer += 20; // Increment timer by the approximate period duration (20ms)
//     if (resetTimer >= 100) {
//         RobotContainer.rotationManager = 0;
//         rotationSet = false; // Allow detection of a new change
//     }
// }


    if (UseLimelight) {
      var lastResult = LimelightHelpers.getLatestResults("limelight").targetingResults;

      Pose2d llPose = lastResult.getBotPose2d_wpiBlue();

      if (lastResult.valid) {
        m_robotContainer.drivetrain.addVisionMeasurement(llPose, Timer.getFPGATimestamp());
      }
    }
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void disabledExit() {}

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {}

  @Override
  public void teleopExit() {
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void testExit() {}

  @Override
  public void simulationPeriodic() {}
}
