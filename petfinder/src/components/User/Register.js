import React, { Component } from 'react';
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import Link from '@material-ui/core/Link';
import Grid from '@material-ui/core/Grid';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import Typography from '@material-ui/core/Typography';
import Container from '@material-ui/core/Container';
import UserApiService from '../ApiService/UserApiService';
import './Register.css';

let emailRegExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
let phoneRegExp = /^\d{3}-\d{3,4}-\d{4}$/;
class Register extends Component {

    constructor(props) {
        super(props);
        console.log();
        this.state = {
            username: '',
            password: '',
            phone: '',
            message: null
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
        //홈경로로 보냄
        this.props.history.push('/');

    }
    onChangeValues = (e) => {
        this.setState({
            [e.target.name]: e.target.value
        });
    }
    register = () => {
        let email = this.state.email;
        let password = this.state.password;
        let phone = this.state.phone;
        // 값 및 형식체크
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
        }
        if (phone === "") {
            alert("휴대폰번호를 입력해주세요");
            return;
        }
        if (!phoneRegExp.test(phone)) {
            alert('휴대폰번호 형식이 아닙니다');
            return;
        }
        //유저객체생성
        let User = {
            username: this.state.email,
            password: this.state.password,
            phone: this.state.phone,
        }
        UserApiService.registerUser(User)
            .then(res => {
                console.log("결과값:", res);
            })
            .catch(err => {
                console.log(err);
            });

    }
    render() {
        return (
            <>
                <Container component="main" maxWidth="xs">
                    <CssBaseline />
                    <div className="paper">
                        <div className="titleBox">
                            <Avatar className="avatar">
                                <LockOutlinedIcon />
                            </Avatar>
                            <Typography component="h1" variant="h5">
                                회원가입
                            </Typography>

                        </div>
                        <Grid container spacing={2}>
                            <Grid item xs={12}>
                                <TextField
                                    variant="outlined"
                                    required
                                    fullWidth
                                    id="email"
                                    label="이메일"
                                    name="email"
                                    onChange={this.onChangeValues}
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    variant="outlined"
                                    required
                                    fullWidth
                                    name="password"
                                    label="비밀번호"
                                    type="password"
                                    onChange={this.onChangeValues}
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    variant="outlined"
                                    required
                                    fullWidth
                                    name="phone"
                                    label="휴대폰"
                                    type="text"
                                    onChange={this.onChange}
                                />
                            </Grid>
                        </Grid>
                        <Button
                            type="submit"
                            fullWidth
                            variant="contained"
                            color="primary"
                            className="submit"
                            children="가입하기"
                            onClick={this.register}
                        />
                        <Grid container justify="flex-end">
                            <Grid item>
                                <Link href="/login" variant="body2">
                                    Already have an account? Sign in
              </Link>
                            </Grid>
                        </Grid>
                    </div>
                </Container>
            </>

        );
    }
}

export default Register;