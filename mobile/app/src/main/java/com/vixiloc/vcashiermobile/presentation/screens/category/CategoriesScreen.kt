package com.vixiloc.vcashiermobile.presentation.screens.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.vixiloc.vcashiermobile.presentation.components.AlertType
import com.vixiloc.vcashiermobile.presentation.components.FilledButton
import com.vixiloc.vcashiermobile.presentation.components.Loading
import com.vixiloc.vcashiermobile.presentation.components.MessageAlert
import com.vixiloc.vcashiermobile.presentation.components.SearchTextField
import com.vixiloc.vcashiermobile.presentation.components.VerticalSpacer
import com.vixiloc.vcashiermobile.presentation.screens.category.components.CategoryListItem
import com.vixiloc.vcashiermobile.presentation.screens.category.components.InputCategoryDialog
import com.vixiloc.vcashiermobile.presentation.screens.category.components.InputType
import com.vixiloc.vcashiermobile.presentation.ui.theme.VcashierMobileTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier
) {
    val viewModel: CategoryViewModel = koinViewModel()
    val state = viewModel.state
    val events = viewModel::onEvent
    val focusManager = LocalFocusManager.current

    ConstraintLayout(
        modifier = modifier.fillMaxSize()
    ) {
        val (mainContent, bottomButton) = createRefs()
        Column(
            modifier = Modifier
                .constrainAs(mainContent) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(25.dp)
        ) {
            SearchTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.searchQuery,
                onValueChanged = {
                    events(CategoryEvent.InputSearchValue(it))
                },
                placeHolder = "Cari kategori",
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            )
            VerticalSpacer(height = 32.dp)
            LazyColumn {
                items(state.categories) { category ->
                    CategoryListItem(
                        onUpdate = {
                            events(CategoryEvent.PreFillFormData(category))
                            events(CategoryEvent.ShowUpdateModal(true))
                        },
                        item = category
                    )
                    VerticalSpacer(height = 12.dp)
                }
            }
        }
        Box(
            modifier = Modifier
                .constrainAs(bottomButton) {
                    width = Dimension.matchParent
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .background(color = Color.White)
                .padding(horizontal = 24.dp, vertical = 14.dp),
            contentAlignment = Alignment.Center
        ) {
            FilledButton(
                onClick = { events(CategoryEvent.ShowCreateModal(true)) },
                text = "Tambah Kategori",
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight(700)),
                contentPadding = PaddingValues(15.dp)
            )
        }

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

        if (state.showCreateModal) {
            InputCategoryDialog(
                type = InputType.CREATE,
                viewModel = viewModel
            )
        }

        if (state.showUpdateModal) {
            InputCategoryDialog(
                type = InputType.UPDATE,
                viewModel = viewModel
            )
        }
    }
}

@Preview
@Composable
private fun CategoriesScreenPreview() {
    VcashierMobileTheme {
        Surface {
        }
    }
}