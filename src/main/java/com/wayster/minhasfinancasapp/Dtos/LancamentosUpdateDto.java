package com.wayster.minhasfinancasapp.Dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LancamentosUpdateDto {

private Long id;
private String descricao;
private Integer mes;
private BigDecimal valor;
private Integer ano;
private Long usuario;
private String tipo;
private String status;
private LocalDate dataCadastro;
private LocalDate dataModificacao = LocalDate.now(); //update




}
