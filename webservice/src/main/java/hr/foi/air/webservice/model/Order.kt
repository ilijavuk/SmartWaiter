package hr.foi.air.webservice.model

import java.sql.Timestamp
import java.time.LocalDateTime
import java.util.*

data class Order (
    val naziv: String,
    val slika_path: String,
    val vrijeme: Int,
    val kolicina: Int

    /*
    val id_zapisa: String,
    val korisnik_id: String,
    val stol_id: String,
    val status: String,
    val vrijeme: LocalDateTime,
    val stavka_naziv: String,
    val slika_path: String,
    val kolicina: Int*/
    )