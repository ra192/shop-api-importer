import client.dto.Product;

import java.util.Map;

/**
 * Created by yakov_000 on 19.03.2015.
 */
public class MotherboardPageParser extends BasePageParser {


    public MotherboardPageParser(Map<String, String> techSpecsMap) {
        super("motherboard", techSpecsMap);
    }

    @Override
    protected void fillProduct(String title, Product product) {
        product.displayName=title;
        product.code=title.replace(" ","-").toLowerCase();
    }
}
