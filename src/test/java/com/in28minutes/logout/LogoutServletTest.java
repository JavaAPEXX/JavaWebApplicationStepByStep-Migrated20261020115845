package com.in28minutes.logout;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@ExtendWith(MockitoExtension.class)
class LogoutServletTest {

    @Test
    @DisplayName("Given valid request and response, when doGet is called, then session is invalidated and request is forwarded to login.jsp")
    void givenValidRequestAndResponse_whenDoGet_thenSessionInvalidatedAndForwardedToLoginJsp() throws ServletException, IOException {
        // Arrange
        LogoutServlet logoutServlet = new LogoutServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("/WEB-INF/views/login.jsp")).thenReturn(requestDispatcher);

        // Act
        assertDoesNotThrow(() -> logoutServlet.doGet(request, response));

        // Assert
        verify(session).invalidate();
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    @DisplayName("Given valid request and response, when doGet is called, then session invalidate is called exactly once")
    void givenValidRequestAndResponse_whenDoGet_thenSessionInvalidateCalledOnce() throws ServletException, IOException {
        // Arrange
        LogoutServlet logoutServlet = new LogoutServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("/WEB-INF/views/login.jsp")).thenReturn(requestDispatcher);

        // Act
        logoutServlet.doGet(request, response);

        // Assert
        verify(session, Mockito.times(1)).invalidate();
    }

    @Test
    @DisplayName("Given valid request and response, when doGet is called, then request dispatcher forward is called exactly once")
    void givenValidRequestAndResponse_whenDoGet_thenRequestDispatcherForwardCalledOnce() throws ServletException, IOException {
        // Arrange
        LogoutServlet logoutServlet = new LogoutServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("/WEB-INF/views/login.jsp")).thenReturn(requestDispatcher);

        // Act
        logoutServlet.doGet(request, response);

        // Assert
        verify(requestDispatcher, Mockito.times(1)).forward(request, response);
    }

    @Test
    @DisplayName("Given session invalidate throws RuntimeException, when doGet is called, then RuntimeException is propagated")
    void givenSessionInvalidateThrowsRuntimeException_whenDoGet_thenRuntimeExceptionPropagated() {
        // Arrange
        LogoutServlet logoutServlet = new LogoutServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        when(request.getSession()).thenReturn(session);
        doThrow(new RuntimeException("Session invalidation failed")).when(session).invalidate();

        // Act & Assert
        assertThrows(RuntimeException.class, () -> logoutServlet.doGet(request, response));
        verify(session).invalidate();
    }

    @Test
    @DisplayName("Given request dispatcher forward throws ServletException, when doGet is called, then ServletException is propagated")
    void givenRequestDispatcherForwardThrowsServletException_whenDoGet_thenServletExceptionPropagated() throws ServletException, IOException {
        // Arrange
        LogoutServlet logoutServlet = new LogoutServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class