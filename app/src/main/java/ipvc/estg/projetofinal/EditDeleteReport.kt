package ipvc.estg.projetofinal

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*

class EditDeleteReport : AppCompatActivity() {

    private lateinit var editType: EditText
    private lateinit var editDescription: EditText
    private lateinit var shared_preferences: SharedPreferences
    private var latitude : Double = 0.0
    private var longitude : Double = 0.0

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val LOCATION_PERMISSION_REQUEST_CODE = 1
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var lastLocation: Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_delete_report)

        editType = findViewById(R.id.editTypeReport)
        editDescription = findViewById(R.id.editDescriptionReport)

        var id = intent.getStringExtra(MapsActivity.REPORT_ID)
        var type = intent.getStringExtra(MapsActivity.REPORT_TYPE)
        var description = intent.getStringExtra(MapsActivity.REPORT_DESCRIPTION)
        editType.setText(type.toString())
        editDescription.setText(description.toString())


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult?) {
                super.onLocationResult(p0)
                lastLocation = p0?.lastLocation!!
                latitude = lastLocation.latitude
                longitude = lastLocation.longitude
            }
        }

        createLocationRequest()

        //BOTÃO EDITAR
        val buttonEdit = findViewById<Button>(R.id.editReport)
        buttonEdit.setOnClickListener {
            val replyIntent = Intent()
            replyIntent.putExtra(EDIT_ID, id)
            if (TextUtils.isEmpty(editType.text)  || TextUtils.isEmpty(editDescription.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val edit_tipo = editType.text.toString()
                replyIntent.putExtra(EDIT_TYPE, edit_tipo)
                val edit_descricao = editDescription.text.toString()
                replyIntent.putExtra(EDIT_DESCRIPTION, edit_descricao)
                val latitude = latitude
                replyIntent.putExtra(EDIT_LATITUDE, latitude)
                val longitude = longitude
                replyIntent.putExtra(EDIT_LONGITUDE, longitude)
                replyIntent.putExtra(STATUS, "EDIT")
                setResult(Activity.RESULT_OK, replyIntent)
            }

            finish()
        }

        val buttonDelete = findViewById<Button>(R.id.deleteReport)
        buttonDelete.setOnClickListener {
            val replyIntent = Intent()
                replyIntent.putExtra(DELETE_ID, id)
                replyIntent.putExtra(STATUS, "DELETE")
                setResult(Activity.RESULT_OK, replyIntent)
            finish()
        }


    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.interval = 10000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

    }
    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

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
    companion object {
        const val STATUS = ""
        const val DELETE_ID = "DELETE_ID"
        const val EDIT_ID = "EDIT_ID"
        const val EDIT_TYPE = "EDIT_TYPE"
        const val EDIT_DESCRIPTION = "EDIT_DESCRIPTION"
        const val EDIT_LATITUDE = "EDIT_LATITUDE"
        const val EDIT_LONGITUDE = "EDIT_LONGITUDE"
    }

}