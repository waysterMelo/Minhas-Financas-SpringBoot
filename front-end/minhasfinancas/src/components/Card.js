import React from 'react';


class Card extends React.Component {

render(){
    return (
        <div className={'card'} style={{marginTop: '8%'}}>
            <h3 className={'card-header bg-light p-3'}>{this.props.title}</h3>
            <div className={'card-body p-5'}>{this.props.children}</div>
        </div>
    )
}
}

export default Card;