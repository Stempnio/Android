package pl.edu.uj.ecommerce.Fragments

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import org.hamcrest.core.StringContains.containsString
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import pl.edu.uj.ecommerce.*
import pl.edu.uj.ecommerce.Data.CURRENT_CUSTOMER_ID
import pl.edu.uj.ecommerce.Data.OrderDetails

@RunWith(AndroidJUnit4::class)
class OrderDetailsFragmentTest {

    private lateinit var navController : TestNavHostController
    private lateinit var scenario : FragmentScenario<OrderDetailsFragment>

    private var orderDetails : OrderDetails? = null

    @Before
    fun setup() {

        addTestCustomer()
        addTestProduct()
        addTestOrder()

        wait(1000)

        val orderDetailsList = RetrofitService
            .create()
            .getCustomerOrderDetailsCall(CURRENT_CUSTOMER_ID)
            .execute()
            .body()

        orderDetails = orderDetailsList?.get(0)

        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        scenario = launchFragmentInContainer()

        scenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)

            Navigation.setViewNavController(fragment.requireView(), navController)
            navController.setCurrentDestination(R.id.orderDetailsFragment)

            if(orderDetails != null)
                fragment.setOrderDetailsText(orderDetails!!.orderId)
        }
    }

    @Test
    fun testCorrectOrderDetails() {
        Truth.assertThat(orderDetails).isNotNull()

        onView(withId(R.id.tvOrderDetails)).check(matches(withText(containsString("Product: ${testProduct.name}"))))
        onView(withId(R.id.tvOrderDetails)).check(matches(withText(containsString("Order ID: ${orderDetails!!.orderId}"))))
        onView(withId(R.id.tvOrderDetails)).check(matches(withText(containsString("price: ${testProduct.price}"))))
    }

    @Test
    fun testCorrectOrderDetailsHeader() {
        Truth.assertThat(orderDetails).isNotNull()

        val expectedString = "DETAILS OF ORDER: ${orderDetails!!.orderId}"

        onView(withId(R.id.tvOrderDetailsHeader)).check(matches(withText(expectedString)))
    }


}