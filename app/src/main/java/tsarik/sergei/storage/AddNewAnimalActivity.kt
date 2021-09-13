package tsarik.sergei.storage

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_new_animal.*
import tsarik.sergei.storage.db.AnimalModel
import tsarik.sergei.storage.db.AnimalsDatabaseHandler

class AddNewAnimalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_animal)

        addAnimalButton.setOnClickListener {
            val animalNameView: TextView = findViewById(R.id.newAnimalName)
            val animalAgeView: TextView = findViewById(R.id.newAnimalAge)
            val animalBreedView: TextView = findViewById(R.id.newAnimalBreed)

            val animalName = animalNameView.text.toString()
            val animalAge = animalAgeView.text.toString().toInt()
            val animalBreed = animalBreedView.text.toString()

            var animal: AnimalModel = AnimalModel(0, animalName, animalAge, animalBreed)

            val databaseHandler: AnimalsDatabaseHandler = AnimalsDatabaseHandler(this)
            databaseHandler.addAnimal2(animal)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
    }
}