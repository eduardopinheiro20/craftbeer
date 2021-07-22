package com.beerhouse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDate;


/**
 * @Author Eduardo Pinheiro
 */
@AllArgsConstructor
@Entity
@Data
public class Produto {

    @Id
    @SequenceGenerator(name = "produto_seq", sequenceName = "produto_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "O campo nome do produto é obrigatório")
    private String nome;

    private String ingredientes;

    private BigDecimal teorAlcoolico;

    @DateTimeFormat(style = "yyyy-mm-dd")
    private LocalDate dtCadastro;

    private BigDecimal valor;

    private Integer quantidade;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    public Produto() {

    }

}
