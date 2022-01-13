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
    fun testDeleteAllOrders() {
        Espresso.onView(ViewMatchers.withId(R.id.btnAdminOrderDeleteAll)).perform(scrollTo(), ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.btnAdminOrderRefresh)).perform(scrollTo(), ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.tvAdminOrderList))
            .check(ViewAssertions.matches(ViewMatchers.withText("")))
    }



}