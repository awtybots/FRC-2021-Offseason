package util.vision;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.MedianFilter;

public class Limelight {

  public final double mountHeight;
  public final double mountAngle;
  public final double heightToTarget;
  private final MedianFilter distanceFilter = new MedianFilter(10);

  private final NetworkTable netTable = NetworkTableInstance.getDefault().getTable("limelight");

  public Limelight(double mountingHeight, double mountingAngle, double targetHeight) {
    mountAngle = mountingAngle;
    mountHeight = mountingHeight;
    heightToTarget = targetHeight - mountingHeight;
  }

  public boolean hasVisibleTarget() {
    double hasTarget = getValue(TableEntry.HasValidTargets);
    if (hasTarget == 0.0) distanceFilter.reset();
    return hasTarget == 1.0;
  }

  /**
   * @return the horizontal angle (between -29.8deg and 29.8deg) from crosshair to center of target
   */
  public double targetXOffset() {
    return getValue(TableEntry.TargetXOffset);
  }

  /**
   * @return the vertical angle (between -24.85deg and 24.85deg) from crosshair to center of target
   */
  public double targetYOffset() {
    return getValue(TableEntry.TargetYOffset);
  }

  /** @return distance in meters to target or 0 if unable to see target */
  public double distanceFromTarget() {
    if (!this.hasVisibleTarget()) {
      return 0.0;
    } else {
      return distanceFilter.calculate(heightToTarget / Math.tan(mountAngle + this.targetYOffset()));
    }
  }

  /** Set the current camera pipeline (integer from 0 to 9, inclusive) */
  public void setPipeline(int pipeline) {
    if (pipeline < 10 && pipeline > -1) setValue(TableEntry.CurrentPipeline, pipeline);
  }

  public void toggleDriverMode(boolean enabled) {
    setValue(TableEntry.OperationMode, enabled ? 1.0 : 0.0);
  }

  public void setLED(LEDMode state) {
    setValue(TableEntry.LEDMode, state.ordinal());
  }

  private double getValue(TableEntry entry) {
    return netTable.getEntry(entry.getter).getValue().getDouble();
  }

  private boolean setValue(TableEntry entry, double value) {
    if (entry.setter == "") return false;
    return netTable.getEntry(entry.setter).setDouble(value);
  }

  public enum LEDMode {
    /** Uses the default mode set in the active Pipeline */
    PipelineDefault,
    Off,
    Blinking,
    On
  }

  private enum TableEntry {
    HasValidTargets("tv"),
    TargetXOffset("tx"),
    TargetYOffset("ty"),
    TargetArea("ta"),
    TargetSkew("ts"),
    PipelineLatency("tl"),
    CurrentPipeline("getpipe", "pipeline"),
    OperationMode("camMode", "camMode"),
    LEDMode("LEDMode", "LEDMode");
    public String setter, getter;

    TableEntry(String getter, String setter) {
      this.setter = setter;
      this.getter = getter;
    }

    TableEntry(String getter) {
      this.setter = "";
      this.getter = getter;
    }
  }
}
