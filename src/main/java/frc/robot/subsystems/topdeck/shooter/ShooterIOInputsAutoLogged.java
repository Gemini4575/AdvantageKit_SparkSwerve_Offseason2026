package frc.robot.subsystems.topdeck.shooter;

import org.littletonrobotics.junction.inputs.LoggableInputs;

public class ShooterIOInputsAutoLogged extends ShooterColumIO.ShooterIOInputs implements LoggableInputs, Cloneable {
    @Override
    public void toLog(org.littletonrobotics.junction.LogTable table) {
        table.put("ShooterConnected", shooterConnected);
        table.put("ShooterVelocityRotPerSec", shooterVelocityRotPerSec);
        table.put("ShooterPositionRad", shooterPositionRad);
        table.put("ShooterAppliedVolts", shooterAppliedVolts);
        table.put("ShooterCurrentAmps", shooterCurrentAmps);
        table.put("OdometryTimestamps", odometryTimestamps);
        table.put("OdometryShooterPositionsRad", odometryShooterPositionsRad);
        table.put("OdometryShooterVelocityRot", odometryShooterVelocityRot);
    }

    @Override
    public void fromLog(org.littletonrobotics.junction.LogTable table) {
        shooterConnected = table.get("ShooterConnected", shooterConnected);
        shooterVelocityRotPerSec = table.get("ShooterVelocityRotPerSec", shooterVelocityRotPerSec);
        shooterPositionRad = table.get("ShooterPositionRad", shooterPositionRad);
        shooterAppliedVolts = table.get("ShooterAppliedVolts", shooterAppliedVolts);
        shooterCurrentAmps = table.get("ShooterCurrentAmps", shooterCurrentAmps);
        odometryTimestamps = table.get("OdometryTimestamps", odometryTimestamps);
        odometryShooterPositionsRad = table.get("OdometryShooterPositionsRad", odometryShooterPositionsRad);
        odometryShooterVelocityRot = table.get("OdometryShooterVelocityRot", odometryShooterVelocityRot);
    }

    public ShooterIOInputsAutoLogged clone() {
        ShooterIOInputsAutoLogged copy = new ShooterIOInputsAutoLogged();
        copy.shooterConnected = this.shooterConnected;
        copy.shooterVelocityRotPerSec = this.shooterVelocityRotPerSec;
        copy.shooterPositionRad = this.shooterPositionRad;
        copy.shooterAppliedVolts = this.shooterAppliedVolts;
        copy.shooterCurrentAmps = this.shooterCurrentAmps;
        copy.odometryTimestamps = this.odometryTimestamps.clone();
        copy.odometryShooterPositionsRad = this.odometryShooterPositionsRad.clone();
        copy.odometryShooterVelocityRot = this.odometryShooterVelocityRot.clone();
        return copy;
    }
}
