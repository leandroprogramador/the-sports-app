package br.leandro.thesportsapp.feature.countries

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SouthAmerica
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.leandro.core.domain.model.Country
import br.leandro.thesportsapp.R
import br.leandro.thesportsapp.ui.components.indicators.EmptyIndicator

@Composable
fun CountriesList(
    countries: List<Country>,
    onCountryClick: (Country) -> Unit,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    LaunchedEffect(countries) {
        listState.scrollToItem(0)
    }
    Column(modifier = modifier.fillMaxSize()) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChanged,
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            placeholder = {
                Text(
                text = stringResource(R.string.search_countries)
            )}
        )
        Spacer(modifier = Modifier.height(16.dp))
        if(countries.isEmpty()) {
            EmptyIndicator(
                message = stringResource(R.string.empty_countries),
                modifier = Modifier,
                icon = Icons.Default.SouthAmerica
            )
        } else {
            LazyColumn(state = listState) {
                items(countries, key = { it.name }) {
                    CountryItem(country = it, onCountryClick = onCountryClick)
                }
            }
        }

    }
}