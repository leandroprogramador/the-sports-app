package br.leandro.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.leandro.core.data.local.dao.SportDao
import br.leandro.core.data.local.entity.SportEntity

@Database(
    entities = [SportEntity::class],
    version = 1,
    exportSchema = false
)
abstract class SportsDatabase : RoomDatabase(){
    abstract fun sportDao() : SportDao
}