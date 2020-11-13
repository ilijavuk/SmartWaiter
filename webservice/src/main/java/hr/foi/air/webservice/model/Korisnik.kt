package hr.foi.air.webservice.model

data class Korisnik (
        val id_korisnik: String,
        val tip_korisnika_id: String,
        val lokal_id: Any,
        val ime: String,
        val prezime: String,
        val korisnicko_ime: String,
        val email: String,
        val lozinka_sha256: String,
        val token: String
)