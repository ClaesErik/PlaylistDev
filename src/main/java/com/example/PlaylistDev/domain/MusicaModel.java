package com.example.PlaylistDev.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TB_MUSICA")
public class MusicaModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true)
    private String titulo;

    @Column()
    private String artista;

    @Column()
    private String album;

    @Column()
    private Integer ano;

    @Column()
    private String genero;

    /**
     Relacionamentos
     */

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(mappedBy = "musicas")
    private Set<ListaModel> listaFk = new HashSet<>();



    /**
     Get and Set
     */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Set<ListaModel> getListaFk() {
        return listaFk;
    }

    public void setListaFk(Set<ListaModel> listaFk) {
        this.listaFk = listaFk;
    }
}