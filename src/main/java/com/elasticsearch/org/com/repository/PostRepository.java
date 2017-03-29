package com.elasticsearch.org.com.repository;


import com.elasticsearch.org.com.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@EnableElasticsearchRepositories(basePackages = "com.elasticsearch.org.com")
public interface PostRepository extends ElasticsearchCrudRepository<Post, String> {

    Page<Post> findByTagsName(String tagName, Pageable pageable);

}