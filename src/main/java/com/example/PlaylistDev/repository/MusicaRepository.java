package com.example.PlaylistDev.repository;


import com.example.PlaylistDev.domain.MusicaModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicaRepository extends JpaRepository<MusicaModel, String> {
    MusicaModel findByTitulo(String titulo);

    Long deleteByTitulo(String titulo);
}
