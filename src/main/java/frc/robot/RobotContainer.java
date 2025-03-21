// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.DriveRequestType;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.generated.TunerConstants;

public class RobotContainer {
  public static ShuffleboardTab botSettingsTab = Shuffleboard
            .getTab("Bot Settings");

  private double MaxSpeed = TunerConstants.kSpeedAt12VoltsMps; // kSpeedAt12VoltsMps desired top speed
  private double MaxAngularRate = 1.5 * Math.PI; // 3/4 of a rotation per second max angular velocity

  public static TalonFX shooterAngle = new TalonFX(21);
  public static TalonFX shooterFeed = new TalonFX(20);
  public static TalonFX shooter1 = new TalonFX(22);
  public static TalonFX shooter2 = new TalonFX(23);
  public static TalonFX shooter3 = new TalonFX(24);
  public static TalonFX shooter4 = new TalonFX(25);

  public static TalonFX intakeAngle = new TalonFX(26);
  public static TalonFX intake1 = new TalonFX(27);
  // public static TalonFX intake2 = new TalonFX(28);


  public static Command shootCommand = Eve.shoot(shooter1, shooter2, shooter3, shooter4);

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

  public static ShuffleboardLayout ampList = Shuffleboard
            .getTab("Bot Settings")
            .getLayout("Amp", BuiltInLayouts.kList)
            .withSize(2, 3)
            .withPosition(4, 0);

