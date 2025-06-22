package com.sakestores.android

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class for the Sake Stores Android app.
 *
 * This class serves as the entry point for Dagger Hilt dependency injection.
 * By annotating it with [HiltAndroidApp], Hilt generates the necessary components
 * and sets up the application-level dependency graph.
 *
 * This class extends [Application] and should be declared in the `AndroidManifest.xml`
 * as the `android:name` attribute of the `<application>` tag.
 */
@HiltAndroidApp
class SakeStoresApplication: Application() {
}