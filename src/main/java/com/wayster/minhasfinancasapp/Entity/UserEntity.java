package com.wayster.minhasfinancasapp.Entity;

import lombok.*;

import java.time.LocalDate;

import javax.persistence.*;

@Table(name = "usuarios")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder

public class UserEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String email;
    @Column
    private String password;
    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;


}
