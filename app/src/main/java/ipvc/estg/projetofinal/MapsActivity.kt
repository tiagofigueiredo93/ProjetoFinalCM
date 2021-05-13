package ipvc.estg.projetofinal


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
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
import ipvc.estg.projetofinal.api.*
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

    //FUNÇÃO PARA POSTERIORMENTE EFETUAR A EDIÇÃO OU APAGAR A REPORTAÇÃO FEITA
    override fun onInfoWindowClick(p0: Marker?) {
        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call :Call <List<Report>> = request.getReportId(p0!!.title)
        val utilizador_id = shared_preferences.getInt(MainLogin.ID_UITLIZADOR, 0)

        call.enqueue(object : Callback<List<Report>> {
            override fun onResponse(call: Call<List<Report>>, response: Response<List<Report>>) {
                if(response.isSuccessful){
                    reports = response.body()!!
                   //Verificação do id
                    for(report in reports){

                        //VERIFICAÇÃO DO ID PARA QUE EU CONSIGA CONTROLAR SE O UTILIZADOR PODE OU NAO EDITAR/APAGAR O SEU REPORT OU DE OUTRO UTILZIADOR
                        if(report.utilizador_id == utilizador_id){

                            val intent = Intent(this@MapsActivity,EditDeleteReport::class.java)
                            intent.putExtra(REPORT_ID, report.id.toString())
                            intent.putExtra(REPORT_TYPE, report.tipo)
                            intent.putExtra(REPORT_DESCRIPTION, report.descricao)
                            intent.putExtra(REPORT_LATITUDE, report.latitude)
                            intent.putExtra(REPORT_LONGITUDE, report.longitude)
                            intent.putExtra(REPORT_UTILIZADOR_ID, report.utilizador_id.toString())
                            startActivityForResult(intent, reportEditActivityRequestCode)
                        }else{
                            Toast.makeText(this@MapsActivity,getString(R.string.acessDenied), Toast.LENGTH_LONG).show()
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
            //Caso haja permissão
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

    //onActivityResult
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == reportEditActivityRequestCode && resultCode == Activity.RESULT_OK) {

            var id = data?.getStringExtra(EditDeleteReport.EDIT_ID)
            var id_delete = data?.getStringExtra(EditDeleteReport.DELETE_ID)
            var edit_type = data?.getStringExtra(EditDeleteReport.EDIT_TYPE).toString()
            var edit_description = data?.getStringExtra(EditDeleteReport.EDIT_DESCRIPTION).toString()
            var edit_latitude = data?.getDoubleExtra(EditDeleteReport.EDIT_LATITUDE, 0.0).toString()
            var edit_longitude = data?.getDoubleExtra(EditDeleteReport.EDIT_LONGITUDE, 0.0).toString()
            val utilizador_id = shared_preferences.getInt("id", 0)

            if(data?.getStringExtra(EditDeleteReport.STATUS) == "EDIT"){
                val request = ServiceBuilder.buildService(EndPoints::class.java)
                //chamado do endpoint para a edição da reportação
                val call = request.editReport(
                    id = id,
                    latitude = edit_latitude,
                    longitude = edit_longitude,
                    tipo = edit_type,
                    descricao = edit_description,
                    imagem = "imagem",
                    utilizador_id = utilizador_id)

                call.enqueue(object : Callback<OutPutEditReport> {
                    override fun onResponse(call: Call<OutPutEditReport>, response: Response<OutPutEditReport>){
                        if (response.isSuccessful){
                            val c: OutPutEditReport = response.body()!!
                            Toast.makeText(this@MapsActivity, c.Mensagem, Toast.LENGTH_LONG).show()
                            val intent = Intent(this@MapsActivity, MapsActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                    override fun onFailure(call: Call<OutPutEditReport>, t: Throwable){
                        Toast.makeText(this@MapsActivity,"${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })


                //DELETE REPORT
            } else if(data?.getStringExtra(EditDeleteReport.STATUS) == "DELETE"){

                val request = ServiceBuilder.buildService(EndPoints::class.java)
                val call = request.deleteReport(id = id_delete)


                call.enqueue(object : Callback<OutPutDeleteReport> {
                    override fun onResponse(call: Call<OutPutDeleteReport>, response: Response<OutPutDeleteReport>){
                        if (response.isSuccessful){
                            val c: OutPutDeleteReport = response.body()!!
                            Toast.makeText(this@MapsActivity, c.Mensagem, Toast.LENGTH_LONG).show()
                            val intent = Intent(this@MapsActivity, MapsActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                    override fun onFailure(call: Call<OutPutDeleteReport>, t: Throwable){
                        Toast.makeText(this@MapsActivity,"${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            }

        } else if (resultCode == Activity.RESULT_CANCELED) {
            if(data?.getStringExtra(EditDeleteReport.STATUS) == "EDIT"){
                Toast.makeText(this, "Report not edited, fields empty", Toast.LENGTH_SHORT).show()
            } else if(data?.getStringExtra(EditDeleteReport.STATUS) == "DELETE"){
                Toast.makeText(this, "Report not deleted, fields empty", Toast.LENGTH_SHORT).show()
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