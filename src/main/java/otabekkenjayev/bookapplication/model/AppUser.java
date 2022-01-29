package otabekkenjayev.bookapplication.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import otabekkenjayev.bookapplication.model.template.UserRole;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 4, max = 255, message = "Minimum username length: 4 characters")
    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Size(min = 8, message = "Minimum password length: 8 characters")
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    List<UserRole> userRoles;


    @Column(updatable = false)
    @CreatedBy
    private UUID createdBy;

    @CreationTimestamp
    private Timestamp createdAt;

    @LastModifiedBy
    private UUID updatedBy;

    @UpdateTimestamp
//    @LastModifiedDate
    private Timestamp updatedAt;

}
