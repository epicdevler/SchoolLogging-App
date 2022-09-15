package com.cedarsstudio.internal.schoollogging

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.ui.AppBarConfiguration
import com.cedarsstudio.internal.schoollogging.databinding.ActivityMainBinding
import com.google.android.material.composethemeadapter3.Mdc3Theme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.composeView.apply {
            setContent {
                Mdc3Theme(setTextColors = true) {
                    Main()
                }
            }
        }
    }

    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    private fun Main() {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val coroutineScope = rememberCoroutineScope()
        ModalNavigationDrawer(
            drawerContent = {
                DrawerContent()
            },
            drawerState = drawerState
        ) {

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {},
                        navigationIcon = {
                            IconButton(onClick = {
                                coroutineScope.launch {
                                    if (drawerState.isOpen) drawerState.close() else drawerState.open()
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Rounded.Menu,
                                    contentDescription = null
                                )
                            }
                        }
                    )
                },
                modifier = Modifier.fillMaxSize()
            ) {
                it.calculateTopPadding()
                Text(text = "Hello")
            }

        }
    }

    @Composable
    private fun DrawerContent() {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth(.8f)
                .fillMaxHeight()
                .background(
                    color = colorScheme.primary,
                    shape = shapes.medium.copy(
                        topStart = CornerSize(0.dp),
                        bottomStart = CornerSize(0.dp)
                    )
                )
        ) {
            val (bckBtn, profileBtn, itemsList, logoutBtn) = createRefs()

            val startGuide = createGuidelineFromStart(16.dp)
            val endGuide = createGuidelineFromEnd(16.dp)
            val bottomGuide = createGuidelineFromBottom(20.dp)

            LazyColumn(modifier = Modifier.constrainAs(itemsList) {
                top.linkTo(parent.top)
                bottom.linkTo(logoutBtn.top)
                start.linkTo(startGuide)
//                end.linkTo(endGuide)
                height = Dimension.fillToConstraints
            }) {
                items(5) { index ->
                    DrawerItem(
                        onClick = { /*TODO*/ },
                        active = false,
                        icon = Icons.Rounded.Edit,
                        label = "Item $index"
                    )
                }
            }

            DrawerItem(
                onClick = { /*TODO*/ },
                active = true,
                icon = Icons.Rounded.Close,
                label = "Logout",
                modifier = Modifier.constrainAs(logoutBtn) {
                    start.linkTo(startGuide)
                    end.linkTo(endGuide)
                    bottom.linkTo(bottomGuide)
                }
            )


        }
    }

    @Composable
    private fun DrawerItem(
        modifier: Modifier = Modifier,
        onClick: () -> Unit,
        active: Boolean,
        icon: ImageVector,
        label: String,
    ) {
        ElevatedButton(onClick = onClick, modifier = modifier) {
            Icon(imageVector = icon, contentDescription = icon.name)
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = label)
        }
    }


}