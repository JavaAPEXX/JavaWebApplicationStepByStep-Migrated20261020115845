package com.in28minutes.filter;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
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
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.RequestDispatcher;

@ExtendWith(MockitoExtension.class)
class LoginRequiredFilterTest {

    private LoginRequiredFilter loginRequiredFilter;

    @Mock
    private ServletRequest servletRequest;

    @Mock
    private ServletResponse servletResponse;

    @Mock
    private FilterChain chain;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private HttpSession httpSession;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private FilterConfig filterConfig;

    @BeforeEach
    void setUp() {
        loginRequiredFilter = new LoginRequiredFilter();
    }

    @Test
    @DisplayName("Given authenticated user when doFilter then chain is invoked")
    void givenAuthenticatedUser_whenDoFilter_thenChainIsInvoked() throws IOException, ServletException {
        // Arrange
        when(servletRequest.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("name")).thenReturn("John Doe");

        // Act
        loginRequiredFilter.doFilter(servletRequest, servletResponse, chain);

        // Assert
        verify(chain, times(1)).doFilter(servletRequest, servletResponse);
        verify(servletRequest, never()).getRequestDispatcher(any());
    }

    @Test
    @DisplayName("Given unauthenticated user when doFilter then forward to login page")
    void givenUnauthenticatedUser_whenDoFilter_thenForwardToLoginPage() throws IOException, ServletException {
        // Arrange
        when(servletRequest.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("name")).thenReturn(null);
        when(servletRequest.getRequestDispatcher("/login.do")).thenReturn(requestDispatcher);

        // Act
        loginRequiredFilter.doFilter(servletRequest, servletResponse, chain);

        // Assert
        verify(requestDispatcher, times(1)).forward(servletRequest, servletResponse);
        verify(chain, never()).doFilter(any(), any());
    }

    @Test
    @DisplayName("Given null session attribute when doFilter then forward to login page")
    void givenNullSessionAttribute_whenDoFilter_thenForwardToLoginPage() throws IOException, ServletException {
        // Arrange
        when(servletRequest.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("name")).thenReturn(null);
        when(servletRequest.getRequestDispatcher("/login.do")).thenReturn(requestDispatcher);

        // Act
        loginRequiredFilter.doFilter(servletRequest, servletResponse, chain);

        // Assert
        verify(requestDispatcher, times(1)).forward(servletRequest, servletResponse);
        verify(chain, never()).doFilter(any(), any());
    }

    @Test
    @DisplayName("Given empty string name attribute when doFilter then chain is invoked")
    void givenEmptyStringNameAttribute_whenDoFilter_thenChainIsInvoked() throws IOException, ServletException {
        // Arrange
        when(servletRequest.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("name")).thenReturn("");

        // Act
        loginRequiredFilter.doFilter(servletRequest, servletResponse, chain);

        // Assert
        verify(chain, times(1)).doFilter(servletRequest, servletResponse);
        verify(servletRequest, never()).getRequestDispatcher(any());
    }

    @Test
    @DisplayName("Given whitespace name attribute when doFilter then chain is invoked")
    void givenWhitespaceNameAttribute_whenDoFilter_thenChainIsInvoked() throws IOException, ServletException {
        // Arrange
        when(servletRequest.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("name")).thenReturn("   ");

        // Act
        loginRequiredFilter.doFilter(servletRequest, servletResponse, chain);

        // Assert
        verify(chain, times(1)).doFilter(servletRequest, servletResponse);
        verify(servletRequest, never()).getRequestDispatcher(any());
    }

