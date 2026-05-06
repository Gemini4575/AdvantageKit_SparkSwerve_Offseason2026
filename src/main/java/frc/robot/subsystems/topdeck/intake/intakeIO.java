package frc.robot.subsystems.topdeck.intake;

import org.littletonrobotics.junction.AutoLog;

public interface intakeIO {
    @AutoLog
    public static class intakeIOInputs {
        public boolean intakeConnected = false;
        public double intakePositionRot = 0.0;
        public double intakeVelocityRPM = 0.0;
        public double intakeSupplyCurrentAmps = 0.0;
        public double intakeStatorCurrentAmps = 0.0;
        public double intakeAppliedVolts = 0.0;

        public double[] odometryTimestamps = new double[] {};
        public double[] odometryIntakePositionsRot = new double[] {};
        public double[] odometryIntakeVelocityRPM = new double[] {};

        public double intakeKrakenPositionRot = 0.0;
        public double intakeKrakenVelocityRPM = 0.0;
        public double intakeKrakenSupplyCurrentAmps = 0.0;
        public double intakeKrakenStatorCurrentAmps = 0.0;
        public double intakeKrakenAppliedVolts = 0.0;

        public double[] odometryKrakenTimestamps = new double[] {};
        public double[] odometryKrakenPositionsRot = new double[] {};
        public double[] odometryKrakenVelocityRPM = new double[] {};

    }

    /** Updates the set of loggable inputs. */
    public default void updateInputs(intakeIOInputs inputs) {
    }

    /** Run the intake motor at the specified open loop percent output. */
    public default void setIntakeOpenLoop(double output) {
    }

    /** Run the intake motor at the specified voltage. */
    public default void setIntakeVoltage(double volts) {
    }

    /**
     * Set The intake motor position in rotations. This should be used for a
     * position control loop, and the motor controller should handle the PID
     * internally. The motor controller should also handle the feedforward to
     * compensate for gravity.
     */
    public default void setIntakePosition(double positionRotations) {
    }

    /** Run the intake motor at the specified velocity. */
    public default void setIntakeVelocity(double velocityRotationsPerMin) {
    }

    /** Run the kraken motor at the specified open loop percent output. */
    public default void setKrakenOpenLoop(double output) {
    }

    /** Run the kraken motor at the specified voltage. */
    public default void setKrakenVoltage(double volts) {
    }

    /** Run the kraken motor at the specified velocity. */
    public default void setKrakenVelocity(double velocityRotationsPerMin) {
    }

}