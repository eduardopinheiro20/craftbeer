package com.beerhouse.controller;

import com.beerhouse.model.Categoria;
import com.beerhouse.model.Produto;
import com.beerhouse.repository.CategoriaRepository;
import com.beerhouse.repository.ProdutoRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.ResourceAccessException;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.http.HttpMethod.PUT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProdutoControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @MockBean
    private ProdutoRepository produtoRepository;

    @MockBean
    private CategoriaRepository categoriaRepository;

    @Autowired
    private MockMvc mockMvc;

    static class Config {
        public RestTemplateBuilder restTemplateBuilder() {
            return  new RestTemplateBuilder().basicAuthentication("beerhoouse", "beerhouse");
        }
    }

    @Test
    public void listCategoriesWhenGetProdutosShouldReturnStatus200() {

        Categoria categoria = new Categoria(1L, "Pilsen");
        categoriaRepository.save(categoria);

        List<Produto> produtoList =  asList(
                        new Produto(1L, "Cerveja Artesanal 1",
                        "Agual, Malte, Lupulo", new BigDecimal(7.5),
                        null, new BigDecimal(35.76), 45, categoria),
                        new Produto(3L, "Cerveja Artesanal 2",
                        "Agual, Malte, Lupulo", new BigDecimal(7.5),
                        null, new BigDecimal(35.76), 45, categoria));


        BDDMockito.when(produtoRepository.findAll()).thenReturn(produtoList);
        ResponseEntity<String> response = restTemplate.getForEntity("/api/produtos", String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void categoryByIdWhenGetProdutoShouldReturnStatus200() {
        restTemplate = restTemplate.withBasicAuth("1", "1");
        ResponseEntity<String> response = restTemplate.getForEntity("/api/produto/1", String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void createProdutoShouldStatusOk () {
        Categoria categoria = new Categoria(1L, "Pilsen");
        categoriaRepository.save(categoria);

        Produto produto = new Produto(1L, "Cerveja Artesanal 1",
                        "Agual, Malte, Lupulo", new BigDecimal(7.5),
                        null, new BigDecimal(35.76), 45, categoria);
        BDDMockito.when(produtoRepository.save(produto)).thenReturn(produto);
        ResponseEntity<String> response = restTemplate.postForEntity("/api/produto", produto, String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);

    }


    @Test
    public void updateProdutoShouldStatusCode() throws Exception {
        restTemplate = restTemplate.withBasicAuth("1", "1");

        Categoria categoria = new Categoria(1L, "Pilsen");
        categoriaRepository.save(categoria);

        Produto produto = new Produto(1L, "Cerveja Artesanal 1",
                        "Agual, Malte, Lupulo", new BigDecimal(7.5),
                        null, new BigDecimal(35.76), 45, categoria);
        BDDMockito.when(produtoRepository.save(produto)).thenReturn(produto);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Produto> entity = new HttpEntity<Produto>(produto, headers);

        assertThrows(
                        ResourceAccessException.class,
                        () -> restTemplate.exchange("/api/produto", PUT, entity, String.class, 1L));
    }
}
