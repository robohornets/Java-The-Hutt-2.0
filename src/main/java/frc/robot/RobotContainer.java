// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.DriveRequestType;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.generated.TunerConstants;
import frc.robot.utilities.WiiRemote;

public class RobotContainer {
  private double MaxSpeed = TunerConstants.kSpeedAt12VoltsMps; // kSpeedAt12VoltsMps desired top speed
  private double MaxAngularRate = 1.5 * Math.PI; // 3/4 of a rotation per second max angular velocity

  /* Setting up bindings for necessary control of the swerve drive platform */
  public final CommandSwerveDrivetrain drivetrain = TunerConstants.DriveTrain;

  private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
      .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
      .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // I want field-centric
                                                               // driving in open loop
  private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
  private final SwerveRequest.RobotCentric forwardStraight = new SwerveRequest.RobotCentric().withDriveRequestType(DriveRequestType.OpenLoopVoltage);
  private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

  /* Path follower */
  // private Command runAuto = drivetrain.getAutoPath("Close");
  private Command runAuto = drivetrain.getAutoPath("Far");

  private final Telemetry logger = new Telemetry(MaxSpeed);

  public static Joystick driver;
  public static Joystick operator;
  
  public static JoystickButton redButton;
  public static JoystickButton blueButton;
  public static JoystickButton yellowButton;
  public static JoystickButton greenButton;
  public static WiiRemote wiiRemote = new WiiRemote(0);



  public static double rotationManager = 0.0;




  public RobotContainer() {
    

    

    drivetrain.setDefaultCommand( // Drivetrain will execute this command periodically
        drivetrain.applyRequest(() -> drive.withVelocityX(wiiRemote.getRawAxis(4) * MaxSpeed)
                      .withVelocityY(wiiRemote.getRawAxis(0) * MaxSpeed)
                      .withRotationalRate(-wiiRemote.getRawAxis(8) * MaxAngularRate))
        .ignoringDisable(false));

   // operator = new Joystick(1);

    
    
    // redButton = new JoystickButton(operator, 2);
    // blueButton = new JoystickButton(operator, 3);
    // greenButton = new JoystickButton(operator, 1);
    // yellowButton = new JoystickButton(operator, 4);
    drivetrain.registerTelemetry(logger::telemeterize);

  }

  public Command getAutonomousCommand() {
    /* First put the drivetrain into auto run mode, then run the auto */
    return runAuto;
  }
}
