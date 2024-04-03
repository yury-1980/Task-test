package ru.clevertec.product.repository.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.clevertec.product.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;

class InMemoryProductRepositoryTest {

    private InMemoryProductRepository productRepository;
    private static Map<UUID, Product> productMap;

    @BeforeAll
    public static void init() {
        List<UUID> uuids = List.of(UUID.fromString("b446633f-50c2-413d-a3aa-1acdcd4b7398"),
                                   UUID.fromString("d1b14c46-ab75-43d6-b84d-870ad67814f9"),
                                   UUID.fromString("c10be3df-4e08-4d5b-989b-34302ac4f254"));
        productMap = new LinkedHashMap<>();

        productMap.put(uuids.get(0), new Product(uuids.get(0), "Яблоко", "круглое вкусное", new BigDecimal("1.1")
                , LocalDateTime.MIN));
        productMap.put(uuids.get(1), new Product(uuids.get(1), "Груша", "спелая вкусная", new BigDecimal("1.1")
                , LocalDateTime.MIN));
        productMap.put(uuids.get(2), new Product(uuids.get(2), "Вишня", "красня вкусня", new BigDecimal("1.1")
                , LocalDateTime.MIN));
    }

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
    }

    @ParameterizedTest
    @MethodSource("uuid")
    void shouldFindById(UUID uuid) {

        // given
        Product expected = productMap.get(uuid);

        // when
        Optional<Product> actual = productRepository.findById(uuid);

        // then
        assertFalse(actual.isPresent());
    }

    @Test
    void shouldFindAllcheckingThatIsNotEmpty() {

        // when
        List<Product> actual = productRepository.findAll(productMap);

        // then
        assertFalse(actual.isEmpty());
    }

    @Test
    void shouldSaveValidProductReturnsSavedProduct() {

        // given
        UUID uuid = UUID.fromString("45854f67-4b8b-4207-bc1b-ef2faae10987");
        Product expected = new Product(uuid, "Банан", "жёлтый вкусный", new BigDecimal("1.1")
                , LocalDateTime.MIN);

        // when
        Product savedProduct = productRepository.save(expected);

        // then
        assertEquals(expected, savedProduct);

    }

    @Test
    void shouldDeleteExistingProduct() {

        // given
        UUID uuid = UUID.fromString("45854f67-4b8b-4207-bc1b-ef2faae10987");
        InMemoryProductRepository productRepository = Mockito.spy(new InMemoryProductRepository());

        // when
        productRepository.delete(uuid);

        // then
        verify(productRepository).delete(uuid);
    }

    static Stream<UUID> uuid() {
        return Stream.of(UUID.fromString("b446633f-50c2-413d-a3aa-1acdcd4b7398"),
                         UUID.fromString("d1b14c46-ab75-43d6-b84d-870ad67814f9"));
    }
}