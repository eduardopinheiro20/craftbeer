package com.beerhouse.model;

import com.sun.istack.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String ingredientes;

    private BigDecimal teorAlcoolico;

    @DateTimeFormat(style = "yyyy-mm-dd")
    private LocalDate dtCadastro;

    private BigDecimal valor;

    private Integer quantidade;

    @OneToOne(cascade = CascadeType.ALL)
    private Categoria categoria;

}
