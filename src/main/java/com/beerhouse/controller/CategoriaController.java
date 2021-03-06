package com.beerhouse.controller;

import com.beerhouse.model.Categoria;
import com.beerhouse.repository.CategoriaRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Eduardo Pinheiro
 */
@RestController
@RequestMapping("/api")
@Api(value = "API REST Beer House")
@CrossOrigin(origins="*")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping("/categorias")
    @ApiOperation(value = "Retona uma lista de categorias")
    public List<Categoria> getCategories() {
        return categoriaRepository.findAll();
    }

    @GetMapping("/categoria/{id}")
    @ApiOperation(value = "Retona uma categoria unica")
    public Categoria getCategoryById(@PathVariable(value = "id") long id) {
        return categoriaRepository.findById(id);
    }

    @PostMapping("/categoria")
    @ApiOperation(value = "Cadastra um produto")
    public Categoria createCategory(@RequestBody Categoria pCategoria) {
        return categoriaRepository.save(pCategoria);
    }

    @DeleteMapping("/categoria")
    @ApiOperation(value = "Deleta uma categoria")
    public void deleteCategory(@RequestBody Categoria pCategoria) {
        categoriaRepository.delete(pCategoria);
    }

    @PutMapping("/categoria")
    @ApiOperation(value = "Altera um categoria")
    public Categoria updateCategory(@RequestBody Categoria pCategoria) {
        return categoriaRepository.save(pCategoria);
    }
}

