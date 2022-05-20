
import React from  'react';
import TestTable from './TestTable';
import './TestApp.css'
import TestForm from "./TestForm";
import {GetTests, DeleteTest, AddTest, UpdateTest} from './utils/rest-calls'


class TestApp extends React.Component{
    constructor(props){
        super(props);
        this.state={tests:[{"id":"11","type":"101","category":"101"}],
            deleteFunc:this.deleteFunc.bind(this),
            addFunc:this.addFunc.bind(this),
            updateFunc:this.updateFunc.bind(this),
        }
        console.log('TestApp constructor')
    }

    addFunc(test){
        console.log('inside add Func '+test);
        AddTest(test)
            .then(res=> GetTests())
            .then(tests=>this.setState({tests}))
            .catch(erorr=>console.log('eroare add ',erorr));
    }

    updateFunc(test){
        console.log('inside update Func '+test);
        UpdateTest(test)
            .then(res=> GetTests())
            .then(tests=>this.setState({tests}))
            .catch(erorr=>console.log('eroare update ',erorr));
    }


    deleteFunc(test){
        console.log('inside deleteFunc '+test);
        DeleteTest(test)
            .then(res=> GetTests())
            .then(tests=>this.setState({tests}))
            .catch(error=>console.log('eroare delete', error));
    }


    componentDidMount(){
        console.log('inside componentDidMount')
        GetTests().then(tests=>this.setState({tests}));
    }

    render(){

        return(
            <div className="TestApp">
                <h1>Competition Test Management</h1>
                <TestForm addFunc={this.state.addFunc} updateFunc={this.state.updateFunc}/>
                <br/>
                <br/>
                <TestTable tests={this.state.tests} deleteFunc={this.state.deleteFunc}/>
            </div>
        );
    }
}

export default TestApp;