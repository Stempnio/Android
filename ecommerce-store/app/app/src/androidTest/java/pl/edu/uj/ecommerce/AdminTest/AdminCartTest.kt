package pl.edu.uj.ecommerce.AdminTest

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import pl.edu.uj.ecommerce.*
import pl.edu.uj.ecommerce.admin.AdminCartFragment

@RunWith(AndroidJUnit4::class)
class AdminCartTest {
    private lateinit var navController : TestNavHostController
    private lateinit var scenario : FragmentScenario<AdminCartFragment>

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
            navController.setCurrentDestination(R.id.adminCartFragment)
        }
    }

    @Test
    fun testRefreshCart() {
        Espresso.onView(ViewMatchers.withId(R.id.btnAdminCartRefresh)).perform(ViewActions.click())

        val expectedString = "customerID:testCustomer| product:${testProduct.id}| quantity:1\n"

        Espresso.onView(ViewMatchers.withId(R.id.tvAdminCartList))
            .check(ViewAssertions.matches(ViewMatchers.withText(expectedString)))
    }

    @Test
    fun testDeleteAndRefreshCart() {
        Espresso.onView(ViewMatchers.withId(R.id.btnAdminCartDeleteAll)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.btnAdminCartRefresh)).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.tvAdminCartList))
            .check(ViewAssertions.matches(ViewMatchers.withText("")))
    }

    @Test
    fun testDeleteAllCarts() {
        Espresso.onView(ViewMatchers.withId(R.id.btnAdminCartDeleteAll)).perform(ViewActions.click())

        val cartList = RetrofitService
            .create()
            .getAllCartItemsCall()
            .execute()

        Truth.assertThat(cartList).isNotNull()
        Truth.assertThat(cartList.body()?.size).isEqualTo(0)

        Truth.assertThat(navController.currentDestination?.id).isEqualTo(R.id.adminCartFragment)
    }

    @Test
    fun testDeleteCustomerCart() {

        Espresso.onView(ViewMatchers.withId(R.id.etAdminCartDeleteCustomerCart))
            .perform(ViewActions.typeText(testCustomer.id))

        Espresso.closeSoftKeyboard()

        Espresso.onView(ViewMatchers.withId(R.id.btnAdminCartDeleteCustomerCart)).perform(ViewActions.click())

        val cartList = RetrofitService
            .create()
            .getCartByIdCall(testCustomer.id)
            .execute()

        Truth.assertThat(cartList).isNotNull()
        Truth.assertThat(cartList.body()?.size).isEqualTo(0)

        Truth.assertThat(navController.currentDestination?.id).isEqualTo(R.id.adminCartFragment)
    }

}