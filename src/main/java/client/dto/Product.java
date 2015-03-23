package client.dto;

import java.util.List;

/**
* Created by yakov_000 on 19.03.2015.
*/
public class Product {

    public String code;
    public String displayName;
    public Double price;
    public String imageUrl;
    public String category;
    public String description = "";
    public List<String> propertyValues;
}
