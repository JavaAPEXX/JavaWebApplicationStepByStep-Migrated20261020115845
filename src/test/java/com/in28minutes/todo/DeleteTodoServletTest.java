package com.in28minutes.todo;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ExtendWith(MockitoExtension.class)
class DeleteTodoServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private TodoService todoService;

    @InjectMocks
    private DeleteTodoServlet deleteTodoServlet;

    @BeforeEach
    void setUp() {
        // Ensure the servlet uses the mocked TodoService instead of the real one
        // Since TodoService is a private field initialized in the constructor, 
        // we need to inject it via reflection or use a spy if possible.
        // However, @InjectMocks works with field injection if the field is not final.
        // In the provided code, `private TodoService todoService = new TodoService();` is not final.
        // Mockito's @InjectMocks will inject the @Mock TodoService into this field.
    }

    @Test
    @DisplayName("Given valid todo and category, when doGet is called, then todo is deleted and redirect occurs")
    void givenValidTodoAndCategory_whenDoGet_thenTodoDeletedAndRedirected() throws ServletException, IOException {
        // Arrange
        String todoText = "Test Todo";
        String category = "Personal";
        when(request.getParameter("todo")).thenReturn(todoText);
        when(request.getParameter("category")).thenReturn(category);
        doNothing().when(todoService).deleteTodo(any(Todo.class));
        doNothing().when(response).sendRedirect(any(String.class));

        // Act
        deleteTodoServlet.doGet(request, response);

        // Assert
        verify(todoService).deleteTodo(any(Todo.class));
        verify(response).sendRedirect("/list-todos.do");
    }

    @Test
    @DisplayName("Given null todo parameter, when doGet is called, then todo is deleted with null text and redirect occurs")
    void givenNullTodoParameter_whenDoGet_thenTodoDeletedWithNullTextAndRedirected() throws ServletException, IOException {
        // Arrange
        when(request.getParameter("todo")).thenReturn(null);
        when(request.getParameter("category")).thenReturn("Work");
        doNothing().when(todoService).deleteTodo(any(Todo.class));
        doNothing().when(response).sendRedirect(any(String.class));

        // Act
        deleteTodoServlet.doGet(request, response);

        // Assert
        verify(todoService).deleteTodo(any(Todo.class));
        verify(response).sendRedirect("/list-todos.do");
    }

    @Test
    @DisplayName("Given null category parameter, when doGet is called, then todo is deleted with null category and redirect occurs")
    void givenNullCategoryParameter_whenDoGet_thenTodoDeletedWithNullCategoryAndRedirected() throws ServletException, IOException {
        // Arrange
        when(request.getParameter("todo")).thenReturn("Test Todo");
        when(request.getParameter("category")).thenReturn(null);
        doNothing().when(todoService).deleteTodo(any(Todo.class));
        doNothing().when(response).sendRedirect(any(String.class));

        // Act
        deleteTodoServlet.doGet(request, response);

        // Assert
        verify(todoService).deleteTodo(any(Todo.class));
        verify(response).sendRedirect("/list-todos.do");
    }

    @Test
    @DisplayName("Given empty string todo and category, when doGet is called, then todo is deleted and redirect occurs")
    void givenEmptyTodoAndCategory_whenDoGet_thenTodoDeletedAndRedirected() throws ServletException, IOException {
        // Arrange
        when(request.getParameter("todo")).thenReturn("");
        when(request.getParameter("category")).thenReturn("");
        doNothing().when(todoService).deleteTodo(any(T