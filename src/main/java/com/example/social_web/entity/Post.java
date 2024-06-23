package com.example.social_web.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String content;
    LocalDateTime publishDate;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    User user;
    boolean isDeleted;
    String imageUrl;
    @ManyToMany
    @JoinTable(
            name = "post_likes",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
   Set<User> liked = new HashSet<>();
    @OneToMany(mappedBy = "post")
    List<Comment> comments = new ArrayList<>();
}
