# Aggregator

Aggregator is a web application which aggregates content from different sources
in one location for easy viewing.
It was built with <a href="https://spring.io/guides/gs/spring-boot">Spring Boot</a> and <a href="https://spring.io/guides/gs/maven/">Maven</a>.

## Building and running
You can build a jar file:
````
git clone https://github.com/pupsk86/aggregator.git
cd spring-petclinic
./mvnw package
java -jar target/*.jar
````
Or you can run it from Maven directly:
 ````
 git clone https://github.com/pupsk86/aggregator.git
 cd spring-petclinic
 ./mvnw spring-boot:run
 ````
After running you can access the application here: http://localhost:8080/

## Components

### Subscriptions
Subscription is an abstraction of content source.
You can manage your subscriptions here: http://localhost:8080/subscriptions
###### Subscription settings:
* Title - subscription title
* Reindex delay (ms) - fixed delay in millisecond for scheduling reindexing task
* Content provider - one of the available content providers 

### Content providers
Content provider is an abstraction of the content extraction algorithm.
Parameters depends on concrete provider realisation.

#### Built-in providers
There are some quite useful built-in providers in the application. 

##### _RSS reader_
It uses <a href="https://rometools.github.io/rome/">ROME Java framework for RSS and Atom feeds</a> under the hood.
###### Parameters:
* Url - RSS Feed URL
###### Examples:
````
habr.com
- Url: "https://habr.com/ru/rss/all/all/?fl=en"
````

##### _Universal html parser_
It uses <a href="https://jsoup.org/">jsoup Java library</a> for parsing html.
So you can use a <a href="https://www.w3.org/TR/2009/PR-css3-selectors-20091215/">CSS</a>
or <a href="https://jquery.com">jQuery</a> like selector syntax to find matching elements.
###### Parameters:
* Url - web page URL
* Article selector - selector for article element
* Link selector - selector for link (relative to article element)
* Title selector - selector for title (relative to article element)
* Description selector - selector for description (relative to article element)
###### Examples:
````
stackoverflow.com
- Url: "https://stackoverflow.com/questions"
- Article selector: ".question-summary"
- Link selector: ".summary h3 a"
- Title selector: ".summary h3"
- Description selector: ".summary .excerpt"

e1.ru
- Url: "https://www.e1.ru/news/"
- Article selector: "article.K7atv"
- Link selector: "a"
- Title selector: "h2 a"
- Description selector: "p a"
```` 
#### Custom providers
New providers can be easily added to the application.
All you need to do is implement ContentProvider interface and register it like spring @Component. 

````
public interface ContentProvider {

    /**
     * Returns a human-readable name of this provider.
     */
    public String getName();

    /**
     * Returns a Unique id which will be stored in db and used for matching later.
     */
    public String getGuid();

    /**
     * Returns a hash table with parameters description where key is parameter id
     * and value is human-readable parameter name.
     */
    public Map<String, String> getParametersDescription();

    /**
     * Returns a collection of ContentItemDto objects which will be stored in db.
     */
    public List<ContentItemDto> getContent(Map<String, String> parameters);
}
````


