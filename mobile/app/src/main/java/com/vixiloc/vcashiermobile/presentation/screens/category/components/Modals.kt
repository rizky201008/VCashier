package com.vixiloc.vcashiermobile.presentation.screens.category.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.vixiloc.vcashiermobile.presentation.components.FilledButton
import com.vixiloc.vcashiermobile.presentation.components.IconButton
import com.vixiloc.vcashiermobile.presentation.components.TextField
import com.vixiloc.vcashiermobile.presentation.components.VerticalSpacer
import com.vixiloc.vcashiermobile.presentation.screens.category.CategoryEvent
import com.vixiloc.vcashiermobile.presentation.screens.category.CategoryViewModel

enum class InputType {
    CREATE, UPDATE
}

@Composable
fun InputCategoryDialog(
    modifier: Modifier = Modifier,
    type: InputType,
    viewModel: CategoryViewModel
) {
    val state = viewModel.state
    val onEvent = viewModel::onEvent
    Dialog(
        onDismissRequest = {
            onEvent(CategoryEvent.ShowCreateModal(false))
            onEvent(CategoryEvent.ShowUpdateModal(false))
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = MaterialTheme.shapes.medium
                )
                .verticalScroll(state = rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.End
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Tambah Kategori",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight(600)
                    )
                )

                IconButton(onClick = {
                    onEvent(CategoryEvent.ShowCreateModal(false))
                    onEvent(CategoryEvent.ShowUpdateModal(false))
                }, icon = Icons.Outlined.Close)
            }
            VerticalSpacer(height = 10.dp)
            TextField(
                value = state.categoryName,
                onValueChanged = {
                    onEvent(CategoryEvent.InputCategoryName(it))
                },
                modifier = Modifier.fillMaxWidth(),
                title = "",
                placeHolder = "Masukkan kategori",
                isError = state.categoryNameError.isNotBlank(),
                errorMessage = state.categoryNameError
            )
            VerticalSpacer(height = 32.dp)
            FilledButton(
                onClick = {
                    if (type == InputType.CREATE) {
                        onEvent(CategoryEvent.SubmitCreateCategory)
                    } else {
                        onEvent(CategoryEvent.SubmitUpdateCategory)
                    }
                },
                text = if (type == InputType.CREATE) "Tambah" else "Ubah",
                modifier = Modifier.fillMaxWidth(0.4f),
                textStyle = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight(600))
            )
        }
    }
}