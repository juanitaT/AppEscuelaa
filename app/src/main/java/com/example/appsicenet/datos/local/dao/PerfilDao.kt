package com.example.appsicenet.datos.local.dao
 import androidx.room.Dao
 import androidx.room.Insert
 import androidx.room.OnConflictStrategy
 import androidx.room.Query
 import com.example.appsicenet.datos.local.entity.PerfilEntity
 import kotlinx.coroutines.flow.Flow

@Dao
interface PerfilDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarPerfil(perfil: PerfilEntity)

    @Query("SELECT * FROM perfil WHERE matricula = :mat")
    fun obtenerPerfil(mat: String): Flow<PerfilEntity?>

    @Query("DELETE FROM perfil")
    suspend fun limpiar()
}