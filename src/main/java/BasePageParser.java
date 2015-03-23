import client.ShopApiClient;
import client.dto.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by yakov_000 on 19.03.2015.
 */
public abstract class BasePageParser {

    private static final String TECH_SPECS_SPLITTER = " • ";
    private static final String PRICE_SPLITTER = " грн ";
    private static final double UAH_TO_USD_INDEX = 1d / 28;

    private static final NumberFormat numberFormat = NumberFormat.getInstance();
    private final Map<String, String> techSpecsMap;
    private final String category;

    public BasePageParser(String category, Map<String, String> techSpecsMap) {
        this.category = category;
        this.techSpecsMap = techSpecsMap;
    }

    public void parsePage(String url) {

        try {
            final Document document = Jsoup.connect(url).get();
            final Elements elements = document.select("td#catalogue ul.catalog li");
            elements.forEach(el -> {
                final String title = el.select("div.title-box a").text();

                String priceText = el.select("div.price span.orng").text().split(PRICE_SPLITTER)[0];
                String imageUrl = "http://hotline.ua".concat(el.select("div.img-box img").first().attr("src"));

                final String techSpecsText = el.select("p.tech-char").text();
                final String[] techSpecs = techSpecsText.split(TECH_SPECS_SPLITTER);

                final Product product = new Product();

                try {
                    product.price = Math.floor(numberFormat.parse(priceText).doubleValue() * UAH_TO_USD_INDEX);
                } catch (ParseException e) {
                    e.printStackTrace();

                }

                product.imageUrl = imageUrl;
                product.category = category;
                product.propertyValues = Arrays.stream(techSpecs).filter(techSpecsMap::containsKey)
                        .map(techSpecsMap::get).collect(Collectors.toList());
                product.propertyValues.add(title.substring(0, title.indexOf(" ")).toLowerCase());

                fillProduct(title,product);

                if (product.price != null) ShopApiClient.createProduct(product);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    abstract protected void fillProduct(String title,Product product);
}
