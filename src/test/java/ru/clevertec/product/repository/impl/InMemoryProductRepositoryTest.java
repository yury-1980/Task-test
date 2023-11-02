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
import java.util.UUID;
import java.util.stream.Stream;

class InMemoryProductRepositoryTest {

    private InMemoryProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
    }

    static Stream<String> uuid() {
        return Stream.of("b446633f-50c2-413d-a3aa-1acdcd4b7398",
                "d1b14c46-ab75-43d6-b84d-870ad67814f9",
                "c10be3df-4e08-4d5b-989b-34302ac4f254");
    }

    @ParameterizedTest
    @MethodSource("uuid")
    void shouldFindById(String uuid) {

        // given
        Product expected = new Product(UUID.fromString(uuid), "apple", "good", new BigDecimal("1.1")
                , LocalDateTime.MIN);

        // when
        Product actual = productRepository.findById(UUID.fromString(uuid)).orElseThrow();

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
        UUID uuid = UUID.fromString(uuid().findFirst().get());
        Product expected = new Product(uuid, "apple", "good", new BigDecimal("1.1"), LocalDateTime.MIN);

        // when
        Product savedProduct = productRepository.save(expected);

        // then
        Assertions.assertEquals(expected, savedProduct);

    }

    @Test
    void shouldDeleteExistingProduct() {

        // given
        UUID uuid = UUID.fromString(uuid().findFirst().get());
        InMemoryProductRepository productRepository = Mockito.spy(InMemoryProductRepository.class);

        // when
        productRepository.delete(uuid);

        // then
        Mockito.verify(productRepository).delete(uuid);
    }
}