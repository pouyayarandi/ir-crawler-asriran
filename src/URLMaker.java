import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Pouya on 5/1/20.
 */
public class URLMaker {

    static String baseUrl = "https://www.asriran.com";
    static String baseArchiveUrl = "https://www.asriran.com/fa/archive";

    static String makeUrl(String fromDate, String toDate, String categoryId) {
        HashMap<String, String> params = new LinkedHashMap<>();
        List<String> paramsString = new ArrayList<>();

        params.put("service_id", "1");
        params.put("sec_id", "-1");
        params.put("rpp", "100");
        params.put("p", "1");
        params.put("cat_id", categoryId);
        params.put("from_date", fromDate);
        params.put("to_date", toDate);

        paramsString.addAll(params.entrySet().stream()
                .map(param -> String.format("%s=%s", param.getKey(), param.getValue()))
                .collect(Collectors.toList()));

        return String.format("%s?%s", URLMaker.baseArchiveUrl, String.join("&", paramsString));
    }

}
