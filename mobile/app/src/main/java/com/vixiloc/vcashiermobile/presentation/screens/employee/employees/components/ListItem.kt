package com.vixiloc.vcashiermobile.presentation.screens.employee.employees.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vixiloc.vcashiermobile.domain.model.user.UsersResponseData
import com.vixiloc.vcashiermobile.presentation.screens.category.components.FilledIconButton

@Composable
fun EmployeeListItem(
    modifier: Modifier = Modifier,
    item: UsersResponseData,
    onDelete: (UsersResponseData) -> Unit,
    onReset: (UsersResponseData) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.White, shape = MaterialTheme.shapes.medium)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight(600))
                )
                Text(
                    text = item.role,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp)
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                FilledIconButton(
                    modifier = Modifier
                        .size(32.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                            shape = MaterialTheme.shapes.small
                        ),
                    shape = MaterialTheme.shapes.small,
                    icon = Icons.Outlined.Key,
                    onClick = {
                        onReset(item)
                    },
                    iconSize = 16
                )
                FilledIconButton(
                    modifier = Modifier
                        .size(32.dp)
                        .background(
                            color = MaterialTheme.colorScheme.error.copy(alpha = 0.2f),
                            shape = MaterialTheme.shapes.small
                        ),
                    shape = MaterialTheme.shapes.small,
                    icon = Icons.Outlined.Delete,
                    onClick = {
                        onDelete(item)
                    },
                    iconTint = MaterialTheme.colorScheme.error,
                    iconSize = 16
                )
            }
        }
    }
}