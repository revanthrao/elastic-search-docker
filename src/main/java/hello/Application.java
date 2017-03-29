package hello;

import com.elasticsearch.org.com.entities.Post;
import com.elasticsearch.org.com.entities.Tag;
import com.elasticsearch.org.com.service.PostService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@SpringBootApplication
@RestController
@EnableElasticsearchRepositories
@ComponentScan(basePackages = {"com.elasticsearch.org.com"})
@EnableAutoConfiguration
public class Application {

    public static int tagCount = 1;
    public static int postCount = 1;

    @Autowired
    private PostService postService;
    @RequestMapping("/")
    public String home() {
        return "Hello Docker World";
    }

    @RequestMapping("/savePost")
    public @ResponseBody
    String savePost(@RequestParam(value="post", required=true) String posts) {
        String result = "";
        try{
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(posts);
            String postJsonString = obj.get("posts").toString();
            JSONObject jsonObject = (JSONObject) parser.parse(postJsonString);
            String tags = jsonObject.get("tags").toString();
            ArrayList<Tag> tagList = new ArrayList<>();
            if(tags.contains("$")){
                String [] tagArray = tags.split("$");
                for(int i= 0; i<= tagArray.length; i++){
                    tagCount++;
                    Tag tg = new Tag();
                    tg.setId(String.valueOf(tagCount));
                    tg.setName(tagArray[i]);
                    tagList.add(tg);
                }
            }
            else{
                tagCount++;
                Tag tg = new Tag();
                tg.setId(String.valueOf(tagCount));
                tg.setName(tg.getName());
                tagList.add(tg);
            }
            String postTitle = jsonObject.get("postTitle").toString();
            String postSummary = jsonObject.get("postSummary").toString();
            Post p = new Post();
            postCount++;
            p.setTitle(postTitle);
            p.setSummary(postSummary);
            p.setTags(tagList);
            if(!postService.isPostExists(p.getTitle())){
                postService.save(p);
                result = "Your post has been saved successfully";
            }else{
                result = "The post you have made already exists";
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("/getPosts")
    public @ResponseBody
    String getPost(){
        String result = "";

        return "";
    }


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
