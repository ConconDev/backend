package com.sookmyung.concon.BootPay.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BootPayConfirmRequestDTO {
    @NotNull(message = "receiptId가 없습니다. ")
    private String receiptId;
}
