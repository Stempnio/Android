package pl.edu.uj.ecommerce.AdminTest

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import pl.edu.uj.ecommerce.*
import pl.edu.uj.ecommerce.admin.AdminProductsFragment

@RunWith(AndroidJUnit4::class)
class AdminProductsTest {

    private lateinit var navController : TestNavHostController
    private lateinit var scenario : FragmentScenario<AdminProductsFragment>

    @Before
    fun setup() {

        addTestCustomer()
        addTestAdmin()

        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        scenario = launchFragmentInContainer()

        scenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)

            Navigation.setViewNavController(fragment.requireView(), navController)
            navController.setCurrentDestination(R.id.adminProductsFragment)
        }
    }

    @Test
    fun testGetProductById() {
        addTestProduct()

        val product = getProducts()

        Truth.assertThat(product).isNotNull()

        Espresso.onView(ViewMatchers.withId(R.id.etAdminProductsGetById)).perform(scrollTo(), typeText(product!![0].id.toString()))
        Espresso.closeSoftKeyboard()

        Espresso.onView(ViewMatchers.withId(R.id.btnAdminProductsGetById)).perform(scrollTo(), click())

        Espresso.onView(ViewMatchers.withId(R.id.tvAdminProductsGetId)).check(ViewAssertions.matches(ViewMatchers.withText(
            product[0].id.toString())))

        Espresso.onView(ViewMatchers.withId(R.id.tvAdminProductsGetName)).check(ViewAssertions.matches(ViewMatchers.withText(
            product[0].name)))

        Espresso.onView(ViewMatchers.withId(R.id.tvAdminProductsGetPrice)).check(ViewAssertions.matches(ViewMatchers.withText(
            product[0].price.toString())))

        Espresso.onView(ViewMatchers.withId(R.id.tvAdminProductsGetDescription)).check(ViewAssertions.matches(ViewMatchers.withText(
            product[0].description)))
    }

    @Test
    fun testAddProduct() {

        RetrofitService
            .create()
            .deleteAllProductsCall()
            .execute()

        Espresso.onView(ViewMatchers.withId(R.id.etAdminProductsAddName)).perform(scrollTo(), typeText(
            testProduct.name))

        closeSoftKeyboard()

        Espresso.onView(ViewMatchers.withId(R.id.etAdminProductsAddPrice)).perform(scrollTo(), typeText(
            testProduct.price.toString()))

        closeSoftKeyboard()

        Espresso.onView(ViewMatchers.withId(R.id.etAdminProductsAddDescription)).perform(scrollTo(), typeText(
            testProduct.description))

        closeSoftKeyboard()

        Espresso.onView(ViewMatchers.withId(R.id.btnAdminProductsAdd)).perform(scrollTo(), click())

        val productList = getProducts()

        Truth.assertThat(productList).isNotNull()
        if (productList != null) {
            Truth.assertThat(productList.size).isEqualTo(1)
            Truth.assertThat(productList[0].name).isEqualTo(testProduct.name)
        }
    }

    @Test
    fun testUpdateProduct() {
        addTestProduct()

        val productBefore = getProducts()

        Truth.assertThat(productBefore).isNotNull()
        Espresso.onView(ViewMatchers.withId(R.id.etAdminProductsUpdateId)).perform(scrollTo(), typeText(
            productBefore!![0].id.toString()))
        closeSoftKeyboard()

        val newName = "new name"
        Espresso.onView(ViewMatchers.withId(R.id.etAdminProductsUpdateName)).perform(scrollTo(), typeText(
            newName))
        closeSoftKeyboard()

        val newPrice = 10
        Espresso.onView(ViewMatchers.withId(R.id.etAdminProductsUpdatePrice)).perform(scrollTo(), typeText(
            newPrice.toString()))
        closeSoftKeyboard()

        val newDescription = "new description"
        Espresso.onView(ViewMatchers.withId(R.id.etAdminProductsUpdateDescription)).perform(scrollTo(), typeText(
            newDescription))
        closeSoftKeyboard()

        Espresso.onView(ViewMatchers.withId(R.id.btnAdminProductsUpdate)).perform(scrollTo(), click())

        val productAfter = getProducts()

        Truth.assertThat(productAfter).isNotNull()
        if (productAfter != null) {
            Truth.assertThat(productAfter.size).isEqualTo(1)
            Truth.assertThat(productAfter[0].name).isEqualTo(newName)
            Truth.assertThat(productAfter[0].price).isEqualTo(newPrice)
            Truth.assertThat(productAfter[0].description).isEqualTo(newDescription)
        }
    }

    @Test
    fun testDeleteAllProducts() {

        addTestProduct()

        Espresso.onView(ViewMatchers.withId(R.id.btnAdminProductsDeleteAll)).perform(scrollTo(), click())

        val productList = getProducts()

        Truth.assertThat(productList).isNotNull()
        Truth.assertThat(productList).isEmpty()
    }

}