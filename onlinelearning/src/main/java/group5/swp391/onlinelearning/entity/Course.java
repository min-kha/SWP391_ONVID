package group5.swp391.onlinelearning.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private int status; // draff = -1; waiting/submited = 0; inprogress = 1; rejected = 2; approved = 3;
    private String description;
    private String imageLink;
    private BigDecimal price;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Topic topic;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<View> views;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<CourseReview> courseReviews;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<Feedback> feedbacks;

    @ManyToMany(mappedBy = "courses")
    @EqualsAndHashCode.Exclude
    private Collection<Cart> carts;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User teacher;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<Lesson> lessons;
}
