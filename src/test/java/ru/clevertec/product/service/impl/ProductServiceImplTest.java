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
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper mapper;

    @InjectMocks
    private ProductServiceImpl productService;

    static Stream<UUID> uuid() {
        return Stream.of(UUID.fromString("f4f6f49d-6186-4b76-abe1-a5e4ebda233c")
                , null);
    }

    @ParameterizedTest
    @MethodSource("uuid")
    void shouldGetInfoProductDtoById(UUID uuid) {
        // given
        InfoProductDto expected = new InfoProductDto(uuid, "apple", "good", new BigDecimal("5.0"));

        Mockito.doReturn(expected)
                .when(mapper).toInfoProductDto(any(Product.class));
        Mockito.doReturn(Optional.of(
                        new Product(uuid, "apple", "good", new BigDecimal("5.0"), LocalDateTime.MIN)))
                .when(productRepository).findById(uuid);

        // when
        InfoProductDto actual = productService.get(uuid);

        //then
        Assertions.assertEquals(expected, actual);

    }

    @Test
    public void shouldGetProductNotFound() {
        // given
        UUID uuid = UUID.fromString("f4f6f49d-6186-4b76-abe1-a5e4ebda233c");

        Mockito.when(productRepository.findById(uuid))
                .thenReturn(Optional.empty());

        // then
        Assertions.assertThrows(ProductNotFoundException.class, () -> productService.get(uuid));
    }

    @Test
    void shouldGetAllInfoProductDtoReturtnList() {
        // given
        UUID uuid = UUID.fromString("f4f6f49d-6186-4b76-abe1-a5e4ebda233c");
        List<Product> products = Arrays.asList(new Product(), new Product());
        List<InfoProductDto> expectedDto = Arrays.asList(
                new InfoProductDto(uuid, "apple", "good", new BigDecimal("5.0"))
                , new InfoProductDto(uuid, "apple", "good", new BigDecimal("3.0")));

        Mockito.when(productRepository.findAll()).thenReturn(products);

        Mockito.when(mapper.toInfoProductDto(products.get(0)))
                .thenReturn(expectedDto.get(0));
        Mockito.when(mapper.toInfoProductDto(products.get(1)))
                .thenReturn(expectedDto.get(1));

        // when
        List<InfoProductDto> actualDto = productService.getAll();

        // then
        Assertions.assertEquals(expectedDto, actualDto);
    }

    @Test
    void shouldCreateNewProductFromDto() {
        // given
        UUID expectedUuid = UUID.fromString("f4f6f49d-6186-4b76-abe1-a5e4ebda233c");
        Product product = new Product(expectedUuid, "apple", "good", new BigDecimal("5.0"), LocalDateTime.MIN);
        ProductDto productDto = new ProductDto("apple", "good", new BigDecimal("5.0"));

        Mockito.when(mapper.toProduct(productDto))
                .thenReturn(product);
        Mockito.when(productRepository.save(product))
                .thenReturn(product);

        // when
        UUID actualUuid = productService.create(productDto);

        // then
        Assertions.assertEquals(expectedUuid, actualUuid);
    }

    @Test
    void shouldUpdateNewProductFromDto() {
        // given
        UUID uuid = UUID.fromString("f4f6f49d-6186-4b76-abe1-a5e4ebda233c");
        ProductDto productDto = new ProductDto("apple", "good", new BigDecimal("3.0"));
        Product product = new Product(uuid, "apple", "good", new BigDecimal("3.0"), LocalDateTime.MIN);

        Mockito.when(productRepository.save(product))
                .thenReturn(product);
        // when
        productService.update(uuid, productDto);

        // then
        Mockito.verify(productRepository).save(product);
    }

    @Test
    void delete() {

        // given
        UUID uuid = UUID.fromString("43b0e1f1-28e8-47ea-9ce3-95b23a873e50");

        // when
        productService.delete(uuid);

        // then
        Mockito.verify(productRepository).delete(uuid);
    }
}