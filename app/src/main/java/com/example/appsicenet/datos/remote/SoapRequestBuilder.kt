package com.example.appsicenet.datos.remote

// sirve para iniciar sesion en elmlogin con la funcion del login que recibe la matricula y el password, y despues con la
// funcion de perfil nos traemos los datos del alumno del sicenet
object SoapRequestBuilder {

    fun login(
        matricula: String,
        password: String
    ): String {

        return """
            <soap:Envelope 
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
                
              <soap:Body>
                <accesoLogin xmlns="http://tempuri.org/">
                  <strMatricula>$matricula</strMatricula>
                  <strContrasenia>$password</strContrasenia>
                  <tipoUsuario>ALUMNO</tipoUsuario>
                </accesoLogin>
              </soap:Body>
            </soap:Envelope>
        """.trimIndent()
    }

    fun perfil(): String {
        return """
        <soap:Envelope 
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xmlns:xsd="http://www.w3.org/2001/XMLSchema"
            xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
          <soap:Body>
            <getAlumnoAcademico xmlns="http://tempuri.org/" />
          </soap:Body>
        </soap:Envelope>
    """.trimIndent()
    }

    fun cargaAcademica(): String {
        return """
        <soap:Envelope 
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xmlns:xsd="http://www.w3.org/2001/XMLSchema"
            xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
          <soap:Body>
            <getCargaAcademicaByAlumno xmlns="http://tempuri.org/" />
          </soap:Body>
        </soap:Envelope>
    """.trimIndent()
    }

    fun cardex(aluLineamiento: Int): String {
        return """
        <soap:Envelope 
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xmlns:xsd="http://www.w3.org/2001/XMLSchema"
            xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
            
          <soap:Body>
            <getAllKardexConPromedioByAlumno xmlns="http://tempuri.org/">
              <aluLineamiento>$aluLineamiento</aluLineamiento>
            </getAllKardexConPromedioByAlumno>
          </soap:Body>
          
        </soap:Envelope>
    """.trimIndent()
    }
    // cris
    fun calificacionesUnidades(): String {
        return """
        <soap:Envelope 
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xmlns:xsd="http://www.w3.org/2001/XMLSchema"
            xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
          <soap:Body>
            <getCalifUnidadesByAlumno xmlns="http://tempuri.org/" />
          </soap:Body>
        </soap:Envelope>
    """.trimIndent()
    }

    fun calificacionesFinales(modEducativo: Int): String {
        return """
        <soap:Envelope 
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xmlns:xsd="http://www.w3.org/2001/XMLSchema"
            xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
          <soap:Body>
            <getAllCalifFinalByAlumnos xmlns="http://tempuri.org/">
              <bytModEducativo>$modEducativo</bytModEducativo>
            </getAllCalifFinalByAlumnos>
          </soap:Body>
        </soap:Envelope>
    """.trimIndent()
    }
}