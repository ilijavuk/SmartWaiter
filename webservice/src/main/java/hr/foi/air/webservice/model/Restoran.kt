package hr.foi.air.webservice.model

data class Restoran (
    val id_lokal: Int,
    val naziv: String,
    val adresa: String,
    val GPS_longitude: Double,
    val GPS_latitude: Double,
){
    override fun toString(): String {
        return naziv.toString()
    }
}
