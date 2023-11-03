package ru.clevertec.product.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class Product {

    /**
     * Идентификатор продукта (генерируется базой)
     */
    private UUID uuid;

    /**
     * Название продукта (не может быть null или пустым, содержит 5-10 символов(русский или пробелы))
     */
    @NotNull
    @Pattern(regexp = "^[а-яА-Я\\s]{5,10}$")
    private String name;

    /**
     * Описание продукта(может быть null или 10-30 символов(русский и пробелы))
     */
    @Pattern(regexp = "^[а-яА-Я\\s]{10,30}$")
    private String description;

    /**
     * Не может быть null и должен быть положительным
     */
    @NotNull
    @Positive
    private BigDecimal price;

    /**
     * Время создания, не может быть null(задаётся до сохранения и не обновляется)
     */
    @NotNull
    private LocalDateTime created;
}
