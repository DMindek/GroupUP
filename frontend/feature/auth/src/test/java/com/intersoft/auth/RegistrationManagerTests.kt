package com.intersoft.auth

import com.intersoft.user.MockUserRepository
import com.intersoft.user.UserModel
import org.junit.Assert.*
import org.junit.Test

class RegistrationManagerTest {
    private lateinit var user: UserModel

    @Test
    fun validRegistration() {
        user = getValidUser()
        RegistrationManager.setRepository(MockUserRepository())
        RegistrationManager.registerUser(user, user.password, {}, {fail(it)})
    }

    @Test
    fun emailNoAt(){
        user = getValidUser()
        user.email = "test.email"
        RegistrationManager.setRepository(MockUserRepository())
        RegistrationManager.registerUser(user, user.password,
            { fail("Validation should have failed")},
            { assertEquals("Please enter a valid e-mail", it) })
    }

    @Test
    fun emailInvalidDomain(){
        user = getValidUser()
        user.email = "test@email"
        RegistrationManager.setRepository(MockUserRepository())
        RegistrationManager.registerUser(user, user.password,
            { fail("Validation should have failed")},
            { assertEquals("Please enter a valid e-mail", it) })
    }

    @Test
    fun usernameTooLong(){
        user = getValidUser()
        user.username = "1234567890123456"
        RegistrationManager.setRepository(MockUserRepository())
        RegistrationManager.registerUser(user, user.password,
            { fail("Validation should have failed")},
            { assertEquals("Username must have at most 15 characters", it) })
    }

    @Test
    fun usernameWithSymbols(){
        user = getValidUser()
        user.username = "test!"
        RegistrationManager.setRepository(MockUserRepository())
        RegistrationManager.registerUser(user, user.password,
            { fail("Validation should have failed")},
            { assertEquals("Username can only contain letters and numbers", it) })
    }

    @Test
    fun usernameEmpty(){
        user = getValidUser()
        user.username = ""
        RegistrationManager.setRepository(MockUserRepository())
        RegistrationManager.registerUser(user, user.password,
            { fail("Validation should have failed")},
            { assertEquals("Please enter a username", it) })
    }

    @Test
    fun locationEmpty(){
        user = getValidUser()
        user.location = ""
        RegistrationManager.setRepository(MockUserRepository())
        RegistrationManager.registerUser(user, user.password,
            { fail("Validation should have failed")},
            { assertEquals("Please enter a location", it) })
    }

    @Test
    fun passwordNoNumbers(){
        user = getValidUser()
        user.password = "aaaaaaaaa!"
        RegistrationManager.setRepository(MockUserRepository())
        RegistrationManager.registerUser(user, user.password,
            { fail("Validation should have failed")},
            { assertEquals("Password must contain at least one letter, number and symbol", it) })
    }

    @Test
    fun passwordNoLetters(){
        user = getValidUser()
        user.password = "123456789!"
        RegistrationManager.setRepository(MockUserRepository())
        RegistrationManager.registerUser(user, user.password,
            { fail("Validation should have failed")},
            { assertEquals("Password must contain at least one letter, number and symbol", it) })
    }

    @Test
    fun passwordNoSymbols(){
        user = getValidUser()
        user.password = "aaaaaaaaa1"
        RegistrationManager.setRepository(MockUserRepository())
        RegistrationManager.registerUser(user, user.password,
            { fail("Validation should have failed")},
            { assertEquals("Password must contain at least one letter, number and symbol", it) })
    }

    @Test
    fun passwordTooShort(){
        user = getValidUser()
        user.password = "a1!"
        RegistrationManager.setRepository(MockUserRepository())
        RegistrationManager.registerUser(user, user.password,
            { fail("Validation should have failed")},
            { assertEquals("Password must have at least 10 characters", it) })
    }

    @Test
    fun passwordTooLong(){
        user = getValidUser()
        user.password = "12345678901234567890a!"
        RegistrationManager.setRepository(MockUserRepository())
        RegistrationManager.registerUser(user, user.password,
            { fail("Validation should have failed")},
            { assertEquals("Password can only have 20 characters at most", it) })
    }

    @Test
    fun passwordRetypeWrong(){
        user = getValidUser()
        RegistrationManager.setRepository(MockUserRepository())
        RegistrationManager.registerUser(user, "different from actual password",
            { fail("Validation should have failed")},
            { assertEquals("Passwords do not match", it) })
    }

    private fun getValidUser(): UserModel{
        return UserModel("Test", "test@mail.com", "1233456789a.", "test")
    }
}