package com.wayster.minhasfinancasapp.Entity;


import lombok.*;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Table(name = "lancamentos")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class Lancamentos {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String  descricao;
    private Integer mes;
    private Integer ano;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private UserEntity usuario;

    @Column
    private BigDecimal valor;

    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;

    @Column(name = "data_modificacao")
    private LocalDate dataModificacao;

    @Column(name = "tipo")
    @Enumerated(value = EnumType.STRING)
    private TipoLancamento tipo;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private StatusLancamento statusLancamento;

}
