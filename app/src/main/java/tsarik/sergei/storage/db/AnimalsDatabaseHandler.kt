package tsarik.sergei.storage.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import tsarik.sergei.storage.utils.Utils
import kotlin.random.Random

class AnimalsDatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_ANIMALS, null, DATABASE_VERSION) {

    private lateinit var db: SQLiteDatabase
    private lateinit var context: Context

    override fun onCreate(db: SQLiteDatabase) {
        this.db = db
        val CREATE_ANIMALS_TABLE = ("CREATE TABLE " + TABLE_ANIMALS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
                + KEY_AGE + " INTEGER," + KEY_BREED + " TEXT" + ")")
        db.execSQL(CREATE_ANIMALS_TABLE)

        populateTableWithAnimals(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        this.db = db
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ANIMALS")
        onCreate(db)
    }

    fun addAnimal(animal: AnimalModel, db: SQLiteDatabase): Long {
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, animal.name)
        contentValues.put(KEY_AGE, animal.age)
        contentValues.put(KEY_BREED, animal.breed)
        val rowID = db.insert(TABLE_ANIMALS, null, contentValues)
        return rowID
    }

    fun addAnimal2(animal: AnimalModel): Long {
        val db = this.writableDatabase
        return addAnimal(animal, db)
    }

    fun deleteAnimal(row_id: String): Boolean {
        var isDeleted = false
        val db = this.writableDatabase
        val result = db.delete(TABLE_ANIMALS,"$KEY_ID=?", arrayOf(row_id))
        if (result == -1) {
        } else {
            isDeleted = true
        }
        return isDeleted
    }

    fun updateAnimal(animal: AnimalModel): Boolean {
        var isUpdated = false
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, animal.name)
        contentValues.put(KEY_AGE, animal.age)
        contentValues.put(KEY_BREED, animal.breed)
        val result = db.update(TABLE_ANIMALS, contentValues, "$KEY_ID=?", arrayOf(animal.id))
        if (result == -1) {
        } else {
            isUpdated = true
        }
        return isUpdated
    }

    private fun populateTableWithAnimals(db: SQLiteDatabase) {
        for (i in 1..12) {
            addAnimal(
                AnimalModel(
                    i.toString(),
                    Utils.generateString(7),
                    Random.nextInt(1, 5).toString(),
                    Utils.generateString(10)
                ),
                db
            )
        }
    }

    @SuppressLint("Recycle")
    fun readAllDataFromDb(): List<AnimalModel> {
        val animals: ArrayList<AnimalModel> = ArrayList<AnimalModel>()
        val selectQuery = "SELECT * FROM $TABLE_ANIMALS"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id: String
        var name: String
        var age: String
        var breed: String
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(KEY_ID)).toString()
                name = cursor.getString(cursor.getColumnIndex(KEY_NAME))
                age = cursor.getString(cursor.getColumnIndex(KEY_AGE))
                breed = cursor.getString(cursor.getColumnIndex(KEY_BREED))
                val animal = AnimalModel(id = id, name = name, age = age, breed = breed)
                animals.add(animal)
            } while (cursor.moveToNext())
        }
        return animals
    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_ANIMALS = "AnimalsDatabase"
        private const val TABLE_ANIMALS = "AnimalsTable"
        private const val KEY_ID = "id"
        private const val KEY_NAME = "name"
        private const val KEY_AGE = "age"
        private const val KEY_BREED = "breed"
    }
}