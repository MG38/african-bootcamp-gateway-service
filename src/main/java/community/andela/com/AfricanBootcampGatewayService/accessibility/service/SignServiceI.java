package community.andela.com.AfricanBootcampGatewayService.accessibility.service;

import community.andela.com.AfricanBootcampGatewayService.accessibility.entity.User;
import io.reactivex.rxjava3.core.Single;

public interface SignServiceI {
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
    Single<User> signIn(User user);

    /**
     * use for user account signing out.
     * @param user
     * @return
     */
    Single<User> signOut(User user);
}
