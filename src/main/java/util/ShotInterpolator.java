package util;

public class ShotInterpolator implements ShotCalculator {

  private final InterpolatableTreeMap<Double> rpmMap = new InterpolatableTreeMap<>();
  private final InterpolatableTreeMap<Double> launchAngleMap = new InterpolatableTreeMap<>();

  public ShotInterpolator(double[][] tunedshots) {
    for (double[] shot : tunedshots) {
      rpmMap.set(shot[0], Interpolatable.interDouble(shot[1]));
      launchAngleMap.set(shot[0], Interpolatable.interDouble(shot[2]));
    }
  }

  public double calculateLaunchAngle(double distanceMeters) {
    return launchAngleMap.get(distanceMeters);
  }

  public double calculateRPM(double distanceMeters) {
    return rpmMap.get(distanceMeters);
  }
}
