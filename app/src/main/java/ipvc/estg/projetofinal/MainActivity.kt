package ipvc.estg.projetofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import ipvc.estg.projetofinal.dataClasses.Notes

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("***TAG", "onCreate")






    }
    override fun onStart() {
        super.onStart()


        Log.d("***TAG", "onStart")

    }

    override fun onResume() {

        super.onResume()
        Log.d("***TAG", "onResume")
    }
    override fun onPause() {
        super.onPause()
        Log.d("***TAG", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("***TAG", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("***TAG", "onDestroy")
    }




    fun openActivityCreateNotes(view: View) {
        val intent = Intent(this, CreateNote::class.java).apply {




        }
        startActivity(intent)
    }




}