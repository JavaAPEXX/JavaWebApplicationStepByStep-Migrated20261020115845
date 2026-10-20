import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';

const Login: React.FC = () => {
  const [name, setName] = useState('');
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setIsLoading(true);
    setErrorMessage('');

    try {
      // Preserving the original backend contract: POST to /login.do with form-urlencoded data
      const formData = new URLSearchParams();
      formData.append('name', name);
      formData.append('password', password);

      const response = await fetch('/login.do', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: formData.toString(),
      });

      if (response.ok) {
        // Assuming successful login redirects to the todos list as per standard auth flow
        navigate('/list-todos');
      } else {
        // Handle error response
        const data = await response.json().catch(() => ({}));
        setErrorMessage(data.errorMessage || 'Login failed. Please try again.');
      }
    } catch (error) {
      setErrorMessage('An error occurred during login. Please try again.');
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="modern-container">
      {/* Navbar */}
      <nav className="modern-navbar">
        <div className="modern-navbar-brand">
          <Link to="/">Brand</Link>
        </div>
        <ul className="modern-navbar-nav">
          <li className="active">
            <a href="#">Home</a>
          </li>
          <li>
            <Link to="/list-todos">Todos</Link>
          </li>
          <li>
            <a href="http://www.in28minutes.com" target="_blank" rel="noopener noreferrer">
              In28Minutes
            </a>
          </li>
        </ul>
        <ul className="modern-navbar-nav modern-navbar-right">
          <li>
            <Link to="/login">Login</Link>
          </li>
        </ul>
      </nav>

      {/* Main Content */}
      <main className="modern-main">
        <div className="modern-card">
          <h2 className="modern-card-title">Login</h2>
          
          {errorMessage && (
            <div className="alert-box alert-error" role="alert">
              {errorMessage}
            </div>
          )}

          <form onSubmit={handleSubmit} className="modern-form">
            <div className="form-group">
              <label htmlFor="name" className="form-label">Name</label>
              <input
                type="text"
                id="name"
                name="name"
                className="form-control"
                value={name}
                onChange={(e) = /> setName(e.target.value)}
                required
              />
            </div>

            <div className="form-group">
              <label htmlFor="password" className="form-label">Password</label>
              <input
                type="password"
                id="password"
                name="password"
                className="form-control"
                value={password}
                onChange={(e) = /> setPassword(e.target.value)}
                required
              />
            </div>

            <div className="form-actions">
              <button type="submit" className="btn btn-primary" disabled={isLoading}>
                {isLoading ? 'Logging in...' : 'Login'}
              </button>
            </div>
          </form>
        </div>
      </main>

      {/* Footer */}
      <footer className="modern-footer">
        <div>footer content</div>
      </footer>

      {/* Modern CSS Styles */}
      <style>{`
        :root {
          --primary-color: #007bff;
          --primary-hover: #0056b3;
          --error-color: #dc3545;
          --error-bg: #f8d7da;
          --border-color: #dee2e6;
          --text-color: #212529;
          --bg-color: #f5f5f5;
          --card-bg: #ffffff;
        }

        * {
          box-sizing: border-box;
        }

        body {
          margin: 0;
          font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
          background-color: var(--bg-color);
          color: var(--text-color);
        }

        .modern-container {
          min-height: 100vh;
          display: flex;
          flex-direction: column;
        }

        /* Navbar Styles */
        .modern-navbar {
          display: flex;
          justify-content: space-between;
          align-items: center;
          padding: 0.5rem 1rem;
          background-color: #f8f9fa;
          border-bottom: 1px solid var(--border-color);
        }

        .modern-navbar-brand a {
          font-size: 1.25rem;
          font-weight: bold;
          color: var(--text-color);
          text-decoration: none;
        }

        .modern-navbar-nav {
          display: flex;
          list-style: none;
          margin: 0;
          padding: 0;
          gap: 1rem;
        }

        .modern-navbar-nav a {
          color: var(--text-color);
          text-decoration: none;
          padding: 0.5rem;
          border-radius: 4px;
          transition: background-color 0.2s;
        }

        .modern-navbar-nav a:hover {
          background-color: #e9ecef;
        }

        .modern-navbar-nav .active a {
          font-weight: 600;
          color: var(--primary-color);
        }

        .modern-navbar-right {
          margin-left: auto;
        }

        /* Main Content */
        .modern-main {
          flex: 1;
          display: flex;
          justify-content: center;
          align-items: center;
          padding: 2rem 1rem;
        }

        .modern-card {
          background-color: var(--card-bg);
          border-radius: 8px;
          box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
          padding: 2rem;
          width: 100%;
          max-width: 400px;
        }

        .modern-card-title {
          margin-top: 0;
          margin-bottom: 1.5rem;
          font-size: 1.5rem;
          text-align: center;
          color: var(--text-color);
        }

        /* Form Styles */
        .modern-form {
          display: flex;
          flex-direction: column;
          gap: 1rem;
        }

        .form-group {
          display: flex;
          flex-direction: column;
          gap: 0.5rem;
        }

        .form-label {
          font-weight: 500;
          font-size: 0.95rem;
          color: var(--text-color);
        }

        .form-control {
          padding: 0.5rem 0.75rem;
          font-size: 1rem;
          line-height: 1.5;
          color: var(--text-color);
          background-color: #fff;
          border: 1px solid var(--border-color);
          border-radius: 4px;
          transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
        }

        .form-control:focus {
          outline: 0;
          border-color: #80bdff;
          box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.25);
        }

        .form-actions {
          margin-top: 1rem;
        }

        /* Button Styles */
        .btn {
          display: inline-block;
          font-weight: 400;
          text-align: center;
          white-space: nowrap;
          vertical-align: middle;
          user-select: none;
          border: 1px solid transparent;
          padding: 0.375rem 0.75rem;
          font-size: 1rem;
          line-height: 1.5;
          border-radius: 0