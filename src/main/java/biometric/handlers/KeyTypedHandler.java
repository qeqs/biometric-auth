package biometric.handlers;

import static biometric.model.EventType.PRESSED;
import static biometric.model.EventType.TYPED;

import biometric.model.BiometricData;
import biometric.model.KeyBoardParameters;
import javafx.scene.input.KeyCode;

public class KeyTypedHandler extends AbstractKeyHandler {


  @Override
  protected void actionOnKey(BiometricData data, KeyCode key, KeyBoardParameters params) {

  }

  @Override
  public boolean isApplicableFor(KeyBoardParameters params) {
    return TYPED.equals(params.getEvent()) && params.getTypedEvent().getCode().isLetterKey();
  }
}
