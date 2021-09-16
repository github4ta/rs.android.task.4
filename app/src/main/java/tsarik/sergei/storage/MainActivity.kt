package tsarik.sergei.storage

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_main.*
import tsarik.sergei.storage.db.AnimalModel
import tsarik.sergei.storage.db.AnimalsDatabaseHandler
import tsarik.sergei.storage.db.AnimalsListAdapter
import tsarik.sergei.storage.sort.SortAnimalActivity

class MainActivity : AppCompatActivity() {

    private var animalsArrayId = mutableListOf<String>()
    private var animalsArrayName = mutableListOf<String>()
    private var animalsArrayAge = mutableListOf<String>()
    private var animalsArrayBreed = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addNewAnimalButton.setOnClickListener {
            val intent = Intent(this, AddNewAnimalActivity::class.java)
            startActivity(intent)
        }

        recycler.onItemClickListener = AdapterView.OnItemClickListener {
                parent, view, position, id ->
            val intent = Intent(this, UpdateDeleteAnimalActivity::class.java)
            intent.putExtra(Extras.ID.name, animalsArrayId[id.toInt()])
            intent.putExtra(Extras.NAME.name, animalsArrayName[id.toInt()])
            intent.putExtra(Extras.AGE.name, animalsArrayAge[id.toInt()])
            intent.putExtra(Extras.BREED.name, animalsArrayBreed[id.toInt()])
            startActivity(intent)
        }
    }

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
        var sortByColumn = prefs.getString("sortByColumn", "0").toString()
        viewRecordsSortedBy(sortByColumn)
        super.onResume()
    }

    private fun viewRecordsSortedBy(column: String) {
        var animals: List<AnimalModel> = getAnimalsFromDb()
        animals = sortAnimalsByColumn(column, animals)
        dataProcessing(animals)
        setDataToView()
    }

    private fun sortAnimalsByColumn(
        sortByColumn: String,
        animals: List<AnimalModel>
    ): List<AnimalModel> {
        return when (sortByColumn) {
            SORT_BY_NAME -> animals.sortedBy { it.name }
            SORT_BY_AGE -> animals.sortedBy { it.age }
            SORT_BY_BREED -> animals.sortedBy { it.breed }
            else -> animals.sortedBy { it.id }
        }
    }

    private fun viewRecords(){
        val animals: List<AnimalModel> = getAnimalsFromDb()
        dataProcessing(animals)
        setDataToView()
    }

    private fun setDataToView() {
        val animalsListAdapter =
            AnimalsListAdapter(this, animalsArrayName, animalsArrayAge, animalsArrayBreed)
        recycler.adapter = animalsListAdapter
    }

    private fun getAnimalsFromDb(): List<AnimalModel> {
        val databaseHandler: AnimalsDatabaseHandler = AnimalsDatabaseHandler(this)
        val animals: List<AnimalModel> = databaseHandler.readAllDataFromDb()
        return animals
    }

    private fun dataProcessing(animals: List<AnimalModel>) {
        animalsArrayId = mutableListOf<String>()
        animalsArrayName = mutableListOf<String>()
        animalsArrayAge = mutableListOf<String>()
        animalsArrayBreed = mutableListOf<String>()
        for ((index, animal) in animals.withIndex()) {
            animalsArrayId.add(index, animal.id)
            animalsArrayName.add(index, animal.name)
            animalsArrayAge.add(index, animal.age)
            animalsArrayBreed.add(index, animal.breed)
        }
    }

    companion object {
        private const val SORT_BY_NAME = "1"
        private const val SORT_BY_AGE = "2"
        private const val SORT_BY_BREED = "3"
    }
}