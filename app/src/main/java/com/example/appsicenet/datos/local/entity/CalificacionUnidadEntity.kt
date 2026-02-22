package com.example.appsicenet.datos.local.entity
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "calificaciones_unidad")
data class CalificacionUnidadEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val materia: String,
    val unidad: Int,
    val calificacion: Int,
    val ultimaActualizacion: Long
)

