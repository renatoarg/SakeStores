package com.sakestores.android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sakestores.android.ui.screens.details.SakeShopDetailsScreen
import com.sakestores.android.ui.screens.list.SakeShopsListScreen

@Composable
fun SakeStoresNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "sake_shops_list"
    ) {
        composable("sake_shops_list") {
            SakeShopsListScreen(
                onSakeShopClick = { shopName ->
                    navController.navigate("sake_shop_details/$shopName")
                }
            )
        }

        composable("sake_shop_details/{shopName}") { backStackEntry ->
            val shopName = backStackEntry.arguments?.getString("shopName") ?: ""
            SakeShopDetailsScreen(
                shopName = shopName,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}