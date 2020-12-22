package hr.foi.air.webservice.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Narudzba(
    val korisnik_id: String,
    val stol_id: String,
    val stavka_id: String,
    val kolicina: String,
    val vrijeme: String
) : Parcelable
