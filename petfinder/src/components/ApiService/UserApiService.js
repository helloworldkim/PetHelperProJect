import axios from 'axios';

const API_URL = 'http://localhost:8080/user'; //spring boot 접속 url

class UserApiService {

    registerUser(User) {
        return axios.post(API_URL, User);
    }

}

export default new UserApiService();