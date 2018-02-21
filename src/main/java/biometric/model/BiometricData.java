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
  private long holdButtonTime;
  private long pressingFrequency;
  private long collisionsTime;
  private long errors;

}
