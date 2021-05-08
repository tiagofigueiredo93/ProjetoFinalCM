package ipvc.estg.projetofinal


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
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import ipvc.estg.projetofinal.api.EndPoints
import ipvc.estg.projetofinal.api.Report
import ipvc.estg.projetofinal.api.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var  reports: List<Report>

    //variaveis para a localização atual
    private lateinit var lastLocation: Location //Variavel que nos permite ter a,localização

    //variavel para o cliente FusedLocationProviderClient para aceder á biblioteca
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    //Constante utilizada na verificação se existe permissão para aceder á localização atual
    private val LOCATION_PERMISSION_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //Inicialização do fuselLocation biblioteca

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        //Obtenção de uma instância do retrofit
        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getReports()
        var position: LatLng



        call.enqueue(object : Callback<List<Report>> {
            override fun onResponse(call: Call<List<Report>>, response: Response<List<Report>>){
                if (response.isSuccessful){
                    reports = response.body()!!
                    //Para cada report vai buscar a latlng e adiciona o Marker
                    for (report in reports){
                        position = LatLng(report.latitude.toDouble(), report.longitude.toDouble())
                        mMap.addMarker(MarkerOptions().position(position).title(report.tipo + "-" + report.descricao))
                    }
                }
            }
            override fun onFailure(call: Call<List<Report>>, t: Throwable){
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
}