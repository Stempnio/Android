import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import pl.edu.uj.ecommerce.Fragments.GoogleMapsFragment
import pl.edu.uj.ecommerce.R

@RunWith(AndroidJUnit4::class)
class AboutAppFragmentsTest {

    private lateinit var navController : TestNavHostController
    private lateinit var scenario : FragmentScenario<GoogleMapsFragment>

    @Before
    fun setup() {

        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        scenario = launchFragmentInContainer()

        scenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)

            Navigation.setViewNavController(fragment.requireView(), navController)
            navController.setCurrentDestination(R.id.googleMapsFragment)
        }
    }

    @Test
    fun testIsMapDisplayed() {
        Espresso.onView(ViewMatchers.withId(R.id.map))
            .check(ViewAssertions.matches(isDisplayed()))
    }

}