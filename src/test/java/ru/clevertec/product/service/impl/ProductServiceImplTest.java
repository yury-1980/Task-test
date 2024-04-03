package ru.clevertec.product.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.exception.ProductNotFoundException;
import ru.clevertec.product.mapper.ProductMapper;
import ru.clevertec.product.repository.ProductRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper mapper;

    @InjectMocks
    private ProductServiceImpl productService;

    Map<UUID, Product> productMap = new LinkedHashMap<>();
    private static final String NUM = "f4f6f49d-6186-4b76-abe1-a5e4ebda233c";

    static Stream<UUID> uuid() {
        return Stream.of(UUID.fromString(NUM));
    }

    @ParameterizedTest
    @MethodSource("uuid")
    void shouldGetInfoProductDtoById(UUID uuid) {
        // given
        InfoProductDto expected = new InfoProductDto(uuid, "apple", "good", new BigDecimal("5.0"));

        doReturn(expected)
                .when(mapper).toInfoProductDto(any(Product.class));
        doReturn(Optional.of(
                        new Product(uuid, "apple", "good", new BigDecimal("5.0"), LocalDateTime.MIN)))
                .when(productRepository).findById(uuid);

        // when
        InfoProductDto actual = productService.get(uuid);

        //then
        assertEquals(expected, actual);
    }

    @Test
    public void shouldGetProductNotFound() {
        // given
        UUID uuid = UUID.fromString("f4f6f49d-6186-4b76-abe1-a5e4ebda233c");

        when(productRepository.findById(uuid))
                .thenReturn(Optional.empty());

        // then
        assertThrows(ProductNotFoundException.class, () -> productService.get(uuid));
    }

    @Test
    void shouldGetAllInfoProductDtoReturtnList() {
        // given
        List<Product> products = Arrays.asList(
                new Product(UUID.fromString("b446633f-50c2-413d-a3aa-1acdcd4b7398"), "Яблоко",
                        "круглое вкусное", new BigDecimal("1.1"), LocalDateTime.MIN),
                new Product(UUID.fromString("d1b14c46-ab75-43d6-b84d-870ad67814f9"), "Груша",
                        "спелая вкусная", new BigDecimal("1.1"), LocalDateTime.MIN));

        List<InfoProductDto> expectedDto = Arrays.asList(
                new InfoProductDto(UUID.fromString("b446633f-50c2-413d-a3aa-1acdcd4b7398"), "Яблоко",
                        "круглое вкусное", new BigDecimal("1.1")),
                new InfoProductDto(UUID.fromString("d1b14c46-ab75-43d6-b84d-870ad67814f9"), "Груша",
                        "спелая вкусная", new BigDecimal("1.1")));

        when(productRepository.findAll(productMap)).thenReturn(products);

        when(mapper.toInfoProductDto(products.get(0)))
                .thenReturn(expectedDto.get(0));
        when(mapper.toInfoProductDto(products.get(1)))
                .thenReturn(expectedDto.get(1));

        // when
        List<InfoProductDto> actualDto = productService.getAll(productMap);

        // then
        assertEquals(expectedDto, actualDto);
    }

    @Test
    void shouldCreateNewProductFromDto() {
        // given
        UUID expectedUuid = UUID.fromString("f4f6f49d-6186-4b76-abe1-a5e4ebda233c");
        Product product = new Product(expectedUuid, "apple", "good", new BigDecimal("5.0"), LocalDateTime.MIN);
        ProductDto productDto = new ProductDto("Яблоко", "круглое вкусное", new BigDecimal("1.1"));

        when(mapper.toProduct(productDto))
                .thenReturn(product);
        when(productRepository.save(product))
                .thenReturn(product);

        // when
        UUID actualUuid = productService.create(productDto);

        // then
        assertEquals(expectedUuid, actualUuid);
    }

    @Test
    void shouldUpdateNewProductFromDto() {
        // given
        UUID uuid = UUID.fromString("b446633f-50c2-413d-a3aa-1acdcd4b7398");
        ProductDto productDto = new ProductDto("Яблоко", "круглое вкусное", new BigDecimal("1.1"));
        Product product = new Product(uuid, "Яблоко", "круглое вкусное", new BigDecimal("1.1"), LocalDateTime.MIN);


        when(productRepository.findById(uuid))
                .thenReturn(Optional.of(product));
        when(productRepository.save(product))
                .thenReturn(product);
        // when
        productService.update(uuid, productDto);

        // then
        verify(productRepository).save(product);
    }

    @Test
    void delete() {

        // given
        UUID uuid = UUID.fromString("43b0e1f1-28e8-47ea-9ce3-95b23a873e50");

        // when
        productService.delete(uuid);

        // then
        verify(productRepository).delete(uuid);
    }
}