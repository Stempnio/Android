package pl.edu.uj.ecommerce.Fragments

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import pl.edu.uj.ecommerce.*


@RunWith(AndroidJUnit4::class)
class BuyFragmentTest {

    private lateinit var navController: TestNavHostController
    private lateinit var scenario: FragmentScenario<BuyFragment>

    @Before
    fun setup() {

        addTestCustomer()
        addTestProduct()
        addTestCartItem()

        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        scenario = launchFragmentInContainer()

        scenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)

            Navigation.setViewNavController(fragment.requireView(), navController)
            navController.setCurrentDestination(R.id.buyFragment)

            fragment.setTestCard()
            fragment.getClientSecret()
        }
    }

    @Test
    fun testCorrectPrice() {
        Espresso.onView(ViewMatchers.withId(R.id.tvBuyFragmentTotalPrice)).check(matches(withText(
            testProduct.price.toString())))
    }

    @Test
    fun testCorrectCartDetails() {
        val expectedString = "Product: ${testProduct.name}, price: ${testProduct.price}, quantity: 1\n\n"

        Espresso.onView(ViewMatchers.withId(R.id.tvBuyFragmentCartDetails)).check(matches(withText(
            expectedString)))
    }

    @Test
    fun testBuy() {
        RetrofitService
            .create()
            .deleteAllOrders()
            .execute()

        wait(3000)

        Espresso.onView(ViewMatchers.withId(R.id.buttonBuyFragmentPlaceOrder))
            .perform(click())

        wait(3000)

        val order = getTestOrder()

        Truth.assertThat(order).isNotNull()
        Truth.assertThat(order.customerId).isEqualTo(testCustomer.id)

        val orderDetails = getTestOrderDetails(order.id)

        Truth.assertThat(orderDetails).isNotNull()
        Truth.assertThat(orderDetails).isNotEmpty()

        val orderDetailsString = getTestOrderToString(orderDetails!!)

        Truth.assertThat(orderDetailsString).contains(testProduct.id.toString())
        Truth.assertThat(orderDetailsString).contains(order.id.toString())

        Truth.assertThat(navController.currentDestination?.id).isEqualTo(R.id.productsFragment)
    }

}
