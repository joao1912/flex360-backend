package com.flex360.api_flex360.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flex360.api_flex360.models.ProdutoCarrinho;

@Repository
public interface ProdutoCarrinhoRepository extends JpaRepository<ProdutoCarrinho, UUID> {

    List<ProdutoCarrinho> findByCarrinhoId(UUID id);
    int deleteByProdutoId(UUID id);

}