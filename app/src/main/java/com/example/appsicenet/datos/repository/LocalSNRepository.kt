package com.example.appsicenet.datos.repository
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
import kotlinx.coroutines.flow.Flow

class LocalSNRepository(
    private val perfilDao: PerfilDao,
    private val cargaAcademicaDao: CargaAcademicaDao,
    private val cardexDao: CardexDao,
    private val calificacionUnidadDao: CalificacionUnidadDao,
    private val calificacionFinalDao: CalificacionFinalDao
) {


    suspend fun guardarPerfil(perfil: PerfilEntity) {
        perfilDao.insertarPerfil(perfil)
    }

    fun obtenerPerfil(matricula: String): Flow<PerfilEntity?> {
        return perfilDao.obtenerPerfil(matricula)
    }

    suspend fun guardarCargaAcademica(
        matricula: String,
        carga: List<CargaAcademicaEntity>
    ) {
        cargaAcademicaDao.limpiar(matricula)
        cargaAcademicaDao.insertarTodo(carga)
    }

    fun obtenerCargaAcademica(matricula: String): Flow<List<CargaAcademicaEntity>> {
        return cargaAcademicaDao.obtenerCargaAcademica(matricula)
    }

    fun obtenerUltimaActualizacionCargaFlow(matricula: String): Flow<Long?> {
        return cargaAcademicaDao.obtenerUltimaActualizacionFlow(matricula)
    }

    suspend fun guardarCardex(cardex: List<CardexEntity>) {
        cardexDao.limpiar()
        cardexDao.insertarTodo(cardex)
    }

    fun obtenerCardex(): Flow<List<CardexEntity>> {
        return cardexDao.obtenerCardex()
    }

    fun obtenerUltimaActualizacionCardex(): Flow<Long?> {
        return cardexDao.obtenerUltimaActualizacionCardex()
    }

    suspend fun guardarCalificacionesUnidad(
        calificaciones: List<CalificacionUnidadEntity>
    ) {
        calificacionUnidadDao.limpiar()
        calificacionUnidadDao.insertarTodo(calificaciones)
    }

    fun obtenerCalificacionesUnidad(): Flow<List<CalificacionUnidadEntity>> {
        return calificacionUnidadDao.obtenerCalificaciones()
    }

    suspend fun guardarCalificacionesFinales(
        calificaciones: List<CalificacionFinalEntity>
    ) {
        calificacionFinalDao.limpiar()
        calificacionFinalDao.insertarTodo(calificaciones)
    }

    fun obtenerCalificacionesFinales(): Flow<List<CalificacionFinalEntity>> {
        return calificacionFinalDao.obtenerCalificacionesFinales()
    }
}