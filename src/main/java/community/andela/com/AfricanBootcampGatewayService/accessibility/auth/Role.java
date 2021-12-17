package community.andela.com.AfricanBootcampGatewayService.accessibility.auth;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


public enum Role {
    LEARNER(new HashSet<>(Arrays.asList(Permission.READ, Permission.WRITE, Permission.UPDATE))),
    TUTOR(new HashSet<>(Arrays.asList(Permission.CREATE_ZOOM_LINK, Permission.READ,Permission.WRITE,Permission.UPDATE))),
    ADMIN(new HashSet<>(Arrays.asList(Permission.DELETE,Permission.CREATE_ZOOM_LINK,Permission.READ,Permission.WRITE,Permission.UPDATE))),
    SERVICE(new HashSet<>(Arrays.asList(Permission.SERVICE, Permission.DELETE,Permission.READ, Permission.WRITE, Permission.UPDATE)));

    public final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<SimpleGrantedAuthority> getRoleAuthorities(){
        var authorities = this.permissions.stream().map(permission -> new SimpleGrantedAuthority(permission.name())).collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return authorities;
    }
}
