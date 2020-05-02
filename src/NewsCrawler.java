import com.sun.istack.internal.Nullable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Pouya on 5/1/20.
 */
public class NewsCrawler {

    @Nullable
    static News crawlPage(String newsPage) {
        Document document;

        try {
            document = Jsoup.connect(newsPage).get();
        } catch (IOException e) {
            return null;
        }

        String newsId = parseNewsId(document);
        String publishDate = parsePublishDate(document);
        String subject = parseSubject(document);
        String title = parseTitle(document);
        String body = parseBody(document);
        String shortUrl = parseShortUrl(document);
        String[] tags = parseTags(document);

        return new News(newsId, publishDate, subject, title, body, shortUrl, tags);
    }

    static private String parseNewsId(Document document) {
        Element element = document.getElementsByClass("news_id_c").first();
        element.select("span.news_nav_title").remove();
        return element.html().trim();
    }

    static private String parsePublishDate(Document document) {
        Element element;
        if ((element = document.getElementsByClass("news_pdate_c").first()) != null) {
            element.select("span").remove();
            return element.html().trim();
        } else {
            return "";
        }
    }

    static private String parseSubject(Document document) {
        Element element = document.getElementsByClass("news_path").first();
        return element.children().last().html().trim();
    }

    static private String parseTitle(Document document) {
        Element element = document.selectFirst("h1.title");
        return element.children().first().html().trim();
    }

    static private String parseBody(Document document) {
        Element element = document.selectFirst("div.body");
        return element.text().replace("\"", "");
    }

    static private String parseShortUrl(Document document) {
        Element element = document.selectFirst("div.short-link");
        Element shortUrlElement;
        if ((shortUrlElement = element.selectFirst("a")) != null)
            return String.format("https://%s", shortUrlElement.html().trim());
        else
            return "";
    }

    static private String[] parseTags(Document document) {
        Element element = document.selectFirst("div.tags_title");
        Elements tagElements = element.select("a");
        return tagElements.eachText().toArray(new String[tagElements.eachText().size()]);
    }

}
