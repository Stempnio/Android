package pl.edu.uj.ecommerce.Fragments

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import pl.edu.uj.ecommerce.Data.*
import pl.edu.uj.ecommerce.R
import pl.edu.uj.ecommerce.RetrofitService

@RunWith(AndroidJUnit4::class)
class ProductsFragmentTest {
    private lateinit var navController : TestNavHostController
    private lateinit var productsScenario : FragmentScenario<ProductsFragment>

    @Before
    fun setup() {

        val testCustomer = Customer().apply {
            this.id="testCustomer"
        }

        RetrofitService
            .create()
            .deleteCustomerCall(testCustomer.id)
            .execute()

        RetrofitService
            .create()
            .postCustomerCall(testCustomer)
            .execute()

        RetrofitService
            .create()
            .deleteAllProductsCall()
            .execute()

        val testProduct = Product().apply {
            this.id = 1
            this.price = 100
            this.name = "test product 1"
        }

        RetrofitService
            .create()
            .postProductCall(testProduct)
            .execute()


        CURRENT_CUSTOMER_ID = testCustomer.id
        getProductsIntoDB()
        getCustomerByIdIntoDB(CURRENT_CUSTOMER_ID)

        refreshCart()
        refreshOrders()

        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        productsScenario = launchFragmentInContainer()

        productsScenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)

            Navigation.setViewNavController(fragment.requireView(), navController)
            navController.setCurrentDestination(R.id.productsFragment)
        }
    }

    @Test
    fun goToCart() {
        Espresso.onView(ViewMatchers.withId(R.id.buttonGoToCart)).perform(ViewActions.click())

        Truth.assertThat(navController.currentDestination?.id).isEqualTo(R.id.cartFragment)
    }

    @Test
    fun goToAboutApp() {
        Espresso.openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().targetContext)
        Espresso.onView(ViewMatchers.withText(R.string.about_app)).perform(ViewActions.click())

        Truth.assertThat(navController.currentDestination?.id).isEqualTo(R.id.aboutAppFragment)
    }

    @Test
    fun goToMyOrders() {
        Espresso.onView(ViewMatchers.withId(R.id.buttonOrders)).perform(ViewActions.click())

        Truth.assertThat(navController.currentDestination?.id).isEqualTo(R.id.ordersFragment)
    }

    @Test
    fun logOut() {
        Espresso.onView(ViewMatchers.withId(R.id.buttonLogOut)).perform(ViewActions.click())

        Truth.assertThat(navController.currentDestination?.id).isEqualTo(R.id.logInFragment)
        Truth.assertThat(CURRENT_CUSTOMER_ID).isEqualTo(DEFAULT_CUSTOMER_ID)
    }

    @Test
    fun goToMaps() {
        Espresso.openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().targetContext)
        Espresso.onView(ViewMatchers.withText(R.string.find_our_shops)).perform(ViewActions.click())

        Truth.assertThat(navController.currentDestination?.id).isEqualTo(R.id.googleMapsFragment)
    }

    @Test
    fun goToChangePassword() {
        Espresso.openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().targetContext)
        Espresso.onView(ViewMatchers.withText(R.string.change_password)).perform(ViewActions.click())

        Truth.assertThat(navController.currentDestination?.id).isEqualTo(R.id.updateCustomerFragment)
    }

    @Test
    fun goToDeleteAccount() {
        Espresso.openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().targetContext)
        Espresso.onView(ViewMatchers.withText(R.string.delete_account)).perform(ViewActions.click())

        Truth.assertThat(navController.currentDestination?.id).isEqualTo(R.id.deleteAccountFragment)
    }
}