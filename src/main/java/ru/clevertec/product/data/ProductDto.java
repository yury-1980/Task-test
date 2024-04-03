package ru.clevertec.product.data;

import lombok.Builder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Builder
public record ProductDto(

        /**
         * {@link ru.clevertec.product.entity.Product}
         */
        @NotBlank(message = "Имя не должен быть пустым")
        @Pattern(regexp = "^[Ёёа-яА-Я\\s]{5,10}$")
        String name,


        /**
         * {@link ru.clevertec.product.entity.Product}
         */
        @NotNull
        @Pattern(regexp = "^[Ёёа-яА-Я\\s]{10,30}$")
        String description,


        /**
         * {@link ru.clevertec.product.entity.Product}
         */
        @NotNull
        @Positive(message = "Должно быть положительным")
        BigDecimal price) {
}
