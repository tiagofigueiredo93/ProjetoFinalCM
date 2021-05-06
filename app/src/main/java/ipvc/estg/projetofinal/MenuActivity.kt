package ipvc.estg.projetofinal

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView

class MenuActivity : AppCompatActivity() {

    private val newWordActivityRequestCode = 1;
    private val newWordActivityRequestCode2 = 2;
    private lateinit var shared_preferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        shared_preferences = getSharedPreferences("shared_preferences", Context.MODE_PRIVATE)



        //Abrir notas pessoais
        val notasPessoais = findViewById<Button>(R.id.notasPessoais)
        notasPessoais.setOnClickListener {
            val intent = Intent(this@MenuActivity, MainActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }
        val reportsMap = findViewById<Button>(R.id.reportsMap)
        reportsMap.setOnClickListener {
            val intent = Intent(this@MenuActivity, MapsActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode2)
        }

    }

    fun logout(view: View) {
        val shared_preferences_edit : SharedPreferences.Editor = shared_preferences.edit()
        shared_preferences_edit.clear()
        shared_preferences_edit.apply()

        val intent = Intent(this@MenuActivity, MainLogin::class.java)
        startActivity(intent)
        finish()
    }


}