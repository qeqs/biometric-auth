package biometric.handlers;

import biometric.model.BiometricData;
import biometric.model.KeyBoardParameters;
import javafx.scene.input.KeyCode;

public abstract class AbstractKeyHandler implements KeyHandler {

  @Override
  public void handleKey(BiometricData data, KeyCode key, KeyBoardParameters params) {
    if (!isApplicableFor(key)) {
      return;
    }
    actionOnKey(data, key, params);
  }

  protected double elapsedValue(double biometricParam, double newData, KeyBoardParameters parameters){
    return (biometricParam*(parameters.getIteration()-1)+newData)/parameters.getIteration();
  }

  protected abstract void actionOnKey(BiometricData data, KeyCode key, KeyBoardParameters params);

}
