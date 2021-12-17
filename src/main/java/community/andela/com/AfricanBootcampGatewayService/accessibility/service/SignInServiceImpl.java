package community.andela.com.AfricanBootcampGatewayService.accessibility.service;

import community.andela.com.AfricanBootcampGatewayService.Global;
import community.andela.com.AfricanBootcampGatewayService.accessibility.entity.User;
import community.andela.com.AfricanBootcampGatewayService.accessibility.repository.UserRepositoryI;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.reactivex.rxjava3.core.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

@Service
public class SignInServiceImpl implements SignInServiceI {
    @Autowired
    UserRepositoryI userRepositoryI;

    @Autowired
    PasswordEncoder passwordEncoder;

    String secret_key = System.getenv("SECRET_KEY");

    @Override
    public Single<User> createAccount(User user) {
        return  Single.create(emitter -> {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setAccountLocked(false);
            user.setAccountExpires(false);
            user.setEnabled(true);
            user.setCredentialExpires(false);
            emitter.onSuccess(userRepositoryI.save(user));
        });
    }

    @Override
    public Single<String> signIn(User user) {
        return  Single.create(emitter -> {
            var loginUser = userRepositoryI.findUserByUserName(user.getUserName());
            Key key = Global.generateKey(secret_key);
            var jwt_token = Jwts.builder()
                    .claim("username", loginUser.getUserName())
                    .claim("firstname", loginUser.getFirstName())
                    .claim("lastname", loginUser.getLastName())
                    .claim("password", loginUser.getPassword())
                    .claim("role", "ROLE_"+loginUser.getRole().name())
                    .setId(loginUser.getId().toString())
                    .setIssuedAt(Date.from(Instant.now()))
                    .setExpiration(Date.from(Instant.now().plus(2L, ChronoUnit.DAYS)))
                    .signWith(key)
                    .compact();
            emitter.onSuccess(jwt_token);
        });
    }

}
