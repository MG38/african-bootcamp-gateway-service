package community.andela.com.AfricanBootcampGatewayService.accessibility.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import community.andela.com.AfricanBootcampGatewayService.accessibility.auth.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="user",schema = "african_bootcamp_server")
@JsonPropertyOrder(alphabetic = true)
public class User {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "user_name", unique = true)
    private String userName;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "account_expires", nullable = false)
    private Boolean accountExpires = false;

    @Column(name = "account_locked", nullable = false)
    private Boolean accountLocked = false;

    @Column(name = "credential_expires", nullable = false)
    private Boolean credentialExpires = false;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = false;

}
