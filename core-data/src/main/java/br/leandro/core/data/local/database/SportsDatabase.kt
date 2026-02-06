package br.leandro.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.leandro.core.data.local.dao.CountryDao
import br.leandro.core.data.local.dao.SportDao
import br.leandro.core.data.local.entity.CountryEntity
import br.leandro.core.data.local.entity.SportEntity

@Database(
    entities = [SportEntity::class, CountryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class SportsDatabase : RoomDatabase(){
    abstract fun sportDao() : SportDao
    abstract fun countryDao(): CountryDao
}

