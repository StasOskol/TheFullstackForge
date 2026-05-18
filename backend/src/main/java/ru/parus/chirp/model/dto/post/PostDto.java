package ru.parus.chirp.model.dto.post;

import java.io.Serializable;
import lombok.Data;

@Data
public class PostDto implements Serializable {
    private String content;
    private Long userId;
}