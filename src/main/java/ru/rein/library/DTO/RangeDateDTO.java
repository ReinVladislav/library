package ru.rein.library.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Schema(description = "Диапазон дат")
public class RangeDateDTO {
    @Schema(description = "Начальная дата", example = "2024-10-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @Schema(description = "Конечная дата", example = "2024-11-08")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate finishDate;
}
