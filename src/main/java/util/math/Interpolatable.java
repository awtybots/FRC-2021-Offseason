package util.math;

public abstract class Interpolatable<T> {
  private final T value;

  public Interpolatable(T value) {
    this.value = value;
  }

  public T get() {
    return this.value;
  }

  public abstract T interpolate(T endValue, double t);

  public T interpolate(Interpolatable<T> endValue, double t) {
    return interpolate(endValue.value, t);
  }

  public static Interpolatable<Double> interpolatableDouble(double value) {
    return new Interpolatable<Double>(value) {
      @Override
      public Double interpolate(Double endValue, double t) {
        return super.value + t * (endValue - super.value);
      }
    };
  }

  public static Interpolatable<Shot> interpolatableShot(Shot shot) {
    return new Interpolatable<Shot>(new Shot(shot.rpm, shot.launchAngle)) {
      @Override
      public Shot interpolate(Shot endValue, double t) {
        return new Shot(
            super.value.rpm + t * (endValue.rpm - super.value.rpm),
            super.value.launchAngle + t * (endValue.launchAngle - super.value.launchAngle));
      }
    };
  }
}
