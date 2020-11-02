package project.scope.am.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date date;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date deadline;
    @ManyToOne
    private User member;
    private int hours;

}
