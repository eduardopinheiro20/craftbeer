package com.beerhouse.repository;

import com.beerhouse.model.Categoria;
import com.beerhouse.model.Produto;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @Author Eduardo Pinheiro
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class CategoriaRepositoryTest {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createShouldPersistData() {
        Categoria categoria = new Categoria(1L, "Peilsen");
        this.categoriaRepository.save(categoria);
        assertThat(categoria.getId()).isNotNull();
        assertThat(categoria.getNome()).isEqualTo("Peilsen");
    }

    @Test
    public void deleteShouldRemoveData() {
        Categoria categoria = new Categoria(1L, "Peilsen");
        this.categoriaRepository.save(categoria);
        categoriaRepository.delete(categoria);
        assertThat(categoriaRepository.findById(categoria.getId()).orElse(null)).isNull();
    }

    @Test
    public void updateShouldChengeData() {
        Categoria categoria = new Categoria(1L, "Pilsen");
        this.categoriaRepository.save(categoria);
        categoria.setNome("Lager");
        categoria = this.categoriaRepository.save(categoria);
        categoria = this.categoriaRepository.findById(categoria.getId()).orElse(null);
        assertThat(categoria.getNome()).isEqualTo("Lager");
    }

    @Test
    public void createWhenNameIsNullShouldThrowConstaintViolationException() {
        thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("O campo nome da categoria é obrigatório");
        this.categoriaRepository.save(new Categoria());
    }
}
