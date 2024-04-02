// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.DriveRequestType;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.generated.TunerConstants;

public class RobotContainer {
  private double MaxSpeed = TunerConstants.kSpeedAt12VoltsMps; // kSpeedAt12VoltsMps desired top speed
  private double MaxAngularRate = 1.5 * Math.PI; // 3/4 of a rotation per second max angular velocity

  public static TalonFX shooterAngle = new TalonFX(20);
  public static TalonFX shooterPush = new TalonFX(21);
  public static TalonFX shooter1 = new TalonFX(22);
  public static TalonFX shooter2 = new TalonFX(23);
  public static TalonFX shooter3 = new TalonFX(24);
  public static TalonFX shooter4 = new TalonFX(25);

  public static TalonFX intakeAngle = new TalonFX(26);
  public static TalonFX intake1 = new TalonFX(27);
  public static TalonFX intake2 = new TalonFX(28);


  public static Command shootCommand = Eve.shoot(shooter1, shooter2, shooter3, shooter4, null, null, null);
  

  /* Setting up bindings for necessary control of the swerve drive platform */
  private final CommandXboxController joystick = new CommandXboxController(0);
  private final CommandXboxController joystick2 = new CommandXboxController(1);
  public final CommandSwerveDrivetrain drivetrain = TunerConstants.DriveTrain;

  private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
      .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1)
      .withDriveRequestType(DriveRequestType.OpenLoopVoltage);
  private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
  private final SwerveRequest.RobotCentric forwardStraight = new SwerveRequest.RobotCentric().withDriveRequestType(DriveRequestType.OpenLoopVoltage);
  private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

  /* Path follower */
  private Command runAuto = drivetrain.getAutoPath("Close");
  //private Command runAuto = drivetrain.getAutoPath("Far");

  private final Telemetry logger = new Telemetry(MaxSpeed);

  private void configureBindings() {
    drivetrain.setDefaultCommand( // Drivetrain will execute this command periodically
        drivetrain.applyRequest(() -> drive.withVelocityX(-joystick.getLeftX() * TunerConstants.kSpeedAt12VoltsMps)
            .withVelocityY(joystick.getLeftY() * TunerConstants.kSpeedAt12VoltsMps)
            .withRotationalRate(-joystick.getRightX() * MaxAngularRate)
        ).ignoringDisable(false));

    // reset the field-centric heading on y button press
    joystick.y().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldRelative()));


    joystick2.y()
    .whileTrue(
      Commands.run(
        () -> {

        }
      )
    );

    drivetrain.registerTelemetry(logger::telemeterize);
  }

  AnalogPotentiometer pot;

  public RobotContainer() {
    configureBindings();
    pot = new AnalogPotentiometer(3, 270, -64);
  }

  public Command getAutonomousCommand() {
    /* First put the drivetrain into auto run mode, then run the auto */
    //return runAuto;
    return m_chooser.getSelected();
  }

  private final SendableChooser<Command> m_chooser = new SendableChooser<>();

  public void onInit() {
		m_chooser.setDefaultOption("Close", drivetrain.getAutoPath("Close"));
		m_chooser.addOption("Close Side", drivetrain.getAutoPath("Close Alt"));
		m_chooser.addOption("Far", drivetrain.getAutoPath("Far"));

		Shuffleboard
				.getTab("Bot Settings")
				.add("Autonomous", m_chooser)
				.withSize(2, 1);
	}
}
