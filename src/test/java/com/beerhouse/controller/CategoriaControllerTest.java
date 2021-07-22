package com.beerhouse.controller;

import com.beerhouse.model.Categoria;
import com.beerhouse.repository.CategoriaRepository;
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

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.http.HttpMethod.PUT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CategoriaControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

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
    public void listCategoriesWhenGetCategoriesShouldReturnStatus200() {
       List<Categoria> categoriaList =  asList(new Categoria(1L, "Pilse"), new Categoria(2L, "American Lager"));

        BDDMockito.when(categoriaRepository.findAll()).thenReturn(categoriaList);
        ResponseEntity<String> response = restTemplate.getForEntity("/api/categorias", String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void categoryByIdWhenGetCategoriesShouldReturnStatus200() {
        restTemplate = restTemplate.withBasicAuth("1", "1");
        ResponseEntity<String> response = restTemplate.getForEntity("/api/categoria/1", String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

//    @Test
//    public void deleteCategoryShouldReturnStatusCode200() {
//        Categoria categoria = new Categoria(1L, "Pilse");
//        BDDMockito.when(categoriaRepository.save(categoria)).thenReturn(categoria);
//        BDDMockito.doNothing().when(categoriaRepository).delete(categoria);
//        ResponseEntity<String> response = restTemplate.exchange("/api/categoria", DELETE, null, String.class);
//        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
//    }

    @Test
    public void createCategoryShouldStatusOk () {
        Categoria categoria = new Categoria(1L, "Pilse");
        BDDMockito.when(categoriaRepository.save(categoria)).thenReturn(categoria);
        ResponseEntity<String> response = restTemplate.postForEntity("/api/categoria", categoria, String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);

    }


    @Test
    public void updateCategoryShouldStatusCode() throws Exception {
        restTemplate = restTemplate.withBasicAuth("1", "1");

        Categoria categoria = new Categoria(1L, "Pilse");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Categoria> entity = new HttpEntity<Categoria>(categoria, headers);

        assertThrows(
                        ResourceAccessException.class,
                        () -> restTemplate.exchange("/api/categoria", PUT, entity, String.class, 1L));
    }
}
