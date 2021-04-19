package ipvc.estg.projetofinal

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils

import android.widget.Button
import android.widget.EditText
import kotlin.collections.ArrayList


class CreateNote : AppCompatActivity() {

    private lateinit var editTitleView: EditText
    private lateinit var editTitleView2: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)

        editTitleView = findViewById(R.id.insertTite)
        editTitleView2 = findViewById(R.id.insertDescription)

        val button = findViewById<Button>(R.id.saveNote)
        button.setOnClickListener {
            val replyIntent = Intent()

            if (TextUtils.isEmpty(editTitleView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            }else if(TextUtils.isEmpty(editTitleView2.text)){
                setResult(Activity.RESULT_CANCELED, replyIntent)
            }

            else {
                val title = editTitleView.text.toString()
                val description = editTitleView2.text.toString()
                replyIntent.putExtra(EXTRA_REPLY_TITLE, title)
                replyIntent.putExtra(EXTRA_REPLY_DESCRIPTION,description)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }
    companion object{
        const val EXTRA_REPLY_TITLE = "com.example.android.title"
        const val EXTRA_REPLY_DESCRIPTION = "com.example.android.description"
    }
}