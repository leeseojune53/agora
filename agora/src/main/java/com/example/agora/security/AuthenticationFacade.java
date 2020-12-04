package com.example.agora.security;

import com.example.agora.entity.post.Post;
import com.example.agora.exception.NoAuthorityException;
import com.example.agora.security.jwt.auth.AuthDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthenticationFacade {
    public Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public List<Post> getPosts(){
        Authentication auth = this.getAuthentication();

        if(auth.getPrincipal() instanceof AuthDetails){
            return ((AuthDetails) auth.getPrincipal()).getPost();
        }else throw new NoAuthorityException();
    }

    public Integer getUserCode(){
        Authentication auth = this.getAuthentication();
//        if(auth.getPrincipal() instanceof UserDetails){
            return ((AuthDetails) auth.getPrincipal()).getUser().getUserCode();
//        }else throw new NoAuthorityException();
    }

    public String getUserName(){
        Authentication auth = this.getAuthentication();
        if(auth.getPrincipal() instanceof AuthDetails){
            return ((AuthDetails) auth.getPrincipal()).getUsername();
        }else throw new NoAuthorityException();
    }
}
