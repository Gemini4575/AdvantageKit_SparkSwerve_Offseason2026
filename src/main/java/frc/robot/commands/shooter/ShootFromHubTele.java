package frc.robot.commands.shooter;

import frc.robot.Constants.ShooterConstants;
import frc.robot.Constants.ShooterRPMConstants;
import frc.robot.commands.shooter.base.TelopShootBase;
import frc.robot.subsystems.topdeck.AdvancerSubsystem;
import frc.robot.subsystems.topdeck.shooter.Shooter;

public class ShootFromHubTele extends TelopShootBase {
    public ShootFromHubTele(Shooter s, AdvancerSubsystem ds) {
        super(s, ds, ShooterRPMConstants.HUB_SHOT);
    }

}
