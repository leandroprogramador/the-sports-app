package br.leandro.core.domain.model

data class League(
    val idLeague : String,
    val division : Int,
    val isCup : Boolean,
    val formedYear : String,
    val badge : String,
    val complete : Boolean,
    val country : String,
    val currentSeason : String,
    val descriptionEN : String,
    val descriptionPT : String?,
    val image : String,
    val gender : String,
    val league : String,
    val leagueAlternate : String,
    val sport : String,
    val tvRights : String

)