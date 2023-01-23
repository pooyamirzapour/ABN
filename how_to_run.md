# Build and Run
Build and Run DockerFile in "abn-recipe" folder by create an image:
```
docker build -f Dockerfile -t recipe .
```
### to see images:
```
docker images
```
### to run the image:
```
docker run -p 8080:8080 recipe
```
### to see all container:
```
docker container ls
```
### to stop a container
```
docker container stop ${container ID}
```
### to remove the container
```
docker container rm ${container ID}
```

## Run
This project can be run by three ways:
1.  Run the docker image on port 8080 (or every available port)
2.	Run com.abn.recipe.AbnRecipeApplication class from your IDE.
3.	Run this command in "abn-recipe" folder by
```
 mvn package
```
and then run:
java -jar /target/abn-recipe-0.0.1-SNAPSHOT.jar

For running the application you can use **Postman**, first you need get a token to call recipe's API. By calling below API in **Postman** and use Put verb a token will be received.

```
 http://localhost:8080/authenticate/register/
```

To define ingredients use below web service in **Postman**. (It needs a valid token so in **Authorization** tab, select **Bearer Token** and put the token). Verb is Post.
```
 http://localhost:8080/ingredients
```
To define recipes use below web service in **Postman**. (It needs a valid token so in **Authorization** tab, select **Bearer Token** and put the token). Verb is Post.
```
 http://localhost:8080/recipes
```

To call search API uses below web service in **Postman**. (It needs a valid token so in **Authorization** tab, select **Bearer Token** and put the token). Verb is Get.
In **Postman** and in **Params** tab you can enter parameters such as ***page, size, includes,excludes***,...
Or you can use this form: **http://localhost:8080/recipes?page=0&size=1&includes=A1&excludes=A3&noserving=1&instruction=oven&type=VEGETARIAN**
```
 http://localhost:8080/recipes
```


H2 console **http://localhost:8086/h2**

Swagger API by this link: **http://localhost:8086/swagger-ui.html**

A Postman collection is placed in the project root (**ABN.postman_collection.json**)