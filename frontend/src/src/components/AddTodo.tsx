import React, { useState, FormEvent } from 'react';

const AddTodo: React.FC = () => {
  const [formData, setFormData] = useState({
    todo: '',
    category: ''
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData(prevState => ({
      ...prevState,
      [name]: value
    }));
  };

  const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    // Backend contract: POST to /add-todo.do with form data
    const form = new FormData();
    form.append('todo', formData.todo);
    form.append('category', formData.category);
    
    fetch('/add-todo.do', {
      method: 'POST',
      body: form
    });
  };

  return (
    <div className="modern-container">
      <div className="modern-card">
        <h2>Your New Action Item:</h2>
        <form method="POST" action="/add-todo.do" onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="todo" className="form-label">Description</label>
            <input
              id="todo"
              name="todo"
              type="text"
              className="form-control"
              value={formData.todo}
              onChange={handleChange}
            />
          </div>
          <div className="form-group">
            <label htmlFor="category" className="form-label">Category</label>
            <input
              id="category"
              name="category"
              type="text"
              className="form-control"
              value={formData.category}
              onChange={handleChange}
            />
          </div>
          <button type="submit" className="btn btn-primary">
            Submit
          </button>
        </form>
      </div>
    </div>
  );
};

export default AddTodo;