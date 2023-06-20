package com.group2.onvidapp.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
public class Notification {
//     notificationID: int
// message: Nvarchar(200)
// linkDirect: Varchar(200)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int notificationID;
    private String message;
    private String linkDirect;

    @ManyToOne
    @JoinColumn(name = "notification_id")
        @EqualsAndHashCode.Exclude
    @ToString.Exclude 
    private User user;
}
