import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import Login from './views/login';
import reportWebVitals from './reportWebVitals';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(  
  <React.StrictMode>
    <Login />
  </React.StrictMode>
);

reportWebVitals();
