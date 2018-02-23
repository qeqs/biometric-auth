package biometric.handlers;

import static biometric.model.EventType.PRESSED;
import static javafx.scene.input.KeyCode.BACK_SPACE;

import biometric.model.BiometricData;
import biometric.model.KeyBoardParameters;
import javafx.scene.input.KeyCode;

public class BackSpaceKeyHandler extends AbstractKeyHandler {

  @Override
  public boolean isApplicableFor(KeyBoardParameters params) {
    return PRESSED.equals(params.getEvent()) && BACK_SPACE
        .equals(params.getReleasedEvent().getCode());
  }

  @Override
  protected void actionOnKey(BiometricData data, KeyCode key, KeyBoardParameters params) {
    double error = data.getErrors();
    data.setErrors(elapsedValue(error, 1.0, params));
  }
}
