package tsarik.sergei.storage

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class UpdateDeleteAnimalActivity : AppCompatActivity() {

    private var selectedAnimalId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_delete_animal)

        val animalNameView: TextView = findViewById(R.id.updatedAnimalName)
        val animalAgeView: TextView = findViewById(R.id.updatedAnimalAge)
        val animalBreedView: TextView = findViewById(R.id.updatedAnimalBreed)

        val intentExtras = intent.extras
        if (intentExtras != null) {
            selectedAnimalId = intentExtras.getInt("selectedItemId")
            animalNameView.text = intentExtras.getString("selectedItemName").toString()
            animalAgeView.text = intentExtras.getString("selectedItemAge").toString()
            animalBreedView.text = intentExtras.getString("selectedItemBreed").toString()
        }

    }

    override fun onResume() {
        super.onResume()
    }
}