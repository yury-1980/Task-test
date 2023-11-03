package ru.clevertec.product.service.impl;

import lombok.RequiredArgsConstructor;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.exception.ProductNotFoundException;
import ru.clevertec.product.mapper.ProductMapper;
import ru.clevertec.product.repository.ProductRepository;
import ru.clevertec.product.service.ProductService;
import ru.clevertec.product.valid.Validator;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper mapper;
    private final ProductRepository productRepository;
    private final Validator validator = new Validator();

    @Override
    public InfoProductDto get(UUID uuid) {
        return productRepository.findById(uuid)
                .map(mapper::toInfoProductDto)
                .orElseThrow(() -> new ProductNotFoundException(uuid));
    }

    @Override
    public List<InfoProductDto> getAll() {
        return productRepository.findAll().stream()
                .map(mapper::toInfoProductDto)
                .toList();
    }

    @Override
    public UUID create(ProductDto productDto) {
        validator.validateProductDto(productDto);
        Product product = mapper.toProduct(productDto);

        return productRepository.save(product).getUuid();
    }

    @Override
    public void update(UUID uuid, ProductDto productDto) {
        validator.validateProductDto(productDto);
        Product product = productRepository.findById(uuid).orElseThrow();
        mapper.merge(product, productDto);
        productRepository.save(product);

    }

    @Override
    public void delete(UUID uuid) {
        productRepository.delete(uuid);
    }
}
