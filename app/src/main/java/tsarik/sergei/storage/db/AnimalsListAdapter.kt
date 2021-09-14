package tsarik.sergei.storage.db

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import tsarik.sergei.storage.R

class AnimalsListAdapter(
    private val context: Activity,
    private val name: List<String>,
    private val age: List<String>,
    private val breed: List<String>
) : ArrayAdapter<String>(context, R.layout.animal_item, name) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.animal_item, null, true)

        val nameText = rowView.findViewById(R.id.animal_name) as TextView
        val ageText = rowView.findViewById(R.id.animal_age) as TextView
        val breedText = rowView.findViewById(R.id.animal_breed) as TextView

        nameText.text = "Name: ${name[position]}"
        ageText.text = "Age: ${age[position]}"
        breedText.text = "Breed: ${breed[position]}"


        return rowView
    }


}