package br.leandro.core.network.model.dto

data class CountryDto(
    val name_en : String,
    val flag_url_32: String
)

data class CountriesResponseDto(
    val countries : List<CountryDto>
)

