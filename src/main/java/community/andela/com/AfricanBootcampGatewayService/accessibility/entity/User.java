package community.andela.com.AfricanBootcampGatewayService.accessibility.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import community.andela.com.AfricanBootcampGatewayService.accessibility.auth.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="user",schema = "african_bootcamp_server")
@JsonPropertyOrder(alphabetic = true)
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(name = "id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @Column(name = "user_name", unique = true)
    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String userName;

    @Column(name = "first_name")
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String firstName;

    @Column(name = "last_name")
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "password", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    private String password;

    @Column(name = "account_expires", nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean accountExpires;

    @Column(name = "account_locked", nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean accountLocked;

    @Column(name = "credential_expires", nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean credentialExpires;

    @Column(name = "enabled", nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean enabled;

}
