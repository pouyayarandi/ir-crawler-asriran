import com.sun.istack.internal.Nullable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Pouya on 5/1/20.
 */
public class ArchiveCrawler {

    String seedUrl;
    String baseUrl;
    String archiveBaseUrl;

    public ArchiveCrawler(String seedUrl, String baseUrl, String archiveBaseUrl) {
        this.seedUrl = seedUrl;
        this.baseUrl = baseUrl;
        this.archiveBaseUrl = archiveBaseUrl;
    }

    public List<String> crawlNewsLinks() throws IOException {
        String nextUrl;
        List<String> newsLinks = new ArrayList<>();

        Document document = Jsoup.connect(seedUrl).get();
        newsLinks.addAll(parseNewsLinks(document));

        while ((nextUrl = nextPageUrl(document)) != null) {
            document = Jsoup.connect(nextUrl).get();
            newsLinks.addAll(parseNewsLinks(document));
        }

        System.out.println(newsLinks.size());
        return newsLinks;
    }

    private List<String> parseNewsLinks(Document document) {
        return document.getElementsByClass("inner-content").select("a.title5").eachAttr("href").stream()
                .map(url -> baseUrl + url).collect(Collectors.toList());
    }

    @Nullable
    private String nextPageUrl(Document document) {
        Element element = document.selectFirst("a.next");
        return element != null ? archiveBaseUrl + element.attributes().get("href") : null;
    }
}
