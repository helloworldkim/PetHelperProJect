import React, { Component } from 'react';
import queryString from 'query-string';
import BoardApiService from '../ApiService/BoardApiService';

class Board_details extends Component {
  constructor(props) {
    super(props);
    this.state = {
      //호출시 기본값 설정없으면 오류 발생함 기본값 설정해둠
      BoardDetails: {
        id: 1,
        title: '',
        userid: 1,
        count: 0,
        content: '',
        createDate: '',
      },
    };
  }

  componentDidMount() {
    console.log('======================================');
    let query = queryString.parse(this.props.location.search);
    //queryString을 객체형태로 반환받는다
    console.log(query);
    console.log(Number(query.boardid));
    //해당 게시물의 상세정보를 불러온다
    this.getBoardDetails(Number(query.boardid));
  }

  getBoardDetails = async (boardid) => {
    console.log('getBoardDetails 메서드 호출');
    // console.log(boardid);
    BoardApiService.boardDetails(boardid)
      .then((res) => {
        console.log(res);
        let BoardDetails = res.data;
        this.setState({ BoardDetails: BoardDetails });
      })
      .catch((err) => {
        console.log(err);
      });
  };
  gotoBoardList = () => {
    console.log('gotoBoardList 메서드 호출');
    this.props.history.goBack();
  };
  render() {
    return (
      <div className="container">
        <div className="row">
          <table className="table">
            <thead>
              <tr>
                <th>상세보기</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <th>글번호</th>
                <td>{this.state.BoardDetails.id}</td>
              </tr>
              <tr>
                <th>제목</th>
                <td>{this.state.BoardDetails.title}</td>
              </tr>
              <tr>
                <th>작성자</th>
                <td>{this.state.BoardDetails.userid}</td>
              </tr>
              <tr>
                <th>조회수</th>
                <td>{this.state.BoardDetails.count}</td>
              </tr>
              <tr>
                <th>작성일</th>
                <td>{this.state.BoardDetails.createDate}</td>
              </tr>
              <tr>
                <td>글내용</td>
                <td>{this.state.BoardDetails.content}</td>
              </tr>
            </tbody>
          </table>
        </div>
        <div className="mb-1 centerblock">
          <button className="btn btn-primary" onClick={this.gotoBoardList}>
            목록
          </button>
        </div>
        <div>
          <textarea placeholder="댓글을 입력해주세요" className="form-control" rows="2" cols="100" name="content" />
          <button type="button" className="btn btn-danger btn-lg" style={{ margin: 10 }}>
            댓글
          </button>
        </div>
      </div>
    );
  }
}

export default Board_details;
