package com.example.appsicenet.datos.local;
import androidx.room.Database
import androidx.room.RoomDatabase

import com.example.appsicenet.datos.local.dao.CalificacionFinalDao;
import com.example.appsicenet.datos.local.dao.CalificacionUnidadDao;
import com.example.appsicenet.datos.local.dao.CardexDao;
import com.example.appsicenet.datos.local.dao.CargaAcademicaDao;
import com.example.appsicenet.datos.local.dao.PerfilDao;
import com.example.appsicenet.datos.local.entity.CalificacionFinalEntity;
import com.example.appsicenet.datos.local.entity.CalificacionUnidadEntity;
import com.example.appsicenet.datos.local.entity.CardexEntity;
import com.example.appsicenet.datos.local.entity.CargaAcademicaEntity;
import com.example.appsicenet.datos.local.entity.PerfilEntity;


@Database(
        entities = [
PerfilEntity::class,
CargaAcademicaEntity::class,
CardexEntity::class,
CalificacionUnidadEntity::class,
CalificacionFinalEntity::class
    ],
version = 1,
exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun perfilDao(): PerfilDao
    abstract fun cargaAcademicaDao(): CargaAcademicaDao
    abstract fun cardexDao(): CardexDao
    abstract fun calificacionUnidadDao(): CalificacionUnidadDao
    abstract fun calificacionFinalDao(): CalificacionFinalDao
}