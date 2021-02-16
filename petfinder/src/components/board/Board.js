import React, { Component } from "react";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import ReplyApiService from "../ApiService/ReplyApiService";

class Board extends Component {
  constructor(props) {
    super(props);
    // console.log(props.Board.id);
    // this.getReplyDetails(this.props.Board.id);
    this.state= {
      replyCount:0,
    }
  }
  
  componentDidMount(){
  }
  
  // 생성될때 해당 게시글의 댓글정보를 불러옴
  getReplyDetails = async (boardid) => {
    console.log("getReplyDetails 메서드 호출");
    console.log(boardid);
    let res = await ReplyApiService.ReplyDetails(boardid);
    let replyCount = res.data.replyCount;
    this.setState({replyCount:replyCount},()=>{
      console.log(replyCount);
    });

    // .catch(
    //   err=>{
    //     console.log(err);
    //   }
    // );
  };
  render() {
    return (
      <TableRow>
       <TableCell>{this.props.Board.id}</TableCell>
        <TableCell>{this.props.Board.title}</TableCell>
        <TableCell>{this.props.Board.userid}</TableCell>
        <TableCell>{this.props.Board.count}</TableCell>
        <TableCell>{this.props.Board.createDate}</TableCell>
        <TableCell>{this.state.replyCount ? this.state.replyCount : '0'}</TableCell>
        <TableCell>
          <button className="btn btn-primary">상세보기</button>
        </TableCell> 
      </TableRow>
    );
  }
}

export default Board;
