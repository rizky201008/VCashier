package com.vixiloc.vcashiermobile.presentation.screens.category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vixiloc.vcashiermobile.presentation.widgets.commons.AlertType
import com.vixiloc.vcashiermobile.presentation.widgets.commons.FilledButton
import com.vixiloc.vcashiermobile.presentation.widgets.commons.Loading
import com.vixiloc.vcashiermobile.presentation.widgets.commons.MessageAlert
import com.vixiloc.vcashiermobile.presentation.widgets.commons.TextField
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateCategoryScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: CategoryViewModel = koinViewModel()
) {
    val state = viewModel.state
    val events = viewModel::onEvent

    Column(modifier = modifier) {
        CategoryForm(viewModel = viewModel, type = FormType.CREATE)

        MessageAlert(
            type = AlertType.SUCCESS,
            message = state.success,
            title = "Sukses",
            modifier = Modifier,
            visible = state.success.isNotEmpty(),
            onDismiss = {
                events(CategoryEvent.DismissAlertMessage)
                navController.navigateUp()
            }
        )
        MessageAlert(
            type = AlertType.ERROR,
            message = state.error,
            title = "Error",
            modifier = Modifier,
            visible = state.error.isNotEmpty(),
            onDismiss = {
                events(CategoryEvent.DismissAlertMessage)
            }
        )

        Loading(modifier = Modifier, visible = state.isLoading)
    }
}