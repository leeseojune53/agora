package com.example.agora.entity.post;

import com.example.agora.entity.comment.Comment;
import com.example.agora.entity.user.User;
import com.example.agora.payload.request.post.ModifyRequest;
import com.example.agora.payload.response.post.Search.SearchData;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Post")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;

    @NonNull
    @Column(length = 30)
    private String title;

    @NonNull
    @Length(max = 1000)
    private String contents;


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private User user;

    @CreationTimestamp
    private Date createAt;

    private Date modifyAt;

    @NonNull
    private int view;

    @NonNull
    private int likes;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Comment> comments;

    public Post addViews() {
        this.view += 1;
        return this;
    }

    public Post setModifyAt(ModifyRequest request){
        this.title = request.getTitle();
        this.contents = request.getContents();
        this.modifyAt = new Date();
        return this;
    }

    public Post addLike(){
        this.likes+=1;
        return this;
    }

    public SearchData myPage(){
        return new SearchData(Integer.toString(postId), title, user.getUserId(), createAt, new Date());
    }
}