package tsarik.sergei.storage

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_update_delete_animal.*
import tsarik.sergei.storage.db.AnimalModel
import tsarik.sergei.storage.db.AnimalsDatabaseHandler
import java.util.*

class UpdateDeleteAnimalActivity : AppCompatActivity() {

    private lateinit var animal: AnimalModel
    private lateinit var animalNameView: TextView
    private lateinit var animalAgeView: TextView
    private lateinit var animalBreedView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_delete_animal)

        animalNameView = findViewById(R.id.updatedAnimalName)
        animalAgeView = findViewById(R.id.updatedAnimalAge)
        animalBreedView = findViewById(R.id.updatedAnimalBreed)

        val intentExtras = intent.extras
        if (intentExtras != null) {
            animal = AnimalModel(
                intentExtras.getString(Extras.ID.name).toString(),
                intentExtras.getString(Extras.NAME.name).toString(),
                intentExtras.getString(Extras.AGE.name).toString(),
                intentExtras.getString(Extras.BREED.name).toString()
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
            animal.name = animalNameView.text.toString().lowercase(Locale.getDefault()).replaceFirstChar { it.uppercase() }
            animal.age = animalAgeView.text.toString()
            animal.breed = animalBreedView.text.toString().lowercase(Locale.getDefault()).replaceFirstChar { it.uppercase() }

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