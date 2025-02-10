package com.mng.couriertracking.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "store_entries")
@Getter
@Setter
public class StoreEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String courierId;

    @Column(nullable = false)
    private String storeName;

    @Column(nullable = false)
    private LocalDateTime entryTime;

}

