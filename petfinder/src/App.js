import React, { Component } from 'react';
import './App.css';
import ViewSido from './components/ViewSido';
import ViewSigugun from './components/ViewSigugun';
import ViewShelter from './components/ViewShelter';
const axios = require('axios');

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      sidos: [],
      siguguns: [],
      shelters: [],
    }
  }

  findSido = async () => {
    let res = await axios.default.get('http://localhost:8080/sido')
      .catch(err => {
        console.log(err);
      }
      );
    const result = res.data.response.body.items.item;
    this.setState({ sidos: result });
    // console.log("sidos값:", this.state.sidos);
    // console.log("현재 result값:", result);
  }

  findSigungu = async (orgCd) => {
    console.log("orgCd값:", orgCd);
    let res = await axios.default.get('http://localhost:8080/sigungu?orgCd=' + orgCd)
      .catch(err => {
        console.log(err);
      }
      );
    const result = res.data.response.body.items.item;
    this.setState({ siguguns: result });
    // console.log(result);
  }
  findShelter = async (orgCd, uprCd) => {
    // console.log("uprCd값:", uprCd);
    // console.log("orgCd값:", orgCd);
    let res = await axios.default.get('http://localhost:8080/shelter?orgCd=' + orgCd + "&uprCd=" + uprCd)
      .catch(err => {
        console.log(err);
      }
      );
    console.log("쉘터정보요청 response:", res);
    const result = res.data.response.body.items.item;
    this.setState({ shelters: result });
    // console.log(result);
  }

  componentDidMount() {
    this.findSido();

  }
  render() {

    return (
      <>
        <div className="container">

          <nav class="navbar navbar-expand-sm bg-light">
            {this.state.sidos.map((item, index) => {
              return <ViewSido findSigungu={this.findSigungu} key={index} item={item}></ViewSido>
            })}
          </nav>
          <nav class="navbar navbar-expand-sm bg-light">
            {this.state.siguguns ? this.state.siguguns.map((item, index) => {
              return <ViewSigugun findShelter={this.findShelter} key={index} item={item}></ViewSigugun>
            }) : ''}
          </nav>
          <nav class="navbar navbar-expand-sm bg-light">
            {this.state.shelters ? this.state.shelters.map((item, index) => {
              return <ViewShelter key={index} item={item}></ViewShelter>
            }) : ''}
          </nav>
          <button
            className="btn btn-primary"
          >조회하기</button>
        </div>
      </>
    );
  }
}
export default App;
