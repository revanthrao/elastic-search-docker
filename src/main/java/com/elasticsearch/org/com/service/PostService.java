package com.elasticsearch.org.com.service;

import com.elasticsearch.org.com.entities.Post;
import com.elasticsearch.org.com.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.stereotype.Service;

import java.util.Iterator;


@Service
@EnableElasticsearchRepositories(basePackages="com.elasticsearch.org.com")
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post save(Post post) {
        postRepository.save(post);
        return post;
    }

    public Post findOne(String id) {
        return postRepository.findOne(id);
    }

    public Iterable<Post> findAll() {
        return postRepository.findAll();
    }

    public boolean isPostExists(String title){
        boolean isPresent = false;
        Iterator<Post> it = findAll().iterator();
        while(it.hasNext()){
            if(it.next().getTitle().equals(title)){
                isPresent = true;
            }
        }
        return isPresent;
    }
    public Page<Post> findByTagsName(String tagName, Pageable pageable) {
        return postRepository.findByTagsName(tagName, pageable);
    }

}