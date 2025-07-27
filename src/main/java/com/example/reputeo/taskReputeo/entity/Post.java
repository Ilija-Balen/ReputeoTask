package com.example.reputeo.taskReputeo.entity;

import com.example.reputeo.taskReputeo.utils.PostType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue
    private Long id;
    @Column(length = 3000)
    private String content;
    private LocalDateTime createdAt;
    private String createdByName;
    private String url;

    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private PostType type;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @Override
    public String toString(){
        return  "Post content:" + this.content + " Created at: " + createdAt;
    }
}
