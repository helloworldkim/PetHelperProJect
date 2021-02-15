import React, { Component } from 'react';
import TableRow from '@material-ui/core/TableRow';
import TableCell from '@material-ui/core/TableCell';

class Board extends Component {
    
    componentDidMount(){
    }

    render() {

        return (
          <TableRow>
            <TableCell>{this.props.Board.id}</TableCell>
            <TableCell>{this.props.Board.title}</TableCell>
            <TableCell>{this.props.Board.userid}</TableCell>
            <TableCell>{this.props.Board.count}</TableCell>
            <TableCell>{this.props.Board.createDate}</TableCell>
          </TableRow>
        );
    }
}



export default Board;