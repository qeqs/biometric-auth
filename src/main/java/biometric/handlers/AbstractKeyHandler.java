package biometric.handlers;

import biometric.model.BiometricData;
import biometric.model.KeyBoardParameters;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public abstract class AbstractKeyHandler implements KeyHandler {

  @Override
  public void handleKey(BiometricData data, KeyEvent event, KeyBoardParameters params) {
    if (!isApplicableFor(params)) {
      return;
    }
    actionOnKey(data, event.getCode(), params);
  }

  protected double elapsedValue(double biometricParam, double newData, KeyBoardParameters parameters){
    return (biometricParam*(parameters.getIteration()-1)+newData)/parameters.getIteration();
  }

  protected abstract void actionOnKey(BiometricData data, KeyCode key, KeyBoardParameters params);

}
