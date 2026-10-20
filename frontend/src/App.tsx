import React from 'react';
import { BrowserRouter, Routes, Route, Link } from 'react-router-dom';
import './styles/modern-ui.css';
import CommonFooterComponent from './CommonFooterComponent';
import Header from './src/components/Header';
import CommonNavigationComponent from './CommonNavigationComponent';
import AddTodo from './src/components/AddTodo';
import ListTodosComponent from './ListTodosComponent';
import Login from './src/components/Login';

export const App: React.FC = () => {
  return (
    <BrowserRouter>
      <div className="modern-app-root">
        <header className="modern-header">
          <div style={{ display: 'flex', alignItems: 'center', gap: '1rem' }}>
            <span style={{ fontWeight: 700, fontSize: '1.1rem', color: '#2563eb' }}>Modernized Application</span>
          </div>
          <nav style={{ display: 'flex', gap: '1rem' }}>
            <Link to="/" style={{ textDecoration: 'none', color: '#475569', fontWeight: 500 }}>Home</Link>
          </nav>
        </header>
        <main className="modern-main-content">
          <Routes>
        <Route path="/" element={<CommonFooterComponent />} />
        <Route path="/commonfooter" element={<CommonFooterComponent />} />
        <Route path="/header" element={<Header />} />
        <Route path="/commonnavigation" element={<CommonNavigationComponent />} />
        <Route path="/addtodo" element={<AddTodo />} />
        <Route path="/listtodos" element={<ListTodosComponent />} />
        <Route path="/login" element={<Login />} />
          </Routes>
        </main>
      </div>
    </BrowserRouter>
  );
};

export default App;
