package biometric;

import biometric.handlers.KeyHandler;
import biometric.model.BiometricData;
import biometric.model.EventType;
import biometric.model.KeyBoardParameters;
import biometric.model.repositories.BiometricDataRepository;
import java.util.Date;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Controller {

  @Autowired
  BiometricDataRepository repository;

  @Autowired
  List<KeyHandler> keyHandlers;

  @Value("${application.registration-iterations}")
  private int maxIterations;

  @FXML
  private TextArea biometricReader;

  KeyBoardParameters parameters = new KeyBoardParameters();

  BiometricData biometricData = new BiometricData();


  @FXML
  private void onKeyPressed(KeyEvent event) {
    handleKey(event, EventType.PRESSED);
  }

  @FXML
  private void onKeyReleased(KeyEvent event) {
    handleKey(event, EventType.RELEASED);
  }

  @FXML
  private void onKeyTyped(KeyEvent event) {
    handleKey(event, EventType.TYPED);
  }


  private void handleKey(KeyEvent event, EventType type) {
    keyHandlers.forEach(handler -> handler.handleKey(biometricData, event, parameters));

    parameters.setTypedEvent(event);
    parameters.setTypedTime(new Date());
    parameters.setEvent(type);
  }

  private boolean isRegistrationInputCompleted(){
    return parameters.getIteration() > maxIterations;
  }

  private void clearData() {
    biometricData = new BiometricData();
  }

}
