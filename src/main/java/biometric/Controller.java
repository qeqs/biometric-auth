package biometric;

import biometric.model.BiometricData;
import biometric.model.repositories.BiometricDataRepository;
import java.util.Date;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Controller {

  @Autowired
  BiometricDataRepository repository;

  @FXML
  private TextArea biometricReader;

  KeyEvent pressedEvent;
  KeyEvent releasedEvent;
  KeyEvent typedEvent;

  Date pressedTime;
  Date releasedTime;
  Date typedTime;

  BiometricData biometricData = new BiometricData();


  @FXML
  private void onKeyPressed(KeyEvent event) {
    //todo

    pressedEvent = event;
    pressedTime = new Date();
  }

  @FXML
  private void onKeyReleased(KeyEvent event) {
    //todo

    releasedEvent = event;
    releasedTime = new Date();

    biometricData.setHoldButtonTime(releasedTime.getTime() - pressedTime.getTime());
  }

  @FXML
  private void onKeyTyped(KeyEvent event) {
    //todo

    typedEvent = event;
    typedTime = new Date();

  }


  private void handleKey(KeyCode code) {
    switch (code) {
      case ENTER:
        break;
      case BACK_SPACE:
        break;
    }
  }


  private void clearData() {
    biometricData = new BiometricData();
  }

}
