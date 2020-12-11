import React, { Component } from 'react';
import './App.css';
import { BrowserRouter, Route, Switch, Link } from "react-router-dom";
import PetFinder from './components/PetFinder';
import Register from './components/User/Register';
import LoginForm from './components/User/LoginForm';
import UserApiService from './components/ApiService/UserApiService';

const JWT = sessionStorage.getItem("Authorization");
class App extends Component {

  tokenCheck = () => {
    console.log(JWT);
    UserApiService.gotohello(JWT)
      .then(res => {
        console.log(res);
      })
      .catch(err => {
        console.log(err);
      });
  }


  logout = () => {
    //세션에 존재하는 JWT토큰 삭제
    sessionStorage.removeItem("Authorization");
    //홈경로로 보냄
    this.state.history.push('/');
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
              <Route exact path="/"></Route>
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
                  <button
                    onClick={this.logout}
                    children="로그아웃" />
                  <button
                    onClick={this.tokenCheck}
                    children="토큰체크" />
                </li>
              </ul>
            </nav>
            <Switch>
              <Route exact path="/"></Route>
              <Route path="/login" component={LoginForm}></Route>
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
