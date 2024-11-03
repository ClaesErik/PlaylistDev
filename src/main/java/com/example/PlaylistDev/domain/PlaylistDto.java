package com.example.PlaylistDev.domain;

import java.util.List;

public record PlaylistDto(
        String listaId,
        List<String> musicasId
) {
}