  private void configureBindings() {
    drivetrain.setDefaultCommand( // Drivetrain will execute this command periodically
        drivetrain.applyRequest(() -> drive.withVelocityX(joystick.getLeftY() * TunerConstants.kSpeedAt12VoltsMps)
            .withVelocityY(joystick.getLeftX() * TunerConstants.kSpeedAt12VoltsMps)
            .withRotationalRate(-joystick.getRightX() * MaxAngularRate)
        ).ignoringDisable(false));

    // reset the field-centric heading on y button press
    joystick.y().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldRelative()));

    // Run Flywheels
    joystick2.rightTrigger()
    .onTrue(
      Commands.run(
          () -> {
              shooter1.set(0.0);
              shooter2.set(0.0);
              shooter3.set(0.0);
              shooter4.set(0.0);
          }
        )
    )
    .whileTrue(
      Eve.shoot(shooter1, shooter2, shooter3, shooter4)
    );

  // Push note from green wheels into the fly wheels to shoot
    joystick2.y()
    .onTrue(
      Commands.sequence(
        Commands.run(
        () -> {
          shooterFeed.set(0.0);
          }
        )
      )
      
    ).whileTrue(
      Eve.shooterFeed(shooterFeed, true)
    );

    // Run shooter feed in reverse
    joystick2.b()
    .onTrue(
      Commands.sequence(
        Commands.run(
        () -> {
          shooterFeed.set(0.0);
          }
        )
      )
      
    ).whileTrue(
      Eve.shooterFeedReverse(shooterFeed)
    );

    
    // Preset shooter angles
    joystick2.povUp()
    .onTrue(
      Commands.run(
        () -> {
          shooterAngle.set(0.0);
        }
      )
    )
    .whileTrue(
      Eve.shootAnglePotentiometer(shooterAngle, 85.0, Robot.pot)
    );

    joystick2.povDown()
    .onTrue(
      Commands.run(
        () -> {
          shooterAngle.set(0.0);
        }
      )
    )
    .whileTrue(
      Eve.shootAnglePotentiometer(shooterAngle, 144.0, Robot.pot)
    );

    joystick2.povLeft()
    .onTrue(
      Commands.run(
        () -> {
          shooterAngle.set(0.0);
        }
      )
    )
    .whileTrue(
      Eve.shootAnglePotentiometer(shooterAngle, 105.0, Robot.pot)
    );

    // joystick2.rightTrigger()
    // .onTrue(
    //   Commands.run(
    //     () -> {
    //       shooterAngle.set(0.0);
    //     }
    //   )
    // )
    // .whileTrue(
    //   //Eve.shootAngleTime(shooterAngle)
    //   Eve.shootAnglePotentiometer(shooterAngle, 130.0, Robot.pot)
    //   //Eve.shootAngleDown(shooterAngle)
    // );

    joystick2.a()
    .onTrue(
      Commands.sequence(
        Commands.run(
        () -> {
          shooterFeed.set(0.0);
          intakeAngle.set(0.0);
          intake1.set(0.0);
          shooterAngle.set(0.0);
        }
        ),
          Commands.run(
			() -> {
				Eve.intakeDown(intakeAngle);
				intake1.set(0.2);
				Eve.shooterFeed(shooterFeed, Eve.limitSwitchTripped);
				Eve.shootAnglePotentiometer(shooterAngle, 105.0, Robot.pot);
			}
		)
      )
    )
    .whileTrue(
      Eve.fullIntake(shooterFeed, intakeAngle, intake1, shooterAngle, Robot.pot)
    )
    .onFalse(
      Commands.run(
        () -> {
          shooterFeed.set(0.0);
          intakeAngle.set(0.0);
          intake1.set(0.0);
          shooterAngle.set(0.0);
        }
      )
    )
    ;

    joystick2.x()
    .onTrue(
      Commands.run(
        () -> {
          shooterFeed.set(0.0);
        }
      )
    )
    .whileTrue(
      Eve.shooterFeed(shooterFeed, false)
    )
    .onFalse(
      Commands.run(
        () -> {
          Eve.limitSwitchTripped = false;
        }
      )
    );


    

    // joystick2.b()
    // .onTrue(
    //   Commands.run(
    //       () -> {
    //           shooterFeed.set(0.0);
    //       }
    //   ).withTimeout(0.0)
    // )
    // .whileTrue(
    //   Eve.shooterFeed(shooterFeed, true)
    // );

    joystick2.leftBumper()
    .onTrue(
      Commands.run(
        () -> {
          intakeAngle.set(0.0);
        }
      )
    )
    .whileTrue(
      Eve.intakeDown(intakeAngle)
    );

    joystick2.rightBumper()
    .onTrue(
      Commands.run(
        () -> {
          intakeAngle.set(0.0);
        }
      )
    )
    // .onFalse(
    //   Commands.run(
    //     intakeAngle.
    //   )
    // )
    .whileTrue(
      Eve.intakeUp(intakeAngle)
    );

    drivetrain.registerTelemetry(logger::telemeterize);
  }


  public RobotContainer() {
    //NamedCommands.registerCommand("shoot", Eve.shoot(shooter1, shooter2, shooter3, shooter4));
    NamedCommands.registerCommand("startShoot", Eve.startShoot(shooter1, shooter2, shooter3, shooter4));
    NamedCommands.registerCommand("endShoot", Eve.endShoot(shooter1, shooter2, shooter3, shooter4));
    NamedCommands.registerCommand("intakeIn", Eve.intakeIn(intake1));
    //NamedCommands.registerCommand("shootFeed", Eve.shooterFeedAuto(shooterFeed));
    NamedCommands.registerCommand("intakeDown", Eve.intakeDown(intakeAngle).unless(null));
    NamedCommands.registerCommand("intakeUp", Eve.intakeUp(intakeAngle));
    NamedCommands.registerCommand("shooterFeed", Eve.shooterFeed(intakeAngle, false));
    NamedCommands.registerCommand("shooterFeedFlywheels", Eve.shooterFeed(intakeAngle, true));
    NamedCommands.registerCommand("shoot", Eve.shoot(shooter1, shooter2, shooter3, shooter4));
    NamedCommands.registerCommand("shootAngleIntake", Eve.shootAnglePotentiometer(shooterAngle, 105.0, Robot.pot));
    NamedCommands.registerCommand("shootNoteCenter", Eve.shootAnglePotentiometer(shooterAngle, 105.0, Robot.pot));
    NamedCommands.registerCommand("shootNoteLeft", Eve.shootAnglePotentiometer(shooterAngle, 105.0, Robot.pot));
    NamedCommands.registerCommand("shootNoteRight", Eve.shootAnglePotentiometer(shooterAngle, 105.0, Robot.pot));
    
    configureBindings();
    
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
				.add("Autonomous Options", m_chooser)
				.withSize(2, 1); 
	}
}
