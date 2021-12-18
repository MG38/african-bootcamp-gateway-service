package community.andela.com.AfricanBootcampGatewayService;

import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

public class Global {

    public static Key generateKey(String secret_key){
        return new SecretKeySpec(Base64.getDecoder().decode(secret_key),
                SignatureAlgorithm.HS256.getJcaName());
    }
}
