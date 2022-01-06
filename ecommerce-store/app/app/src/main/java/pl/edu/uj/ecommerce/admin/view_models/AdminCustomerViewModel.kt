package pl.edu.uj.ecommerce.admin.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AdminCustomerViewModel : ViewModel() {
    val customerListString = MutableLiveData<String>()

}