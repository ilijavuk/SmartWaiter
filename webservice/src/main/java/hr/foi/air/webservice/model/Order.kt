package hr.foi.air.webservice.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Order(
    val korisnik_id: Int,
    val stol_id: Int,
    val status: Int,
    val vrijeme: String,
    val stavka_id: Int,
    val kolicina: Int
) : Parcelable
