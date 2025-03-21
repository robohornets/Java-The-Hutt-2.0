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
  public static void print(String content) {
    java.lang.System.out.println(content);
  }

  // Shooting Commands
  public static Command shoot(TalonFX shooter1, TalonFX shooter2, TalonFX shooter3,
    TalonFX shooter4) {  
    return Commands.sequence(
      Commands.run(
        () -> {
          	shooter1.set(Variables.shooterSpeed);
			shooter2.set(-Variables.shooterSpeed);
			shooter3.set(-Variables.shooterSpeed);
			shooter4.set(Variables.shooterSpeed);
        }
      )
    ).withName("shoot");
  }

  public static Command startShoot(TalonFX shooter1, TalonFX shooter2, TalonFX shooter3,
      TalonFX shooter4) {
    return Commands.sequence(
        Commands.run(
        () -> {
            shooter1.set(Variables.shooterSpeed);
            shooter2.set(-Variables.shooterSpeed);
            shooter3.set(-Variables.shooterSpeed);
            shooter4.set(Variables.shooterSpeed);
            print("Starting Shooter");
        }
	).withTimeout(0.1));
  }

public static Command endShoot(TalonFX shooter1, TalonFX shooter2, TalonFX shooter3, TalonFX shooter4) {
    return Commands.sequence(
        Commands.run(
        () -> {
            shooter1.set(0.0);
            shooter2.set(0.0);
            shooter3.set(0.0);
            shooter4.set(0.0);
            print("Ending Shooter");
        }
	).withTimeout(0.1));
}

public static Command fullIntake(TalonFX shooterFeed, TalonFX intakeAngle, TalonFX intake1, TalonFX shooterAngle, AnalogPotentiometer pot) {
	return Commands.sequence(
		Commands.run(
			() -> {
				intakeDown(intakeAngle);
			}
		),
		Commands.run(
			()-> {
				intakeIn(intake1);
			}
		)
	);
}

public static Command shooterFeedAuto(TalonFX shooterFeed) {
    return Commands.sequence(
        Commands.run(
          	() -> {
              	shooterFeed.set(0.0);
          	}
		).withTimeout(0.0),
        Commands.run(
          () -> {
            print("Feeding to shooter");
            shooterFeed.set(0.8);
          }
		).withTimeout(1.0),
        Commands.run(
        	() -> {
            	shooterFeed.set(0.0);
        	}
		).withTimeout(0.0)
	).withName("shootFeed");
}

// intake in until note hits green
// switch override true 
public static boolean limitSwitchTripped = false;
  public static Command shooterFeed(TalonFX shooterFeed, boolean overrideSwitch) { 
    return Commands.sequence(
      Commands.run(
        () -> {
          if(overrideSwitch) {
            limitSwitchTripped = true;
          }

			if(Robot.limitSwitch.get() || limitSwitchTripped) {
            	shooterFeed.set(0.25);
			}
			else {
				shooterFeed.set(0.0);
			}
        }
      )
    );
  }

  public static Command shooterFeedReverse(TalonFX shooterFeed) { 
    return Commands.sequence(
      Commands.run(
        () -> {
            shooterFeed.set(-0.25);
        }
      )
    );
  }

  // Intake Commands
  	// public static Command intakeDown(TalonFX intakeAngle) {   
	// 	return Commands.sequence(
	// 		Commands.run(
	// 			() -> {
	// 			intakeAngle.set(-0.2);
	// 			}
	// 		)
	// 	).withName("intakeDown");
	// }

	public static Command intakeDown(TalonFX intakeAngle) { 
    return Commands.sequence(
      Commands.run(
        () -> {
			if(Robot.limitSwitch.get() || Robot.intakeDownLimit.get()) {
            	intakeAngle.set(-0.2);
			}
			else {
				intakeAngle.set(0.0);
			}
        }
      )
    );
  }

//   public static Command intakeUp(TalonFX intakeAngle) {      
//     return Commands.sequence(  
//       Commands.run(
//         () -> {
//           intakeAngle.set(0.3);
//         }
//       )
//     );
//   }

public static Command intakeUp(TalonFX intakeAngle) { 
    return Commands.sequence(
      Commands.run(
        () -> {
			if(Robot.limitSwitch.get() || Robot.intakeUpLimit.get()) {
            	intakeAngle.set(0.3);
			}
			else {
				intakeAngle.set(0.0);
			}
        }
      )
    );
  }

  

  public static Command intakeIn(TalonFX intake1) { 
    return Commands.sequence(
      Commands.run(
        () -> {
          intake1.set(0.3);
        }
      )
    );
  }

  // Shooting Angles
  public static Command shootAnglePotentiometer(TalonFX shooterAngle, Double targetAngle, AnalogPotentiometer pot) {
    return Commands.sequence(
      Commands.run(
        () -> {
          if(Math.abs(pot.get()-targetAngle) <= 3) {
				      shooterAngle.set(0.0);
			      }
            else {
              if(pot.get()-targetAngle >= 0 && pot.get() > 85) {
                shooterAngle.set(-0.25);
              } else if(pot.get()-targetAngle >= 0 && pot.get() < 140) {
                shooterAngle.set(0.25);
              }
            }
        }
      )
    ).withName("shootAngle");
  }

  public static Command shootAngle(TalonFX shooterAngle) {
    return Commands.sequence(
      Commands.run(
        () -> {
            shooterAngle.set(0.1);
        }
      )
    ).withName("shootAngle");
  }

  public static Command shootAngleDown(TalonFX shooterAngle) {
    return Commands.sequence(
      Commands.run(
        () -> {
          shooterAngle.set(-0.1);
        }
      )
    ).withName("shootAngle");
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
        }
      ).withTimeout(1.0),
      Commands.run(
        () -> {
          shooterAngle.set(0.0);
        }
      ).withTimeout(0.0)
    );
  }
}
