package com.in28minutes.todo;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ExtendWith(MockitoExtension.class)
class ListTodoServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Spy
    private TodoService todoService;

    @InjectMocks
    private ListTodoServlet listTodoServlet;

    @BeforeEach
    void setUp() {
        // Ensure the spy's todoService is used by the servlet if possible, 
        // but since ListTodoServlet instantiates TodoService directly, 
        // we need to handle this carefully.
        // However, the prompt says "Mock only external dependencies". 
        // TodoService is a dependency. 
        // Since ListTodoServlet has a private field `todoService = new TodoService()`, 
        // @InjectMocks might not inject into it if it's not a constructor/setter injection.
        // Let's assume standard Mockito behavior where it tries to inject into fields.
        // If TodoService is not injectable via field (because it's initialized inline), 
        // we might need to use reflection or PowerMock, but the prompt forbids PowerMock.
        // Let's re-read the target code:
        // private TodoService todoService = new TodoService();
        // This is a direct instantiation. @InjectMocks will NOT replace this field 
        // because it's not null and not injected via constructor/setter.
        // Therefore, we cannot easily mock TodoService with standard Mockito without reflection.
        // However, the instructions say "Mock only external dependencies". 
        // If we can't mock it, we must test with the real TodoService or use reflection.
        // Given the constraints, I will use reflection to inject the mock/spy into the private field.
    }

    private void injectTodoService(TodoService service) throws Exception {
        java.lang.reflect.Field field = ListTodoServlet.class.getDeclaredField("todoService");
        field.setAccessible(true);
        field.set(listTodoServlet, service);
    }

    @Test
    @DisplayName("Given a valid request with todos, when doGet is called, then todos are set in request and view is forwarded")
    void givenValidRequestWithTodos_whenDoGet_thenReturnSuccess() throws Exception {
        // Arrange
        List<Todo> todos = Arrays.asList(
                new Todo(1, "User1", "Learn Java", "2023-01-01", false),
                new Todo(2, "User1", "Learn Spring", "2023-01-02", true)
        );
        when(todoService.retrieveTodos()).thenReturn(todos);
        when(request.getRequestDispatcher("/WEB-INF/views/list-todos.jsp")).thenReturn(requestDispatcher);
        injectTodoService(todoService);

        // Act
        listTodoServlet.doGet(request, response);

        // Assert
        verify(request).setAttribute("todos", todos);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    @DisplayName("Given a valid request with empty todos, when doGet is called, then empty list is set in request and view is forwarded")
    void givenValidRequestWithEmptyTodos_whenDoGet_thenReturnSuccess() throws Exception {
        // Arrange
        List<Todo> todos =