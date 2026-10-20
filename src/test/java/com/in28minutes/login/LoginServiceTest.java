package com.in28minutes.login;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {

    private final LoginService loginService = new LoginService();

    @Test
    @DisplayName("Given valid user and password, when checking user validity, then return true")
    void givenValidUserAndPassword_whenCheckingUserValidity_thenReturnTrue() {
        // Arrange
        String user = "in28Minutes";
        String password = "dummy";

        // Act
        boolean result = loginService.isUserValid(user, password);

        // Assert
        assertTrue(result);
    }

    @Test
    @DisplayName("Given valid user and invalid password, when checking user validity, then return false")
    void givenValidUserAndInvalidPassword_whenCheckingUserValidity_thenReturnFalse() {
        // Arrange
        String user = "in28Minutes";
        String password = "wrongPassword";

        // Act
        boolean result = loginService.isUserValid(user, password);

        // Assert
        assertFalse(result);
    }

    @Test
    @DisplayName("Given invalid user and valid password, when checking user validity, then return false")
    void givenInvalidUserAndValidPassword_whenCheckingUserValidity_thenReturnFalse() {
        // Arrange
        String user = "wrongUser";
        String password = "dummy";

        // Act
        boolean result = loginService.isUserValid(user, password);

        // Assert
        assertFalse(result);
    }

    @Test
    @DisplayName("Given invalid user and invalid password, when checking user validity, then return false")
    void givenInvalidUserAndInvalidPassword_whenCheckingUserValidity_thenReturnFalse() {
        // Arrange
        String user = "wrongUser";
        String password = "wrongPassword";

        // Act
        boolean result = loginService.isUserValid(user, password);

        // Assert
        assertFalse(result);
    }

    @Test
    @DisplayName("Given null user and valid password, when checking user validity, then return false")
    void givenNullUserAndValidPassword_whenCheckingUserValidity_thenReturnFalse() {
        // Arrange
        String user = null;
        String password = "dummy";

        // Act
        boolean result = loginService.isUserValid(user, password);

        // Assert
        assertFalse(result);
    }

    @Test
    @DisplayName("Given valid user and null password, when checking user validity, then return false")
    void givenValidUserAndNullPassword_whenCheckingUserValidity_thenReturnFalse() {
        // Arrange
        String user = "in28Minutes";
        String password = null;

        // Act
        boolean result = loginService.isUserValid(user, password);

        // Assert
        assertFalse(result);
    }

    @Test
    @DisplayName("Given null user and null password, when checking user validity, then return false")
    void givenNullUserAndNullPassword_whenCheckingUserValidity_thenReturnFalse() {
        // Arrange
        String user = null;
        String password = null;

        // Act
        boolean result = loginService.isUserValid(user, password);

        // Assert
        assertFalse(result);
    }

    @Test
    @DisplayName("Given empty user and valid password, when checking user validity, then return false")
    void givenEmptyUserAndValidPassword_whenCheckingUserValidity_thenReturnFalse() {
        // Arrange
        String user = "";
        String password = "dummy";

        // Act
        boolean result = loginService.isUserValid(user, password);

        // Assert
        assertFalse(result);
    }

    @Test
    @DisplayName("Given valid user and empty password, when checking user validity, then return false")
    void givenValidUserAndEmptyPassword_whenCheckingUserValidity_thenReturnFalse() {
        // Arrange
        String user = "in28Minutes";
        String password = "";

        // Act
        boolean result = loginService.isUserValid(user, password);

        // Assert
        assertFalse(result);
    }

    @Test
    @DisplayName("Given empty user and empty password, when checking user validity, then return false")
    void givenEmptyUserAndEmptyPassword_whenCheckingUserValidity_thenReturnFalse() {
        // Arrange
        String user = "";
        String password = "";

        // Act
        boolean result = loginService.isUserValid(user, password);

        // Assert
        assertFalse(result);
    }

    @Test
    @DisplayName("Given user with different case, when checking user validity, then return false")
    void givenUserWithDifferentCase_whenCheckingUserValidity_thenReturnFalse() {
        // Arrange
        String user = "In28Minutes";
        String password = "dummy";

        // Act
        boolean result = loginService.isUserValid(user, password);

        // Assert
        assertFalse(result);
    }

    @Test
    @DisplayName("Given password with different case, when checking user validity, then return false")
    void givenPasswordWithDifferentCase_whenCheckingUserValidity_thenReturnFalse() {
        // Arrange
        String user = "in28Minutes";
        String password = "Dummy";

        // Act
        boolean result = loginService.isUserValid(user, password);

        // Assert
        assertFalse(result);
    }

    @Test
    @DisplayName("Given user with leading spaces, when checking user validity, then return false")
    void givenUserWithLeadingSpaces_whenCheckingUserValidity_thenReturnFalse() {
        // Arrange
        String user = " in28Minutes";
        String password = "dummy";

        // Act
        boolean result = loginService.isUserValid(user, password);

        // Assert
        assertFalse(result);
    }

    @Test
    @DisplayName("Given user with trailing spaces, when checking user validity, then return false")
    void givenUserWithTrailingSpaces_whenCheckingUserValidity_thenReturnFalse() {
        // Arrange
        String user = "in28Minutes ";
        String password = "dummy";

        // Act
        boolean result = loginService.isUserValid(user, password);

        // Assert
        assertFalse(result);
    }

    @Test
    @DisplayName("Given password with leading spaces, when checking user validity, then return false")
    void givenPasswordWithLeadingSpaces_whenCheckingUserValidity_thenReturnFalse() {
        // Arrange
        String user = "in28Minutes";
        String password = " dummy";

        // Act
        boolean result = loginService.isUserValid(user, password);

        // Assert
        assertFalse(result);
    }

    @Test
    @DisplayName("Given password with trailing spaces, when checking user validity, then return false")
    void givenPasswordWithTrailingSpaces_whenCheckingUserValidity_thenReturnFalse() {
        // Arrange
        String user = "in28Minutes";
        String password = "dummy ";

        // Act
        boolean result = loginService.isUserValid(user, password);

        // Assert
        assertFalse(result);
    }
}