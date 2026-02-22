package com.example.appsicenet.datos.remote

// sirve para iniciar sesion en elmlogin con la funcion del login que recibe la matricula y el password, y despues con la
// funcion de perfil nos traemos los datos del alumno del sicenet
object SoapRequestBuilder {

    // Construye la cadena, para la petición de inicio de sesión (login).
    fun login(
        matricula: String,
        password: String
    ): String {
        // ve si las cadenas de Kotlin ($matricula, $password) son correctos e inserta los valores
        // de los parámetros directamente en las etiquetas XML correspondientes.
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
}
