package ru.clevertec.product.data;

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
        @NotNull
        @Pattern(regexp = "^[а-яА-Я\\s]{5,10}$")
        String name,

        /**
         * Описание продукта не может быть null, может быть пустой строкой
         */
        @NotNull
        @Size(min = 0)
        String description,

        /**
         * Стоимость не может быть null или негативным
         */
        @NotNull
        @Positive
        BigDecimal price) {
}
