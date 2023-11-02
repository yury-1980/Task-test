package ru.clevertec.product.mapper.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.mapper.ProductMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

class ProductMapperImplTest {

    private final ProductMapper mapper = Mappers.getMapper(ProductMapper.class);

    @Test
    void shouldConvertProductDtoToProduct() {
        // given
        ProductDto expectedDto = new ProductDto("Apple", "Good", BigDecimal.valueOf(1.0));

        // when
        Product actual = mapper.toProduct(expectedDto);

        // then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expectedDto.name(), actual.getName());
        Assertions.assertEquals(expectedDto.description(), actual.getDescription());
        Assertions.assertEquals(expectedDto.price(), actual.getPrice());

    }

    @Test
    void ShouldConvertProductToInfoProductDto() {
        // given
        UUID uuid = UUID.fromString("f4f6f49d-6186-4b76-abe1-a5e4ebda233c");
        Product expected = new Product(uuid, "Apple", "Good", BigDecimal.valueOf(1.0), LocalDateTime.MIN);

        // when
        InfoProductDto actual = mapper.toInfoProductDto(expected);

        // then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected.getUuid(), actual.uuid());
        Assertions.assertEquals(expected.getName(), actual.name());
        Assertions.assertEquals(expected.getDescription(), actual.description());
        Assertions.assertEquals(expected.getPrice(), actual.price());
    }

    @Test
    void shouldMergeProductAndProductDtoReturnNewProduct() {
        // given
        UUID uuid = UUID.fromString("f4f6f49d-6186-4b76-abe1-a5e4ebda233c");
        Product product = new Product(uuid, "Apple", "Good", BigDecimal.valueOf(1.0), LocalDateTime.MIN);
        ProductDto expected = new ProductDto("Apple", "Good", BigDecimal.valueOf(1.0));

        // when
        Product actual = mapper.merge(product, expected);

        // then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected.name(), actual.getName());
        Assertions.assertEquals(expected.description(), actual.getDescription());
        Assertions.assertEquals(expected.price(), actual.getPrice());
    }
}