package ipvc.estg.projetofinal


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import ipvc.estg.projetofinal.api.EndPoints
import ipvc.estg.projetofinal.api.Report
import ipvc.estg.projetofinal.api.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var  reports: List<Report>

    //variaveis para a localização atual
    private lateinit var lastLocation: Location //Variavel que nos permite ter a,localização

    //variavel para o cliente FusedLocationProviderClient para aceder á biblioteca
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    //Constante utilizada na verificação se existe permissão para aceder á localização atual
    private val LOCATION_PERMISSION_REQUEST_CODE = 1

    //Constante para edição
    private val reportEditActivityRequestCode = 1

    //Declaração do shared_preferences
    private lateinit var shared_preferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //Inicialização do fuselLocation biblioteca
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        shared_preferences = getSharedPreferences("shared_preferences", Context.MODE_PRIVATE)

        //Obtenção de uma instância do retrofit
        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getReports()
        var position: LatLng
        val utilizador_id = shared_preferences.getInt(MainLogin.ID_UITLIZADOR, 0)

        call.enqueue(object : Callback<List<Report>> {
            override fun onResponse(call: Call<List<Report>>, response: Response<List<Report>>){
                if (response.isSuccessful){
                    reports = response.body()!!
                    //Para cada report vai buscar a latlng e adiciona o Marker
                    for (report in reports){
                        //Guardar a latitude e longitude do report
                        position = LatLng(report.latitude.toDouble(), report.longitude.toDouble())
                        if(report.utilizador_id == utilizador_id){
                            mMap.addMarker(MarkerOptions().position(position).title(report.id.toString()).snippet(report.tipo + "-" + report.descricao))
                                .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                        }else{
                            mMap.addMarker(MarkerOptions().position(position).title(report.id.toString()).snippet(report.tipo + "-" + report.descricao))
                        }

                    }
                }
            }
            override fun onFailure(call: Call<List<Report>>, t: Throwable){
                Toast.makeText(this@MapsActivity,"${t.message}", Toast.LENGTH_LONG).show()
            }
        })

    }
    override fun onInfoWindowClick(p0: Marker?) {
        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call :Call <List<Report>> = request.getReportId(p0!!.title)
        val utilizador_id = shared_preferences.getInt(MainLogin.ID_UITLIZADOR, 0)

        call.enqueue(object : Callback<List<Report>> {
            override fun onResponse(call: Call<List<Report>>, response: Response<List<Report>>) {
                if(response.isSuccessful){
                    reports = response.body()!!
                    for(report in reports){
                        if(report.utilizador_id == utilizador_id){
                            Toast.makeText(this@MapsActivity, report.descricao, Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@MapsActivity,EditDeleteReport::class.java)
                            intent.putExtra(REPORT_ID, report.id.toString())
                            intent.putExtra(REPORT_TYPE, report.tipo)
                            intent.putExtra(REPORT_DESCRIPTION, report.descricao)
                            intent.putExtra(REPORT_LATITUDE, report.latitude)
                            intent.putExtra(REPORT_LONGITUDE, report.longitude)
                            intent.putExtra(REPORT_UTILIZADOR_ID, report.utilizador_id.toString())
                            startActivityForResult(intent, reportEditActivityRequestCode)
                        }else{
                            Toast.makeText(this@MapsActivity,"R.string.id_error_edit", Toast.LENGTH_LONG).show()
                        }

                    }
                }
            }
            override fun onFailure(call: Call<List<Report>>, t: Throwable) {
                Toast.makeText(this@MapsActivity,"${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Viana
//        val viana = LatLng(41.6932, -8.83287)
//        mMap.addMarker(MarkerOptions().position(viana).title("Marker in Viana do Castelo"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(viana))
        mMap.setOnInfoWindowClickListener(this)
        setUpMap()
    }

    private fun setUpMap() {
        //Verifica se existe permissão para aceder á localiozação atual
        if(ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

                    //Se o utilizador não deu permissão vai larçar uma mensagem para o utilizador dar permissão
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)

            return
            //Caso haja permissão vai entrar aqui
        } else {
            //Inserção de um simbolo com a nossa localização atual no mapa
            mMap.isMyLocationEnabled = true

            //Listener sobre a ultima localização
            fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
                //3
                if (location != null) {
                    lastLocation = location
                    Toast.makeText(this@MapsActivity, lastLocation.toString(), Toast.LENGTH_SHORT).show()

                    //Guardar a latitude e longitude da localização atual
                    val currentLatLng = LatLng(location.latitude, location.longitude)
                    //animação da camera para a localização atual
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
                }
            }
        }
    }
    companion object {
        const val STATUS = ""
        const val DELETE_ID = "DELETE_ID"
        const val REPORT_ID = "REPORT_ID"
        const val REPORT_TYPE = "REPORT_TYPE"
        const val REPORT_DESCRIPTION = "REPORT_DESCRIPTION"
        const val REPORT_LATITUDE = "REPORT_LATITUDE"
        const val REPORT_LONGITUDE = "REPORT_LONGITUDE"
        const val REPORT_UTILIZADOR_ID = "REPORT_UTILIZADOR_ID"
    }
}