package com.example.appsicenet.datos.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.appsicenet.datos.local.dao.CalificacionFinalDao
import com.example.appsicenet.datos.local.dao.CalificacionUnidadDao
import com.example.appsicenet.datos.local.dao.CardexDao
import com.example.appsicenet.datos.local.dao.CargaAcademicaDao
import com.example.appsicenet.datos.local.dao.PerfilDao
import com.example.appsicenet.datos.local.entity.CalificacionFinalEntity
import com.example.appsicenet.datos.local.entity.CalificacionUnidadEntity
import com.example.appsicenet.datos.local.entity.CardexEntity
import com.example.appsicenet.datos.local.entity.CargaAcademicaEntity
import com.example.appsicenet.datos.local.entity.PerfilEntity

@Database(
    entities = [
        PerfilEntity::class,
        CargaAcademicaEntity::class,
        CardexEntity::class,
        CalificacionUnidadEntity::class,
        CalificacionFinalEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class SicenetDatabase : RoomDatabase() {

    abstract fun perfilDao(): PerfilDao
    abstract fun cargaAcademicaDao(): CargaAcademicaDao
    abstract fun cardexDao(): CardexDao
    abstract fun calificacionUnidadDao(): CalificacionUnidadDao
    abstract fun calificacionFinalDao(): CalificacionFinalDao

    companion object {
        @Volatile
        private var INSTANCE: SicenetDatabase? = null

        fun getDatabase(context: Context): SicenetDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    SicenetDatabase::class.java,
                    "sicenet_db"
                )
                    .fallbackToDestructiveMigration()
                    .build().also {
                        INSTANCE = it
                    }
            }
        }
    }
}