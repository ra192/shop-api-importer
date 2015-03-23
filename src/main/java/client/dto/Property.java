package client.dto;

import java.util.Arrays;
import java.util.List;

/**
 * Created by yakov_000 on 19.03.2015.
 */
public class Property {

    public String name;
    public String displayName;
    public List<PropertyValue> propertyValues;

    public Property(String name, String displayName, PropertyValue... propertyValues) {
        this.name = name;
        this.displayName = displayName;
        this.propertyValues = Arrays.asList(propertyValues);
    }
}
