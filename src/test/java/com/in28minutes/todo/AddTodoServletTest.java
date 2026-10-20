package com.in28minutes.todo;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ExtendWith(MockitoExtension.class)
class AddTodoServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @InjectMocks
    private AddTodoServlet addTodoServlet;

    private TodoService todoService;

    @BeforeEach
    void setUp() throws Exception {
        // Use reflection to inject a mocked TodoService into the private field
        todoService = mock(TodoService.class);
        java.lang.reflect.Field field = AddTodoServlet.class.getDeclaredField("todoService");
        field.setAccessible(true);
        field.set(addTodoServlet, todoService);
    }

    @Test
    @DisplayName("Given valid request when doGet is called then forward to add-todo.jsp")
    void givenValidRequest_whenDoGet_thenForwardToAddTodoJsp() throws ServletException, IOException {
        // Arrange
        when(request.getRequestDispatcher("/WEB-INF/views/add-todo.jsp")).thenReturn(requestDispatcher);
        doNothing().when(requestDispatcher).forward(request, response);

        // Act
        addTodoServlet.doGet(request, response);

        // Assert
        verify(request).getRequestDispatcher("/WEB-INF/views/add-todo.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    @DisplayName("Given null request dispatcher when doGet is called then throw NullPointerException")
    void givenNullRequestDispatcher_whenDoGet_thenReturnNullPointerException() throws ServletException, IOException {
        // Arrange
        when(request.getRequestDispatcher("/WEB-INF/views/add-todo.jsp")).thenReturn(null);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            addTodoServlet.doGet(request, response);
        });
    }

    @Test
    @DisplayName("Given valid todo and category when doPost is called then add todo and redirect")
    void givenValidTodoAndCategory_whenDoPost_thenAddTodoAndRedirect() throws ServletException, IOException {
        // Arrange
        String newTodo = "Learn JUnit 5";
        String category = "Testing";
        when(request.getParameter("todo")).thenReturn(newTodo);
        when(request.getParameter("category")).thenReturn(category);
        doNothing().when(todoService).addTodo(any(Todo.class));
        doNothing().when(response).sendRedirect("/list-todos.do");

        // Act
        addTodoServlet.doPost(request, response);

        // Assert
        verify(request).getParameter("todo");
        verify(request).getParameter("category");
        verify(todoService).addTodo(new Todo(newTodo, category));
        verify(response).sendRedirect("/list-todos.do");
    }

    @Test
    @DisplayName("Given null todo parameter when doPost is called then add todo with null description")
    void givenNullTodoParameter_whenDoPost_thenAddTodoWithNullDescription() throws ServletException, IOException {
        // Arrange
        String category = "General";
        when(request.getParameter("todo")).thenReturn(null);
        when(request.getParameter("category")).thenReturn(category);
        doNothing().when(todoService).addTodo(any(Todo.class));
        doNothing().when(response).sendRedirect("/list-todos.do");

        // Act
        addTodoServlet.doPost(request, response);

        // Assert
        verify(todoService).addTodo(new Todo(null, category));
        verify(response).sendRedirect("/list-todos.do");
    }

    @Test
    @DisplayName("Given null category parameter when doPost is called then add todo with null category")