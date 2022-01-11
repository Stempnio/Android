package pl.edu.uj.ecommerce.Fragments

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import pl.edu.uj.ecommerce.R
import pl.edu.uj.ecommerce.addTestCustomer
import pl.edu.uj.ecommerce.addTestOrder
import pl.edu.uj.ecommerce.addTestProduct

@RunWith(AndroidJUnit4::class)
class AboutAppFragmentsTest {

    private lateinit var navController : TestNavHostController
    private lateinit var scenario : FragmentScenario<AboutAppFragment>

    @Before
    fun setup() {

        addTestCustomer()
        addTestProduct()
        addTestOrder()

        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        scenario = launchFragmentInContainer()

        scenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)

            Navigation.setViewNavController(fragment.requireView(), navController)
            navController.setCurrentDestination(R.id.aboutAppFragment)
        }
    }

    @Test
    fun testIsTvAboutAppDisplayed() {
        onView(withId(R.id.tvAboutApp)).check(matches(withText(R.string.about_app_text)))
    }

    @Test
    fun testGoBack() {
        Espresso.onView(ViewMatchers.withId(R.id.btnAboutAppGoBack)).perform(ViewActions.click())
        Truth.assertThat(navController.currentDestination?.id).isEqualTo(R.id.productsFragment)
    }
}