import React, { Component } from 'react';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import Link from '@material-ui/core/Link';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import Container from '@material-ui/core/Container';
import './LoginForm.css';
import UserApiService from '../ApiService/UserApiService';
let emailRegExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
class LoginForm extends Component {
    constructor(props) {
        super(props);
        this.state = {
            username: '',
            password: '',
        }
    }
    componentDidMount() {
        this.checkJWTToken();
    }
    checkJWTToken = () => {
        let JWT = sessionStorage.getItem("Authorization");
        console.log(JWT);
        if (JWT === null) {
            return;
        }
        alert('이미로그인하셨습니다');
        //홈경로로 보냄
        this.props.history.push('/');

    }
    onChangeValues = (e) => {
        this.setState({
            [e.target.name]: e.target.value
        });
    }
    login = () => {
        let email = this.state.email;
        let password = this.state.password;
        if (email === "") {
            alert('이메일을 입력해주세요');
            return;
        }
        if (!emailRegExp.test(email)) {
            alert('이메일 형식이 아닙니다');
            return;
        }
        if (password === "") {
            alert('비밀번호를 입력해주세요');
            return;
        }//유저객체생성
        let User = {
            username: this.state.email,
            password: this.state.password,
        }
        UserApiService.login(User)
            .then(res => {
                console.log("데이터값:", res.data);
                let result = res.data.result;
                console.log("result값:", result);
                if (result === 'fail') {
                    alert('정보가 다릅니다.');
                    return;
                }
                // 결과값으로 받은 JWT토근을 session에 저장함
                sessionStorage.setItem("Authorization", res.data.Authorization);
                let JWT = sessionStorage.getItem("Authorization");
                // console.log(JWT);
                //홈경로로 보냄
                alert('로그인성공');
                // window.location.assign('/');//redirect형식으로 보내버림
                // this.props.history.push('/');
            })
            .catch(err => {
                console.log(err);
            });

    }
    render() {
        return (
            <Container component="main" maxWidth="xs">
                <CssBaseline />
                <div className="paper">
                    <Typography component="h1" variant="h5">
                        로그인
                </Typography>
                    <TextField
                        variant="outlined"
                        margin="normal"
                        required
                        fullWidth
                        label="Email Address"
                        name="email"
                        autoFocus
                        onChange={this.onChangeValues}
                    />
                    <TextField
                        variant="outlined"
                        margin="normal"
                        required
                        fullWidth
                        name="password"
                        label="Password"
                        type="password"
                        onChange={this.onChangeValues}
                    />
                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        color="primary"
                        className="submit"
                        children="로그인"
                        onClick={this.login}
                    />
                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        color="primary"
                        className="submit"
                        children="구글로그인"
                    />
                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        color="primary"
                        className="submit"
                        children="페이스북 로그인"
                    />
                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        color="primary"
                        className="submit"
                        children="네이버 로그인"
                    />
                    <Grid container>
                        <Grid item xs>
                            <Link href="#" variant="body2">
                                Forgot password?(아직안됨)
                        </Link>
                        </Grid>
                        <Grid item>
                            <Link href="/register" variant="body2">
                                {"Don't have an account? Sign Up"}
                            </Link>
                        </Grid>
                    </Grid>
                </div>
            </Container>
        );
    }
}

export default LoginForm;
