package hr.foi.air.webservice

import hr.foi.air.webservice.model.Korisnik
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthApi {

    @GET("sw-api/api.php")
    suspend fun getKorisnik(
        @Query("metoda") metoda : String,
        @Query("tablica") tablica : String,
        @Query("korisnicko_ime") username : String,
        @Query("lozinka_sha256") password : String
    ): Response<List<Korisnik>>
}
