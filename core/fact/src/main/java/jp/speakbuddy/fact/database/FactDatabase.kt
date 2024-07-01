package jp.speakbuddy.fact.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FactEntity::class], version = 1)
abstract class FactDatabase: RoomDatabase() {
    abstract fun factDao(): FactDao
}