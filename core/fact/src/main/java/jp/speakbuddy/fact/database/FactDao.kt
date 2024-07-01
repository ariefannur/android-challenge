package jp.speakbuddy.fact.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FactDao {
    @Query("SELECT * FROM FactEntity")
    fun getAllFact(): List<FactEntity>
    @Query("SELECT * FROM FactEntity WHERE fact LIKE :param")
    fun searchFacts(param: String): List<FactEntity>?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFact(data: FactEntity)
}