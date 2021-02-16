import { Paper, Table, TableBody, TableCell, TableHead, TableRow } from '@material-ui/core';
import React, { Component } from 'react';
import BoardApiService from '../ApiService/BoardApiService';
import Pagenation from '../Pagenation/Pagenation';
import Board from './Board';

class Board_list extends Component {
  constructor(props) {
    super(props);
    this.state = {
      pageNum: 1,
    };
    this.getBoardList(1);
  }

  componentDidMount() {}

  //해당 게시판의 글 정보를 받아오는 메소드
  getBoardList = (pageNum) => {
    console.log('getBoardLiST 메소드 호출');
    console.log(pageNum);

    BoardApiService.boardList(pageNum)
      .then((res) => {
        console.log(res);
        let BoardList = res.data.boardList;
        let BoardCount = res.data.boardCount;
        let TotalPage = res.data.totalPage;
        let LastPage = res.data.lastPage;
        this.setState({
          BoardList: BoardList,
          BoardCount: BoardCount,
          TotalPage: TotalPage,
          LastPage: LastPage,
        });
      })
      .catch((err) => {
        console.log(err);
      });
  };

  //페이지핸들러
  pageHandler = ({ number }) => {
    if (number === this.state.pageNum) {
      alert('현재페이지입니다');
      return;
    }
    if (number > this.state.TotalPage) {
      alert('없는페이지입니다');
      return;
    }

    this.setState({ pageNum: number }, () => {
      this.getBoardList(this.state.pageNum);
    });
  };

  getDetailsPage = (boardid) => {
    console.log('getDetailsPage 메서드 호출');
    console.log(boardid);
    //디테일 페이지로 해당 게시글 인덱스 넘버를 가지고 이동한다
    this.props.history.push('/Board_details?boardid=' + boardid);
  };

  render() {
    return (
      <Paper>
        <Table>
          {/* 해당하는 목록이 어딘지 표시하는 머리부분 */}
          <TableHead>
            <TableRow>
              <TableCell>글번호</TableCell>
              <TableCell>제목</TableCell>
              <TableCell>작성자</TableCell>
              <TableCell>조회수</TableCell>
              <TableCell>작성일</TableCell>
              <TableCell>댓글수</TableCell>
              <TableCell>내용보기</TableCell>
            </TableRow>
          </TableHead>
          {/* 게시글 10개 가져와서 렌더링 하는부분 */}
          <TableBody>
            {this.state.BoardList
              ? this.state.BoardList.map((board) => {
                  return <Board key={board.id} Board={board} toggle={this.toggle} getDetailsPage={this.getDetailsPage}></Board>;
                })
              : '게시글 로딩중입니다'}
          </TableBody>
        </Table>
        {/* 페이징 하는부분 */}
        <Pagenation page={this.state.pageNum} pageHandler={this.pageHandler} />
      </Paper>
    );
  }
}

export default Board_list;
