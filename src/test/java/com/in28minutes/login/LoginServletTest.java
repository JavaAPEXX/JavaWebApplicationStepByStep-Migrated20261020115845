package com.in28minutes.login;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.in28minutes.todo.TodoService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@ExtendWith(MockitoExtension.class)
class LoginServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private LoginService userValidationService;

    @Mock
    private TodoService todoService;

    @InjectMocks
    private LoginServlet loginServlet;

    @BeforeEach
    void setUp() {
        // No additional setup needed as Mockito handles injection
    }

    @Test
    @DisplayName("Given a valid GET request, when doGet is called, then forward to login.jsp")
    void givenValidGetRequest_whenDoGet_thenReturnForwardToLoginJsp() throws ServletException, IOException {
        // Arrange
        when(request.getRequestDispatcher("/WEB-INF/views/login.jsp")).thenReturn(requestDispatcher);

        // Act
        loginServlet.doGet(request, response);

        // Assert
        verify(request, times(1)).getRequestDispatcher("/WEB-INF/views/login.jsp");
        verify(requestDispatcher, times(1)).forward(request, response);
    }

    @Test
    @DisplayName("Given a GET request with dispatcher throwing exception, when doGet is called, then throw ServletException")
    void givenGetRequestWithDispatcherException_whenDoGet_thenThrowServletException() throws ServletException, IOException {
        // Arrange
        when(request.getRequestDispatcher("/WEB-INF/views/login.jsp")).thenReturn(requestDispatcher);
        doThrow(new ServletException("Dispatcher error")).when(requestDispatcher).forward(request, response);

        // Act & Assert
        assertThrows(ServletException.class, () -> loginServlet.doGet(request, response));
        verify(requestDispatcher, times(1)).forward(request, response);
    }

    @Test
    @DisplayName("Given valid credentials, when doPost is called, then set session attribute and redirect to list-todos")
    void givenValidCredentials_whenDoPost_thenSetSessionAndRedirectToListTodos() throws ServletException, IOException {
        // Arrange
        String validName = "john";
        String validPassword = "secret";
        when(request.getParameter("name")).thenReturn(validName);
        when(request.getParameter("password")).thenReturn(validPassword);
        when(userValidationService.isUserValid(validName, validPassword)).thenReturn(true);
        when(request.getSession()).thenReturn(session);

        // Act
        loginServlet.doPost(request, response);

        // Assert
        verify(userValidationService, times(1)).isUserValid(validName, validPassword);
        verify(session, times(1)).setAttribute("name", validName);
        verify(response, times(1)).sendRedirect("/list-todos.do");
        verify(request, never()).setAttribute(anyString(), any());
        verify(request, never()).getRequestDispatcher(anyString());
    }

    @Test
    @DisplayName("Given invalid credentials, when doPost is called, then set error message and forward to login.jsp")
    void givenInvalidCredentials_whenDoPost_thenSetErrorMessageAndForwardToLoginJsp() throws ServletException, IOException {
        // Arrange
        String invalidName = "john";
        String invalidPassword = "wrongpass";
        when(request.getParameter("name")).thenReturn(invalidName);
        when(request.getParameter("password")).thenReturn(invalidPassword);
        when(userValidationService.isUserValid(invalidName, invalidPassword)).thenReturn