package util.controls;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;

public class Controller {
  private final XboxController controller;
  private final double kDeadzoneStick;
  private final double kDeadzoneTrigger;

  public Controller(int port) {
    this(port, 0.08, 0.1);
  }

  public Controller(int port, double stickDeadzone) {
    this(port, stickDeadzone, 0.1);
  }

  public Controller(int port, double stickDeadzone, double triggerDeadzone) {
    this.controller = new XboxController(port);
    this.kDeadzoneStick = stickDeadzone;
    this.kDeadzoneTrigger = triggerDeadzone;

    this.dpadUp = new POVButton(controller, 0);
    this.dpadRight = new POVButton(controller, 90);
    this.dpadDown = new POVButton(controller, 180);
    this.dpadLeft = new POVButton(controller, 270);
  }

  public JoystickButton buttonA = createButton(XboxController.Button.kA.value);
  public JoystickButton buttonX = createButton(XboxController.Button.kX.value);
  public JoystickButton buttonY = createButton(XboxController.Button.kY.value);
  public JoystickButton buttonB = createButton(XboxController.Button.kB.value);
  public JoystickButton buttonBack = createButton(XboxController.Button.kBack.value);
  public JoystickButton buttonStart = createButton(XboxController.Button.kStart.value);

  public JoystickButton bumperLeft = createButton(XboxController.Button.kBumperLeft.value);
  public JoystickButton bumperRight = createButton(XboxController.Button.kBumperRight.value);

  public Button triggerLeft = new Button(() -> getTrigger(Hand.kLeft) > 0);
  public Button triggerRight = new Button(() -> getTrigger(Hand.kRight) > 0);

  public JoystickButton joystickClickLeft = createButton(XboxController.Button.kStickLeft.value);
  public JoystickButton joystickClickRight = createButton(XboxController.Button.kStickRight.value);

  public POVButton dpadUp;
  public POVButton dpadRight;
  public POVButton dpadDown;
  public POVButton dpadLeft;

  private double getTrigger(Hand hand) {
    return deadzone(controller.getTriggerAxis(hand), kDeadzoneTrigger);
  }

  public double getX(Hand hand) {
    return deadzone(controller.getX(hand), kDeadzoneStick);
  }

  public double getY(Hand hand) {
    return deadzone(-controller.getY(hand), kDeadzoneStick);
  }

  // --- Utilities --- //
  private JoystickButton createButton(int buttonID) {
    return new JoystickButton(this.controller, buttonID);
  }

  private double deadzone(double x, double dz) {
    if (Math.abs(x) > dz) return (x - dz * Math.signum(x)) / (1.0 - dz);
    else return 0.0;
  }
}
