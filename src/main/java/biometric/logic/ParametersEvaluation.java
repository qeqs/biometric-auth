package biometric.logic;

import biometric.model.BiometricData;
import biometric.enums.EventType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ParametersEvaluation {

  private List<KeyParams> keys = new ArrayList<>();
  private List<List<KeyParams>> phrases = new ArrayList<>();

  @Value("${application.password}")
  private String passwordOrigin;

  @Value("${application.threshold}")
  private double threshold;

  public void handle(String keyString, KeyEvent key, EventType eventType) {
    Optional<KeyParams> keyOpt = find(key);
    if (keyOpt.isPresent()) {
      writeParam(keyOpt.get(), eventType);
    } else {
      KeyParams newKey = new KeyParams();
      newKey.setCode(key.getCode());
      newKey.setText(keyString);
      writeParam(newKey, eventType);
      keys.add(newKey);
    }
  }

  public BiometricData matchClosest(BiometricData sample, List<BiometricData> base) {
    List<BiometricData> data = base.stream().filter(b -> match(sample, b))
        .collect(Collectors.toList());
    Optional<BiometricData> opData = data.stream()
        .min((left, right)->
            (int)(sumParams(getParamsDiff(sample, left)) - sumParams(getParamsDiff(sample, right))));
    if(opData.isPresent())
      return opData.get();
    return null;
  }

  private double sumParams(List<Double> list){
    return list.stream().flatMapToDouble((d)-> DoubleStream.builder().add(d).build()).sum();
  }

  private boolean match(BiometricData left, BiometricData right) {

    List<Double> params = getParamsDiff(left, right);
    return params.stream().filter(param -> Math.abs(param) >= threshold).findAny().isPresent();
  }

  private List<Double> getParamsDiff(BiometricData left, BiometricData right) {
    List<Double> params = new ArrayList<>();
    params.add(right.getFullTime() - left.getFullTime());
    params.add(right.getHoldButtonTime() - left.getHoldButtonTime());
    params.add(right.getPressingFrequency() - left.getPressingFrequency());
    params.add(right.getPrintingSpeed() - left.getPrintingSpeed());
    params.add(right.getCollisionsTime() - left.getCollisionsTime());
    params.add(right.getErrors() - left.getErrors());
    return params;
  }

  public void iteration() {
    phrases.add(keys);
    keys = new ArrayList<>();
  }


  public BiometricData calculate() {
    BiometricData data = new BiometricData();

    data.setFullTime(countFullTime());
    data.setHoldButtonTime(countHoldButtonTime());
    data.setPressingFrequency(countPressingTime());
    data.setPrintingSpeed(countPrintingSpeed());
    data.setCollisionsTime(countCollisionTime());

    String actual = buildString();
    data.setErrors(countErrors(actual, passwordOrigin));

    //clear();
    return data;
  }

  public void clear() {
    keys.clear();
    phrases.clear();
  }

  public BiometricData combine(BiometricData left, BiometricData right) {
    BiometricData data = new BiometricData();
    data.setFullTime((right.getFullTime() + left.getFullTime()) / 2);
    data.setHoldButtonTime((right.getHoldButtonTime() + left.getHoldButtonTime()) / 2);
    data.setPressingFrequency((right.getPressingFrequency() + left.getPressingFrequency()) / 2);
    data.setPrintingSpeed((right.getPrintingSpeed() + left.getPrintingSpeed()) / 2);
    data.setCollisionsTime((right.getCollisionsTime() + left.getCollisionsTime()) / 2);
    data.setErrors((right.getErrors() + left.getErrors()) / 2);
    return data;
  }

  private double countErrors(String actual, String expected) {

    if (validate(actual)) {
      return 0;
    } else {
      double errors = 0;
      for (List<KeyParams> phrase : phrases) {
        for (int i = 0; i < phrase.size(); i++) {
          if (phrase.get(i).getCode().equals(KeyCode.BACK_SPACE)) {
            errors++;
          }
        }
      }
      errors /= phrases.size();

      return errors;
    }
  }

  private double countFullTime() {
    double time = 0;
    for (List<KeyParams> phrase : phrases) {
      time += phrase.get(phrase.size() - 1).getReleaseTime() - phrase.get(0).getPressedTime();
    }
    time /= phrases.size();
    return time;
  }

  private double countHoldButtonTime() {
    double time = 0;
    for (List<KeyParams> phrase : phrases) {
      double temp = 0;
      for (KeyParams key : phrase) {
        temp += key.getReleaseTime() - key.getPressedTime();
      }
      temp /= phrase.size();
      time += temp;
    }
    time /= phrases.size();
    return time;
  }

  private double countPressingTime() {
    double time = 0;
    for (List<KeyParams> phrase : phrases) {
      double temp = 0;
      for (int i = 0; i < phrase.size() - 1; i++) {
        temp += phrase.get(i + 1).getPressedTime() - phrase.get(i).getPressedTime();
      }
      temp /= phrase.size() - 1;
      time += temp;
    }
    time /= phrases.size();
    return time;
  }

  private double countCollisionTime() {
    double time = 0;
    for (List<KeyParams> phrase : phrases) {
      double temp = 0;
      int collisionsCount = 0;
      for (int i = 0; i < phrase.size() - 1; i++) {
        double collision = phrase.get(i).getReleaseTime() - phrase.get(i + 1).getPressedTime();
        if (collision > 0) {
          collisionsCount++;
          temp += collision;
        }
      }
      temp /= collisionsCount;
      time += temp;
    }
    time /= phrases.size();
    return time;
  }

  private double countPrintingSpeed() {
    double time = 0;

    for (List<KeyParams> phrase : phrases) {
      time += phrase.size() /
          (phrase.get(phrase.size() - 1).getReleaseTime() - phrase.get(0).getPressedTime());
    }
    time /= phrases.size();
    return time;
  }

  private String buildString() {
    StringBuilder builder = new StringBuilder();
    keys.forEach(key -> {
      if (key.getCode().isLetterKey()) {
        builder.append(key.getText());
      }
    });
    return builder.toString();
  }

  public boolean validate(String text) {
    return passwordOrigin.equals(text);
  }

  private boolean isWritable(KeyParams key) {
    return key.getPressedTime() == 0.0 || key.getReleaseTime() == 0.0;
  }

  private Optional<KeyParams> find(KeyEvent key) {
    return keys.stream().filter(k -> k.getCode().equals(key.getCode()) && isWritable(k)).findAny();
  }

  private void writeParam(KeyParams key, EventType eventType) {
    switch (eventType) {
      case RELEASED:
        key.setReleaseTime(new Date().getTime());
        break;
      case PRESSED:
        key.setPressedTime(new Date().getTime());
        break;
    }
  }

}
