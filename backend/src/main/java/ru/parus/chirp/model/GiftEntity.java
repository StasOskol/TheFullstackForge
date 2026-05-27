package ru.parus.chirp.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "gifts")
public class GiftEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "from_user_id", nullable = false)
    private Long fromUserId;
    
    @Column(name = "to_user_id", nullable = false)
    private Long toUserId;
    
    @Column(name = "gift_type_id", nullable = false)
    private Long giftTypeId;
    
    private String comment;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}