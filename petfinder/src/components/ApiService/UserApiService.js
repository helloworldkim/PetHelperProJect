import axios from 'axios';

// const API_URL = 'http://localhost:8080/user/'; //spring boot 접속 url

class UserApiService {

    registerUser(User) {
        return axios.post('http://localhost:8080/user/join', User);
    }
    login(User) {
        return axios.post('http://localhost:8080/login', User);
    }
    jwtcheck(token) {
        return axios.get("http://localhost:8080/user/jwtcheck", { headers: { Authorization: token } });
    }
}

export default new UserApiService();