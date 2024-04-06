import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ocado.basket.BasketSplitter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BasketSplitterTest {

    private BasketSplitter basketSplitter;
    @BeforeEach
    public void setUp() {
        basketSplitter = new BasketSplitter("F:\\GitHub\\Delivery_Manager\\Delivery_Manager\\src\\test\\java\\config.json");
    }

    @Test
    public void singleItemTest() {
        List<String> basket = new ArrayList<>();
        basket.add("Cocoa Butter");
        String[] solution = {"[Next day shipping]", "[Mailbox delivery]", "[In-store pick-up]", "[Parcel locker]", "[Courier]", "[Same day delivery]"};

        Map<String, List<String>> result = basketSplitter.split(basket);

        Assertions.assertEquals(1, result.size());
        String tmp = result.keySet().toString();
        Assertions.assertTrue(Arrays.asList(solution).contains(tmp));
    }

    @Test
    public void singleDeliveryTest() {
        String[] items = {"Cookies Oatmeal Raisin","Shrimp - 21/25, Peel And Deviened","Flour - Buckwheat, Dark","Sauce - Salsa"};
        String solution = "Pick-up point";

        Map<String, List<String>> result = basketSplitter.split(Arrays.asList(items));

        Assertions.assertEquals(1, result.size());
        Assertions.assertTrue(result.containsKey(solution));
    }

    @Test
    public void haveAllItemsBasket1() {
        List<String> basket1 = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            basket1 = objectMapper.readValue(new File("F:\\GitHub\\Delivery_Manager\\Delivery_Manager\\src\\test\\java\\basket-1.json"), new TypeReference<>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, List<String>> result = basketSplitter.split(basket1);

        int tmp = 0;

        for (List<String> values : result.values()) {
            tmp += values.size();
        }

        Assertions.assertEquals(6, tmp);
    }

    @Test
    public void basket1Test() {
        List<String> basket1 = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            basket1 = objectMapper.readValue(new File("F:\\GitHub\\Delivery_Manager\\Delivery_Manager\\src\\test\\java\\basket-1.json"), new TypeReference<>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, List<String>> result = basketSplitter.split(basket1);

        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void basket2Test() {
        List<String> basket2 = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            basket2 = objectMapper.readValue(new File("F:\\GitHub\\Delivery_Manager\\Delivery_Manager\\src\\test\\java\\basket-2.json"), new TypeReference<>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, List<String>> result = basketSplitter.split(basket2);

        Assertions.assertEquals(4, result.size());
    }
}