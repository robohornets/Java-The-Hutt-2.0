// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Optional;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  private final boolean UseLimelight = false;

  public static AddressableLED m_led = new AddressableLED(9);
  public static AddressableLEDBuffer m_ledBuffer = new AddressableLEDBuffer(512);
  public static AddressableLEDBuffer amp_ledBuffer = new AddressableLEDBuffer(512);
  public static AddressableLEDBuffer speaker_ledBuffer = new AddressableLEDBuffer(512);

  int timeCounter = 1;
  int loopNumber = 0;

  Optional<Alliance> ally = DriverStation.getAlliance();

  @Override
  public void robotInit() {
    m_robotContainer = new RobotContainer();

    m_robotContainer.drivetrain.getDaqThread().setThreadPriority(99);

    m_led.setLength(m_ledBuffer.getLength());

    for (var i = 0; i < m_ledBuffer.getLength(); i++) {
      m_ledBuffer.setRGB(i, ledArrangements.hornet1209[i][0], ledArrangements.hornet1209[i][1], ledArrangements.hornet1209[i][2]);
    }

    m_led.setData(m_ledBuffer);
    m_led.start();

    m_robotContainer.onInit();
  }
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
  public void autonomousPeriodic() {
    if (timeCounter % 50 == 0) {
      m_led.setLength(m_ledBuffer.getLength());

      for (var i = 0; i < m_ledBuffer.getLength(); i++) {
        // Sets the specified LED to the RGB values for red
        m_ledBuffer.setRGB(i, theHutt.javaTheHutt[loopNumber][i][0], theHutt.javaTheHutt[loopNumber][i][1], theHutt.javaTheHutt[loopNumber][i][2]);
      }

      m_led.setData(m_ledBuffer);

      m_led.start();

      if (loopNumber < 2) {
        loopNumber += 1;
      } else {
        loopNumber = 0;
      }
    }
    
    timeCounter++;
  }

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {
    if (timeCounter % 200 == 0) {
      m_led.setLength(m_ledBuffer.getLength());

      if (loopNumber == 4) {
        //if (alliance == "red") {
        if (ally.get() == Alliance.Red) {
          for (var i = 0; i < m_ledBuffer.getLength(); i++) {
            // Sets the specified LED to the RGB values for red
            m_ledBuffer.setRGB(i, ledArrangements.redAlliance[i][0], ledArrangements.redAlliance[i][1], ledArrangements.redAlliance[i][2]);
          }
        } else {
          for (var i = 0; i < m_ledBuffer.getLength(); i++) {
            // Sets the specified LED to the RGB values for red
            m_ledBuffer.setRGB(i, ledArrangements.blueAlliance[i][0], ledArrangements.blueAlliance[i][1], ledArrangements.blueAlliance[i][2]);
          }
        } 
      } else {
        for (var i = 0; i < m_ledBuffer.getLength(); i++) {
          // Sets the specified LED to the RGB values for red
          m_ledBuffer.setRGB(i, ledArrangements.cycleArray[loopNumber][i][0], ledArrangements.cycleArray[loopNumber][i][1], ledArrangements.cycleArray[loopNumber][i][2]);
        }
      }

      m_led.setData(m_ledBuffer);

      m_led.start();

      if (loopNumber < 4) {
        loopNumber += 1;
      } else {
        loopNumber = 0;
      }
    }
    
    timeCounter++;
  }

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
