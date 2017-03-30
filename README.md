# elastic-search-docker
* This is a post application implemented using spring-boot, elastic search and docker.
* To run the application use the following command `./gradlew build buildDocker`
* Once the application is built use the following command to expose to port `8024` to public ` docker run -p 8024:8024 -t [dockerhub username]/gs-spring-boot-docker`
* Make sure that you are logged in using dockerhub credentials. 
* Encode the url (`http://192.168.99.100:8024/api/savePost?post={%22posts%22:{%22postTitle%22:%22hello%22,%22postSummary%22:%22testSummary%22,%22tags%22:%22tag1$tag2$tag3%22}}`
* In the tag list if you are applying more than one tag use `$` as a delimiter to save multiple tags.
* You can get the list of all the posts made using the following url `http://192.168.99.100:8024/api/getPosts`
