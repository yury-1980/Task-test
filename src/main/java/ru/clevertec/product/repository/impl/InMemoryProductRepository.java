package ru.clevertec.product.repository.impl;

import ru.clevertec.product.entity.Product;
import ru.clevertec.product.repository.ProductRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryProductRepository implements ProductRepository {

    private static final InMemoryProductRepository inMemoryProductRepository = new InMemoryProductRepository();

    private final Map<UUID, Product> productMap = new LinkedHashMap<>();

    private InMemoryProductRepository() {
    }

    private final List<UUID> uuids = List.of(UUID.fromString("b446633f-50c2-413d-a3aa-1acdcd4b7398"),
            UUID.fromString("d1b14c46-ab75-43d6-b84d-870ad67814f9"),
            UUID.fromString("c10be3df-4e08-4d5b-989b-34302ac4f254"));

    {
        productMap.put(uuids.get(0), new Product(uuids.get(0), "Яблоко", "круглое вкусное", new BigDecimal("1.1")
                , LocalDateTime.MIN));
        productMap.put(uuids.get(1), new Product(uuids.get(1), "Груша", "спелая вкусная", new BigDecimal("1.1")
                , LocalDateTime.MIN));
        productMap.put(uuids.get(2), new Product(uuids.get(2), "Вишня", "красня вкусня", new BigDecimal("1.1")
                , LocalDateTime.MIN));
    }

    public static InMemoryProductRepository getProductRepository() {
        return inMemoryProductRepository;
    }

    public Map<UUID, Product> getProductMap() {
        return new HashMap<>(productMap);
    }

    @Override
    public Optional<Product> findById(UUID uuid) {
        return Optional.ofNullable(productMap.get(uuid));
    }

    @Override
    public List<Product> findAll() {
        return productMap.values().stream().toList();
    }

    @Override
    public Product save(Product product) {
        productMap.put(product.getUuid(), product);
        return productMap.get(product.getUuid());
    }

    @Override
    public void delete(UUID uuid) {
        productMap.remove(uuid);
    }
}
