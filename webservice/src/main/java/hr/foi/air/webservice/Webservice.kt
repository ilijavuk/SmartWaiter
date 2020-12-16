package hr.foi.air.webservice

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import hr.foi.air.webservice.model.Korisnik
import java.net.URL

public class Webservice {

    companion object{
        var BASE_URL="https://smartwaiter.app/sw-api/api.php"
    }

    public fun Korisnici(): String {
        return URL("https://smartwaiter.app/sw-api/api.php?metoda=select&tablica=Korisnik").readText();
        //return
    }
    public fun Korisnici(id:String): String { //Dohvati info o korisniku prema ID-ju
        return URL("https://smartwaiter.app/sw-api/api.php?metoda=select&tablica=Korisnik&id_korisnik="+id).readText();
        //return
    }
    public fun Korisnici(username:String, password: String): String { //Dohvati info o korisniku prema username-password
        return URL("https://smartwaiter.app/sw-api/api.php?metoda=select&tablica=Korisnik&korisnicko_ime="+username+"&lozinka_sha256="+password).readText();
        //return
    }

    public fun RegistrirajKorisnika(username: String, password: String, ime:String, prezime:String, email:String): String {
        return URL("https://smartwaiter.app/sw-api/api.php?metoda=insert&tablica=Korisnik" +
                "&korisnicko_ime="+username+
                "&ime="+ime+
                "&prezime="+prezime+
                "&email="+email+
                "&tip_korisnika_id=1" +
                "&lozinka_sha256="+password)
                .readText();
    }

    fun APICall(metoda:String, tablica:String, argumeti:Map<String, String>): String {
        var noviURL=BASE_URL
        noviURL+="?metoda="+metoda+
                 "&tablica="+tablica;
        for(entry in argumeti){
            noviURL+="&"+entry.key+"="+entry.value
        }
        return URL(noviURL).readText()

    }
    

}