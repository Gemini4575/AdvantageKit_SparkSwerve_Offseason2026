package frc.robot.subsystems.topdeck.shooter;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Shooter extends SubsystemBase {
  static final Lock odometryLock = new ReentrantLock();
  private final ShooterColum[] shooterColums = new ShooterColum[4];

  public Shooter(
      ShooterColumIO OPshooterColumIO,
      ShooterColumIO IPshooterColimIO,
      ShooterColumIO IGShooterColumIO,
      ShooterColumIO OGShooterColumIO) {
    shooterColums[0] = new ShooterColum(OPshooterColumIO, 0);
    shooterColums[1] = new ShooterColum(IPshooterColimIO, 1);
    shooterColums[2] = new ShooterColum(IGShooterColumIO, 2);
    shooterColums[3] = new ShooterColum(OGShooterColumIO, 3);
  }

  @Override
  public void periodic() {
    odometryLock.lock();
    for (var colum : shooterColums) {
      colum.periodic();
    }
    odometryLock.unlock();

    // Stop the shooter if the robots disabled duh
    if (DriverStation.isDisabled()) {
      for (var colum : shooterColums) {
        colum.stop();
      }
    }

    // Update odometry
    double[] sampleTimestamps =
        shooterColums[0].getOdometryTimestamps(); // All signals are sampled together
    int sampleCount = sampleTimestamps.length;
    for (int i = 0; i < sampleCount; i++) {
      // Read wheel positions and deltas from each module
      double[] shooterVelocity = new double[4];
      double[] shooterPositions = new double[4];
      for (int moduleIndex = 0; moduleIndex < 4; moduleIndex++) {
        shooterVelocity[moduleIndex] = shooterColums[moduleIndex].getVelocity()[i];
        shooterPositions[moduleIndex] = shooterColums[moduleIndex].getPosition()[i];
      }
    }
  }

  /**
   * Sets the shooter velocity in RPM based off of voltage.
   *
   * @param velocity Velocity in RPM
   */
  public void runVelocity(double velocity) {
    for (int i = 0; i < 4; i++) {
      shooterColums[i].setVelocity(velocity);
    }
  }

  public void setOpenLoop(double output) {
    for (int i = 0; i < 4; i++) {
      shooterColums[i].setOpenLoop(output);
    }
  }

  public void stop() {
    for (int i = 0; i < 4; i++) {
      shooterColums[i].stop();
    }
  }
}
