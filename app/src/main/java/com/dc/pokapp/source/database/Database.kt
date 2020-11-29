package com.dc.pokapp.source.database

import android.content.Context
import androidx.room.*
import com.dc.pokapp.model.Pokemon
import com.dc.pokapp.model.PokemonDetail
import com.dc.pokapp.model.ServerTotalCount

@Database(
    entities = [
        Pokemon::class,
        PokemonDetail::class,
        ServerTotalCount::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getDao(): Dao

    companion object {
        fun buildAppDatabase(context: Context): AppDatabase {
            return Room
                .databaseBuilder<AppDatabase>(context, AppDatabase::class.java, "pokapp-db")
                .fallbackToDestructiveMigration()
                .build()
        }

        fun buildDao(database: AppDatabase): Dao {
            return database.getDao()
        }
    }
}