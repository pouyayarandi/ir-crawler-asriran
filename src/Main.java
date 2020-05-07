import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        List<String> newsLinks;
        Scanner scanner = new Scanner(System.in);
        String fromDate, toDate, categoryId;

        System.out.print("Enter from_date (format: 1399/01/01): ");
        fromDate = scanner.next();

        System.out.print("Enter to_date (format: 1399/01/28): ");
        toDate = scanner.next();

        System.out.print("Enter category_id (mentioned in document): ");
        categoryId = scanner.next();

        String url = URLMaker.makeUrl(fromDate, toDate, categoryId);
        System.out.println("Seed url: " + url);

        ArchiveCrawler crawler = new ArchiveCrawler(url, URLMaker.baseUrl, URLMaker.baseArchiveUrl);

        try {
            newsLinks = crawler.crawlNewsLinks();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        System.out.println(String.format("%d news link(s) were found", newsLinks.size()));

        List<News> newsList = new ArrayList<>();

        for (String link: newsLinks) {
            System.out.println(link);
            News news = NewsCrawler.crawlPage(link);
            if (news != null) newsList.add(news);
        }

        Gson gson = new Gson();
        List<String> newsJsons = newsList.stream().map(gson::toJson).collect(Collectors.toList());
        String dataSet = "[" + String.join(",", newsJsons) + "]";

        try {
            PrintWriter out = new PrintWriter("dataset.json");
            out.println(dataSet);
            out.close();
            System.out.println("Data set has been saved in the dataset.json file");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
