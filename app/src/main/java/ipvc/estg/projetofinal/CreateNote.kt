package ipvc.estg.projetofinal

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View

import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class CreateNote : AppCompatActivity() {

    private lateinit var editTitleView: EditText
    private lateinit var editTitleView2: EditText
    private lateinit var editTitleView3: TextView
    private lateinit var editTitleView4: EditText

    var currentData:String? = null


    private val newWordActivityRequestCode = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)

        editTitleView = findViewById(R.id.insertTite)
        editTitleView2 = findViewById(R.id.insertDescription)
        editTitleView3 = findViewById(R.id.tvDateTime)
        editTitleView4 = findViewById(R.id.insertText)

        val data = SimpleDateFormat("dd/MM/yy HH:mm")
        currentData = data.format(Date())

        editTitleView3.text = currentData

        val button = findViewById<Button>(R.id.saveNote)
        button.setOnClickListener {
            val replyIntent = Intent()

            //verificar se os campos estão vazios
            if (TextUtils.isEmpty(editTitleView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            }else if(TextUtils.isEmpty(editTitleView2.text)){
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else if(TextUtils.isEmpty(editTitleView4.text)){
                setResult(Activity.RESULT_CANCELED, replyIntent)
            }


            else {
                val title = editTitleView.text.toString()
                val description = editTitleView2.text.toString()
                val dateTime = editTitleView3.text.toString()
                val text = editTitleView4.text.toString()

                replyIntent.putExtra(EXTRA_REPLY_TITLE, title)
                replyIntent.putExtra(EXTRA_REPLY_DESCRIPTION,description)
                replyIntent.putExtra(EXTRA_REPLY_DATE,dateTime)
                replyIntent.putExtra(EXTRA_REPLY_TEXT,text)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }

        val buttonBack = findViewById<ImageView>(R.id.img_back)

        buttonBack.setOnClickListener {
            val intent = Intent(this@CreateNote, MainActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }


    }
    companion object{
        const val EXTRA_REPLY_TITLE = "com.example.android.title"
        const val EXTRA_REPLY_DESCRIPTION = "com.example.android.description"
        const val EXTRA_REPLY_DATE = "com.example.android.dateTime"
        const val EXTRA_REPLY_TEXT = "com.example.android.text"
    }


}