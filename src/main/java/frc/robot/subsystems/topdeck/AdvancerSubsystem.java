package frc.robot.subsystems.topdeck;

import frc.robot.subsystems.topdeck.shooter.Advancer;
import frc.robot.subsystems.topdeck.shooter.AdvancerIOSpark;
import frc.robot.subsystems.topdeck.shooter.AdvancerIOSparkFlex;
import frc.robot.subsystems.topdeck.shooter.AdvancerIOTalonFX;

public class AdvancerSubsystem extends Advancer {
  public AdvancerSubsystem() {
    super(new AdvancerIOTalonFX(), new AdvancerIOSpark(), new AdvancerIOSparkFlex());
  }
}
