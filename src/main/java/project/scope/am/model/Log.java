package project.scope.am.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "log")
public class Log {

    @Id
    @GeneratedValue
    private int id;
    @ManyToOne
    private Project project;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date date;
    private int hour;

}
