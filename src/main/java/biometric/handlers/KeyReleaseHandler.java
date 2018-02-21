package biometric.handlers;

import biometric.model.BiometricData;
import biometric.model.KeyBoardParameters;
import javafx.scene.input.KeyCode;

public class KeyReleaseHandler extends AbstractKeyHandler {

  @Override
  public boolean isApplicableFor(KeyCode key) {
    return false;
  }

  @Override
  protected void actionOnKey(BiometricData data, KeyCode key, KeyBoardParameters params) {

  }
}
