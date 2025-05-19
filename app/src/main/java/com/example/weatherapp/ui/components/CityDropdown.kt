package com.example.weatherapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

@Composable
fun CitySearchDropdown(
    cities: List<String>,
    selectedCity: String,
    onCitySelected: (String) -> Unit
) {
    var query by remember { mutableStateOf(TextFieldValue("")) } // Изначально пустое поле
    val focusManager = LocalFocusManager.current
    var showSuggestions by remember { mutableStateOf(false) }

    // Не используем selectedCity для инициализации query, поле остается пустым при запуске

    val filteredCities = remember(query.text, cities) {
        if (query.text.isBlank()) emptyList()
        else cities.filter {
            it.contains(query.text, ignoreCase = true)
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
                showSuggestions = it.text.isNotBlank()
            },
            modifier = Modifier
                .fillMaxWidth(),
            placeholder = { Text("Введите город") }, // Плейсхолдер
            singleLine = true,
            trailingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Поиск")
            },
            shape = MaterialTheme.shapes.medium.copy(all = ZeroCornerSize),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    showSuggestions = false
                    if (cities.contains(query.text)) {
                        onCitySelected(query.text)
                    }
                }
            ),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedContainerColor = MaterialTheme.colorScheme.surface
            )
        )

        if (showSuggestions && filteredCities.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            ) {
                items(filteredCities.take(6)) { city ->
                    ListItem(
                        headlineContent = { Text(city) },
                        modifier = Modifier
                            .clickable {
                                query = TextFieldValue(city)
                                showSuggestions = false
                                focusManager.clearFocus()
                                onCitySelected(city)
                            }
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                    Divider()
                }
            }
        }
    }
}



