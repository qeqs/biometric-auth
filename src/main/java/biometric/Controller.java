package biometric;

import biometric.logic.ParametersEvaluation;
import biometric.model.BiometricData;
import biometric.enums.EventType;
import biometric.enums.Mode;
import biometric.model.UserData;
import biometric.model.repositories.BiometricDataRepository;
import biometric.model.repositories.UserDataRepository;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Controller {

  @Autowired
  private BiometricDataRepository repository;
  @Autowired
  private UserDataRepository userRepository;

  @Value("${application.registration-iterations}")
  private int maxIterations;

  @Value("${application.password}")
  private String passwordPhrase;

  @FXML
  private Label labelPasswordPhrase;

  private int iterations;

  @Autowired
  private ParametersEvaluation evaluation;

  @FXML
  private TextArea biometricReader;

  @FXML
  private TextField loginTextField;

  @FXML
  private Button signInButton;

  @FXML
  private Button signUpButton;

  @FXML
  private Label labelIterations;

  private Mode mode = Mode.SIGN_UP;

  public void init() {
    labelPasswordPhrase.setText("Text example: " + passwordPhrase);
    updateLabelIterations(0, maxIterations);
  }

  private void updateLabelIterations(int iter, int maxIters) {
    labelIterations.setText(iter + "/" + maxIters);
  }

  @FXML
  private void onKeyPressed(KeyEvent event) {
    handleKey(event, EventType.PRESSED);
  }

  @FXML
  private void onKeyReleased(KeyEvent event) {
    handleKey(event, EventType.RELEASED);
  }

  @FXML
  private void onSignIn() {
    mode = Mode.SIGN_IN;
    loginTextField.setDisable(true);
    signUpButton.setDisable(false);
    signInButton.setDisable(true);
    iterations = 0;
    evaluation.clear();
    biometricReader.clear();
    loginTextField.clear();
    updateLabelIterations(iterations, 1);
  }

  @FXML
  private void onSignUp() {
    mode = Mode.SIGN_UP;
    loginTextField.setDisable(false);
    signInButton.setDisable(false);
    signUpButton.setDisable(true);
    iterations = 0;
    evaluation.clear();
    biometricReader.clear();
    loginTextField.clear();
    updateLabelIterations(iterations, maxIterations);
  }


  private void handleKey(KeyEvent event, EventType type) {
    evaluation.handle(getKeyString(event), event, type);

    if (evaluation.validate(biometricReader.getText())) {
      inputCompleted();
      biometricReader.clear();
    }

    if (Mode.SIGN_IN.equals(mode) && isSignInInputCompleted()) {

      BiometricData data = evaluation.calculate();
      List<BiometricData> record = repository.getAll();
      BiometricData closest = evaluation.matchClosest(data, record);
      if (closest != null) {
        UserData userData = userRepository.findByBiometricData(closest);
        evaluation.clear();
        showMessage("Hello, " + userData.getLogin(), AlertType.INFORMATION);
      } else {
        showMessage("Try again", AlertType.WARNING);
      }
      iterations = 0;
    }

    if (Mode.SIGN_UP.equals(mode) && isRegistrationInputCompleted()) {
      UserData user = new UserData();

      if (loginTextField.getText().equals("")) {
        return;
      }
      BiometricData data = evaluation.calculate();
      repository.save(data);
      user.setBiometricData(data);
      user.setLogin(loginTextField.getText());
      userRepository.save(user);
      iterations = 0;
      evaluation.clear();
      showMessage("Registration completed", AlertType.CONFIRMATION);
    }
  }

  private void showMessage(String text, AlertType type) {
    Alert alert = new Alert(type);
    alert.setContentText(text);
    alert.show();
  }

  private String getKeyString(KeyEvent event) {
    if (event.getCode().isLetterKey()) {
      return event.getText();
    }
    return "";
  }

  private boolean isRegistrationInputCompleted() {
    return iterations >= maxIterations;
  }

  private boolean isSignInInputCompleted() {
    return iterations >= 1;
  }

  private void inputCompleted() {
    iterations++;
    if(Mode.SIGN_UP.equals(mode)){
      updateLabelIterations(iterations, maxIterations);
    }
    else {
      updateLabelIterations(iterations, 1);
    }
    evaluation.iteration();
  }

}
