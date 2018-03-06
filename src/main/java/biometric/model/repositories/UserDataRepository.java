package biometric.model.repositories;

import biometric.model.BiometricData;
import biometric.model.UserData;
import java.util.List;
import javax.jws.soap.SOAPBinding.Use;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDataRepository extends CrudRepository<UserData, String>,
    JpaSpecificationExecutor<BiometricData> {

  UserData findByBiometricData(BiometricData biometric_data);
}
