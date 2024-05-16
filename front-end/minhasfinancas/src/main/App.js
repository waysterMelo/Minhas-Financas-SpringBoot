import React from 'react';
import Login from "../views/login";
import '../css/custom.min.css';
import CadastroUsuarios from "../views/CadastroUsuarios";
import 'bootstrap/dist/css/bootstrap.min.css';

class App extends React.Component {


  render() {
         return (
             <CadastroUsuarios />
         )

  }
}

export default App;