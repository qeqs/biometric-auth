package biometric.handlers;

import static biometric.model.EventType.PRESSED;
import static javafx.scene.input.KeyCode.ENTER;

import biometric.model.BiometricData;
import biometric.model.KeyBoardParameters;
import javafx.scene.input.KeyCode;

public class EnterKeyHandler extends AbstractKeyHandler {

  @Override
  public boolean isApplicableFor(KeyBoardParameters params) {
    return PRESSED.equals(params.getEvent()) && ENTER.equals(params.getPressedEvent().getCode());
  }

  @Override
  protected void actionOnKey(BiometricData data, KeyCode key, KeyBoardParameters params) {
    params.setIteration(params.getIteration() + 1);
  }
}
