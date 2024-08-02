package com.appkickstarter.app.persons
  
class PersonRepository {
    private val persons = mutableListOf<Person>()

    init {
        persons.addAll(
            listOf(
                Person("1", "Mariah", "android dev"),
                Person("20", "Lucy", "iOS dev"),
            )
        )
    }

    fun getPerson(id: String): Person? {
        return persons.firstOrNull { it.id == id }
    }

    fun getPersons(): List<Person> {
        return persons
    }
}
