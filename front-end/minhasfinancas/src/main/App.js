import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import Rotas from './Rotas';
import NavBar from "../components/NavBar";
import {Container, Row, Stack} from "react-bootstrap";


class App extends React.Component {

  render() {
         return (
             <>{/*<> </> é um atalho para <React.Fragment>, que é um componente especial do React que permite agrupar vários elementos sem criar um nó DOM adicional*/}
                 <Stack gab={1}>
                     <Container fluid={true}>
                       <Row>
                           <NavBar />
                       </Row>
                     </Container>
                    <Container>
                        <Row>
                            <Rotas/>
                        </Row>
                    </Container>
                 </Stack>
             </>
         )

  }
}

export default App;