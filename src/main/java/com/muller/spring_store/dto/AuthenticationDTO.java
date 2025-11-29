package com.muller.spring_store.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationDTO {
    @Schema(description = "O email para o login do usuário", example = "johndoe@gmail.com")
    @NotBlank(message = "O email é obrigatório")
    private String email;
    @Schema(description = "A senha para login do usuário", example = "1234")
    @NotBlank(message = "A senha é obrigatória")
    private String password;
}
