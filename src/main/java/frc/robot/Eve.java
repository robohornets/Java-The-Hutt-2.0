package frc.robot;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest.RobotCentric;

import edu.wpi.first.math.proto.System;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

/*
                                   ......                                                 
                              ..............                                              
                           .....:::::::.......                                            
                          ...:::::-=+*****++-:.                                           
                         ..:::-*%@@@@@@@@@@@%#++=.                                        
                        ..:.:#@@@@%%@@@@@@@@@%##%@#-                                      
                       ..::=@@@%*+++*#%@@@@@%%##%%%%+                                     
                      ...:-@@@@#+====+*%@@%*+++=+*#%%=                                    
                      ..::=@@@@%#*++=+*%@%#+====+*#%%#                                    
                      ..::-*@@@@@@%%%%@@@@%#***##%@@%#                                    
                       .::--=*%@@@@@@@@@@@@@@@@@@@@@%:                                    
                         .::---=+**#%%@@@@@@@@@@%#*-.                                     
                            ..::-------------::::.....                                    
                                ...::::::::::::::..                                       
                                     ...:::::::::::::::.                                  
                         ...:::::::------:::::.....                                       
                       ..:::---::::::..........           .                               
                       ..:--===-::::::........            :-:                             
                       ...::-+++-:::::.........           :=-:.                           
                      ......:+++=:::::.........            -==-:.                         
                     .......-+++=-::::..........            :-=-:.                        
                    ........=++==-::::..........              .---:.                      
                   ........-=====-:::::.........                .:--:.                    
                  .........--====--::::.........                  .:-:.                   
                  ....:... ::-===--:::::........                    .--:                  
                 .....:... .:--==--:::::.........        .            :-:.                
                 ... .::.   .:--=---:::::........                      .:-:               
                .....::..    .:------::::........                        .-:              
                ....::::      .:-----:::::.......       .                  .:             
               ....::::.       .:-----::::.......      .                                  
               ....::::.        .::---::::........     .                                  
               ...::::.          .::--:::::.........  .                                   
               ..:::::            ..::::::::.......                                       
               .:::::.              .::::::::::...  .                                     
                .:::.  .              ..::::::::....                                      
                 ...                     ........                                         
                                                .                                         
                                                                                   .......
                                                                                ..........
                                        ............                             .........
                                      ..::--------::....           ......         ........
........................................... ..............................................
..........................................................................................
                       .....................................    ..........................
                    ......................................................................
*/
/*
 * Shoot command: angles, out
 * Feed to shooter command 
 * Ground intake: down; in; up (set po)
 * 
-[x] shooter out
-[] shooter feed
-[X] shooter angles (potentiometer and with time)
-[X] intake down
-[X] intake up
-[X] intake in
 */

public class Eve {
    public static Command shoot(TalonFX shooter1, TalonFX shooter2, TalonFX shooter3,
      TalonFX shooter4) {
            
        return Commands.sequence(
        Commands.run(
          () -> {
              shooter1.set(0.0);
              shooter2.set(0.0);
              shooter3.set(0.0);
              shooter4.set(0.0);
          }).withTimeout(0.0),
        Commands.run(
          () -> {
              shooter1.set(-0.8);
              shooter2.set(-0.8);
              shooter3.set(-0.8);
              shooter4.set(-0.8);
          }).withTimeout(3.0),
        Commands.run(
          () -> {
              shooter1.set(0.0);
              shooter2.set(0.0);
          }).withTimeout(0.0)).withName("shoot");
  }

  public static Command shooterFeed(TalonFX shooterFeed) {
            
        return Commands.sequence(
        Commands.run(
          () -> {
              shooterFeed.set(0.0);
          }).withTimeout(0.0),
        Commands.run(
          () -> {
              shooterFeed.set(0.8);
          }).withTimeout(3.0),
        Commands.run(
          () -> {
              shooterFeed.set(0.0);
          }).withTimeout(0.0));
  }

  public static Command intakeDown(TalonFX intakeAngle) {
            
        return Commands.sequence(
        Commands.run(
          () -> {
              intakeAngle.set(0.0);
          }
        ),
        Commands.run(
          () -> {
              intakeAngle.set(-0.8);
          }).withTimeout(3.0),
        Commands.run(
          () -> {
              intakeAngle.set(0.0);
          }).withTimeout(0.0)).withName("intakeDown");
  }

  public static Command intakeUp(TalonFX intakeAngle) {
            
        return Commands.sequence(
        Commands.run(
          () -> {
              intakeAngle.set(0.0);
          }
        ),
        Commands.run(
          () -> {
              intakeAngle.set(0.8);
          }),//.withTimeout(3.0),
        Commands.run(
          () -> {
              intakeAngle.set(0.0);
          }));//.withTimeout(0.0));
  }

    public static Command intakeIn(TalonFX intake1, TalonFX intake2, GenericEntry MaxShooterSpeedTop, GenericEntry MaxShooterSpeedBottom, GenericEntry AutoDriveTime) {
            
        return Commands.sequence(
        Commands.run(
          () -> {
              intake1.set(0.0);
              intake2.set(0.0);
          }).withTimeout(0.0),
        Commands.run(
          () -> {
              intake1.set(0.8);
              intake2.set(0.8);
          }).withTimeout(3.0),
        Commands.run(
          () -> {
              intake1.set(0.0);
              intake2.set(0.0);
          }).withTimeout(0.0));
  }

  public static Command shootAnglePotentiometer(TalonFX shooterAngle, Double targetAngle, AnalogPotentiometer pot) {
            
        return Commands.sequence(
        Commands.run(
          () -> {
              shooterAngle.set(0.0);
              while (pot.get() < targetAngle) {
                shooterAngle.set(0.4);
              }
          }),
         Commands.run(
                  () -> {
              shooterAngle.set(0.0);
              
          })).withName("shootAngle");
  }

  public static Command shootAngleTime(TalonFX shooterAngle) {
            
        return Commands.sequence(
        Commands.run(
          () -> {
              shooterAngle.set(0.0);
          }
        ),
        Commands.run(
          () -> {
              shooterAngle.set(-0.1);
          }).withTimeout(1.0),
        Commands.run(
          () -> {
              shooterAngle.set(0.0);
          }).withTimeout(0.0));
  }
}
