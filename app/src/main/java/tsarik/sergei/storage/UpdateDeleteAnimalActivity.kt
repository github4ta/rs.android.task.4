package tsarik.sergei.storage

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_update_delete_animal.*
import tsarik.sergei.storage.db.AnimalModel
import tsarik.sergei.storage.db.AnimalsDatabaseHandler

class UpdateDeleteAnimalActivity : AppCompatActivity() {

    private lateinit var animal: AnimalModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_delete_animal)

        val animalNameView: TextView = findViewById(R.id.updatedAnimalName)
        val animalAgeView: TextView = findViewById(R.id.updatedAnimalAge)
        val animalBreedView: TextView = findViewById(R.id.updatedAnimalBreed)

        val intentExtras = intent.extras
        if (intentExtras != null) {
            animal = AnimalModel(
                intentExtras.getString("selectedItemId").toString(),
                intentExtras.getString("selectedItemName").toString(),
                intentExtras.getString("selectedItemAge").toString(),
                intentExtras.getString("selectedItemBreed").toString()
            )
        }

        animalNameView.text = animal.name
        animalAgeView.text = animal.age
        animalBreedView.text = animal.breed

        deleteAnimalButton.setOnClickListener {
            val databaseHandler: AnimalsDatabaseHandler = AnimalsDatabaseHandler(this)
            if (!databaseHandler.deleteAnimal(animal.id)) {
                Toast.makeText(this, "Failed to Delete.", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

        updateAnimalButton.setOnClickListener {
            val animalNameView: TextView = findViewById(R.id.updatedAnimalName)
            val animalAgeView: TextView = findViewById(R.id.updatedAnimalAge)
            val animalBreedView: TextView = findViewById(R.id.updatedAnimalBreed)

            animal.name = animalNameView.text.toString()
            animal.age = animalAgeView.text.toString()
            animal.breed = animalBreedView.text.toString()

            val databaseHandler: AnimalsDatabaseHandler = AnimalsDatabaseHandler(this)

            if (!databaseHandler.updateAnimal(animal)) {
                Toast.makeText(this, "Failed to Update.", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }
}