package br.leandro.core.network.model.dto

data class SportsDto(
    val idSport: String,
    val strFormat: String,
    val strSport: String,
    val strSportDescription: String,
    val strSportIconGreen: String,
    val strSportThumb: String,
    val strSportThumbBW: String
)

data class SportsResponseDto(
    val sports : List<SportsDto>?
)