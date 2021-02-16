import axios from 'axios';

const BOARD_URL = 'http://localhost:8080/board'; //spring boot 접속 url

class BoardApiService {
  boardWrite(token, Board) {
    // return axios.post(BOARD_URL+"/write",Board,{header:{Authorization: token}});
    return fetch(BOARD_URL + '/write', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: token,
      },
      body: JSON.stringify(Board),
    });
  }
  boardList(pageNum) {
    return axios.get(BOARD_URL + '/list?pageNum=' + pageNum);
  }
  boardDetails(boardid) {
    return axios.get(BOARD_URL + '/details?boardid=' + boardid);
  }
}

export default new BoardApiService();
