package ipvc.estg.projetofinal.database

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import ipvc.estg.projetofinal.dao.NoteDao
import ipvc.estg.projetofinal.entities.Notes

@Database(entities = [Notes::class], version = 1, exportSchema = false)
abstract class NotesDatabase: RoomDatabase() {





    companion object {
        var notesDatabase: NotesDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): NotesDatabase {
            if (notesDatabase != null) {
                notesDatabase = Room.databaseBuilder(
                    context, NotesDatabase::class.java, "notes.db"
                ).build()
            }
            return notesDatabase!!
        }
    }
        abstract fun noteDao():NoteDao
    }
