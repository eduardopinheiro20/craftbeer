package com.beerhouse.controller;

import com.beerhouse.model.Produto;
import com.beerhouse.repository.ProdutoRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Api(value = "API REST Beer House")
@CrossOrigin(origins="*")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping("/produtos")
    @ApiOperation(value = "Retona uma lista de produtos")
    public List<Produto> getProdutos() {
        return produtoRepository.findAll();
    }

    @GetMapping("/produto/{id}")
    @ApiOperation(value = "Retona um produto unico")
    public Produto getProdutosById(@PathVariable(value = "id") long id) {
        return produtoRepository.findById(id);
    }

    @PostMapping("/produto")
    @ApiOperation(value = "Cadastra um produto")
    public Produto createProduto(@RequestBody Produto pProduto) {
        return produtoRepository.save(pProduto);
    }

    @DeleteMapping("/produto")
    @ApiOperation(value = "Deleta um produto")
    public void deleteProduto(@RequestBody Produto pProduto) {
        produtoRepository.delete(pProduto);
    }

    @PutMapping("/produto")
    @ApiOperation(value = "Altera um produto")
    public Produto updateProduto(@RequestBody Produto pProduto) {
        return produtoRepository.save(pProduto);
    }
}

