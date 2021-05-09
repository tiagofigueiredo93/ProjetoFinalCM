package ipvc.estg.projetofinal

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ipvc.estg.projetofinal.api.EndPoints
import ipvc.estg.projetofinal.api.OutputLogin
import ipvc.estg.projetofinal.api.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class MainLogin : AppCompatActivity() {


    private lateinit var editUsernameView: EditText
    private lateinit var editPasswordView: EditText
    private lateinit var checkboxRemeber: CheckBox
    private lateinit var shared_preferences: SharedPreferences
    private var check = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_login)

        editUsernameView = findViewById(R.id.username)
        editPasswordView = findViewById(R.id.password)
        checkboxRemeber = findViewById(R.id.checkBox_Remeber)

        //inicialização da variavel shared_preferences e colocamos em modo privado
        shared_preferences = getSharedPreferences("shared_preferences", Context.MODE_PRIVATE)
        //começar o "check" a false
        check = shared_preferences.getBoolean(REMEMBER, false)


        //verificação checkbox ativa
        if(check){
            val intent = Intent(this@MainLogin, MenuActivity::class.java)
            startActivity(intent);
            finish()
        }
    }

    //Botão login
    fun login(view: View){

        //Obtenção de uma instância do retrofit
        val request = ServiceBuilder.buildService(EndPoints::class.java)
        //Buscar os parametros inseridos nos EditTexts
        val username = editUsernameView.text.toString()
        val password = editPasswordView.text.toString()
        val checked_remember: Boolean = checkboxRemeber.isChecked

        //Passar o username e password que o utilizador inserir
        val call = request.login(username = username, password = password)
        call.enqueue(object : Callback<OutputLogin> {

            override fun onResponse(call: Call<OutputLogin>, response: Response<OutputLogin>){
                if (response.isSuccessful){

                    //output do json
                    val c: OutputLogin = response.body()!!
                    if(TextUtils.isEmpty(editUsernameView.text) || TextUtils.isEmpty(editPasswordView.text)) {
                        Toast.makeText(this@MainLogin, "Campos vazios, insira login válido!", Toast.LENGTH_LONG).show()
                    }else{
                        if(c.status =="false"){
                            Toast.makeText(this@MainLogin, c.Mensagem, Toast.LENGTH_LONG).show()
                        }else{

                            //shared_preferences.edit()
                            val shared_preferences_edit : SharedPreferences.Editor = shared_preferences.edit()
                            shared_preferences_edit.putString(USERNAME, username)
                            shared_preferences_edit.putString(PASSWORD, password)
                            shared_preferences_edit.putInt(ID_UITLIZADOR, c.id)
                            shared_preferences_edit.putBoolean(REMEMBER, checked_remember)
                            //Guardar os campos nas preferencias com o apply
                            shared_preferences_edit.apply()




                            val intent = Intent(this@MainLogin, MenuActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }
            override fun onFailure(call: Call<OutputLogin>, t: Throwable){
                Toast.makeText(this@MainLogin,"${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object{
        const val USERNAME = "username"
        const val PASSWORD = "pasword"
        const val REMEMBER = "remember"
        const val ID_UITLIZADOR = "id"

    }

}