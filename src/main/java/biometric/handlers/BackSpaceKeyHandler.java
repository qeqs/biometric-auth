package biometric.handlers;

import static javafx.scene.input.KeyCode.BACK_SPACE;

import biometric.model.BiometricData;
import biometric.model.KeyBoardParameters;
import javafx.scene.input.KeyCode;

public class BackSpaceKeyHandler extends AbstractKeyHandler {

  @Override
  public boolean isApplicableFor(KeyCode key) {
    return BACK_SPACE.equals(key);
  }

  @Override
  protected void actionOnKey(BiometricData data, KeyCode key, KeyBoardParameters params) {

  }
}
