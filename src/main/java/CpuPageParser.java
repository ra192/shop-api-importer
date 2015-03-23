import client.dto.Product;

import java.util.Map;

/**
 * Created by yakov_000 on 19.03.2015.
 */
public class CpuPageParser extends BasePageParser {


    public CpuPageParser(Map<String, String> techSpecsMap) {
        super("cpu", techSpecsMap);
    }

    @Override
    protected void fillProduct(String title, Product product) {

        final int splitIndex = title.lastIndexOf(" ");

        product.displayName=title.substring(0, splitIndex);
        product.code=title.substring(splitIndex + 1);
    }
}
