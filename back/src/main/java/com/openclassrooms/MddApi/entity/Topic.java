package com.openclassrooms.MddApi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "topics")
@Data
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // @OneToMany(mappedBy = "topic")
    // private Set<Post> posts;

    private String name;

    private String description;
}
