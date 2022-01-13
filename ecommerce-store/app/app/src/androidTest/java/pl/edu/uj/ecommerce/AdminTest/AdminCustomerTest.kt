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
import pl.edu.uj.ecommerce.admin.AdminCustomerFragment

@RunWith(AndroidJUnit4::class)
class AdminCustomerTest {
    private lateinit var navController : TestNavHostController
    private lateinit var scenario : FragmentScenario<AdminCustomerFragment>

    @Before
    fun setup() {

        addTestCustomer()
        addTestProduct()
        addTestCartItem()
        addTestAdmin()

        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        scenario = launchFragmentInContainer()

        scenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)

            Navigation.setViewNavController(fragment.requireView(), navController)
            navController.setCurrentDestination(R.id.adminCustomerFragment)

            fragment.displayCustomers(mutableListOf(testCustomer))
        }
    }

    @Test
    fun testRefreshCustomers() {

        val customerList = getAllCustomers()

        Truth.assertThat(customerList).isNotNull()
        Truth.assertThat(customerList).isNotEmpty()

        var expectedString = ""
        customerList.forEach {
            expectedString += "customerID:" +
                    it.id + " | first name:" +
                    it.firstName + " | last name:" +
                    it.lastName + " | email:" +
                    it.email + "\n"
        }

        Espresso.onView(ViewMatchers.withId(R.id.btnAdminCustomerRefresh)).perform(ViewActions.click())


        Espresso.onView(ViewMatchers.withId(R.id.tvAdminCustomerList))
            .check(ViewAssertions.matches(ViewMatchers.withText(expectedString)))
    }

    @Test
    fun testDeleteAllCustomers() {

        Espresso.onView(ViewMatchers.withId(R.id.btnAdminCustomerDeleteAll)).perform(scrollTo(), ViewActions.click())

        val customerList = getAllCustomers()

        Truth.assertThat(customerList).isNotNull()
        Truth.assertThat(customerList).isEmpty()

    }

    @Test
    fun testDeleteCustomerById() {

        Espresso.onView(ViewMatchers.withId(R.id.etAdminCustomerDeleteById))
            .perform(scrollTo(), ViewActions.typeText(testCustomer.id))

        Espresso.closeSoftKeyboard()

        Espresso.onView(ViewMatchers.withId(R.id.btnAdminCustomerDeleteById)).perform(scrollTo(), ViewActions.click())

        val customerList = getAllCustomers()

        Truth.assertThat(customerList).isNotNull()
        Truth.assertThat(customerList).doesNotContain(testCustomer)

    }
}