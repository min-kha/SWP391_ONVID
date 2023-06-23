package group5.swp391.onlinelearning.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Builder
@Data
public class Learn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int learnid;

    private boolean status;

    @ManyToOne
    @JoinColumn(name = "student_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User student;

    @ManyToOne
    @JoinColumn(name = "lession_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Lesson lession;

}
