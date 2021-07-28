package util.controls;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;

public class Controller {
  private XboxController controller;
  private double kDeadzoneStick = 0.08;
  private double kDeadzoneTrigger = 0.1;

  public JoystickButton buttonA;
  public JoystickButton buttonX;
  public JoystickButton buttonY;
  public JoystickButton buttonB;
  public JoystickButton buttonBack;
  public JoystickButton buttonStart;

  public JoystickButton bumperLeft;
  public JoystickButton bumperRight;

  public JoystickButton joystickClickLeft;
  public JoystickButton joystickClickRight;

  public POVButton dpadUp;
  public POVButton dpadRight;
  public POVButton dpadDown;
  public POVButton dpadLeft;

  public Button triggerLeft;
  public Button triggerRight;

  public Controller(int port) {
    controller = new XboxController(port);

    buttonA = createButton(XboxController.Button.kA.value);
    buttonX = createButton(XboxController.Button.kX.value);
    buttonY = createButton(XboxController.Button.kY.value);
    buttonB = createButton(XboxController.Button.kB.value);
    buttonBack = createButton(XboxController.Button.kBack.value);
    buttonStart = createButton(XboxController.Button.kStart.value);

    bumperLeft = createButton(XboxController.Button.kBumperLeft.value);
    bumperRight = createButton(XboxController.Button.kBumperRight.value);

    joystickClickLeft = createButton(XboxController.Button.kStickLeft.value);
    joystickClickRight = createButton(XboxController.Button.kStickRight.value);

    dpadUp = new POVButton(controller, 0);
    dpadRight = new POVButton(controller, 90);
    dpadDown = new POVButton(controller, 180);
    dpadLeft = new POVButton(controller, 270);

    triggerLeft = new Button(() -> getTrigger(Hand.kLeft) > 0);
    triggerRight = new Button(() -> getTrigger(Hand.kRight) > 0);
  }

  public double getTrigger(Hand hand) {
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
