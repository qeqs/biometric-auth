package biometric.handlers;

import static biometric.model.EventType.PRESSED;
import static biometric.model.EventType.RELEASED;
import static javafx.scene.input.KeyCode.SPACE;

import biometric.model.BiometricData;
import biometric.model.KeyBoardParameters;
import javafx.scene.input.KeyCode;

public class SpaceKeyHandler extends AbstractKeyHandler {


  @Override
  protected void actionOnKey(BiometricData data, KeyCode key, KeyBoardParameters params) {

  }

  @Override
  public boolean isApplicableFor(KeyBoardParameters params) {
    return RELEASED.equals(params.getEvent()) && SPACE.equals(params.getReleasedEvent().getCode());
  }
}
