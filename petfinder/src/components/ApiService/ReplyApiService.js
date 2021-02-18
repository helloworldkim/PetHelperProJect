import axios from 'axios';

const REPLY_URL = 'http://localhost:8080/reply'; //spring boot 접속 url

class ReplyApiService {
  //게시판글에 달린 댓글을 불러오는 메서드
  ReplyList(boardid, replyPageNum) {
    return axios.get(REPLY_URL + `?boardid=${boardid}&pageNum=${replyPageNum}`);
  }

  //댓글 생성하는부분
  ReplyWrite(JWT, Reply) {
    return fetch(REPLY_URL + '/write', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: JWT,
      },
      body: JSON.stringify(Reply),
    });
  }
}

export default new ReplyApiService();
