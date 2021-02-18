import React, { Component } from 'react';
import './Reply.css';

class Reply extends Component {
  constructor(props) {
    super(props);
    //id, content, boardid, userid,createDate
    console.log(props);
  }

  render() {
    return (
      <>
        <div className="d-flex flex-column">
          <p className="p-2">작성자</p>
          <p className="col overFlowWord">{this.props.reply.content}</p>
          <div className="d-flex justify-content-between">
            <p className="p-2 overFlowWord">{this.props.reply.createDate}</p>
            <div className="d-flex">
              <button className="btn btn-sm btn-primary">수정</button>
              <button className="btn btn-sm btn-primary">삭제</button>
            </div>
          </div>
        </div>
      </>
    );
  }
}

export default Reply;
