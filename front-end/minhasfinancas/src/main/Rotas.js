import React from 'react';
import Login from "../views/login";
import CadastroUsuarios from "../views/CadastroUsuarios";



function Rotas(){
    return (
        <HasRouter>
            <Switch>
                <Route exact path={'/'} component={Login}/>
                <Route exact path={'/cadastro'} component={CadastroUsuarios}/>
            </Switch>
        </HasRouter>
    )
}