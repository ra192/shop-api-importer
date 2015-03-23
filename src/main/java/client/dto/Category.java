package client.dto;

import java.util.Arrays;
import java.util.List;

/**
* Created by yakov_000 on 19.03.2015.
*/
public class Category {
    public String name;
    public String displayName;
    public String parent;
    public List<String> properties;

    public Category(String name, String displayName, String parent, String... properties) {
        this.name = name;
        this.displayName = displayName;
        this.parent = parent;
        this.properties = Arrays.asList(properties);
    }
}
