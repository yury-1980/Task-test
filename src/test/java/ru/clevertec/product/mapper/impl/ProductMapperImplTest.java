package ru.clevertec.product.mapper.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.mapper.ProductMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;

class ProductMapperImplTest {

    private final ProductMapper mapper = Mappers.getMapper(ProductMapper.class);
    private static final String NUM = "f4f6f49d-6186-4b76-abe1-a5e4ebda233c";
    private ProductDto expectedDto;
    private Product expectedProduct;

    @BeforeEach
    void setUp() {
        UUID uuid = UUID.fromString(NUM);
        expectedDto = ProductDto.builder()
                                .name("Apple")
                                .description("Good")
                                .price(BigDecimal.valueOf(1.0)).build();

        expectedProduct = Product.builder()
                                 .uuid(uuid)
                                 .name("Apple")
                                 .description("Good")
                                 .price(BigDecimal.valueOf(1.0))
                                 .created(LocalDateTime.MIN).build();
    }

    @Test
    void shouldConvertProductDtoToProduct() {
        // when
        Product actual = mapper.toProduct(expectedDto);

        // then
        assertAll("ConvertProductDtoToProduct",
                  () -> Assertions.assertNotNull(actual),
                  () -> Assertions.assertEquals(expectedDto.name(), actual.getName()),
                  () -> Assertions.assertEquals(expectedDto.description(), actual.getDescription()),
                  () -> Assertions.assertEquals(expectedDto.price(), actual.getPrice())
                 );
    }

    @Test
    void ShouldConvertProductToInfoProductDto() {
        // when
        InfoProductDto actual = mapper.toInfoProductDto(expectedProduct);

        // then
        assertAll("ConvertProductToInfoProductDto",
                  () -> Assertions.assertNotNull(actual),
                  () -> Assertions.assertEquals(expectedProduct.getUuid(), actual.uuid()),
                  () -> Assertions.assertEquals(expectedProduct.getName(), actual.name()),
                  () -> Assertions.assertEquals(expectedProduct.getDescription(), actual.description()),
                  () -> Assertions.assertEquals(expectedProduct.getPrice(), actual.price())
                 );
    }

    @Test
    void shouldMergeProductAndProductDtoReturnNewProduct() {
        // given
        ProductDto expectedDto = ProductDto.builder()
                                     .name("Banana")
                                     .description("Banana")
                                     .price(BigDecimal.valueOf(1.5)).build();

        // when
        Product actual = mapper.merge(expectedProduct, expectedDto);

        // then
        assertAll("MergeProductAndProductDto",
                  () -> Assertions.assertNotNull(actual),
                  () -> Assertions.assertEquals(expectedDto.name(), actual.getName()),
                  () -> Assertions.assertEquals(expectedDto.description(), actual.getDescription()),
                  () -> Assertions.assertEquals(expectedDto.price(), actual.getPrice())
                 );
    }
}