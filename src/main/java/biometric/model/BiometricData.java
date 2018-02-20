package biometric.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
public class BiometricData {

  @Id
  private String id;
  private double holdButtonTime;
  private double pressingFrequency;
  private double errors;

}
