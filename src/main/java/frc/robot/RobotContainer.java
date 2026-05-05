// Copyright (c) 2021-2026 Littleton Robotics
// http://github.com/Mechanical-Advantage
//
// Use of this source code is governed by a BSD
// license that can be found in the LICENSE file
// at the root directory of this project.

package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.commands.PathfindingCommand;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.commands.DriveCommands;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.GyroIO;
import frc.robot.subsystems.drive.GyroIONavX;
import frc.robot.subsystems.drive.ModuleIO;
import frc.robot.subsystems.drive.ModuleIOSim;
import frc.robot.subsystems.drive.ModuleIOSpark;
import frc.robot.subsystems.topdeck.advancer.Advancer;
import frc.robot.subsystems.topdeck.advancer.AdvancerIO;
import frc.robot.subsystems.topdeck.advancer.AdvancerIOSim;
import frc.robot.subsystems.topdeck.advancer.AdvancerIOSpark;
import frc.robot.subsystems.topdeck.advancer.AdvancerIOSparkFlex;
import frc.robot.subsystems.topdeck.advancer.AdvancerIOTalonFX;
import frc.robot.subsystems.topdeck.shooter.Shooter;
import frc.robot.subsystems.topdeck.shooter.ShooterColumIO;
import frc.robot.subsystems.topdeck.shooter.ShooterColumIOSim;
import frc.robot.subsystems.topdeck.shooter.ShooterColumIOSpark;
import org.littletonrobotics.junction.networktables.LoggedDashboardChooser;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    // Subsystems
    private final Drive drive;
    private final Shooter shooter;
    private final Advancer advancer;

    // Controller
    private final XboxController driver = new XboxController(0);
    private final Joystick operator = new Joystick(1);

    // Dashboard inputs
    private final LoggedDashboardChooser<Command> autoChooser;

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        switch (Constants.currentMode) {
            case REAL:
                // Real robot, instantiate hardware IO implementations
                drive = new Drive(
                        new GyroIONavX(),
                        new ModuleIOSpark(0),
                        new ModuleIOSpark(1),
                        new ModuleIOSpark(2),
                        new ModuleIOSpark(3));
                shooter = new Shooter(
                        new ShooterColumIOSpark(0),
                        new ShooterColumIOSpark(1),
                        new ShooterColumIOSpark(2),
                        new ShooterColumIOSpark(3));
                advancer = new Advancer(new AdvancerIOTalonFX(), new AdvancerIOSpark(), new AdvancerIOSparkFlex());
                break;

            case SIM:
                // Sim robot, instantiate physics sim IO implementations
                drive = new Drive(
                        new GyroIO() {
                        },
                        new ModuleIOSim(),
                        new ModuleIOSim(),
                        new ModuleIOSim(),
                        new ModuleIOSim());
                shooter = new Shooter(
                        new ShooterColumIOSim(0),
                        new ShooterColumIOSim(1),
                        new ShooterColumIOSim(2),
                        new ShooterColumIOSim(3));
                advancer = new Advancer(new AdvancerIOSim(), new AdvancerIOSim(), new AdvancerIOSim());
                break;

            default:
                // Replayed robot, disable IO implementations
                drive = new Drive(
                        new GyroIO() {
                        },
                        new ModuleIO() {
                        },
                        new ModuleIO() {
                        },
                        new ModuleIO() {
                        },
                        new ModuleIO() {
                        });
                shooter = new Shooter(
                        new ShooterColumIO() {
                        },
                        new ShooterColumIO() {
                        },
                        new ShooterColumIO() {
                        },
                        new ShooterColumIO() {
                        });
                advancer = new Advancer(new AdvancerIO() {
                }, new AdvancerIO() {
                }, new AdvancerIO() {
                });
                break;
        }

        // Set up auto routines
        autoChooser = new LoggedDashboardChooser<>("Auto Choices", AutoBuilder.buildAutoChooser());

        PathfindingCommand.warmupCommand().schedule();

        // Set up SysId routines
        autoChooser.addOption(
                "Drive Wheel Radius Characterization", DriveCommands.wheelRadiusCharacterization(drive));
        autoChooser.addOption(
                "Drive Simple FF Characterization", DriveCommands.feedforwardCharacterization(drive));
        autoChooser.addOption(
                "Drive SysId (Quasistatic Forward)",
                drive.sysIdQuasistatic(SysIdRoutine.Direction.kForward));
        autoChooser.addOption(
                "Drive SysId (Quasistatic Reverse)",
                drive.sysIdQuasistatic(SysIdRoutine.Direction.kReverse));
        autoChooser.addOption(
                "Drive SysId (Dynamic Forward)", drive.sysIdDynamic(SysIdRoutine.Direction.kForward));
        autoChooser.addOption(
                "Drive SysId (Dynamic Reverse)", drive.sysIdDynamic(SysIdRoutine.Direction.kReverse));
        autoChooser.addOption(
                "Advancer Talon SysId (Quasistatic Forward)",
                advancer.talonSysIdQuasistatic(SysIdRoutine.Direction.kForward));
        autoChooser.addOption(
                "Advancer Talon SysId (Quasistatic Reverse)",
                advancer.talonSysIdQuasistatic(SysIdRoutine.Direction.kReverse));
        autoChooser.addOption(
                "Advancer Talon SysId (Dynamic Forward)",
                advancer.talonSysIdDynamic(SysIdRoutine.Direction.kForward));
        autoChooser.addOption(
                "Advancer Talon SysId (Dynamic Reverse)",
                advancer.talonSysIdDynamic(SysIdRoutine.Direction.kReverse));
        autoChooser.addOption(
                "Advancer Neo SysId (Quasistatic Forward)",
                advancer.neoSysIdQuasistatic(SysIdRoutine.Direction.kForward));
        autoChooser.addOption(
                "Advancer Neo SysId (Quasistatic Reverse)",
                advancer.neoSysIdQuasistatic(SysIdRoutine.Direction.kReverse));
        autoChooser.addOption(
                "Advancer Neo SysId (Dynamic Forward)",
                advancer.neoSysIdDynamic(SysIdRoutine.Direction.kForward));
        autoChooser.addOption(
                "Advancer Neo SysId (Dynamic Reverse)",
                advancer.neoSysIdDynamic(SysIdRoutine.Direction.kReverse));
        autoChooser.addOption(
                "Advancer Roller SysId (Quasistatic Forward)",
                advancer.rollerSysIdQuasistatic(SysIdRoutine.Direction.kForward));
        autoChooser.addOption(
                "Advancer Roller SysId (Quasistatic Reverse)",
                advancer.rollerSysIdQuasistatic(SysIdRoutine.Direction.kReverse));
        autoChooser.addOption(
                "Advancer Roller SysId (Dynamic Forward)",
                advancer.rollerSysIdDynamic(SysIdRoutine.Direction.kForward));
        autoChooser.addOption(
                "Advancer Roller SysId (Dynamic Reverse)",
                advancer.rollerSysIdDynamic(SysIdRoutine.Direction.kReverse));

        // Configure the button bindings
        configureButtonBindings();
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be
     * created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
     * it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        // Default command, normal field-relative drive
        drive.setDefaultCommand(
                DriveCommands.joystickDrive(
                        drive, () -> -driver.getLeftY(), () -> -driver.getLeftX(), () -> -driver.getRightX()));

        // Switch to X pattern when X button is pressed
        new JoystickButton(driver, 3).onTrue(Commands.runOnce(drive::stopWithX, drive));

        // Reset gyro to 0 when B button is pressed
        new JoystickButton(driver, 2)
                .onTrue(
                        Commands.runOnce(
                                () -> drive.setPose(
                                        new Pose2d(drive.getPose().getTranslation(), Rotation2d.kZero)),
                                drive)
                                .ignoringDisable(true));

        // new JoystickButton(operator, 0).whileFalse(new ShootFromHubTele(shooter,
        // advancer));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        return autoChooser.get();
    }
}
