package ru.clevertec.product.data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.UUID;

public record InfoProductDto(

        /**
         * Идентификатор не может быть null
         */
        @NotNull
        UUID uuid,

        /**
         * Имя продукта смотрите {@link ru.clevertec.product.entity.Product}
         */
        @NotBlank(message = "Имя не должен быть пустым")
        @Pattern(regexp = "^[Ёёа-яА-Я\\s]{5,10}$")
        String name,

        /**
         * Описание продукта не может быть null, может быть пустой строкой
         */
        @NotNull
        @Pattern(regexp = "^[Ёёа-яА-Я\\s]{10,30}$")
        String description,

        /**
         * Стоимость не может быть null или негативным
         */
        @NotNull
        @Positive(message = "Должно быть положительным")
        BigDecimal price) {
}
