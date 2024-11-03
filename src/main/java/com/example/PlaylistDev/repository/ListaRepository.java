package com.example.PlaylistDev.repository;

import com.example.PlaylistDev.domain.ListaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ListaRepository extends JpaRepository<ListaModel, String> {

    ListaModel findByNome(String nome);

    Long deleteByNome(String nome);

}
