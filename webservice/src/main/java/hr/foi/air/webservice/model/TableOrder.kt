package hr.foi.air.webservice.model

import java.sql.Timestamp
import java.util.concurrent.TimeUnit

data class TableOrder (
    val id_stol: Int,
    val broj_osoba: Int,
    val rezerviran: Int
)