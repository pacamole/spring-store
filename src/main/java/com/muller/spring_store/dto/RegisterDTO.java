package com.muller.spring_store.dto;

import com.muller.spring_store.model.UserRole;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {
    @Schema(description = "Nome de exibição do usuário", example = "Admin Chefe")
    @NotBlank(message = "O nome é obrigatório")
    private String name;
    @Schema(description = "Email do usuário - único e um por conta", example = "admin@store.com")
    @NotBlank(message = "O email é obrigatório")
    private String email;
    @Schema(description = "Senha do usuário", example = "1234")
    @NotBlank(message = "A senha é obrigatória")
    private String password;

    @Schema(description = "Papel do usuário: ADMIN ou USER", example = "ADMIN")
    @NotNull(message = "O papel (role) é obrigatório")
    private UserRole role;
}
