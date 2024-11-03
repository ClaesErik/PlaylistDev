package com.example.PlaylistDev.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "TB_LISTA")
public class ListaModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column()
    private String descricao;

    @ManyToMany()
    @JoinTable(
            name = "tb_lista_musica",
            joinColumns = @JoinColumn(name = "lista_id"),
            inverseJoinColumns = @JoinColumn(name = "musica_id")
    )
    private Set<MusicaModel> musicas = new HashSet<>();

    /**
     * Get and Set
     */

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<MusicaModel> getMusicas() {
        return musicas;
    }

    public void setMusicas(Set<MusicaModel> musicas) {
        this.musicas = musicas;
    }


}