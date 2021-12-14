package community.andela.com.AfricanBootcampGatewayService.accessibility.auth;

public enum Permission {
    READ("auth:read"),
    WRITE("auth:write"),
    UPDATE("auth:update"),
    DELETE("auth:delete"),
    CREATZOOMLINK("auth:create-zoom-link"),
    SERVICE("auth:service");

    private final String authority;

    Permission(String authority) {
        this.authority = authority;
    }
}
