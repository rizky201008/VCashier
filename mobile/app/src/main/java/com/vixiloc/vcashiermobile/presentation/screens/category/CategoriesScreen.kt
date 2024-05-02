package com.vixiloc.vcashiermobile.presentation.screens.category

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.vixiloc.vcashiermobile.presentation.widgets.category.CategoryItem
import com.vixiloc.vcashiermobile.presentation.widgets.commons.FilledButton
import com.vixiloc.vcashiermobile.presentation.widgets.commons.TextField
import com.vixiloc.vcashiermobile.presentation.widgets.commons.VerticalSpacer

@Preview
@Composable
fun CategoriesScreen(modifier: Modifier = Modifier) {
    ConstraintLayout {
        val (searchInput, categories, addButton, addButtonSpacer) = createRefs()

        TextField(
            value = "Cari",
            onValueChanged = {},
            modifier = Modifier.constrainAs(searchInput) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            title = "Cari",
            textStyle = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onBackground)
        )

        FilledButton(
            onClick = { /*TODO*/ },
            text = "Tambah Kategori",
            modifier = Modifier.constrainAs(addButton) {
                top.linkTo(searchInput.bottom)
                start.linkTo(searchInput.start, margin = 10.dp)
                end.linkTo(searchInput.end, margin = 10.dp)
                width = Dimension.fillToConstraints
            })

        VerticalSpacer(height = 10.dp, modifier = Modifier.constrainAs(addButtonSpacer) {
            top.linkTo(addButton.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })

        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .constrainAs(categories) {
                top.linkTo(addButtonSpacer.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) {
            items(10) {
                CategoryItem(
                    headline = "Test Category",
                    modifier = Modifier
                        .padding(10.dp),
                    onDelete = {}
                )
            }
            item { VerticalSpacer(height = 300.dp, modifier = Modifier) }
        }

    }
}