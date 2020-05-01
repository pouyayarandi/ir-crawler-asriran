import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<String> newsLinks;
        String url = URLMaker.makeUrl("1399/01/01", "1399/01/28", "6");
        System.out.println(url);

        ArchiveCrawler crawler = new ArchiveCrawler(url, URLMaker.baseUrl, URLMaker.baseArchiveUrl);

        try {
            newsLinks = crawler.crawlNewsLinks();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        List<News> newsList = new ArrayList<>();

        for (String link: newsLinks) {
            System.out.println(link);
            News news = NewsCrawler.crawlPage(link);
            if (news != null) newsList.add(news);
        }

        for (News news: newsList) {
            System.out.println(news.getTitle());
        }

    }
}
