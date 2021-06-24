package util;

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

  /** Use for inserting double */
  public static Interpolatable<Double> interDouble(double value) {
    return new Interpolatable<Double>(value) {
      @Override
      public Double interpolate(Double endValue, double t) {
        return (1 - t) * super.value + t * endValue;
      }
    };
  }
}
