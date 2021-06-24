package util;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShotTuner implements ShotCalculator, Sendable {

  private double targetRPM;
  private double targetLaunchAngle;

  public ShotTuner() {
    SmartDashboard.putData("Shot Tuner", this);
  }

  public void initSendable(SendableBuilder builder) {
    builder.addDoubleProperty("Target RPM", null, (double rpm) -> this.targetRPM = rpm);
    builder.addDoubleProperty(
        "Target Launch Angle", null, (double angle) -> this.targetLaunchAngle = angle);
  }

  public double calculateLaunchAngle(double distanceMeters) {
    return targetLaunchAngle;
  }

  public double calculateRPM(double distanceMeters) {
    return targetRPM;
  }
}
