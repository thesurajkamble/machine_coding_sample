package com.surajkamble.testprojectstockgro

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.surajkamble.testprojectstockgro.data.repository.SearchRepositoryImpl
import com.surajkamble.testprojectstockgro.domain.SearchUseCase
import com.surajkamble.testprojectstockgro.presentation.SearchViewModel
import com.surajkamble.testprojectstockgro.ui.theme.TestProjectStockgroTheme

class MainActivity : ComponentActivity() {

    private val searchViewModel: SearchViewModel by viewModels {
        val repository = SearchRepositoryImpl()
        val useCase = SearchUseCase(repository)
        SearchViewModel.provideFactory(useCase)
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestProjectStockgroTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                  SearchScreen(searchViewModel)
                }
            }
        }
    }
}

@Composable
fun SearchScreen(viewModel: SearchViewModel) {
    val state by viewModel.uiState.collectAsState()
    var textFieldValue by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = textFieldValue,
            onValueChange = { textFieldValue = it },
            label = { Text("Enter Query") }
        )

        Row {
            Button(onClick = { viewModel.onSearchClicked(textFieldValue) }) {
                Text("Search")
            }
            Spacer(Modifier.width(8.dp))
            Button(onClick = { viewModel.onCancelClicked() }) {
                Text("Cancel")
            }
        }

        if (state.isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        LazyColumn {
            items(state.results) { item ->
                Text(text = item, modifier = Modifier.padding(8.dp))
            }
        }
    }
}