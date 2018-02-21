package biometric.model;

import java.util.Date;
import javafx.scene.input.KeyEvent;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class KeyBoardParameters {
  private KeyEvent pressedEvent;
  private KeyEvent releasedEvent;
  private KeyEvent typedEvent;

  private Date pressedTime;
  private Date releasedTime;
  private Date typedTime;

  private int iteration;
}
