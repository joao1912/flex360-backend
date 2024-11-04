package com.flex360.api_flex360.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flex360.api_flex360.models.Cadeira;

@Repository
public interface CadeiraRepository extends JpaRepository<Cadeira, UUID> {

    List<Cadeira> findByNomeContainingIgnoreCase(String nome);

}