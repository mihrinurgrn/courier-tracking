package com.mng.couriertracking.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "store")
@Getter
@Setter
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;
}
