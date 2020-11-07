package hr.foi.air.webservice

import hr.foi.air.webservice.model.Korisnik
import retrofit2.Response
import retrofit2.http.*

interface AuthApi {

    @GET("sw-api/api.php")
    suspend fun getKorisnik(
        @Query("tablica") table : String,
        @Query("metoda") method : String,
        @Query("korisnicko_ime") username : String,
        @Query("lozinka_sha256") password : String
    ): Response<List<Korisnik>>

    @GET("sw-api/api.php")
    suspend fun RegisterKorisnik(
        @Query("tablica") table : String,
        @Query("metoda") method : String,
        @Query("korisnicko_ime") username : String,
        @Query("ime") firstName : String,
        @Query("prezime") lastName : String,
        @Query("email") email : String,
        @Query("tip_korisnika_id") userType : String,
        @Query("lozinka_sha256") password : String
        )
}
