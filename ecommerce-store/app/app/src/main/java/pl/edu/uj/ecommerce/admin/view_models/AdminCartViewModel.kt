package pl.edu.uj.ecommerce.admin.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AdminCartViewModel : ViewModel() {
    val cartListString = MutableLiveData<String>()
}