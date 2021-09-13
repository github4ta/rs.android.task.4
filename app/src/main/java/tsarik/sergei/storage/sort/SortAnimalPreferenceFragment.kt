package tsarik.sergei.storage.sort

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import tsarik.sergei.storage.R

class SortAnimalPreferenceFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        return
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.filter)
    }
}