package com.example.PlaylistDev.controllers;



import com.example.PlaylistDev.domain.ListaModel;
import com.example.PlaylistDev.repository.ListaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ListaController {

    @Autowired
    private ListaRepository listaRepository;

    @PostMapping("/lists")
    @Transactional
    public ResponseEntity<ListaModel> setLista(@RequestBody ListaModel lista) {
        try {
            ListaModel listaObj = listaRepository.save(lista);

            return new ResponseEntity<>(listaObj, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/lists")
    public ResponseEntity<List<ListaModel>> getListas() {
        try {
            List<ListaModel> listas = new ArrayList<>();
            listaRepository.findAll().forEach(listas::add);

            if (listas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(listas, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/lists/{listNome}")
    public ResponseEntity<ListaModel> getListaByName(@PathVariable String listNome) {
        ListaModel lista = listaRepository.findByNome(listNome);

        if (lista == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(lista, HttpStatus.OK);
    }


    @DeleteMapping("/lists/{listNome}")
    @Transactional
    public ResponseEntity<ListaModel> deleteListas(@PathVariable String listNome) {
        ListaModel lista = listaRepository.findByNome(listNome);

        if (lista != null) {
            lista.getMusicas().forEach(musica -> musica.getListaFk().remove(lista));
            lista.getMusicas().clear();
            Long res = listaRepository.deleteByNome(listNome);

            if (res != 1) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
