package hr.foi.air.webservice.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(
     tableName = "meals"
)
@Parcelize
data class Meal (
     @PrimaryKey(autoGenerate = false)
     val id_stavka: String,
     val naziv: String,
     val cijena: String,
     val opis: String,
     val slika_path: String,
     val lokal_id: String,
     val aktivno: Int
 ) : Parcelable