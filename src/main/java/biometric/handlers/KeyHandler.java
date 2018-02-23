package biometric.handlers;

import biometric.model.BiometricData;
import biometric.model.EventType;
import biometric.model.KeyBoardParameters;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.springframework.stereotype.Component;

@Component("prototype")
public interface KeyHandler {

  boolean isApplicableFor(KeyBoardParameters params);

  void handleKey(BiometricData data, KeyEvent key, KeyBoardParameters params);
}
