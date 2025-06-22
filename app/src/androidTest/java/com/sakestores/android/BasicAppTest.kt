package com.sakestores.android

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class BasicAppTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun app_opensCorrectly() {
        composeTestRule.onNodeWithText("Sake Stores")
            .assertIsDisplayed()
    }

    @Test
    fun app_showsMainScreen() {
        composeTestRule.onNodeWithTag("main_screen")
            .assertIsDisplayed()
    }

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