package com.flex360.api_flex360.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flex360.api_flex360.models.Cor;

 import java.util.UUID;

@Repository
public interface CorRepository extends JpaRepository<Cor, UUID> {

    Optional<Cor> findByName(String name);

}
