package project.scope.am.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String surname;
    private String username;
    private String password;
    private String profile_picture;
    @Enumerated(value = EnumType.STRING)
    private Type type;

}
