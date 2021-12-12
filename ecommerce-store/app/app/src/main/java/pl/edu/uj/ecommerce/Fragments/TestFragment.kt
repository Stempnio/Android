package pl.edu.uj.ecommerce.Fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import pl.edu.uj.ecommerce.R

//TODO delete

class TestFragments : Fragment(R.layout.fragment_test) {

    lateinit var button: Button
    lateinit var textView: TextView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val bt : Button = getView()
    }
}