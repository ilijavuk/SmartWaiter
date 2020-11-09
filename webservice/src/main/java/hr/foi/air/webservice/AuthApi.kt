package hr.foi.air.webservice

import hr.foi.air.webservice.model.Korisnik
import hr.foi.air.webservice.model.Restoran
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface AuthApi {

    @GET("sw-api/api.php")
    suspend fun getKorisnik(
        @Query("tablica") table : String,
        @Query("metoda") method : String,
        @Query("korisnicko_ime") username : String,
        @Query("lozinka_sha256") password : String
    ): Response<List<Korisnik>>

    @GET("sw-api/api.php")
    suspend fun getRestorani(
        @Query("tablica") table : String,
        @Query("metoda") method : String,
    ): Response<List<Restoran>>

    @GET("sw-api/api.php")
    suspend fun insertRestoran(
        @Query("tablica") table : String,
        @Query("metoda") method : String,
        @Query("naziv") restaurantName: String,
        @Query("adresa") restaurantAddress: String,
        @Query("GPS_Longitude") GPS_Longitude: Double,
        @Query("GPS_Latitude") GPS_Latitude: Double,
    ): Response<String>

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

    @GET("sw-api/api.php")
    suspend fun getUsername(
        @Query("tablica") table : String,
        @Query("metoda") method : String,
        @Query("korisnicko_ime") username : String,
    ): Response<List<Korisnik>>
}
