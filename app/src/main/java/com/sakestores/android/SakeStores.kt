package com.sakestores.android

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sakestores.feat_sake_details.ui.SakeShopDetailsScreen
import com.sakestores.feat_sake_list.ui.SakeShopsListScreen
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SakeStores(
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