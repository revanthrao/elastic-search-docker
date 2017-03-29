package com.elasticsearch.org.services;

import com.elasticsearch.org.com.entities.Post;
import com.elasticsearch.org.com.entities.Tag;
import com.elasticsearch.org.com.service.PostService;
import hello.Application;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class PostServiceTest {
    @Autowired
    private PostService postService;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    @Before
    public void before() {
        elasticsearchTemplate.deleteIndex(Post.class);
        elasticsearchTemplate.createIndex(Post.class);
        elasticsearchTemplate.putMapping(Post.class);
        elasticsearchTemplate.refresh(Post.class);
    }

    @Test
    public void testFindByTagsName() throws Exception {
        Tag tag = new Tag();
        tag.setId("1");
        tag.setName("tech");
        Tag tag2 = new Tag();
        tag2.setId("2");
        tag2.setName("elasticsearch");
        Post post = new Post();
        post.setId("1");
        post.setTitle("Bigining with spring boot application and elasticsearch");
        post.setTags(Arrays.asList(tag, tag2));
        postService.save(post);
        Post post2 = new Post();
        post2.setId("1");
        post2.setTitle("Bigining with spring boot application");
        post2.setTags(Arrays.asList(tag));
        postService.save(post);
        Page<Post> posts = postService.findByTagsName("tech", new PageRequest(0, 10));
        Page<Post> posts2 = postService.findByTagsName("tech", new PageRequest(0, 10));
        Page<Post> posts3 = postService.findByTagsName("maz", new PageRequest(0, 10));
        // assertThat(posts.getTotalElements(), is(1L));
        // assertThat(posts2.getTotalElements(), is(1L));
        // assertThat(posts3.getTotalElements(), is(0L));
        Assert.assertEquals(posts.getTotalElements(), 1L);
        Assert.assertEquals(posts2.getTotalElements(), 1L);
        Assert.assertEquals(posts3.getTotalElements(), 0L);
    }

}