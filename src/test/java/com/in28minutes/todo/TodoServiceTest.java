package com.in28minutes.todo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TodoServiceTest {

    private TodoService todoService;

    @BeforeEach
    void setUp() throws Exception {
        todoService = new TodoService();
        // Reset the static list to a known state for each test
        Field todosField = TodoService.class.getDeclaredField("todos");
        todosField.setAccessible(true);
        List<Todo> todos = (List<Todo>) todosField.get(null);
        todos.clear();
    }

    @Test
    @DisplayName("Given an empty list, when retrieving todos, then return an empty list")
    void givenEmptyList_whenRetrieveTodos_thenReturnEmptyList() {
        // Arrange
        // The list is already cleared in setUp

        // Act
        List<Todo> result = todoService.retrieveTodos();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Given no todos added, when retrieving todos, then return the default seeded list")
    void givenNoTodosAdded_whenRetrieveTodos_thenReturnDefaultSeedList() throws Exception {
        // Arrange
        // Re-initialize the static block by creating a new instance if the list was cleared
        // But since the static block only runs once, we need to manually add the defaults for this test
        // Actually, the static block runs when the class is loaded. Since we cleared it in setUp,
        // we need to re-add the defaults to simulate the initial state.
        Field todosField = TodoService.class.getDeclaredField("todos");
        todosField.setAccessible(true);
        List<Todo> todos = (List<Todo>) todosField.get(null);
        todos.add(new Todo("Learn Web Application Development", "Study"));
        todos.add(new Todo("Learn Spring MVC", "Study"));
        todos.add(new Todo("Learn Spring Rest Services", "Study"));

        // Act
        List<Todo> result = todoService.retrieveTodos();

        // Assert
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("Learn Web Application Development", result.get(0).getText());
        assertEquals("Learn Spring MVC", result.get(1).getText());
        assertEquals("Learn Spring Rest Services", result.get(2).getText());
    }

    @Test
    @DisplayName("Given a valid todo, when adding a todo, then the list size increases by one")
    void givenValidTodo_whenAddTodo_thenListSizeIncreasesByOne() {
        // Arrange
        Todo newTodo = new Todo("New Task", "Work");

        // Act
        todoService.addTodo(newTodo);
        List<Todo> result = todoService.retrieveTodos();

        // Assert
        assertEquals(1, result.size());
        assertEquals("New Task", result.get(0).getText());
        assertEquals("Work", result.get(0).getOwner());
    }

    @Test
    @DisplayName("Given multiple todos, when adding another todo, then all todos are present")
    void givenMultipleTodos_whenAddAnotherTodo_thenAllTodosArePresent() {
        // Arrange
        Todo todo1 = new Todo("Task 1", "Owner 1");
        Todo todo2 = new Todo("Task 2", "Owner 2");
        Todo todo3 = new Todo("Task 3", "Owner 3");
        todoService.addTodo(todo1);
        todoService.addTodo(todo2);

        // Act
        todoService.addTodo(todo3);
        List<Todo> result = todoService.retrieveTodos();

        // Assert
        assertEquals(3, result.size());
        assertTrue(result.contains(todo1));
        assertTrue(result.contains(todo2));
        assertTrue(result.contains(todo3));
    }

    @Test
    @DisplayName("Given a null todo, when adding a todo, then the list contains a null element")
    void givenNullTodo_whenAddTodo_thenListContainsNullElement() {
        // Arrange
        Todo nullTodo = null;

        // Act
        todoService.addTodo(nullTodo);
        List<Todo> result = todoService.retrieveTodos();

        // Assert
        assertEquals