import React from 'react';
import "bootswatch/dist/vapor/bootstrap.min.css";
import Rotas from './Rotas';
import NavBar from "../components/NavBar";


class App extends React.Component {

  render() {
      return(
          <div>
              <NavBar/>
              <div className="container">
                  <Rotas />
              </div>
          </div>
      )
  }
}

export default App;