    @Test
    @DisplayName("Given null servletRequest when doFilter then NullPointerException is thrown")
    void givenNullServletRequest_whenDoFilter_thenNullPointerExceptionIsThrown() {
        // Arrange
        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            loginRequiredFilter.doFilter(null, servletResponse, chain);
        });
    }

    @Test
    @DisplayName("Given null servletResponse when doFilter then NullPointerException is thrown")
    void givenNullServletResponse_whenDoFilter_thenNullPointerExceptionIsThrown() throws IOException, ServletException {
        // Arrange
        when(servletRequest.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("name")).thenReturn("John Doe");

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            loginRequiredFilter.doFilter(servletRequest, null, chain);
        });
    }

    @Test
    @DisplayName("Given null filterChain when doFilter then NullPointerException is thrown")
    void givenNullFilterChain_whenDoFilter_thenNullPointerExceptionIsThrown() throws IOException, ServletException {
        // Arrange
        when(servletRequest.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("name")).thenReturn("John Doe");

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            loginRequiredFilter.doFilter(servletRequest, servletResponse, null);
        });
    }

    @Test
    @DisplayName("Given null session when doFilter then NullPointerException is thrown")
    void givenNullSession_whenDoFilter_thenNullPointerExceptionIsThrown() {
        // Arrange
        when(servletRequest.getSession()).thenReturn(null);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            loginRequiredFilter.doFilter(servletRequest, servletResponse, chain);
        });
    }

    @Test
    @DisplayName("Given chain throws IOException when doFilter then IOException is propagated")
    void givenChainThrowsIOException_whenDoFilter_thenIOExceptionIsPropagated() throws IOException, ServletException {
        // Arrange
        when(servletRequest.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("name")).thenReturn("John Doe");
        doThrow(new IOException("Chain error")).when(chain).doFilter(servletRequest, servletResponse);

        // Act & Assert
        assertThrows(IOException.class, () -> {
            loginRequiredFilter.doFilter(servletRequest, servletResponse, chain);
        });
    }

    @Test
    @DisplayName("Given chain throws ServletException when doFilter then ServletException is propagated")
    void givenChainThrowsServletException_whenDoFilter_thenServletExceptionIsPropagated() throws IOException, ServletException {
        // Arrange
        when(servletRequest.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("name")).thenReturn("John Doe");
        doThrow(new ServletException("Chain error")).when(chain).doFilter(servletRequest, servletResponse);

        // Act & Assert
        assertThrows(ServletException.class, () -> {
            loginRequiredFilter.doFilter(servletRequest, servletResponse, chain);
        });
    }

    @Test
    @DisplayName("Given requestDispatcher throws IOException when forward then IOException is propagated")
    void givenRequestDispatcherThrowsIOException_whenForward_thenIOExceptionIsPropagated() {
        // Arrange
        when(servletRequest.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("name")).thenReturn(null);
        when(servletRequest.getRequestDispatcher("/login.do")).thenReturn(requestDispatcher);
        doThrow(new IOException("Forward error")).when(requestDispatcher).forward(servletRequest, servletResponse);

        // Act & Assert
        assertThrows(IOException.class, () -> {
            loginRequiredFilter.doFilter(servletRequest, servletResponse, chain);
        });
    }

    @Test
    @DisplayName("Given requestDispatcher throws ServletException when forward then ServletException is propagated")
    void givenRequestDispatcherThrowsServletException_whenForward_thenServletExceptionIsPropagated() {
        // Arrange
        when(servletRequest.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("name")).thenReturn(null);
        when(servletRequest.getRequestDispatcher("/login.do")).thenReturn(requestDispatcher);
        doThrow(new ServletException("Forward error")).when(requestDispatcher).forward(servletRequest, servletResponse);

        // Act & Assert
        assertThrows(ServletException.class, () -> {
            loginRequiredFilter.doFilter(servletRequest, servletResponse, chain);
        });
    }

    @Test
    @DisplayName("Given null filterConfig when init then no exception is thrown")
    void givenNullFilterConfig_whenInit_thenNoExceptionIsThrown() {
        // Arrange
        // Act & Assert
        assertDoesNotThrow(() -> {
            loginRequiredFilter.init(null);
        });
    }

    @Test
    @DisplayName("Given valid filterConfig when init then no exception is thrown")
    void givenValidFilterConfig_whenInit_thenNoExceptionIsThrown() {
        // Arrange
        // Act & Assert
        assertDoesNotThrow(() -> {
            loginRequiredFilter.init(filterConfig);
        });
    }

    @Test
    @DisplayName("Given filter instance when destroy then no exception is thrown")
    void givenFilterInstance_whenDestroy_thenNoExceptionIsThrown() {
        // Arrange
        // Act & Assert
        assertDoesNotThrow(() -> {
            loginRequiredFilter.destroy();
        });
    }

    @Test
    @DisplayName("Given authenticated user with special characters in name when doFilter then chain is invoked")
    void givenAuthenticatedUserWithSpecialCharactersInName_whenDoFilter_thenChainIsInvoked() throws IOException, ServletException {
        // Arrange
        when(servletRequest.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("name")).thenReturn("John@Doe#123");

        // Act
        loginRequiredFilter.doFilter(servletRequest, servletResponse, chain);

        // Assert
        verify(chain, times(1)).doFilter(servletRequest, servletResponse);
        verify(servletRequest, never()).getRequestDispatcher(any());
    }

    @Test
    @DisplayName("Given very long name attribute when doFilter then chain is invoked")
    void givenVeryLongNameAttribute_whenDoFilter_thenChainIsInvoked() throws IOException, ServletException {
        // Arrange
        String longName = "a".repeat(10000);
        when(servletRequest.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("name")).thenReturn(longName);

        // Act
        loginRequiredFilter.doFilter(servletRequest, servletResponse, chain);

        // Assert
        verify(chain, times(1)).doFilter(servletRequest, servletResponse);
        verify(servletRequest, never()).getRequestDispatcher(any());
    }

    @Test
    @DisplayName("Given unauthenticated user when doFilter then correct dispatcher path is used")
    void givenUnauthenticatedUser_whenDoFilter_thenCorrectDispatcherPathIsUsed() throws IOException, ServletException {
        // Arrange
        when(servletRequest.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("name")).thenReturn(null);
        when(servletRequest.getRequestDispatcher("/login.do")).thenReturn(requestDispatcher);

        // Act
        loginRequiredFilter.doFilter(servletRequest, servletResponse, chain);

        // Assert
        verify(servletRequest, times(1)).getRequestDispatcher("/login.do");
        verify(requestDispatcher, times(1)).forward(servletRequest, servletResponse);
    }
}