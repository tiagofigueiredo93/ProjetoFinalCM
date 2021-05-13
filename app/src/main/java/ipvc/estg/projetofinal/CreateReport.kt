/**/package ipvc.estg.projetofinal

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*



import ipvc.estg.projetofinal.api.EndPoints
import ipvc.estg.projetofinal.api.OutPutReport
import ipvc.estg.projetofinal.api.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateReport : AppCompatActivity() {


    private lateinit var editTipoView: EditText
    private lateinit var editDescricaoView: EditText
    lateinit var shared_preferences: SharedPreferences
    private var latitude : Double = 0.0
    private var longitude : Double = 0.0
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val LOCATION_PERMISSION_REQUEST_CODE = 1
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var lastLocation: Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_report)

        editTipoView = findViewById(R.id.insertType)
        editDescricaoView = findViewById(R.id.insertDescriptionReport)

        shared_preferences = getSharedPreferences( "shared_preferences", Context.MODE_PRIVATE)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult?) {
                super.onLocationResult(p0)

                //Recebemos as coordenadas a partir do LocationResult e atribuimos a cada variável
                lastLocation = p0?.lastLocation!!
                latitude = lastLocation.latitude
                longitude = lastLocation.longitude
            }
        }

        createLocationRequest()

    }
    //Obter a localização de 10 em 10 segnds com a maior precisão
    private fun createLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.interval = 10000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

    }
    fun reportar(view: View) {

        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val latitude = latitude
        val longitude = longitude
        val tipo = editTipoView.text.toString()
        val descricao = editDescricaoView.text.toString()
        val replyIntent = Intent()
        val utilizador_id = shared_preferences.getInt(MainLogin.ID_UITLIZADOR, 0)


        val call = request.report(
            latitude = latitude.toString(),
            longitude = longitude.toString(),
            tipo = tipo,
            descricao = descricao,
            imagem = "imagem",
            utilizador_id = utilizador_id
        )

        if(TextUtils.isEmpty(editTipoView.text)  || TextUtils.isEmpty(editDescricaoView.text)){
            Toast.makeText(this@CreateReport, R.string.camposNecessarios, Toast.LENGTH_LONG).show()
        }else{
            call.enqueue(object : Callback<OutPutReport> {
                override fun onResponse(call: Call<OutPutReport>, response: Response<OutPutReport>){
                    if (response.isSuccessful){
                        val c: OutPutReport = response.body()!!
                        Toast.makeText(this@CreateReport, c.Mensagem, Toast.LENGTH_LONG).show()

                        val intent = Intent(this@CreateReport, MapsActivity::class.java)
                        startActivity(intent);
                        finish()


                    }
                }
                override fun onFailure(call: Call<OutPutReport>, t: Throwable){
                    Toast.makeText(this@CreateReport,"${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

    }

    //onPause
    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
    //onResume
    override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }

    private fun startLocationUpdates() {
        if(ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)

            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)

    }


}