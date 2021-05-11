package ipvc.estg.projetofinal

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ipvc.estg.projetofinal.MainLogin.Companion.USERNAME

class MenuActivity : AppCompatActivity() {

    private val newWordActivityRequestCode = 1;
    private val newWordActivityRequestCode2 = 2;
    private val newWordActivityRequestCode3  = 3;
    private val newWordActivityRequestCode4  = 4;
    private lateinit var shared_preferences: SharedPreferences
    private lateinit var textUtilizador: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        shared_preferences = getSharedPreferences("shared_preferences", Context.MODE_PRIVATE)


        //Nome do utilizador no menu
        val username = shared_preferences.getString(USERNAME,"")

        val id = shared_preferences.getInt("id",0)
        textUtilizador = findViewById(R.id.textUsername)
        textUtilizador.text = "$username"+" id: $id"

        //Criar
        val criar = findViewById<Button>(R.id.createReportMenu)
        criar.setOnClickListener {
            val intent = Intent(this@MenuActivity, CreateReport::class.java)
            startActivityForResult(intent, newWordActivityRequestCode4)
        }
            //Abrir notas pessoais
        val notasPessoais = findViewById<Button>(R.id.notasPessoais)
        notasPessoais.setOnClickListener {
            val intent = Intent(this@MenuActivity, MainActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }

        //Abrir Mapa
        val reportsMap = findViewById<Button>(R.id.reportsMap)
        reportsMap.setOnClickListener {
            val intent = Intent(this@MenuActivity, MapsActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode2)
        }

        val reportsList = findViewById<Button>(R.id.reportsList)
        reportsList.setOnClickListener {
            val intent = Intent(this@MenuActivity, AllReports::class.java)
            startActivityForResult(intent, newWordActivityRequestCode3)
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