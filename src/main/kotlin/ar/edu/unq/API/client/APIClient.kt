package ar.edu.unq.API.client

import java.net.HttpURLConnection
import java.net.URL

class APIClient(val url: String, val puerto: Int) {
//    fun sendRequest(path: String, method: MetodoHTTP, contexto: String): List<String> {
//        val url = URL("${this.url}:${this.puerto}/${path}")
//
//        return with(url.openConnection() as HttpURLConnection) {
//            requestMethod = method.toString()
//            println("\nSent '$method' request to URL : $url; Response Code : $responseCode")
//            inputStream.bufferedReader().readLines()
//        }
//    }

    fun sendGet(path: String) : List<String> {
        val url = URL("${this.url}:${this.puerto}/${path}")
        return with(url.openConnection() as HttpURLConnection) {
            requestMethod = MetodoHTTP.GET.toString()
            println("\nSent '${MetodoHTTP.GET}' request to URL : $url; Response Code : $responseCode")
            inputStream.bufferedReader().readLines()
        }
    }

    fun sendDelete(path: String) : List<String>{
        val url = URL("${this.url}:${this.puerto}/${path}")
        return with(url.openConnection() as HttpURLConnection) {
            requestMethod = MetodoHTTP.DELETE.toString()
            println("\nSent '${MetodoHTTP.DELETE}' request to URL : $url; Response Code : $responseCode")
            inputStream.bufferedReader().readLines()
        }
    }

    fun sendPost(path: String, json: String) : List<String>{
        val url = URL("${this.url}:${this.puerto}/${path}")
        return with(url.openConnection() as HttpURLConnection) {
            requestMethod = MetodoHTTP.POST.toString()
            setRequestProperty("Content-Type", "application/json; utf-8")
            setRequestProperty("Accept", "application/json")
            doOutput = true
            outputStream.use { os ->
                val input: ByteArray = json.toByteArray(Charsets.UTF_8)
                os.write(input, 0, input.size)
            }
            println("\nSent '${MetodoHTTP.POST}' request to URL : $url; Response Code : $responseCode")
            inputStream.bufferedReader().readLines()
        }
    }

    fun sendPut(path: String, json: String) : List<String>{
        val url = URL("${this.url}:${this.puerto}/${path}")
        return with(url.openConnection() as HttpURLConnection) {
            requestMethod = MetodoHTTP.PUT.toString()
            setRequestProperty("Content-Type", "application/json; utf-8")
            setRequestProperty("Accept", "application/json")
            doOutput = true
            outputStream.use { os ->
                val input: ByteArray = json.toByteArray(Charsets.UTF_8)
                os.write(input, 0, input.size)
            }
            println("\nSent '${MetodoHTTP.PUT}' request to URL : $url; Response Code : $responseCode")
            inputStream.bufferedReader().readLines()
        }
    }
}