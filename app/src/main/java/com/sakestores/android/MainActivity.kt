@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.sakestores.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sakestores.design_system.theme.SakePrimary
import com.sakestores.design_system.theme.SakeStoresTheme
import com.sakestores.design_system.utils.StatusBarColor
import com.sakestores.feat_sake_details.ui.SakeShopDetailsScreen
import com.sakestores.feat_sake_list.ui.SakeShopsListScreen
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SakeStoresTheme {
                StatusBarColor(
                    color = SakePrimary,
                    darkIcons = false
                )
                Scaffold(
                    modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SakeStoresApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun SakeStoresApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    SharedTransitionLayout {
        NavHost(
            navController = navController,
            startDestination = "sake_shops_list",
            modifier = modifier
        ) {
            composable("sake_shops_list") {
                SakeShopsListScreen(
                    animatedVisibilityScope = this,
                    onSakeShopClick = { shopName ->
                        val encodedShopName = URLEncoder.encode(shopName, StandardCharsets.UTF_8.toString())
                        navController.navigate("sake_shop_details/$encodedShopName")
                    }
                )
            }

            composable("sake_shop_details/{shopName}") { backStackEntry ->
                val encodedShopName = backStackEntry.arguments?.getString("shopName") ?: ""
                val shopName = URLDecoder.decode(encodedShopName, StandardCharsets.UTF_8.toString())

                SakeShopDetailsScreen(
                    animatedVisibilityScope = this,
                    shopName = shopName,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}