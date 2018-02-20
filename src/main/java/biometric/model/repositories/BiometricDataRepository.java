package biometric.model.repositories;

import biometric.model.BiometricData;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BiometricDataRepository  extends CrudRepository<BiometricData, String>,
    JpaSpecificationExecutor<BiometricData> {

}
