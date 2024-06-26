package com.ocado.basket;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BasketSplitter {

    private Map<String, List<String>> deliveryOptions;

    public BasketSplitter(String absolutePathToConfigFile) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            this.deliveryOptions = objectMapper.readValue(new File(absolutePathToConfigFile), new TypeReference<>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, List<String>> split(List<String> basketItems) {
        Map<String, List<String>> delivery = new HashMap<>();
        Map<String, Integer> deliverOptionUsages = new HashMap<>();

        // possible delivery options
        for (List<String> options : deliveryOptions.values()) {
            for (String option : options) {
                deliverOptionUsages.put(option, 0);
            }
        }

        // assigning the item to possible delivery options
        for (String item : basketItems) {
            List<String> deliveryOptionsForItem = deliveryOptions.get(item);
            for (String option : deliveryOptionsForItem) {
                deliverOptionUsages.put(option, deliverOptionUsages.get(option) + 1);
            }
        }

        // Sort orders by the most common delivery options
        List<Map.Entry<String, Integer>> sortedDeliveryUsages = new ArrayList<>(deliverOptionUsages.entrySet());
        sortedDeliveryUsages.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        // Assigning items to delivery option by the most common delivery options
        for (String item : basketItems) {

            for (Map.Entry<String, Integer> entry : sortedDeliveryUsages) {
                String option = entry.getKey();
                if (deliveryOptions.get(item).contains(option)) {
                    if (!delivery.containsKey(option)) {
                        delivery.put(option, new ArrayList<>());
                    }
                    delivery.get(option).add(item);
                    break;
                }
            }
        }
        return delivery;
    }
}