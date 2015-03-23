package client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.Response;
import client.dto.Category;
import client.dto.Product;
import client.dto.Property;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by yakov_000 on 19.03.2015.
 */
public class ShopApiClient {

    private static final ObjectMapper jsonMapper = new ObjectMapper();
    private static final AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    private static final String API_URL = "http://localhost:9000";

    public static void createProperty(Property property) {

       create("/property/create.json",property);
    }

    public static void createCategory(Category category) {

        create("/category/create.json",category);
    }

    public static void createProduct(Product product) {

        create("/product/create.json",product);
    }

    private static <T> void create(String urlPrefix,T input) {

        try {
            final ListenableFuture<Response> futureResponse = asyncHttpClient.preparePost(API_URL + urlPrefix).addHeader("Content-Type", "application/json")
                    .setBody(jsonMapper.writeValueAsString(input)).execute();
            final Response response = futureResponse.get();
            System.out.println(response.getResponseBody());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
