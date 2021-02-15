import axios from 'axios';

const API_URL = 'http://localhost:8080/user'; //spring boot 접속 url

class UserApiService {

    registerUser(User) {
        return axios.post(API_URL+'/join', User);
    }
    login(User) {
        return axios.post('http://localhost:8080/login', User);
    }
    jwtcheck(token) {
        return axios.get(API_URL+'/jwtcheck', { headers: { Authorization: token,'Content-Type': 'application/json' } });
    }
}

export default new UserApiService();