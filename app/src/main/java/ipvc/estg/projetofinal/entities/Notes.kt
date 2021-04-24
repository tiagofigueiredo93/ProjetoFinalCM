package ipvc.estg.projetofinal.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "notes_table")
 class Notes(

    @PrimaryKey(autoGenerate = true) val id: Int?=null,

    @ColumnInfo(name = "title") val title: String,

    @ColumnInfo(name = "description") val description: String,

    @ColumnInfo(name = "note_text") val noteText: String,

    @ColumnInfo(name = "date_time") val dateTime:String? = null


)
