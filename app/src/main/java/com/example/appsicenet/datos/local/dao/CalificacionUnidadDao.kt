package com.example.appsicenet.datos.local.dao
    import androidx.room.Dao
    import androidx.room.Insert
    import androidx.room.OnConflictStrategy
    import androidx.room.Query
    import com.example.appsicenet.datos.local.entity.CalificacionUnidadEntity
    import kotlinx.coroutines.flow.Flow

    @Dao
    interface CalificacionUnidadDao {

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertarTodo(calificaciones: List<CalificacionUnidadEntity>)

        @Query("SELECT * FROM calificaciones_unidad")
        fun obtenerCalificaciones(): Flow<List<CalificacionUnidadEntity>>

        @Query("DELETE FROM calificaciones_unidad")
        suspend fun limpiar()
    }