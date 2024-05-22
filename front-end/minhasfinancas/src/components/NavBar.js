import React from "react";
import NavBarItens from "./NavBarItens";

function navBar(props){
    return (
        <div className="navbar navbar-expand-lg fixed-top navbar-dark bg-primary">
            <div className="container">
                <a href="/home" className="navbar-brand">Minhas Finanças</a>
                <button className="navbar-toggler" type="button"
                        data-toggle="collapse" data-target="#navbarResponsive"
                        aria-controls="navbarResponsive" aria-expanded="false"
                        aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>
                <div className="collapse navbar-collapse" id="navbarResponsive">
                    <ul className="navbar-nav">
                        <NavBarItens href="#/home" label="Home"/>
                        <NavBarItens href="#/cadastro-usuarios" label="Usuários"/>
                        <NavBarItens href="#/consulta-lancamentos" label="Lançamentos"/>
                        <NavBarItens href="#/login" label="Sair"/>
                    </ul>
                </div>
            </div>
        </div>
    )
}

export default navBar;