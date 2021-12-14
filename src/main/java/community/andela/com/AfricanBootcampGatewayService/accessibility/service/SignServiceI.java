package community.andela.com.AfricanBootcampGatewayService.accessibility.service;

import community.andela.com.AfricanBootcampGatewayService.accessibility.entity.User;

public interface SignServiceI {
    void createAccount(User user);

    void signIn(User user);

    void signOut(User user);
}
