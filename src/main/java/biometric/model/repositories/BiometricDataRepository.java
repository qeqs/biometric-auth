package biometric.model.repositories;

import biometric.model.BiometricData;
import biometric.model.UserData;
import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BiometricDataRepository  extends CrudRepository<BiometricData, String>,
    JpaSpecificationExecutor<BiometricData> {
  @Query("FROM BiometricData")
  List<BiometricData> getAll();
}
