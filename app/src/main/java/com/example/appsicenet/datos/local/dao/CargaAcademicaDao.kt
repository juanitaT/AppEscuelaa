package com.example.appsicenet.datos.local.dao
 import androidx.room.Dao
 import androidx.room.Insert
 import androidx.room.OnConflictStrategy
 import androidx.room.Query
 import com.example.appsicenet.datos.local.entity.CargaAcademicaEntity
 import kotlinx.coroutines.flow.Flow

//import kotlinx.coroutines.flow.Flow

@Dao
interface CargaAcademicaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarTodo(lista: List<CargaAcademicaEntity>)

    @Query("DELETE FROM carga_academica WHERE matricula = :mat")
    suspend fun limpiar(mat: String)

    @Query("SELECT * FROM carga_academica WHERE matricula = :mat")
    fun obtenerCargaAcademica(mat: String): Flow<List<CargaAcademicaEntity>>

    @Query("SELECT MAX(ultimaActualizacion) FROM carga_academica")
    suspend fun obtenerUltimaActualizacion(): Long?

    @Query("SELECT MAX(ultimaActualizacion) FROM carga_academica WHERE matricula = :mat")
    fun obtenerUltimaActualizacionFlow(mat: String): Flow<Long?>
}