package com.example.PlaylistDev.controllers;

import com.example.PlaylistDev.domain.MusicaModel;
import com.example.PlaylistDev.repository.MusicaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MusicaController {

    @Autowired
    private MusicaRepository musicaRepository;

    @PostMapping("/musics")
    @Transactional
    public ResponseEntity<MusicaModel> setMusica(@RequestBody MusicaModel musica){
        try {
            MusicaModel musicaObj = musicaRepository.save(musica);

            return new ResponseEntity<>(musicaObj, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/musics")
    public ResponseEntity<List<MusicaModel>> getMusicas(){
        try {
            List<MusicaModel> musicas = new ArrayList<>();
            musicaRepository.findAll().forEach(musicas::add);

            if (musicas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(musicas, HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/musics/{musicTitle}")
    public ResponseEntity<MusicaModel> getMusicaByTitulo(@PathVariable String musicTitle){
        MusicaModel musica = musicaRepository.findByTitulo(musicTitle);

        if (musica == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(musica, HttpStatus.OK);
    }

    @DeleteMapping("/musics/{musicTitle}")
    @Transactional
    public ResponseEntity<MusicaModel> deleteMusicas(@PathVariable String musicTitle){
        MusicaModel musica = musicaRepository.findByTitulo(musicTitle);

        if (musica != null) {
            musica.getListaFk().forEach(lista -> lista.getMusicas().remove(musica));
            musica.getListaFk().clear();
            Long res = musicaRepository.deleteByTitulo(musicTitle);

            if (res != 1) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
