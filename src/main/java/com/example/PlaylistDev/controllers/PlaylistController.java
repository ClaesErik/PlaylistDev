package com.example.PlaylistDev.controllers;


import com.example.PlaylistDev.domain.ListaModel;
import com.example.PlaylistDev.domain.MusicaModel;
import com.example.PlaylistDev.domain.PlaylistDto;
import com.example.PlaylistDev.repository.ListaRepository;
import com.example.PlaylistDev.repository.MusicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
public class PlaylistController {

    @Autowired
    private ListaRepository listaRepository;

    @Autowired
    private MusicaRepository musicaRepository;

    @PostMapping("/playlist")
    public ResponseEntity<ListaModel> criarPlaylist(@RequestBody PlaylistDto playlistDto) {
        Set<MusicaModel> newMusics = new HashSet<>();
        Set<ListaModel> newList = new HashSet<>();

        Optional<ListaModel> listaOpt = listaRepository.findById(playlistDto.listaId());

        if(listaOpt.isPresent()){
            playlistDto.musicasId().forEach(id ->
            {
                Optional<MusicaModel> musicaOpt = musicaRepository.findById(id);

                if (musicaOpt.isPresent()) {
                    ListaModel list = listaOpt.get();
                    MusicaModel musica = musicaOpt.get();

                    newMusics.add(musica);

                    musica.getListaFk().add(list);

                    musicaRepository.save(musica);
                }

            });
            ListaModel lista = listaOpt.get();
            lista.getMusicas().addAll(newMusics);
            listaRepository.save(lista);

            return new ResponseEntity<>(lista, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
