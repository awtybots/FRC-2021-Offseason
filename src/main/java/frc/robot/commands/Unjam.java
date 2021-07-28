package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.StartEndCommand;
import static frc.robot.RobotContainer.*;

public class Unjam extends StartEndCommand {

  public Unjam() {
    super(() -> {
      s_Indexer.unjam();
      s_Tower.unjam();
    }, () -> {
      s_Indexer.stop();
      s_Tower.stop();
    }, s_Indexer, s_Tower);
  }
}
