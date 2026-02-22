package com.example.appsicenet.datos.local.dao
 import androidx.room.Dao
 import androidx.room.Insert
 import androidx.room.OnConflictStrategy
 import androidx.room.Query

    @Dao
    interface PerfilDao {

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        //suspend fun insertarPerfil(perfil: PerfilEntity)

        //@Query("SELECT * FROM perfil LIMIT 1")
       // suspend fun obtenerPerfil(): PerfilEntity?

        //@Query("DELETE FROM perfil")
        suspend fun limpiar()
    }