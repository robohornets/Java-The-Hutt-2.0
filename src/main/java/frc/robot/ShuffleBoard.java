// package frc.robot;

// import java.util.Map;

// import edu.wpi.first.networktables.GenericEntry;
// import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
// import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
// import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
// import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
// import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
// import frc.robot.generated.TunerConstants;

// public class ShuffleBoard {
//     public static double defaultValue = 1.0;

//     // Bot Setting Tab
//     public static ShuffleboardTab botSettingsTab = Shuffleboard
//             .getTab("Bot Settings");

//     // Swerve List Code
//     public static ShuffleboardLayout swerveList = Shuffleboard
//             .getTab("Bot Settings")
//             .getLayout("Swerve", BuiltInLayouts.kList)
//             .withSize(2, 3)
//             .withPosition(0, 0);

//     public static GenericEntry MaxBotSpeed = botSettingsTab
//             .getLayout("Swerve")
//             .add("Max Swerve Speed", TunerConstants.kSpeedAt12VoltsMps)
//             .withWidget(BuiltInWidgets.kNumberSlider)
//             .withProperties(Map.of("min", 0.0, "max", TunerConstants.kSpeedAt12VoltsMps))
//             .getEntry();

//     public static GenericEntry forwardRateLimit = botSettingsTab
//             .getLayout("Swerve")
//             .add("Swerve Acceleration Limiter", defaultValue)
//             .withWidget(BuiltInWidgets.kNumberSlider)
//             .withProperties(Map.of("min", 0.0, "max", defaultValue * 10.0))
//             .getEntry();

//     // Shooter List Code
//     public static ShuffleboardLayout shooterList = Shuffleboard
//             .getTab("Bot Settings")
//             .getLayout("Shooter", BuiltInLayouts.kList)
//             .withSize(2, 3)
//             .withPosition(2, 0);

//     public static GenericEntry TopShooterOutSpeed = botSettingsTab
//             .getLayout("Shooter")
//             .add("Top Shooter Out Speed", -1)
//             .withWidget(BuiltInWidgets.kNumberSlider)
//             .withProperties(Map.of("min", -1.0, "max", 1.0))
//             .getEntry();

//     public static GenericEntry BottomShooterOutSpeed = botSettingsTab
//             .getLayout("Shooter")
//             .add("Bottom Shooter Out Speed", -0.5)
//             .withWidget(BuiltInWidgets.kNumberSlider)
//             .withProperties(Map.of("min", -1.0, "max", 1.0))
//             .getEntry();

//     public static GenericEntry ShooterIntakeSpeed = botSettingsTab
//             .getLayout("Shooter")
//             .add("Shooter Intake Speed", 0.5)
//             .withWidget(BuiltInWidgets.kNumberSlider)
//             .withProperties(Map.of("min", -1.0, "max", 1.0))
//             .getEntry();

//     // Amp List Code
//     public static ShuffleboardLayout ampList = Shuffleboard
//             .getTab("Bot Settings")
//             .getLayout("Amp", BuiltInLayouts.kList)
//             .withSize(2, 3)
//             .withPosition(4, 0);

//     public static GenericEntry AmpIntakeSpeed = botSettingsTab
//             .getLayout("Amp")
//             .add("Amp Intake Speed", 0.3)
//             .withWidget(BuiltInWidgets.kNumberSlider)
//             .withProperties(Map.of("min", -1.0, "max", 1.0))
//             .getEntry();

//     public static GenericEntry AmpReleaseSpeed = botSettingsTab
//             .getLayout("Amp")
//             .add("Amp Release Speed", -0.3)
//             .withWidget(BuiltInWidgets.kNumberSlider)
//             .withProperties(Map.of("min", -1.0, "max", 1.0))
//             .getEntry();

//     // Autonomous Drive List Code
//     public static ShuffleboardLayout autoDriveList = Shuffleboard
//             .getTab("Bot Settings")
//             .getLayout("Autonomous Drive", BuiltInLayouts.kList)
//             .withSize(2, 2)
//             .withPosition(6, 1);

//     public static GenericEntry AutoDriveSpeed = botSettingsTab
//             .getLayout("Autonomous Drive")
//             .add("Autonomous Forward Drive Speed", 0.5)
//             .withWidget(BuiltInWidgets.kNumberSlider)
//             .withProperties(Map.of("min", -1.0, "max", 1.0))
//             .getEntry();

//     public static GenericEntry AutoDriveTime = botSettingsTab
//             .getLayout("Autonomous Drive")
//             .add("Autonomous Forward Drive Time", 4.0)
//             .withWidget(BuiltInWidgets.kNumberSlider)
//             .withProperties(Map.of("min", 0, "max", 5.0))
//             .getEntry();

        
//     public static ShuffleboardLayout autoRotationList = Shuffleboard
//             .getTab("Bot Settings")
//             .getLayout("Autonomous Rotation", BuiltInLayouts.kList)
//             .withSize(2, 2)
//             .withPosition(8, 0);

//     public static GenericEntry AutoRotationSpeed = botSettingsTab
//             .getLayout("Autonomous Rotation")
//             .add("Autonomous Rotation Speed", 0.5)
//             .withWidget(BuiltInWidgets.kNumberSlider)
//             .withProperties(Map.of("min", -1.0, "max", 1.0))
//             .getEntry();

//     public static GenericEntry AutoRotationTime = botSettingsTab
//             .getLayout("Autonomous Rotation")
//             .add("Autonomous Rotation Time", 1.0)
//             .withWidget(BuiltInWidgets.kNumberSlider)
//             .withProperties(Map.of("min", 0.0, "max", 5.0))
//             .getEntry();
// }
