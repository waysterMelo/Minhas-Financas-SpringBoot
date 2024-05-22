import React from 'react';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import {Col, Row} from "react-bootstrap";
import Card from "../components/Card";


class CadastroUsuarios extends React.Component {

    state={
        nome:'',
        email:'',
        senha:'',
        senharepeticao:''
}

    cadastrar=()=>{
            console.log(this.state)
    }

    render(){
        return(
              <Row style={{marginTop: '10%'}}>
                    <Card title={'Cadastrar Usuários'}>
                        <Col className={'col-12 mx-auto'}>
                            <Form>
                                <Form.Group className="mb-3" controlId="nome">
                                    <Form.Label>Nome:</Form.Label>
                                    <Form.Control type="text" placeholder="Digite seu nome"
                                   value={this.state.nome}
                                   onChange={e => {this.setState({nome: e.target.value})}} />
                                </Form.Group>
                                <Form.Group className="mb-3" controlId="email">
                                    <Form.Label>Email:</Form.Label>
                                    <Form.Control type="email" placeholder="Enter email"
                                        value={this.state.email}
                                        onChange={e => {this.setState({email: e.target.value})}}/>
                                </Form.Group>
                                <Form.Group className="mb-3" controlId="senha">
                                    <Form.Label>Senha</Form.Label>
                                    <Form.Control type="password" placeholder="Senha" value={this.state.senha}
                                     onChange={e => this.setState({senha: e.target.value})}/>
                                </Form.Group>
                                <Form.Group className="mb-3" controlId="repitasenha">
                                    <Form.Label>Senha</Form.Label>
                                    <Form.Control type="password" placeholder="repita a senha" value={this.state.senharepeticao}
                                    onChange={e => this.setState({senharepeticao: e.target.value})}/>
                                </Form.Group>
                                <Button onClick={this.cadastrar} variant="success" type="button">
                                    Salvar
                                </Button>
                                <Button variant="danger" type="button">
                                    Cancelar
                                </Button>
                            </Form>
                        </Col>
                    </Card>
              </Row>
        )
    }
}

export default CadastroUsuarios;