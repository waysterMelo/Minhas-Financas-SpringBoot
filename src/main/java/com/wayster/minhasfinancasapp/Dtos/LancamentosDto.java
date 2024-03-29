package com.wayster.minhasfinancasapp.Dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.wayster.minhasfinancasapp.Entity.Lancamentos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LancamentosDto {

private Long id;
private String descricao;
private Integer mes;
private BigDecimal valor;
private Integer ano;
private Long usuario;
private String tipo;
private String status;
private LocalDate dataCadastro = LocalDate.now();

}
