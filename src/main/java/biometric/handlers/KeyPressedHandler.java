package biometric.handlers;

import static biometric.model.EventType.PRESSED;

import biometric.model.BiometricData;
import biometric.model.KeyBoardParameters;
import java.util.Date;
import javafx.scene.input.KeyCode;

public class KeyPressedHandler extends AbstractKeyHandler {

  @Override
  public boolean isApplicableFor(KeyBoardParameters params) {
    return PRESSED.equals(params.getEvent()) && params.getPressedEvent().getCode().isLetterKey();
  }

  @Override
  protected void actionOnKey(BiometricData data, KeyCode key, KeyBoardParameters params) {
    Date pressedKeyTime = new Date();
    long timeBetweenPressing = pressedKeyTime.getTime() - params.getPressedTime().getTime();
    data.setPressingFrequency(elapsedValue(data.getPressingFrequency(), timeBetweenPressing, params));

    if(!params.getPressedEvent().getCode().equals(params.getReleasedEvent().getCode())){
      //calculate collisions
    }
    params.setPressedTime(pressedKeyTime);
  }
}
