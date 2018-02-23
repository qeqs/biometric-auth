package biometric.handlers;

import static biometric.model.EventType.RELEASED;

import biometric.model.BiometricData;
import biometric.model.KeyBoardParameters;
import javafx.scene.input.KeyCode;

public class KeyReleaseHandler extends AbstractKeyHandler {

  @Override
  protected void actionOnKey(BiometricData data, KeyCode key, KeyBoardParameters params) {

  }

  @Override
  public boolean isApplicableFor(KeyBoardParameters params) {
    return RELEASED.equals(params.getEvent()) && params.getReleasedEvent().getCode().isLetterKey();
  }
}
