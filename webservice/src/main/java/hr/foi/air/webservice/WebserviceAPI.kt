package hr.foi.air.webservice

import hr.foi.air.webservice.model.*
import hr.foi.air.webservice.model.Tag
import retrofit2.Call

import retrofit2.Response
import retrofit2.http.*
import retrofit2.http.GET
import retrofit2.http.Query

interface WebserviceAPI {

    @GET("sw-api/api.php")
    suspend fun getKorisnik(
        @Query("tablica") table: String,
        @Query("metoda") method: String,
        @Query("korisnicko_ime") username: String,
        @Query("lozinka_sha256") password: String
    ): List<Korisnik>

    @GET("sw-api/api.php")
    suspend fun getRestorani(
        @Query("tablica") table: String,
        @Query("metoda") method: String,
        @Query("korisnicko_ime") username: String,
        @Query("lozinka_sha256") password: String
    ): Response<List<Korisnik>>

    @GET("sw-api/api.php")
    suspend fun getRestorani(
        @Query("tablica") table: String,
        @Query("metoda") method: String,
    ): Response<List<Restoran>>

    @GET("sw-api/api.php")
    suspend fun insertRestoran(
        @Query("tablica") table: String,
        @Query("metoda") method: String,
        @Query("naziv") restaurantName: String,
        @Query("adresa") restaurantAddress: String,
        @Query("GPS_Longitude") GPS_Longitude: Double,
        @Query("GPS_Latitude") GPS_Latitude: Double,
    ): Response<String>


    @GET("sw-api/api.php")
    suspend fun RegisterKorisnik(
        @Query("tablica") table: String,
        @Query("metoda") method: String,
        @Query("korisnicko_ime") username: String,
        @Query("ime") firstName: String,
        @Query("prezime") lastName: String,
        @Query("email") email: String,
        @Query("tip_korisnika_id") userType: String,
        @Query("lozinka_sha256") password: String
    )

    @GET("sw-api/api.php")
    suspend fun getLvl(
        @Query("metoda") method : String,
    ): Response<List<Lvl>>

    @GET("sw-api/api.php")
    suspend fun getUsername(
        @Query("tablica") table: String,
        @Query("metoda") method: String,
        @Query("korisnicko_ime") username: String,
        @Query("operator") operator: String,
        @Query("email") email: String
    ): Response<List<Korisnik>>

    @GET("sw-api/api.php")
    suspend fun insertMeal(
        @Query("tablica") table: String,
        @Query("metoda") method: String,
        @Query("naziv") mealName: String,
        @Query("cijena") mealPrice: String,
        @Query("opis") mealDescription: String,
        @Query("slika_path") mealPhotoPath: String,
        @Query("lokal_id") lokalId: String
    ): String

    @GET("sw-api/api.php")
    suspend fun getMeal(
        @Query("tablica") table: String,
        @Query("metoda") method: String,
        @Query("lokal_id") lokal_id: String
    ): List<Meal>

    @GET("sw-api/api.php")
    suspend fun getMealById(
        @Query("tablica") table: String,
        @Query("metoda") method: String,
        @Query("id_stavka") stavka_id: String
    ): List<Meal>

    @GET("sw-api/api.php")
    suspend fun setMealAvailability(
        @Query("tablica") table: String,
        @Query("metoda") method: String,
        @Query("key") key: String,
        @Query("aktivno") aktivno: String,
    )

    @GET("sw-api/api.php")
    suspend fun updateMeal(
        @Query("tablica") table: String,
        @Query("metoda") method: String,
        @Query("key") key: String,
        @Query("naziv") mealName: String,
        @Query("cijena") mealPrice: String,
        @Query("opis") mealDescription: String,
        @Query("slika_path") mealPhotoPath: String,
    ): String

    @GET("sw-api/api.php")
    suspend fun getTables(
        //@Query("tablica") table : String,
        @Query("metoda") method : String,
        //@Query("rezerviran") rezerviran : String,
        ): Response<List<TableOrder>>

    @GET("sw-api/api.php")
    suspend fun getOrders(
        //@Query("tablica") table : String,
        @Query("metoda") method : String,
        @Query("stol_id") lokal_id: String
    ): Response<List<Order2>>

    @GET("sw-api/api.php")
    suspend fun setOrders(
        @Query("tablica") table: String,
        @Query("metoda") method: String,
        @Query("key") id_stol: Int,
        @Query("rezerviran") rezerviran: Int
    )

    @GET("sw-api/api.php")
    suspend fun setXp(
        @Query("tablica") table: String,
        @Query("metoda") method: String,
        @Query("key") id_korisnik: Int,
        @Query("iskustvo") iskustvo: Int
    )


    @GET("sw-api/api.php")
    suspend fun getTableFromHash(
        @Query("tablica") table: String,
        @Query("metoda") method: String,
        @Query("hash") hash: String
    ): List<Stol>


    @GET("sw-api/api.php")
    suspend fun getAllTags(
        @Query("tablica") table: String,
        @Query("metoda") method: String,
    ): List<Tag>

    @GET("sw-api/api.php")
    suspend fun tagsByRestaurant(
        @Query("metoda") method: String,
        @Query("lokal_id") lokal_id: String,
    ): List<Tag>

    @GET("sw-api/api.php")
    suspend fun insertTag(
        @Query("tablica") table: String,
        @Query("metoda") method: String,
        @Query("tag") mealName: String,
    ): String

    @GET("sw-api/api.php")
    suspend fun bindTag(
        @Query("tablica") table: String,
        @Query("metoda") method: String,
        @Query("stavka_id") stavkaId: String,
        @Query("tag_id") tagId: String,
    ): String

    @GET("sw-api/api.php")
    suspend fun menuByTag(
        @Query("metoda") method: String,
        @Query("id_tag") id_tag: String,
        @Query("lokal_id") lokal_id: String,
    ): List<Meal>

    @GET("sw-api/api.php")
    suspend fun makeOrder(
        @Query("tablica") table: String,
        @Query("metoda") method: String,
        @Query("korisnik_id") user_id: Int,
        @Query("stol_id") table_id: Int,
        @Query("status") status: Int,
        @Query("vrijeme") time: String,
        @Query("stavka_id") meal_id: Int,
        @Query("kolicina") amount: Int
    ): String
    @GET("sw-api/api.php")
    suspend fun tagsByMeal(
        @Query("funkcija") function : String,
        @Query("meal_id") lokal_id : String,
    ): List<Tag>

    @GET("sw-api/api.php")
    suspend fun RemoveTagsFromMeal(
        @Query("funkcija") function : String,
        @Query("meal_id") lokal_id : String,
    ): String



    @GET("sw-api/create.php")
    suspend fun createCustomer(): Customer

    @FormUrlEncoded
    @POST("sw-api/checkout.php")
    fun getEphemeralKey(
        @Field("api_version") apiVersion: String,
        @Field("customerID") customerID: String
        ): Call<Any>

    @FormUrlEncoded
    @POST("sw-api/checkout2.php")
    fun payMeal(
        @Field("amount") amount: String,
        @Field("customerID") customer: String
    ): Call<Any>

    @GET("sw-api/api.php")
    suspend fun getRestorani2(
        @Query("tablica") table: String,
        @Query("metoda") method: String,
    ): List<Restoran>

    /*@GET("sw-api/api.php")
    suspend fun getEmail(
        @Query("tablica") table : String,
        @Query("metoda") method : String,
        @Query("email") email : String
    ): Response<List<Korisnik>>*/

}
