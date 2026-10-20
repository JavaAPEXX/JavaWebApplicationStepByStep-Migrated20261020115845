package com.in28minutes.todo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Todo} class.
 */
class TodoTest {

    @Test
    @DisplayName("Given valid name and category, when creating Todo, then getters return correct values")
    void givenValidNameAndCategory_whenCreateTodo_thenGettersReturnCorrectValues() {
        // Arrange
        String name = "Learn Java";
        String category = "Education";

        // Act
        Todo todo = new Todo(name, category);

        // Assert
        assertEquals(name, todo.getName());
        assertEquals(category, todo.getCategory());
    }

    @Test
    @DisplayName("Given null name and category, when creating Todo, then getters return null")
    void givenNullNameAndCategory_whenCreateTodo_thenGettersReturnNull() {
        // Arrange
        String name = null;
        String category = null;

        // Act
        Todo todo = new Todo(name, category);

        // Assert
        assertNull(todo.getName());
        assertNull(todo.getCategory());
    }

    @Test
    @DisplayName("Given empty string name and category, when creating Todo, then getters return empty strings")
    void givenEmptyStringNameAndCategory_whenCreateTodo_thenGettersReturnEmptyStrings() {
        // Arrange
        String name = "";
        String category = "";

        // Act
        Todo todo = new Todo(name, category);

        // Assert
        assertEquals("", todo.getName());
        assertEquals("", todo.getCategory());
    }

    @Test
    @DisplayName("Given existing Todo, when setting new name, then getName returns updated name")
    void givenExistingTodo_whenSetName_thenGetNameReturnsUpdatedName() {
        // Arrange
        Todo todo = new Todo("Old Name", "Category");

        // Act
        todo.setName("New Name");

        // Assert
        assertEquals("New Name", todo.getName());
    }

    @Test
    @DisplayName("Given existing Todo, when setting null name, then getName returns null")
    void givenExistingTodo_whenSetNullName_thenGetNameReturnsNull() {
        // Arrange
        Todo todo = new Todo("Old Name", "Category");

        // Act
        todo.setName(null);

        // Assert
        assertNull(todo.getName());
    }

    @Test
    @DisplayName("Given existing Todo, when setting empty name, then getName returns empty string")
    void givenExistingTodo_whenSetEmptyName_thenGetNameReturnsEmptyString() {
        // Arrange
        Todo todo = new Todo("Old Name", "Category");

        // Act
        todo.setName("");

        // Assert
        assertEquals("", todo.getName());
    }

    @Test
    @DisplayName("Given existing Todo, when setting new category, then getCategory returns updated category")
    void givenExistingTodo_whenSetCategory_thenGetCategoryReturnsUpdatedCategory() {
        // Arrange
        Todo todo = new Todo("Name", "Old Category");

        // Act
        todo.setCategory("New Category");

        // Assert
        assertEquals("New Category", todo.getCategory());
    }

    @Test
    @DisplayName("Given existing Todo, when setting null category, then getCategory returns null")
    void givenExistingTodo_whenSetNullCategory_thenGetCategoryReturnsNull() {
        // Arrange
        Todo todo = new Todo("Name", "Old Category");

        // Act
        todo.setCategory(null);

        // Assert
        assertNull(todo.getCategory());
    }

    @Test
    @DisplayName("Given existing Todo, when setting empty category, then getCategory returns empty string")
    void givenExistingTodo_whenSetEmptyCategory_thenGetCategoryReturnsEmptyString() {
        // Arrange
        Todo todo = new Todo("Name", "Old Category");

        // Act
        todo.setCategory("");

        // Assert
        assertEquals("", todo.getCategory());
    }

    @Test
    @DisplayName("Given Todo with name and category, when calling toString, then returns formatted string")
    void givenTodoWithNameAndCategory_whenToString_thenReturnFormattedString() {
        // Arrange
        Todo todo = new Todo("Learn Java", "Education");

        // Act
        String result = todo.toString();

        // Assert
        assertEquals("Todo [name=Learn Java, category=Education