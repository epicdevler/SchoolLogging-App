@file:OptIn(ExperimentalMaterial3Api::class)

package com.cedarsstudio.internal.schoollogging.admins.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import com.cedarsstudio.internal.schoollogging.admins.utils.DrawerScreen

@Composable
fun Home(
    onNavigate: (route: String, up: Boolean) -> Unit,
    drawerScreenItems: List<DrawerScreen>,
) {
    ConstraintLayout(modifier = Modifier
        .fillMaxSize())
    {
        val (welcomeText, username, gridItems) = createRefs()

        Text("Welcome",
            modifier = Modifier.constrainAs(welcomeText) {
                top.linkTo(parent.top, 16.dp)
                start.linkTo(parent.start, 16.dp)
            })
        Text("John Doe",
            style = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .constrainAs
                    (username) {
                    top.linkTo(welcomeText.bottom)
                    start.linkTo(parent.start, 16.dp)
                })

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.constrainAs(gridItems) {
                top.linkTo(username.bottom, 22.dp)
                centerHorizontallyTo(parent)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
        ) {
            items(drawerScreenItems) { item ->
                HomeCardItem(onClick = { /*TODO*/ }, icon = item.icon,
                    label = item.label)
            }
        }
    }
}


@Composable
private fun HomeCardItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: ImageVector,
    label: String,
) {
    ElevatedCard(onClick = onClick, colors = CardDefaults.elevatedCardColors(
        contentColor = Color.White,
        containerColor = MaterialTheme.colorScheme.primary
    ), shape = MaterialTheme.shapes.small, modifier = modifier) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(PaddingValues(18.dp, 22.dp))) {
            Icon(imageVector = icon, contentDescription = icon.name, modifier = Modifier.size
                (34.dp))
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = label)
        }
    }
}