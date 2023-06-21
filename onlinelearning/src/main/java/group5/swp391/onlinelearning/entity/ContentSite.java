package group5.swp391.onlinelearning.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table
@Data
@Builder

public class ContentSite {
    @Id
    @GeneratedValue
    private int webContentId;
    private String title;
    private String image;

    @ManyToOne
    @JoinColumn(name = "staff_Id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User staff;
}
