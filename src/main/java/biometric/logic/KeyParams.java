package biometric.logic;

import javafx.scene.input.KeyCode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class KeyParams {

  private KeyCode code;
  private double pressedTime;
  private double releaseTime;
  private String text;

}
