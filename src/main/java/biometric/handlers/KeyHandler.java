package biometric.handlers;

import biometric.model.BiometricData;
import biometric.model.KeyBoardParameters;
import javafx.scene.input.KeyCode;
import org.springframework.stereotype.Component;

@Component
public interface KeyHandler {
  boolean isApplicableFor(KeyCode key);
  void handleKey(BiometricData data, KeyCode key, KeyBoardParameters params);
}
