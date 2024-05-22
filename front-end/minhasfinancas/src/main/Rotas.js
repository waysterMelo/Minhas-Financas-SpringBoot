import React from 'react';
import Login from "../views/login";
import CadastroUsuarios from "../views/CadastroUsuarios";
import {HashRouter, Switch, Route} from 'react-router-dom';


function Rotas(){
    return (
        <HashRouter>
            <Switch>
                <Route exact path="/login" component={Login} />
                <Route exact path="/cadastro-usuarios" component={CadastroUsuarios} />
            </Switch>
        </HashRouter>
    )
}


export default Rotas;