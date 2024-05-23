package com.vixiloc.vcashiermobile.presentation.screens.transaction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vixiloc.vcashiermobile.domain.model.CustomerResponseItem
import com.vixiloc.vcashiermobile.presentation.widgets.transaction.CustomerItem
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchCustomerScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: TransactionViewModel = koinViewModel()
) {
    val state = viewModel.state
    var searchStatus: Boolean by remember {
        mutableStateOf(false)
    }
    var query by remember {
        mutableStateOf("")
    }

    val customerLists = state.customers

    val customerFiltered = customerLists.filter {
        it.name.contains(query, ignoreCase = true)
    }

    LaunchedEffect(Unit) {
        viewModel.getCustomers()
    }

    Column(modifier = modifier) {
        SearchBar(
            modifier = Modifier
                .padding(horizontal = if (searchStatus) 0.dp else 10.dp)
                .fillMaxWidth(),
            query = query,
            onQueryChange = { query = it },
            onSearch = {

            },
            placeholder = { Text("Search Customer") },
            active = searchStatus,
            onActiveChange = {
                searchStatus = it
            }) {
            customerFiltered.forEach { data ->
                CustomerItem(
                    modifier = Modifier.padding(vertical = 5.dp, horizontal = 5.dp),
                    headlineText = data.name,
                    supportingText = data.phoneNumber ?: "",
                    onClick = {
                        navController.popBackStack()
                        navController.currentBackStackEntry
                            ?.savedStateHandle
                            ?.set("customer", data)
                    },
                    showUpdateButton = false
                )
            }
        }

        LazyColumn {
            items(customerLists) { data ->
                CustomerItem(
                    modifier = Modifier.padding(vertical = 5.dp, horizontal = 5.dp),
                    headlineText = data.name,
                    supportingText = data.phoneNumber ?: "",
                    onClick = {
                        navController.popBackStack()
                        navController.currentBackStackEntry
                            ?.savedStateHandle
                            ?.set("customer", data)
                    },
                    showUpdateButton = false
                )
            }
        }
    }
}