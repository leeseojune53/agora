package com.example.agora.Entity.Comment;

import com.example.agora.Entity.Post.Post;
import com.example.agora.Payload.Request.Post.CommentModifyRequest;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comment")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cmtId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Post post;

    @Column(length = 200)
    @NonNull
    private String contents;

    @Column(length = 20)
    private String userId;

    private Date createAt;

    private Date modifyAt;

    private int likes;

    public Comment addLikes(){
        this.likes+=1;
        return this;
    }

    public Comment Modify(CommentModifyRequest request){
        this.contents =  request.getComment();
        this.modifyAt = new Date();
        return this;
    }
}
