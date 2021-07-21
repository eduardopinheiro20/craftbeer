package com.beerhouse.repository;

import com.beerhouse.model.Categoria;
import com.beerhouse.model.Produto;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProdutoRepositoryTest {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createShouldPersistData() {

        Produto produto = new Produto(1L, "Cerveja Artesanal 1",
                        "Agual, Malte, Lupulo", new BigDecimal(7.5),
                        null, new BigDecimal(35.76), 45, new Categoria());
        produtoRepository.save(produto);
        assertThat(produto.getId()).isNotNull();
        assertThat(produto.getNome()).isEqualTo("Cerveja Artesanal 1");
        assertThat(produto.getIngredientes()).isEqualTo("Agual, Malte, Lupulo");
        assertThat(produto.getTeorAlcoolico()).isEqualTo(new BigDecimal(7.5));
        assertThat(produto.getDtCadastro()).isNull();
        assertThat(produto.getValor()).isEqualTo(new BigDecimal(35.76));
        assertThat(produto.getQuantidade()).isEqualTo(45);
    }

    @Test
    public void deleteShouldRemoveData () {

        Categoria categoria = new Categoria(1L, "Pilsen");
        categoriaRepository.save(categoria);

        Produto produto = new Produto(1L, "Cerveja Artesanal 1",
                        "Agual, Malte, Lupulo", new BigDecimal(7.5),
                        null, new BigDecimal(35.76), 45, categoria);
        this.produtoRepository.save(produto);
        produtoRepository.delete(produto);
    }


    @Test
    public void updateShouldChengeData() {
        Categoria categoria = new Categoria(1L, "Pilsen");
        categoriaRepository.save(categoria);

        Produto produto = new Produto(1L, "Cerveja Artesanal 1",
                        "Agual, Malte, Lupulo", new BigDecimal(7.5),
                        null, new BigDecimal(35.76), 45, categoria);
        produtoRepository.save(produto);
        produto.setNome("Cerveja Artesanal 2");
        produto.setValor(new BigDecimal(30.00));
        produto = this.produtoRepository.save(produto);
        produtoRepository.delete(produto);
        assertThat(produto.getNome()).isEqualTo("Cerveja Artesanal 2");
        assertThat(produto.getValor()).isEqualTo(new BigDecimal(30.00));
    }
}
