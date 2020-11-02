package project.scope.am.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

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
    private LocalDate date;
    private int hour;

}
