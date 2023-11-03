package ru.clevertec.product.data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public record ProductDto(

        /**
         * {@link ru.clevertec.product.entity.Product}
         */
        @NotNull
        @Pattern(regexp = "^[а-яА-Я\\s]{5,10}$")
        String name,


        /**
         * {@link ru.clevertec.product.entity.Product}
         */
        @Pattern(regexp = "^[а-яА-Я\\s]{10,30}$")
        String description,


        /**
         * {@link ru.clevertec.product.entity.Product}
         */
        @NotNull
        @Positive
        BigDecimal price) {
}
