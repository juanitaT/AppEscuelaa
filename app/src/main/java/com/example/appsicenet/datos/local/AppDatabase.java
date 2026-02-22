package com.example.appsicenet.datos.local;
import androidx.room.Database
import androidx.room.RoomDatabase
public class AppDatabase {
    @Database(
            entities = [
    PerfilEntity::class,
    CargaAcademicaEntity::class,
    CardexEntity::class,
    CalificacionUnidadEntity::class,
    CalificacionFinalEntity::class
    ],
    version = 1,
    exportSchema = false
            )
    abstract class AppDatabase : RoomDatabase() {

        abstract fun perfilDao(): PerfilDao
        abstract fun cargaAcademicaDao(): CargaAcademicaDao
        abstract fun cardexDao(): CardexDao
        abstract fun calificacionUnidadDao(): CalificacionUnidadDao
        abstract fun calificacionFinalDao(): CalificacionFinalDao
    }
}
