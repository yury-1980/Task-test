package ru.clevertec.product.valid;


import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.exception.ProductNotValidate;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.Set;

public class Validator {

    public void validateProduct(Product product) {
        javax.validation.Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        if (!violations.isEmpty()) {
            try {
                for (ConstraintViolation<Product> violation : violations) {
                    System.out.println(violation.getMessage());
                }
                throw new ProductNotValidate();
            } catch (ProductNotValidate notValidate) {
                notValidate.printStackTrace();
            }
        }
    }

    public void validateInfoProductDto(InfoProductDto infoProductDto) {
        javax.validation.Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        Set<ConstraintViolation<InfoProductDto>> violations = validator.validate(infoProductDto);

        if (!violations.isEmpty()) {
            try {
                for (ConstraintViolation<InfoProductDto> violation : violations) {
                    System.out.println(violation.getMessage());
                }
                throw new ProductNotValidate();
            } catch (ProductNotValidate notValidate) {
                notValidate.printStackTrace();
            }
        }
    }

    public void validateProductDto(ProductDto productDto) {
        javax.validation.Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        Set<ConstraintViolation<ProductDto>> violations = validator.validate(productDto);

        if (!violations.isEmpty()) {
            try {
                for (ConstraintViolation<ProductDto> violation : violations) {
                    System.out.println(violation.getMessage());
                }
                throw new ProductNotValidate();
            } catch (ProductNotValidate notValidate) {
                notValidate.printStackTrace();
            }
        }
    }
}
