package com.sakestores.android

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented UI tests for the Sake Stores Android application.
 *
 * This class uses Jetpack Compose Testing APIs to verify that the main UI elements
 * of the application are rendered correctly and that navigation between screens behaves
 * as expected.
 *
 * All tests run on an Android device or emulator using the [AndroidJUnit4] test runner.
 */
@RunWith(AndroidJUnit4::class)
class BasicAppTest {

    /**
     * Compose test rule used to launch [MainActivity] and provide access
     * to its Compose UI tree for assertions and actions.
     */
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    /**
     * Verifies that the app launches and displays the main title text.
     */
    @Test
    fun app_opensCorrectly() {
        composeTestRule.onNodeWithText("Sake Stores")
            .assertIsDisplayed()
    }

    /**
     * Verifies that the main screen is rendered when the app starts.
     */
    @Test
    fun app_showsMainScreen() {
        composeTestRule.onNodeWithTag("main_screen")
            .assertIsDisplayed()
    }

    /**
     * Waits for either the success or error UI state to appear,
     * ensuring the app reacts to data loading states properly.
     */
    @Test
    fun app_showsContent() {
        composeTestRule.waitUntil(timeoutMillis = 3000) {
            try {
                composeTestRule.onNodeWithTag("success_state").assertExists()
                true
            } catch (e: Exception) {
                try {
                    composeTestRule.onNodeWithTag("error_state").assertExists()
                    true
                } catch (e: Exception) {
                    false
                }
            }
        }
    }

    /**
     * Verifies that tapping on a shop item navigates to the details screen.
     */
    @Test
    fun navigation_toDetailsScreen_works() {
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            try {
                composeTestRule.onNodeWithTag("success_state").assertExists()
                true
            } catch (e: Exception) {
                false
            }
        }

        composeTestRule.onAllNodes(hasTestTag("shop_item"))
            .onFirst()
            .assertIsDisplayed()
            .performClick()

        composeTestRule.onNodeWithTag("details_screen")
            .assertIsDisplayed()
    }

    /**
     * Verifies that navigating to the details screen and then tapping the back button
     * successfully returns the user to the main list screen.
     */
    @Test
    fun navigation_backToList_success() {
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            try {
                composeTestRule.onNodeWithTag("success_state").assertExists()
                true
            } catch (e: Exception) {
                false
            }
        }

        composeTestRule.onAllNodes(hasTestTag("shop_item"))
            .onFirst()
            .performClick()

        composeTestRule.onNodeWithTag("details_screen")
            .assertIsDisplayed()

        composeTestRule.onNodeWithTag("back_button")
            .assertIsDisplayed()
            .performClick()

        composeTestRule.onNodeWithTag("main_screen")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Sake Stores")
            .assertIsDisplayed()
    }
}