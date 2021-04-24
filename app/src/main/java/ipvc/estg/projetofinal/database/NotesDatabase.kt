package ipvc.estg.projetofinal.database

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import ipvc.estg.projetofinal.dao.NoteDao
import ipvc.estg.projetofinal.entities.Notes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Notes::class], version = 7, exportSchema = false)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback(){
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var noteDao = database.noteDao()

                    //noteDao.deleteAll()
                    /*var note = Notes(1, "Nota 1","","","")
                    noteDao.insert(note)

                    note = Notes(2, "Nota 2", "", "", "")
                    noteDao.insert(note)*/
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: NotesDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): NotesDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotesDatabase::class.java,
                    "notes_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(WordDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                return instance

            }
        }
    }
}
