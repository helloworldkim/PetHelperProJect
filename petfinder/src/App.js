import React, { Component } from 'react';
import './App.css';
import ViewSido from './components/ViewSido';
import ViewSigugun from './components/ViewSigugun';
import ViewShelter from './components/ViewShelter';
import ViewCardPets from './components/ViewCardPets';
const axios = require('axios');

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      sidos: [],
      siguguns: [],
      shelters: [],
      pets: [
        { kind: '강아지', code: 417000 },
        { kind: '고양이', code: 422400 },
        { kind: '다른동물들', code: 429900 },
      ],
      bgnde: {},
      endde: {},
      findedPets: [],
      totalCount: 0,
      pageNo: 1,
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
    if (this.state.siguguns !== []) {
      this.setState({ siguguns: [], shelters: [] });
    }
    // console.log("orgCd값:", orgCd);
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
    if (this.state.shelters !== []) {
      this.setState({ shelters: [] });
    }
    // console.log("uprCd값:", uprCd);
    // console.log("orgCd값:", orgCd);
    let res = await axios.default.get('http://localhost:8080/shelter?orgCd=' + orgCd + "&uprCd=" + uprCd)
      .catch(err => {
        console.log(err);
      }
      );
    console.log("쉘터정보요청 response:", res);
    if (res.data.response.body.items === '') {  //해당구역에 쉘터가 하나도 없을때
      alert('해당 구역에는 쉘터가없습니다');
      return;
    }
    console.log(res);
    // console.log("length", res.data.response.body.items.item.length);
    const result = res.data.response.body.items.item; //필요한 쉘터 데이터
    if (res.data.response.body.items.item.length > 1) { //1개 이상일때
      this.setState({ shelters: result });

    } else {  //한개일때 배열로 보내기위해 처리함
      let temp = []
      temp.push(result)
      this.setState({ shelters: temp });
    }
    console.log("현재 쉘터정보!:", this.state.shelters);
  }

  findPet = async (code) => {
    let res = await axios.default.get('http://localhost:8080/kindofpet?up_kind_cd=' + code)
      .catch(err => {
        console.log(err);
      }
      );
    // console.log("fidpet결과값:", res);
    let result = res.data.response.body.items.item;
    // console.log("result값:", result);
    this.setState({ findedPets: result });

  }
  inputHandler = (e) => {
    this.setState({
      [e.target.name]: e.target.value
    });
  }
  //유기동물 날짜로 검색해주는 메서드
  findAbandonmentPublic = async () => {
    let res = await axios.default.get(`http://localhost:8080/abandonmentPublic?bgnde=${this.state.bgnde}&endde=${this.state.endde}`)
      .catch(err => {
        console.log(err);
      }
      );
    console.log("findAbandonmentPublic:", res);
    if (res.data.response.body.items === '') {
      alert('검색결과가 없습니다');
      return;
    }
    let result = res.data.response.body.items.item
    let totalCount = res.data.response.body.totalCount
    let pageNo = res.data.response.body.pageNo
    this.setState({
      findedPets: result,
      totalCount: totalCount,
      pageNo: pageNo,
    })
    // console.log(this.state.findedPets);
  }
  componentDidMount() {

  }

  render() {

    return (
      <>
        <div className="container">

          <nav class="navbar navbar-expand-sm bg-light">
            {this.state.sidos ? this.state.sidos.map((item, index) => {
              return <ViewSido findSigungu={this.findSigungu} key={index} item={item}></ViewSido>
            }) : ''}
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
            onClick={this.findSido}
          >유기동물 보호센터 위치 조회하기</button>
          <button
            className="btn btn-primary"
          >유기동물 정보 조회하기</button>

          <div class="form-group">
            <label>검색시작일(YYYYMMDD)</label>
            <input type="date" name="bgnde" className="form-control" onChange={this.inputHandler} />
          </div>
          <div class="form-group">
            <label>검색종료일(YYYYMMDD)</label>
            <input type="date" name="endde" className="form-control" onChange={this.inputHandler} />
          </div>
          <button className="btn btn-primary" onClick={this.findAbandonmentPublic}>검색</button>
          {this.state.totalCount !== 0 ? <p>검색된 유기동물 수:{this.state.totalCount}</p> : ''}
          {/* 유기동물들 프로필 나오는부분 */}
          <div className="flexContainer">
            {this.state.findedPets ? this.state.findedPets.map((pet, index) => {
              return <ViewCardPets key={index} pet={pet} totalCount={this.state.totalCount} pageNo={this.state.pageNo}></ViewCardPets>
            }) : ''}
          </div>
        </div>

      </>
    );
  }
}

export default App;
