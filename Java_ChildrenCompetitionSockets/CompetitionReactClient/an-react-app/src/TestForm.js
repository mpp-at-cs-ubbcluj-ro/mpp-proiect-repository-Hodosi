
// import React from  'react';
// class TestForm extends React.Component{
//
//     constructor(props) {
//         super(props);
//         // this.state = {id: '', type:'', category:''};
//         this.state = {id: '',
//                       type:{id:'4',type:'fain'},
//                       category:{id:'3',minAge:100,maxAge:300}};
//
//         //  this.handleChange = this.handleChange.bind(this);
//         // this.handleSubmit = this.handleSubmit.bind(this);
//     }
//
//     handleTestChange=(event) =>{
//         this.setState({id: event.target.value});
//     }
//
//     handleTypeChange=(event) =>{
//         this.setState({type: event.target.value});
//     }
//
//     handleCategoryChange=(event) =>{
//         this.setState({category: event.target.value});
//     }
//
//     handleSubmit =(event) =>{
//
//         let test={id:this.state.id,
//             type:this.state.type,
//             category:this.state.category
//         }
//         console.log('A user was submitted: ');
//         console.log(test);
//         this.props.addFunc(test);
//         event.preventDefault();
//     }
//
//     render() {
//         return (
//             <form onSubmit={this.handleSubmit}>
//                 <label>
//                     Id:
//                     <input type="text" value={this.state.id} onChange={this.handleTestChange} />
//                 </label><br/>
//                 <label>
//                     Type:
//                     <input type="text" value={this.state.type} onChange={this.handleTypeChange} />
//                 </label><br/>
//                 <label>
//                     Category:
//                     <input type="text" value={this.state.category} onChange={this.handleCategoryChange} />
//                 </label><br/>
//
//                 <input type="submit" value="Add test" />
//             </form>
//         );
//     }
// }
// export default TestForm;

import React from  'react';
class TestForm extends React.Component{

    constructor(props) {
        super(props);
        this.state = {id: '',
                        type_id: '',
                        type: '',
                        category_id:'',
                        minAge:'',
                        maxAge:''}

        //  this.handleChange = this.handleChange.bind(this);
        // this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleTestChange=(event) =>{
        this.setState({id: event.target.value});
    }

    handleTypeIdChange=(event) =>{
        this.setState({type_id: event.target.value});
    }

    handleTypeChange=(event) =>{
        this.setState({type: event.target.value});
    }

    handleCategoryIdChange=(event) =>{
        this.setState({category_id: event.target.value});
    }

    handleMinAgeChange=(event) =>{
        this.setState({minAge: event.target.value});
    }

    handleMaxAgeChange=(event) =>{
        this.setState({maxAge: event.target.value});
    }

    handleSubmit =(event) =>{

        let test={id:this.state.id,
            type_id:this.state.type_id,
            type:this.state.type,
            category_id:this.state.category_id,
            minAge:this.state.minAge,
            maxAge:this.state.maxAge
        }
        console.log('A user was submitted: ');
        console.log(test);

        console.log(event.nativeEvent.submitter.name)

        if(event.nativeEvent.submitter.name === 'add'){
            this.props.addFunc(test);
        }else if(event.nativeEvent.submitter.name === 'update'){
            this.props.updateFunc(test);
        }
        event.preventDefault();
    }

    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                <label>
                    Id:
                    <input type="text" value={this.state.id} onChange={this.handleTestChange} />
                </label><br/>
                <label>
                    Type Id:
                    <input type="text" value={this.state.type_id} onChange={this.handleTypeIdChange} />
                </label><br/>
                <label>
                    Type:
                    <input type="text" value={this.state.type} onChange={this.handleTypeChange} />
                </label><br/>
                <label>
                    Category Id:
                    <input type="text" value={this.state.category_id} onChange={this.handleCategoryIdChange} />
                </label><br/>
                <label>
                    Min age:
                    <input type="text" value={this.state.minAge} onChange={this.handleMinAgeChange} />
                </label><br/>
                <label>
                    Max age:
                    <input type="text" value={this.state.maxAge} onChange={this.handleMaxAgeChange} />
                </label><br/>

                <input type="submit" name="add" value="Add test" />
                <input type="submit" name="update" value="Update test" />
            </form>
        );
    }
}
export default TestForm;
