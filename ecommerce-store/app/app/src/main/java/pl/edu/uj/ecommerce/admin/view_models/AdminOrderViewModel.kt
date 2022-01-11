package pl.edu.uj.ecommerce.admin.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AdminOrderViewModel : ViewModel() {
    val orderListString = MutableLiveData<String>()

    val orderDetailsListString = MutableLiveData<String>()

}