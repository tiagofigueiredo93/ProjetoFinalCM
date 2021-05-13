package ipvc.estg.projetofinal

import android.content.SharedPreferences
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest

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
    }
}