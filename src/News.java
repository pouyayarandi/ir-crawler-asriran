/**
 * Created by Pouya on 5/1/20.
 */
public class News {
    private String id, publishDate, subject, title, body, shortUrl;
    private String[] tags;

    public News(String id, String publishDate, String subject, String title, String body, String shortUrl, String[] tags) {
        this.id = id;
        this.publishDate = publishDate;
        this.subject = subject;
        this.title = title;
        this.body = body;
        this.shortUrl = shortUrl;
        this.tags = tags;
    }

    public String getId() {
        return id;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public String getSubject() {
        return subject;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public String[] getTags() {
        return tags;
    }
}