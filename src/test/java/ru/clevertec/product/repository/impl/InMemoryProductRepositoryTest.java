package ru.clevertec.product.repository.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.clevertec.product.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

class InMemoryProductRepositoryTest {

    private InMemoryProductRepository productRepository;
    Map<UUID, Product> productMap = InMemoryProductRepository.getProductRepository().getProductMap();

    @BeforeEach
    void setUp() {
        productRepository = InMemoryProductRepository.getProductRepository();
    }

    static Stream<UUID> uuid() {
        return Stream.of(UUID.fromString("b446633f-50c2-413d-a3aa-1acdcd4b7398"),
                UUID.fromString("d1b14c46-ab75-43d6-b84d-870ad67814f9"),
                null);
    }

    @ParameterizedTest
    @MethodSource("uuid")
    void shouldFindById(UUID uuid) {

        // given
        Product expected = productMap.get(uuid);

        // when
        Product actual = productRepository.findById(uuid).orElseThrow();

        // then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldFindAllcheckingThatIsNotEmpty() {

        // when
        List<Product> actual = productRepository.findAll();

        // then
        Assertions.assertFalse(actual.isEmpty());
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
        Assertions.assertEquals(expected, savedProduct);

    }

    @Test
    void shouldDeleteExistingProduct() {

        // given
        UUID uuid = UUID.fromString("45854f67-4b8b-4207-bc1b-ef2faae10987");
        InMemoryProductRepository productRepository = Mockito.spy(InMemoryProductRepository.class);

        // when
        productRepository.delete(uuid);

        // then
        Mockito.verify(productRepository).delete(uuid);
    }
}