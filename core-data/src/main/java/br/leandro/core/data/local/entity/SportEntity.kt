package br.leandro.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sports")
data class SportEntity(
    @PrimaryKey
    val id : String,
    val name: String,
    val iconUrl: String,
    val imageUrl: String,
    val description: String
)