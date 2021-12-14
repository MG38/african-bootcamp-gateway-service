package community.andela.com.AfricanBootcampGatewayService.accessibility.service;

import community.andela.com.AfricanBootcampGatewayService.accessibility.entity.User;
import community.andela.com.AfricanBootcampGatewayService.accessibility.repository.UserRepositoryI;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignServiceImpl implements SignServiceI {
    @Autowired
    UserRepositoryI userRepositoryI;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Single<User> createAccount(User user) {
        return  Single.create(emitter -> {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setAccountLocked(false);
            user.setAccountExpires(false);
            user.setEnabled(true);
            user.setCredentialExpires(false);
            try{
                emitter.onSuccess(userRepositoryI.save(user));
            }catch (Exception e){
                emitter.onError(e);
            }
        });
    }

    @Override
    public Single<User> signIn(User user) {
        return  Single.create(emitter -> {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setAccountLocked(false);
            user.setAccountExpires(false);
            user.setEnabled(true);
            user.setCredentialExpires(false);
            try{
                emitter.onSuccess(userRepositoryI.save(user));
            }catch (Exception e){
                emitter.onError(e);
            }
        });
    }

    @Override
    public Single<User> signOut(User user) {
        return  Single.create(emitter -> {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setAccountLocked(false);
            user.setAccountExpires(false);
            user.setEnabled(true);
            user.setCredentialExpires(false);
            try{
                emitter.onSuccess(userRepositoryI.save(user));
            }catch (Exception e){
                emitter.onError(e);
            }
        });
    }
}
