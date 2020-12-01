import React, { Component } from 'react';

class ViewCardPets extends Component {

    constructor(props) {
        super(props);
        console.log("props값:", props);
        this.state = {
            ...props
        }
        console.log(this.state);
    }
    componentDidMount() {

    }

    render() {
        return (
            <>
                <div className="card" style={{ width: '400px' }}>
                    <img className="card-img-top" src="" alt="pet images" />
                    <div className="card-body">
                        <h4 className="card-title">John Doe</h4>
                        <p className="card-text">Some example text.</p>

                    </div>
                </div>
            </>

        );
    }
}
export default ViewCardPets;
