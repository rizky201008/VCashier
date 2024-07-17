package com.vixiloc.vcashiermobile.presentation.screens.employee

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.vixiloc.vcashiermobile.domain.model.user.UsersResponseData
import com.vixiloc.vcashiermobile.presentation.components.AlertType
import com.vixiloc.vcashiermobile.presentation.components.FilledButton
import com.vixiloc.vcashiermobile.presentation.components.Loading
import com.vixiloc.vcashiermobile.presentation.components.MessageAlert
import com.vixiloc.vcashiermobile.presentation.components.VerticalSpacer
import com.vixiloc.vcashiermobile.presentation.screens.employee.components.AddEmployeeDialog
import com.vixiloc.vcashiermobile.presentation.screens.employee.components.EmployeeListItem
import com.vixiloc.vcashiermobile.presentation.ui.theme.VcashierMobileTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun EmployeesScreen(modifier: Modifier = Modifier) {
    val viewModel: EmployeesViewModel = koinViewModel()
    val state = viewModel.state.value
    val onEvent = viewModel::onEvent

    ConstraintLayout(
        modifier = modifier.fillMaxSize()
    ) {
        val (content, bottomButton) = createRefs()
        Column(
            modifier = Modifier
                .constrainAs(content) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(25.dp)
        ) {
            LazyColumn(
                modifier = Modifier.height((state.users.size * 100).dp)
            ) {
                items(state.users) { item: UsersResponseData ->
                    EmployeeListItem(
                        item = item,
                        onReset = {
                            onEvent(EmployeesEvent.SelectEmployee(it.id))
                            onEvent(EmployeesEvent.ShowResetPasswordAlert(true))
                        }
                    )
                    VerticalSpacer(12.dp)
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
                onClick = {
                    onEvent(EmployeesEvent.ShowAddDialog(true))
                },
                text = "Tambah Pegawai",
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight(700)),
                contentPadding = PaddingValues(15.dp)
            )
        }

        Loading(modifier = Modifier, visible = state.isLoading)

        MessageAlert(
            type = AlertType.SUCCESS,
            message = state.success,
            title = "Sukses",
            modifier = Modifier,
            visible = state.showSuccessAlert,
            onDismiss = {
                onEvent(EmployeesEvent.ShowSuccessAlert(false))
            }
        )
        MessageAlert(
            type = AlertType.ERROR,
            message = state.error,
            title = "Error",
            modifier = Modifier,
            visible = state.showErrorAlert,
            onDismiss = {
                onEvent(EmployeesEvent.ShowErrorAlert(false))
            }
        )

        MessageAlert(
            type = AlertType.WARNING,
            message = "Anda yakin ingin mereset password pegawai ini?",
            title = "Reset Password",
            visible = state.showResetPasswordAlert,
            modifier = Modifier,
            confirmButton = {
                FilledButton(
                    onClick = {
                        onEvent(EmployeesEvent.ShowResetPasswordAlert(false))
                        onEvent(EmployeesEvent.ResetPassword)
                    },
                    text = "Ya",
                    modifier = Modifier
                )
            },
            dismissButton = {
                FilledButton(
                    onClick = {
                        onEvent(EmployeesEvent.ShowResetPasswordAlert(false))
                        onEvent(EmployeesEvent.SelectEmployee(null))
                    },
                    text = "Tidak",
                    modifier = Modifier
                )
            },
            onDismiss = {
                onEvent(EmployeesEvent.ShowResetPasswordAlert(false))
                onEvent(EmployeesEvent.SelectEmployee(null))
            }
        )
        if (state.showAddDialog) {
            AddEmployeeDialog(viewModel = viewModel)
        }
    }
}

@Preview
@Composable
private fun EmployeesScreenPreview() {
    VcashierMobileTheme {
        Surface {
            EmployeesScreen()
        }
    }
}