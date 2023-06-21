package group5.swp391.onlinelearning.entity;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.ToString.Exclude;

@Entity
@Data
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int courseId;

    private String courseName;
    private int status;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Topic topic;

    private String decription;

    private BigDecimal price;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<View> views;

    @ManyToMany(mappedBy = "courses")
    @EqualsAndHashCode.Exclude
    @Exclude
    private Collection<Cart> carts;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User teacher;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<Lession> lessions;
}
