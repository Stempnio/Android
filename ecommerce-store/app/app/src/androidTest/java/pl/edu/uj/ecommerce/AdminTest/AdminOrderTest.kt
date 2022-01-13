package pl.edu.uj.ecommerce.AdminTest

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import pl.edu.uj.ecommerce.*
import pl.edu.uj.ecommerce.admin.AdminOrderFragment

@RunWith(AndroidJUnit4::class)
class AdminOrderTest {

    private lateinit var navController : TestNavHostController
    private lateinit var scenario : FragmentScenario<AdminOrderFragment>

    @Before
    fun setup() {

        addTestCustomer()
        addTestProduct()
        addTestOrder()
        addTestAdmin()

        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        scenario = launchFragmentInContainer()

        scenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)

            Navigation.setViewNavController(fragment.requireView(), navController)
            navController.setCurrentDestination(R.id.adminOrderFragment)
        }
    }

    @Test
    fun testRefreshOrders() {
        Espresso.onView(ViewMatchers.withId(R.id.btnAdminOrderRefresh)).perform(scrollTo(), ViewActions.click())

        val testOrder = getTestOrder()

        val expectedString = "customer ID: " + testOrder.customerId +
                " | date: " + testOrder.date +
                " | order ID: " + testOrder.id + "\n"

        Espresso.onView(ViewMatchers.withId(R.id.tvAdminOrderList))
            .check(ViewAssertions.matches(ViewMatchers.withText(expectedString)))
    }

    @Test
    fun testDeleteAllOrders() {
        Espresso.onView(ViewMatchers.withId(R.id.btnAdminOrderDeleteAll)).perform(scrollTo(), ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.btnAdminOrderRefresh)).perform(scrollTo(), ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.tvAdminOrderList))
            .check(ViewAssertions.matches(ViewMatchers.withText("")))
    }

    @Test
    fun testGetOderDetailsById() {

        val testCustomerOrders = RetrofitService
            .create()
            .getCustomerOrderDetailsCall(testCustomer.id)
            .execute()
            .body()

        Truth.assertThat(testCustomerOrders).isNotNull()
        Truth.assertThat(testCustomerOrders).isNotEmpty()
        Truth.assertThat(testCustomerOrders!!.size).isEqualTo(1)

        val expectedString = getTestOrderToString(testCustomerOrders)

        Espresso.onView(ViewMatchers.withId(R.id.etAdminOrderDetailsGetById))
            .perform(ViewActions.typeText(testCustomerOrders[0].orderId.toString()))

        Espresso.closeSoftKeyboard()

        Espresso.onView(ViewMatchers.withId(R.id.btnAdminOrderDetailsGetById)).perform(scrollTo(), ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.tvAdminOrderDetailsGetById))
            .check(ViewAssertions.matches(ViewMatchers.withText(expectedString)))

    }

    @Test
    fun testDeleteOrderById() {

        val id = getTestOrderId()
        Truth.assertThat(id).isNotEqualTo(-1)

        Espresso.onView(ViewMatchers.withId(R.id.etAdminOrderDeleteById))
            .perform(ViewActions.typeText(id.toString()))

        Espresso.closeSoftKeyboard()

        Espresso.onView(ViewMatchers.withId(R.id.btnAdminOrderDeleteById)).perform(scrollTo(), ViewActions.click())
    }

}