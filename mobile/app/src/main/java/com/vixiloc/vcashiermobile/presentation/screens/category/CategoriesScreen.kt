package com.vixiloc.vcashiermobile.presentation.screens.category

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import com.vixiloc.vcashiermobile.domain.model.CategoriesResponseItem
import com.vixiloc.vcashiermobile.presentation.navigations.Screens
import com.vixiloc.vcashiermobile.presentation.widgets.category.CategoryItem
import com.vixiloc.vcashiermobile.presentation.widgets.commons.AlertType
import com.vixiloc.vcashiermobile.presentation.widgets.commons.FilledButton
import com.vixiloc.vcashiermobile.presentation.widgets.commons.Loading
import com.vixiloc.vcashiermobile.presentation.widgets.commons.MessageAlert
import com.vixiloc.vcashiermobile.presentation.widgets.commons.TextField
import com.vixiloc.vcashiermobile.presentation.widgets.commons.VerticalSpacer
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: CategoryViewModel = koinViewModel()
) {
    val state = viewModel.state
    val events = viewModel::onEvent

    LaunchedEffect(Unit) {
        viewModel.getAllCategories()
    }

    ConstraintLayout(modifier = modifier) {
        val (searchInput, categories, addButton) = createRefs()

        TextField(
            value = state.searchQuery,
            onValueChanged = {
                events(CategoryEvent.InputSearchValue(it))
            },
            modifier = Modifier.constrainAs(searchInput) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            title = "Cari",
            textStyle = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onBackground)
        )

        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
            .constrainAs(categories) {
                top.linkTo(searchInput.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) {
            items(state.categories) { category: CategoriesResponseItem ->
                CategoryItem(
                    headline = category.name,
                    modifier = Modifier
                        .padding(10.dp),
                    onDelete = {
                        events(CategoryEvent.DeleteCategory(category))
                    },
                    onUpdate = {
                        navController.navigate(
                            Screens.Categories.UpdateCategory(
                                id = category.id,
                                name = category.name
                            )
                        )
                    }
                )
            }
            item { VerticalSpacer(height = 300.dp, modifier = Modifier) }
        }

        FilledButton(
            onClick = {
                navController.navigate(Screens.Categories.CreateCategory)
            },
            text = "Tambah Kategori",
            modifier = Modifier.constrainAs(addButton) {
                top.linkTo(parent.bottom)
                start.linkTo(parent.start, margin = 10.dp)
                end.linkTo(parent.end, margin = 10.dp)
                width = Dimension.matchParent
            })

        Loading(modifier = Modifier, visible = state.isLoading)

        MessageAlert(
            type = AlertType.ERROR,
            message = state.error,
            title = "Error",
            modifier = Modifier,
            visible = state.error.isNotBlank(),
            onDismiss = { events(CategoryEvent.DismissAlertMessage) }
        )

        MessageAlert(
            type = AlertType.SUCCESS,
            message = state.success,
            title = "Success",
            modifier = Modifier,
            visible = state.success.isNotBlank(),
            onDismiss = { events(CategoryEvent.DismissAlertMessage) }
        )

        MessageAlert(
            type = AlertType.WARNING,
            message = state.confirmationMessage,
            title = "Hapus Kategori",
            visible = state.confirmationMessage.isNotBlank(),
            modifier = Modifier,
            confirmButton = {
                FilledButton(
                    onClick = { events(CategoryEvent.ProcessDeleteCategory) },
                    text = "Ya",
                    modifier = Modifier
                )
            },
            dismissButton = {
                FilledButton(
                    onClick = { events(CategoryEvent.DismissAlertMessage) },
                    text = "Tidak",
                    modifier = Modifier
                )
            }
        )

    }
}