package ru.parus.chirp.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "gift_types")
public class GiftTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name  = "name", nullable = false)
    private String name;

    private String description;

    @Column(name = "icon_url")
    private String iconUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}