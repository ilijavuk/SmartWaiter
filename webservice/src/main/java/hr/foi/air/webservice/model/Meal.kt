package hr.foi.air.webservice.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Meal (
     val id_stavka: String,
     val naziv: String,
     val cijena: String,
     val opis: String,
     val slika_path: String,
     val lokal_id: String,
     val aktivno: Int
 ) : Parcelable