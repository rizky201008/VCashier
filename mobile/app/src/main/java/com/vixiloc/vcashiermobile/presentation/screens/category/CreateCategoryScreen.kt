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
        TextField(
            value = state.categoryName,
            onValueChanged = { events(CategoryEvent.InputCategoryName(it)) },
            modifier = Modifier,
            title = "Nama Kategori"
        )

        FilledButton(
            onClick = { events(CategoryEvent.SubmitCreateCategory) },
            text = "Submit",
            modifier = Modifier.padding(horizontal = 10.dp)
        )

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

        Loading(modifier = Modifier, visible = state.isLoading)
    }
}