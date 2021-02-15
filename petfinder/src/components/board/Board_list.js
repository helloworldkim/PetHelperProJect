import {
  Paper,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
} from "@material-ui/core";
import React, { Component } from "react";
import BoardApiService from "../ApiService/BoardApiService";
import Board from "./Board";

class Board_list extends Component {
  constructor(props) {
    super(props);
    this.state = {
        pageNum :1,
    };
  }

  componentDidMount() {
    this.getBoardList();
  }
  //해당 게시판의 글 정보를 받아오는 메소드
  getBoardList = () => {
    BoardApiService.boardList(this.state.pageNum)
      .then((res) => {
        console.log(res);
        let BoardList = res.data.boardList;
        let BoardCount = res.data.boardCount;
        this.setState({ BoardList: BoardList,BoardCount:BoardCount });
      })
      .catch((err) => {
        console.log(err);
      });
  };
  

  render() {
    return (
      <Paper>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>글번호</TableCell>
              <TableCell>제목</TableCell>
              <TableCell>작성자</TableCell>
              <TableCell>조회수</TableCell>
              <TableCell>작성일</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {this.state.BoardList
              ? this.state.BoardList.map((board) => {
                  return <Board key={board.id} Board={board}></Board>;
                })
              : "게시글 로딩중입니다"}
          </TableBody>
        </Table>
      </Paper>
    );
  }
}

export default Board_list;
