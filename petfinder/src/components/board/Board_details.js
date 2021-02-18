import React, { Component } from 'react';
import queryString from 'query-string';
import BoardApiService from '../ApiService/BoardApiService';
import JWTService from '../JWTService/JWTService';
import ReplyApiService from '../ApiService/ReplyApiService';
import Reply from '../reply/Reply';
import MoreReply from '../reply/MoreReply';

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
      //기본 보여줄 댓글페이지 5개 기준 1페이지
      replyPageNum: 1,
    };
  }

  componentDidMount() {
    console.log('======================================');
    let query = this.getQueryString();
    console.log(Number(query.boardid));
    //해당 게시물의 상세정보와 게시글 정보를 다 가져온다
    this.getBoardDetails(Number(query.boardid));
    this.getReplyList(Number(query.boardid));
  }

  //queryString에 있는 정보를 가져와 전달 객체형태로 반환받는다
  getQueryString = () => {
    let query = queryString.parse(this.props.location.search);
    // console.log(query);
    return query;
  };

  // 해당 게시물의 정보를 불러오는 메서드
  getBoardDetails = (boardid) => {
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
  //목록 누르면 이전페이지로 돌아가는 메서드
  gotoBoardList = () => {
    console.log('gotoBoardList 메서드 호출');
    this.props.history.goBack();
  };
  //state 값 변경 등록 메서드
  onChangeValues = (e) => {
    this.setState({
      [e.target.name]: e.target.value,
    });
  };
  // REPLY 작성 메서드
  ReplyWrite = () => {
    console.log('ReplyWrite 메서드 호출');
    let content = this.state.content;
    let boardid = this.state.BoardDetails.id;
    //로그인된 사용자만 이용가능 JWT토큰 확인
    let JWT = sessionStorage.getItem('Authorization');

    //토큰값이 없는경우 login 페이지로 보내버림
    JWTService.checkLogin(JWT);

    // Reply 객체 생성(등록할 게시글의 아이디, 작성한 댓글의 내용을 전달한다)
    let Reply = {
      boardid: boardid,
      content: content,
    };
    //댓글 내용과 보낼 JWT토큰을 같이 전달
    ReplyApiService.ReplyWrite(JWT, Reply)
      .then((res) => {
        console.log(res);
      })
      .then(() => {
        //작성된 댓글을 지우고 해당 게시물의 댓글을 새로 받아온다
        this.setState(
          {
            content: '',
          },
          () => {
            this.getReplyList(this.state.BoardDetails.id);
          }
        );
      })
      .catch((err) => {
        console.log(err);
      });
  };
  //해당 리스트의 댓글들을 가져온다
  getReplyList = (boardid) => {
    let replyPageNum = this.state.replyPageNum;
    console.log('getReplyList 메서드 호출');
    ReplyApiService.ReplyList(boardid, replyPageNum)
      .then((res) => {
        console.log(res);
        let replyList = res.data.replyList;
        let replyCount = res.data.replyCount;
        this.setState({
          replyList: replyList,
          replyCount: replyCount,
        });
      })
      .catch((err) => {
        console.log(err);
      });
  };
  // 댓글 더 보기를 눌렀을때 해당 메서드 호출
  getMoreReply = () => {
    console.log('getMoreReply 메소드 호출');
    console.log(this.state.replyPageNum);
    let replyPageNum = this.state.replyPageNum;

    //현재 페이지넘버를 1증가 시키고, 다시 댓글을 호출해서 그려준다
    this.setState(
      {
        replyPageNum: replyPageNum + 1,
      },
      () => {
        this.getReplyList(this.state.BoardDetails.id);
      }
    );
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
        <div>
          <textarea
            placeholder="댓글을 입력해주세요"
            value={this.state.content}
            className="form-control"
            rows="2"
            cols="100"
            name="content"
            onChange={this.onChangeValues}
          />
          <div className="d-flex justify-content-center">
            <button className="btn btn-danger btn-lg" onClick={this.ReplyWrite} style={{ margin: 10 }}>
              댓글
            </button>
            <button className="btn btn-primary btn-lg" onClick={this.gotoBoardList} style={{ margin: 10 }}>
              목록
            </button>
          </div>
        </div>
        {this.state.replyList
          ? this.state.replyList.map((reply) => {
              return <Reply key={reply.id} reply={reply}></Reply>;
            })
          : ''}
        {/* 댓글 갯수가 5개 이상, 현재 불러온 댓글 갯수가 총 갯수보다 작아야지만 댓글 더 보기를 보여준다 */}
        {this.state.replyCount > 5 && this.state.replyPageNum * 5 < this.state.replyCount ? (
          <MoreReply getMoreReply={this.getMoreReply}></MoreReply>
        ) : (
          ''
        )}
      </div>
    );
  }
}

export default Board_details;
