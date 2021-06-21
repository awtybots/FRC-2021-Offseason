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

  public Controller(int port) {
    controller = new XboxController(port);
  }

  public JoystickButton buttonA = createButton(XboxController.Button.kA.value);
  public JoystickButton buttonX = createButton(XboxController.Button.kX.value);
  public JoystickButton buttonY = createButton(XboxController.Button.kY.value);
  public JoystickButton buttonB = createButton(XboxController.Button.kB.value);
  public JoystickButton buttonBack = createButton(XboxController.Button.kBack.value);
  public JoystickButton buttonStart = createButton(XboxController.Button.kStart.value);

  public JoystickButton bumperLeft = createButton(XboxController.Button.kBumperLeft.value);
  public JoystickButton bumperRight = createButton(XboxController.Button.kBumperRight.value);

  public JoystickButton joystickClickLeft = createButton(XboxController.Button.kStickLeft.value);
  public JoystickButton joystickClickRight = createButton(XboxController.Button.kStickRight.value);

  public POVButton dpadUp = new POVButton(controller, 0);
  public POVButton dpadRight = new POVButton(controller, 90);
  public POVButton dpadDown = new POVButton(controller, 180);
  public POVButton dpadLeft = new POVButton(controller, 270);

  public Button triggerLeft = new Button(() -> getTrigger(Hand.kLeft) > 0);
  public Button triggerRight = new Button(() -> getTrigger(Hand.kRight) > 0);

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
