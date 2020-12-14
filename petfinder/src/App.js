import React, { Component } from 'react';
import './App.css';
import { BrowserRouter, Route, Switch, Link } from "react-router-dom";
import PetFinder from './components/PetFinder';
import Register from './components/User/Register';
import LoginForm from './components/User/LoginForm';
import UserApiService from './components/ApiService/UserApiService';
import Logout from './components/User/Logout';

const JWT = sessionStorage.getItem("Authorization");
class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      token: false
    }
  }


  tokenCheck = () => {
    console.log(JWT);
    UserApiService.gotohello(JWT)
      .then(res => {
        console.log(res);
        let tokenCheckResult = res.data;
        console.log(tokenCheckResult);
        if (tokenCheckResult === '토큰만료') {
          //토큰만료됬다는 데이터가 오면 로그인페이지로 강제이동
          // 토큰이 저장된 세션 지우고
          sessionStorage.removeItem("Authorization");
          let login = '/login';
          window.location.assign(login);
        }
      })
      .catch(err => {
        console.log(err);
      });
  }

  render() {
    console.log(JWT);
    // JWT토큰 없을때
    if (JWT === null) {
      return (
        <>
          <BrowserRouter>
            <nav className="navbar navbar-expand-sm bg-light">
              <ul className="navbar-nav">
                <li className="nav-item m-2">
                  <Link to="/">Home</Link>
                </li>
                <li className="nav-item m-2">
                  <Link to="/login">로그인</Link>
                </li>
                <li className="nav-item m-2">
                  <Link to="/register">회원가입</Link>
                </li>
                <li className="nav-item m-2">
                  <Link to="/board">게시판</Link>
                </li>
                <li className="nav-item m-2">
                  <Link to="/pets">유기동물 정보 조회하기</Link>
                </li>
              </ul>
            </nav>
            <Switch>
              <Route exact path="/" ></Route>
              <Route path="/login" component={LoginForm}></Route>
              <Route path="/register" component={Register}></Route>
              <Route path="/board" ></Route>
              <Route path="/pets" component={PetFinder}></Route>
            </Switch>
          </BrowserRouter>

        </>
      );
      //있을때
    } else {
      return (
        <>
          <BrowserRouter>
            <nav className="navbar navbar-expand-sm bg-light">
              <ul className="navbar-nav">
                <li className="nav-item m-2">
                  <Link to="/">Home</Link>
                </li>
                <li className="nav-item m-2">
                  <Link to="/board">게시판</Link>
                </li>
                <li className="nav-item m-2">
                  <Link to="/pets">유기동물 정보 조회하기</Link>
                </li>
                <li className="nav-item m-2">
                  <Link to="/logout">로그아웃 페이지</Link>
                </li>
                <button
                  onClick={this.tokenCheck}
                  children="토큰체크" />
              </ul>
            </nav>
            <Switch>
              <Route exact path="/"></Route>
              <Route path="/login" component={LoginForm}></Route>
              <Route path="/logout" component={Logout}></Route>
              <Route path="/register" component={Register}></Route>
              <Route path="/board" ></Route>
              <Route path="/pets" component={PetFinder}></Route>
            </Switch>
          </BrowserRouter>

        </>
      );
    }
  }
}

export default App;
