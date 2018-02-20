package biometric;

import java.util.Date;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;

public class Controller {


  @FXML
  private TextArea biometricReader;

  KeyEvent pressedEvent;
  KeyEvent releasedEvent;
  KeyEvent typedEvent;

  Date pressedTime;
  Date releasedTime;
  Date typedTime;


  @FXML
  private void onKeyPressed(KeyEvent event){
    //todo

    pressedEvent = event;
    pressedTime = new Date();
  }

  @FXML
  private void onKeyReleased(KeyEvent event){
    //todo

    releasedEvent = event;
    releasedTime = new Date();
  }

  @FXML
  private void onKeyTyped(KeyEvent event){
    //todo

    typedEvent = event;
    typedTime = new Date();
  }

}
