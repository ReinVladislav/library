package ru.rein.library.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

@Data
@Schema(description = "Id читателя и кнгиги для транзакции")
public class MakeTransactionDTO {

    @Schema(description = "Id читателя", example = "3")
    private long idReader;
    @Schema(description = "Id книги", example = "5")
    private long idBook;
}
