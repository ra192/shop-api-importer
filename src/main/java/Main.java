import client.ShopApiClient;
import client.dto.Category;
import client.dto.Property;
import client.dto.PropertyValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by yakov_000 on 04.03.2015.
 */
public class Main {

    private static final int CPU_PAGE_COUNT = 10;
    private static final int MOTHERBOARD_PAGE_COUNT = 33;

    private static final List<Property> properties = new ArrayList<>();
    private static final Map<String, String> techSpecsMap;

    static {
        properties.add(new Property("manufacturer", "Manufacturer"
                , new PropertyValue("intel", "Intel"), new PropertyValue("amd", "AMD")
                , new PropertyValue("asrock", "AsRock"), new PropertyValue("asus", "Asus")
                , new PropertyValue("biostar", "Biostar"), new PropertyValue("elitegroup", "EliteGroup")
                , new PropertyValue("fujitsu", "Fujitsu"), new PropertyValue("gigabyte", "Gigabyte")
                , new PropertyValue("msi", "MSI"), new PropertyValue("supermicro", "Supermicro")
        ));

        properties.add(new Property("socket", "Socket",
                new PropertyValue("socket-1150", "Socket 1150"), new PropertyValue("socket-2011-v3", "Socket 2011-v3"),
                new PropertyValue("socket-2011", "Socket 2011"), new PropertyValue("socket-am3-plus", "Socket AM3+"),
                new PropertyValue("socket-am3", "Socket AM3"), new PropertyValue("socket-fm2", "Socket FM2"),
                new PropertyValue("socket-fm2-plus", "Socket FM2+")));

        properties.add(new Property("cores", "Cores",
                new PropertyValue("2-cores", "2 cores"), new PropertyValue("4-cores", "4 cores"),
                new PropertyValue("6-cores", "6 cores"), new PropertyValue("8-cores", "8 cores")));

        techSpecsMap = properties.stream().flatMap(itm -> itm.propertyValues.stream()).collect(Collectors.toMap(itm -> itm.displayName, itm -> itm.name));
    }




    public static void main(String[] args) {

        properties.forEach(ShopApiClient::createProperty);

        ShopApiClient.createCategory(new Category("pc_parts", "Computer parts", null));
        ShopApiClient.createCategory(new Category("cpu", "Processors", "pc_parts"));

        ShopApiClient.createCategory(new Category("motherboard","Motherboards","pc_parts"));

        final CpuPageParser cpuPageParser = new CpuPageParser(techSpecsMap);
        cpuPageParser.parsePage("http://hotline.ua/computer/processory/");

        for (int i = 1; i <= CPU_PAGE_COUNT; i++) {
            cpuPageParser.parsePage("http://hotline.ua/computer/processory/?p=".concat(String.valueOf(i)));
        }

        final MotherboardPageParser motherboardPageParser = new MotherboardPageParser(techSpecsMap);
        motherboardPageParser.parsePage("http://hotline.ua/computer/materinskie-platy/");
        for (int i=1;i<=MOTHERBOARD_PAGE_COUNT;i++) {
            motherboardPageParser.parsePage("http://hotline.ua/computer/materinskie-platy/?p=".concat(String.valueOf(i)));
        }
    }



}
