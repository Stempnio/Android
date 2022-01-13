package pl.edu.uj.ecommerce.AdminTest

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import pl.edu.uj.ecommerce.*
import pl.edu.uj.ecommerce.admin.AdminHomeFragment

@RunWith(AndroidJUnit4::class)
class AdminHomeTest {
    private lateinit var navController : TestNavHostController
    private lateinit var scenario : FragmentScenario<AdminHomeFragment>

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
            navController.setCurrentDestination(R.id.adminHomeFragment)
        }
    }

    @Test
    fun testGoToAdminCart() {
        Espresso.onView(ViewMatchers.withId(R.id.btnAdminHomeCarts)).perform(ViewActions.click())

        Truth.assertThat(navController.currentDestination?.id).isEqualTo(R.id.adminCartFragment)
    }

    @Test
    fun testGoToAdminCustomer() {
        Espresso.onView(ViewMatchers.withId(R.id.btnAdminHomeCustomers)).perform(ViewActions.click())

        Truth.assertThat(navController.currentDestination?.id).isEqualTo(R.id.adminCustomerFragment)
    }

    @Test
    fun testGoToAdminOrder() {
        Espresso.onView(ViewMatchers.withId(R.id.btnAdminHomeOrders)).perform(ViewActions.click())

        Truth.assertThat(navController.currentDestination?.id).isEqualTo(R.id.adminOrderFragment)
    }

    @Test
    fun testGoToAdminProducts() {
        Espresso.onView(ViewMatchers.withId(R.id.btnAdminHomeProducts)).perform(ViewActions.click())

        Truth.assertThat(navController.currentDestination?.id).isEqualTo(R.id.adminProductsFragment)
    }

    @Test
    fun testAdminHomeLogOut() {
        Espresso.onView(ViewMatchers.withId(R.id.btnAdminHomeLogOut)).perform(ViewActions.click())

        Truth.assertThat(navController.currentDestination?.id).isEqualTo(R.id.logInFragment)
    }
}