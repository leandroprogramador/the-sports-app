package br.leandro.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "leagues")
data class LeagueEntity (
    @PrimaryKey
    val idLeague : String,
    val division : String,
    val formedYear : String,
    val badge : String,
    val complete : String,
    val country : String,
    val currentSeason : String,
    val descriptionEN : String,
    val descriptionPT : String,
    val image : String,
    val gender : String,
    val league : String,
    val leagueAlternate : String,
    val sport : String,
    val tvRights : String

)