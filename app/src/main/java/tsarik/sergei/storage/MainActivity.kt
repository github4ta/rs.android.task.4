package tsarik.sergei.storage

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_main.*
import tsarik.sergei.storage.db.AnimalModel
import tsarik.sergei.storage.db.AnimalsDatabaseHandler
import tsarik.sergei.storage.db.AnimalsListAdapter
import tsarik.sergei.storage.sort.SortAnimalActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addNewAnimalButton.setOnClickListener {
            val intent = Intent(this, AddNewAnimalActivity::class.java)
            startActivity(intent)
        }

        viewRecords() // происходит обращение к БД и вывод данных в список
    }

    // за что отвечает onActivityResult ?
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            recreate()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.sortButton) {
            val intent = Intent(this, SortAnimalActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
//        textView1.text = prefs.getBoolean("sort_by_name", false).toString()
//        textView2.text = prefs.getBoolean("sort_by_age", false).toString()
//        textView3.text = prefs.getBoolean("sort_by_breed", false).toString()
//        textView4.text = prefs.getString("downloadType", "0")
        super.onResume()
    }

    private fun viewRecords(){
        val databaseHandler: AnimalsDatabaseHandler = AnimalsDatabaseHandler(this)
        val animals: List<AnimalModel> = databaseHandler.readAllDataFromDb()
        val animalsArrayId = Array<String>(animals.size){"0"}
        val animalsArrayName = Array<String>(animals.size){"null"}
        val animalsArrayAge = Array<String>(animals.size){"0"}
        val animalsArrayBreed = Array<String>(animals.size){"null"}
        for((index, animal) in animals.withIndex()){
            animalsArrayId[index] = animal.id.toString()
            animalsArrayName[index] = animal.name
            animalsArrayAge[index] = animal.age.toString()
            animalsArrayBreed[index] = animal.breed
        }
        //creating custom ArrayAdapter
        val animalsListAdapter = AnimalsListAdapter(this, animalsArrayName, animalsArrayAge, animalsArrayBreed)
        recycler.adapter = animalsListAdapter
    }
}