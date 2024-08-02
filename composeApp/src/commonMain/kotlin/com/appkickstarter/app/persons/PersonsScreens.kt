package com.appkickstarter.app.persons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import com.appkickstarter.app.designsystem.Heading
import com.appkickstarter.app.designsystem.RoundedButton
import com.appkickstarter.app.designsystem.SimpleLabel
import com.appkickstarter.app.di.koinScreenModel
import com.appkickstarter.app.theme.AppTheme
import kotlinx.coroutines.launch
import org.koin.core.parameter.parametersOf

data class PersonDetailScreen(val personId: String) : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val screenModel: PersonDetailScreenModel = koinScreenModel { parametersOf(personId) }
        val state by screenModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        if (state is PersonDetailScreenModel.State.Result) {
            val person = (state as PersonDetailScreenModel.State.Result).person
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Heading(person.name) },
                        navigationIcon = {
                            IconButton(onClick = { navigator.pop() }) {
                                Icon(Icons.Rounded.ArrowBack, "back")
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors()
                            .copy(containerColor = AppTheme.colors.background),
                    )
                },
                containerColor = AppTheme.colors.background,
            ) {
                Card(
                    Modifier
                        .fillMaxSize()
                        .wrapContentHeight(Alignment.CenterVertically)
                        .padding(horizontal = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = AppTheme.colors.cardBackground
                    ),
                ) {
                    Row(Modifier.padding(16.dp)) {
                        val context = LocalPlatformContext.current
                        AsyncImage(
                            model = "https://i.pravatar.cc/150?img=${person.id}",
                            contentDescription = person.name + " profile picture",
                            imageLoader = ImageLoader(context)
                        )
                        Spacer(Modifier.width(4.dp))
                        Column {
                            SimpleLabel(person.name)
                            SimpleLabel(person.role)
                        }
                    }
                }
            }
        }
    }
}

class PersonDetailScreenModel(
    personId: String,
    private val repository: PersonRepository
) : StateScreenModel<PersonDetailScreenModel.State>(State.Loading) {

    sealed class State {
        data object Loading : State()
        data object Error : State()
        data class Result(val person: Person) : State()
    }

    init {
        getPerson(personId)
    }

    private fun getPerson(id: String) {
        screenModelScope.launch {
            mutableState.value = State.Loading
            mutableState.value =
                repository.getPerson(id)?.let { State.Result(person = it) } ?: State.Error
        }
    }
}

object PersonsListScreen : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel: PersonListScreenModel = koinScreenModel()
        val state by viewModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalPlatformContext.current
        val uriHandler = LocalUriHandler.current

        if (state is PersonListScreenModel.State.Result) {
            val persons = (state as PersonListScreenModel.State.Result).persons

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                SimpleLabel("AppKickstarter.com free version")
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors()
                            .copy(containerColor = AppTheme.colors.background),
                    )
                },
                bottomBar = {
                    RoundedButton(
                        text = "Powerful KMP App Template",
                        modifier = Modifier.padding(48.dp)
                    ) {
                        uriHandler.openUri("https://appkickstarter.com")
                    }
                },
                containerColor = AppTheme.colors.background
            ) { paddingValues ->
                Column(
                    Modifier
                        .padding(paddingValues)
                ) {
                    LazyColumn(
                        Modifier
                            .padding(32.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(persons) { person ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                AsyncImage(
                                    model = "https://i.pravatar.cc/150?img=${person.id}",
                                    modifier = Modifier.size(50.dp).clip(CircleShape),
                                    imageLoader = ImageLoader(context),
                                    contentDescription = person.name + " profile picture"
                                )
                                Spacer(Modifier.width(4.dp))
                                TextButton({
                                    navigator.push(PersonDetailScreen(person.id))
                                }) {
                                    SimpleLabel("See more info about ${person.name}")
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}

class PersonListScreenModel(
    private val personRepository: PersonRepository
) : StateScreenModel<PersonListScreenModel.State>(State.Init) {

    sealed class State {
        data object Init : State()
        data object Loading : State()
        data object Error : State()
        data class Result(val persons: List<Person>) : State()
    }

    init {
        getPersons()
    }

    private fun getPersons() {
        screenModelScope.launch {
            mutableState.value = State.Loading
            mutableState.value = State.Result(persons = personRepository.getPersons())
        }
    }
}