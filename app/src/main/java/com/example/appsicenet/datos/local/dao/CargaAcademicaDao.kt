package com.example.appsicenet.datos.local.dao
 import androidx.room.Dao
 import androidx.room.Insert
 import androidx.room.OnConflictStrategy
 import androidx.room.Query
//import kotlinx.coroutines.flow.Flow

    @Dao
    interface CargaAcademicaDao {

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertarTodo(lista: List<CargaAcademicaEntity>)

        @Query("DELETE FROM carga_academica")
        suspend fun limpiar()

        @Query("SELECT * FROM carga_academica")
        fun obtenerCargaAcademica(): kotlinx.coroutines.flow.Flow<List<CargaAcademicaEntity>>

        @Query("SELECT MAX(ultimaActualizacion) FROM carga_academica")
        suspend fun obtenerUltimaActualizacion(): Long?

        @Query("SELECT MAX(ultimaActualizacion) FROM carga_academica")
        fun obtenerUltimaActualizacionFlow(): Flow<Long?>
    }