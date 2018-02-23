package biometric.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor
public class BiometricData {

  @Id
  private String id;
  private double holdButtonTime;
  private double pressingFrequency;
  private double collisionsTime;
  private double errors;

}
