package util;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class ShotTuner implements ShotCalculator {

  private final NetworkTable glassTable;
  private final NetworkTableEntry rpm, launchAngle;

  public ShotTuner() {
    glassTable = NetworkTableInstance.getDefault().getTable("ShotTuning");
    rpm = glassTable.getEntry("target_rpm");
    launchAngle = glassTable.getEntry("target_launch_angle");
  }

  public double[] calculate(double distanceMeters) {
    final double angle = calculateLaunchAngle(distanceMeters);
    final double rpm = calculateRPM(distanceMeters);
    return new double[] {rpm, angle};
  }

  public double calculateLaunchAngle(double distanceMeters) {
    return launchAngle.getDouble(0);
  }

  public double calculateRPM(double distanceMeters) {
    return rpm.getDouble(0);
  }
}
