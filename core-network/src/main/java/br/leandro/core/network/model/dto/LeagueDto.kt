package br.leandro.core.network.model.dto

data class LeagueDto(
    val idLeague: String,
    val intDivision: String,
    val intFormedYear: String,
    val strBadge: String,
    val strComplete: String,
    val strCountry: String,
    val strCurrentSeason: String,
    val strDescriptionEN: String,
    val strDescriptionPT: String?,
    val strFanart1: String?,
    val strGender : String,
    val strLeague: String,
    val strLeagueAlternate: String,
    val strPoster: String,
    val strSport: String,
    val strTvRights: String?,

)

data class LeagueResponseDto(
    val countries: List<LeagueDto>
)

