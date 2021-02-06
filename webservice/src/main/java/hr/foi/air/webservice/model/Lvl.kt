package hr.foi.air.webservice.model

data class Lvl(
    val id_razina: Int,
    val iskustvo: Int,
    val razina: Int,
    val id_korisnik: Int,
    val ime: String,
    val prezime: String
)