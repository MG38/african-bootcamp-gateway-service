package community.andela.com.AfricanBootcampGatewayService.accessibility.service;

import community.andela.com.AfricanBootcampGatewayService.Global;
import community.andela.com.AfricanBootcampGatewayService.accessibility.auth.Role;
import community.andela.com.AfricanBootcampGatewayService.accessibility.entity.User;
import community.andela.com.AfricanBootcampGatewayService.accessibility.repository.UserRepositoryI;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.reactivex.rxjava3.core.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;


@Service
public class SignInServiceImpl implements SignInServiceI {
    @Autowired
    UserRepositoryI userRepositoryI;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public Single<User> createAccount(User user) {
        return  Single.just(createAccountHandler(user));
    }

    private User createAccountHandler(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAccountLocked(false);
        user.setAccountExpires(false);
        user.setEnabled(true);
        user.setCredentialExpires(false);
        return userRepositoryI.save(user);
    }

    @Override
    public Single<String> signIn(String username) {
        return  Single.just(signInHandler(username));
    }

    private String signInHandler(String username) {
        var loginUser = userRepositoryI.findUserByUserName(username);
        var jwt_builder =  Jwts.builder()
                .claim("username", loginUser.getUserName())
                .claim("firstname", loginUser.getFirstName())
                .claim("lastname", loginUser.getLastName())
                .claim("password", loginUser.getPassword())
                .claim("role", "ROLE_"+loginUser.getRole().name())
                .setId(loginUser.getId().toString())
                .setIssuedAt(Date.from(Instant.now()));
        if(loginUser.getRole().equals(Role.SERVICE)){//service jwt should not get expired and be signed with a unique key
             return jwt_builder
                    .signWith(Global.generateKey("asdfSFS34wfsdfsdfSSDS32dfsddDDerQSNCK34SOWEK5354fdgdg5"))
                    .compact();

        }else{ //other roles jwt token should have 2 days expiration
              return jwt_builder
                      .setExpiration(Date.from(Instant.now().plus(2L, ChronoUnit.DAYS)))
                      .signWith(Global.generateKey("asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4"))
                      .compact();

        }

    }

}
