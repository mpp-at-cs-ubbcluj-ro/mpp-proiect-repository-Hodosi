
import React from  'react';
import './TestApp.css'

class TestRow extends React.Component{

    handleDelete=(event)=>{
        console.log('delete button pentru '+this.props.test.id);
        this.props.deleteFunc(this.props.test.id);
    }

    render() {
        return (
            <tr>
                <td>{this.props.test.id}</td>
                <td>{this.props.test.type.id}</td>
                <td>{this.props.test.category.id}</td>
                <td><button  onClick={this.handleDelete}>Delete</button></td>
            </tr>
        );
    }
}

class TestTable extends React.Component {
    render() {
        let rows = [];
        let functieStergere=this.props.deleteFunc;
        this.props.tests.forEach(function(test) {

            rows.push(<TestRow test={test}  key={test.id} deleteFunc={functieStergere} />);
        });
        return (<div className="TestTable">

                <table className="center">
                    <thead>
                    <tr>
                        <th>Id Test</th>
                        <th>Id Type</th>
                        <th>Id Category</th>

                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>{rows}</tbody>
                </table>

            </div>
        );
    }
}

export default TestTable;