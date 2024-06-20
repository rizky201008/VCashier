package com.vixiloc.vcashiermobile.presentation.screens.category

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vixiloc.vcashiermobile.presentation.components.commons.FilledButton
import com.vixiloc.vcashiermobile.presentation.components.commons.TextField

enum class FormType {
    CREATE, UPDATE
}

@Composable
fun CategoryForm(modifier: Modifier = Modifier, viewModel: CategoryViewModel, type: FormType) {
    val state = viewModel.state
    val events = viewModel::onEvent
    TextField(
        value = state.categoryName,
        onValueChanged = { events(CategoryEvent.InputCategoryName(it)) },
        modifier = Modifier,
        title = "Nama Kategori"
    )

    FilledButton(
        onClick = {
            if (type == FormType.CREATE) {
                events(CategoryEvent.SubmitCreateCategory)
            } else {
                events(CategoryEvent.SubmitUpdateCategory)
            }
        },
        text = "Submit",
        modifier = Modifier.padding(horizontal = 10.dp)
    )
}