package community.andela.com.AfricanBootcampGatewayService.accessibility.service;

import community.andela.com.AfricanBootcampGatewayService.accessibility.entity.User;
import io.reactivex.rxjava3.core.Single;

public interface SignInServiceI {
    /**
     * Use for user account creation.
     * @param user
     * @return Single<User>
     */
    Single<User> createAccount(User user);

    /**
     * Use for user account signing in.
     * @param user
     * @return Single<User>
     */
    Single<String> signIn(User user);

}
