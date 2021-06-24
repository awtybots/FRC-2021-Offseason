package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Shooter;
import frc.robot.States;
import frc.robot.States.ShooterState;
import util.ShotCalculator;

public class ShooterSubsystem extends SubsystemBase {

  private TalonFX flywheel;
  private TalonSRX hood;
  private double[] setpoints;

  private final ShotCalculator shotCalculator;

  public void initSendable(SendableBuilder builder) {
    builder.addDoubleProperty("Flywheel kP", null, (double set) -> flywheel.config_kP(0, set));
    builder.addDoubleProperty("Flywheel kI", null, (double set) -> flywheel.config_kI(0, set));
    builder.addDoubleProperty("Flywheel kD", null, (double set) -> flywheel.config_kD(0, set));
    builder.addDoubleProperty("Flywheel kF", null, (double set) -> flywheel.config_kF(0, set));

    builder.addDoubleProperty("Hood kP", null, (double set) -> hood.config_kP(0, set));
    builder.addDoubleProperty("Hood kI", null, (double set) -> hood.config_kI(0, set));
    builder.addDoubleProperty("Hood kD", null, (double set) -> hood.config_kD(0, set));
    builder.addDoubleProperty("Hood kF", null, (double set) -> hood.config_kF(0, set));
  }

  public ShooterSubsystem(ShotCalculator sCalculator) {
    shotCalculator = sCalculator;

    flywheel = new TalonFX(Shooter.flywheelMotorID);
    flywheel.configFactoryDefault();
    flywheel.setNeutralMode(NeutralMode.Coast);
    flywheel.config_kP(0, 0.0); // TODO
    flywheel.config_kI(0, 0.0);
    flywheel.config_kD(0, 0.0);
    flywheel.config_kF(0, 0.0);

    hood = new TalonSRX(Shooter.hoodMotorID);
    hood.configFactoryDefault();
    hood.setNeutralMode(NeutralMode.Brake);
    hood.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    hood.setSelectedSensorPosition(0 /* angleToHoodPosition(maxLaunchAngle) */);
    hood.config_kP(0, 0.0); // TODO
    hood.config_kI(0, 0.0);
    hood.config_kD(0, 0.0);
    hood.config_kF(0, 0.0);
  }

  private static double rpmToFlywheelVelocity(double rpm) { // flywheel RPM --> motor ticks/100ms
    rpm *= Shooter.flywheelGearRatio;
    double revsPer100ms = rpm / 60.0 / 10.0;
    return revsPer100ms * 2048.0; // 2048 ticks per rev on falcon encoder
  }

  private static double angleToHoodPosition(double launchAngle) {
    double revs = (90 - launchAngle) / 360.0; // hood angle to revs
    revs *= Shooter.hoodGearRatio;
    return revs * 4096.0; // 4096 ticks per rev on CTRE Mag Encoder
  }

  private void setFlywheelRPM(double rpm) {
    if (0 > rpm || rpm > Shooter.flywheelMaxRPM) return;
    if (rpm != 0) flywheel.set(ControlMode.Velocity, rpmToFlywheelVelocity(rpm));
    else flywheel.set(ControlMode.PercentOutput, 0);
  }

  private void setHoodLaunchAngle(double angle) {
    if (Shooter.minHoodLaunchAngle > angle || Shooter.maxHoodLaunchAngle < angle) return;
    hood.set(ControlMode.Position, angleToHoodPosition(angle));
  }

  @Override
  public void periodic() {
    switch (States.shooterState) {
      case autoTargeting:
        double[] target = shotCalculator.calculate(0); // TODO get data from limelight
        setFlywheelRPM(target[0]);
        setHoodLaunchAngle(target[1]);
        break;
      case manualShooting:
        setFlywheelRPM(setpoints[0]);
        setHoodLaunchAngle(setpoints[1]);
        break;
      case standby:
        setFlywheelRPM(0);
        setHoodLaunchAngle(Shooter.maxHoodLaunchAngle);
        break;
      case disabled:
      default:
        flywheel.set(ControlMode.PercentOutput, 0);
        hood.set(ControlMode.PercentOutput, 0);
        break;
    }
  }

  // PUBLIC METHODS

  public void setTarget(double flywheelRpm, double hoodLaunchAngle) {
    setpoints = new double[] {flywheelRpm, hoodLaunchAngle};
    States.shooterState = ShooterState.manualShooting;
  }

  public void autoTarget() {
    States.shooterState = ShooterState.autoTargeting;
  }

  public void standby() {
    States.shooterState = ShooterState.standby;
  }

  public void disable() {
    States.shooterState = ShooterState.disabled;
  }
}
