package br.leandro.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.leandro.core.data.local.entity.LeagueEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LeagueDao {
    @Query("SELECT * FROM leagues WHERE country = :country AND sport = :sport")
    fun getLeagues(country : String, sport : String): Flow<List<LeagueEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLeagues(leagues: List<LeagueEntity>)

    @Query("SELECT COUNT(*) FROM leagues")
    suspend fun count(): Int

    @Query("DELETE FROM leagues WHERE country = :country AND sport = :sport")
    suspend fun clearLeagues(country: String, sport: String)


}