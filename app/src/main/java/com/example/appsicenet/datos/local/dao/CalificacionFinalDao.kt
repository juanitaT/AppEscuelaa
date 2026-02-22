package com.example.appsicenet.datos.local.dao
    import androidx.room.Dao
    import androidx.room.Insert
    import androidx.room.OnConflictStrategy
    import androidx.room.Query
    import com.example.appsicenet.datos.local.entity.CalificacionFinalEntity
    import kotlinx.coroutines.flow.Flow

    @Dao
    interface CalificacionFinalDao {

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertarTodo(calificaciones: List<CalificacionFinalEntity>)

        @Query("SELECT * FROM calificacion_final")
        fun obtenerCalificacionesFinales(): Flow<List<CalificacionFinalEntity>>

        @Query("DELETE FROM calificacion_final")
        suspend fun limpiar()
    }
