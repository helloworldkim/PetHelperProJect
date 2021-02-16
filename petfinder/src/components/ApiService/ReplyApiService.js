import axios from 'axios';

const REPLY_URL = 'http://localhost:8080/reply'; //spring boot 접속 url

class ReplyApiService {

    ReplyDetails(boardid){
        return axios.get(REPLY_URL+"?boardid="+boardid);
    }
}

export default new ReplyApiService();
