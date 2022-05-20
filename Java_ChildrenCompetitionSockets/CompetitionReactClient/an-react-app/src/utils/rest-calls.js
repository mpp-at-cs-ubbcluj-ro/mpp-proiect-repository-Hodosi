
import {COMPETITION_TESTS_BASE_URL} from './consts';

function status(response) {
    console.log('response status '+response.status);
    if (response.status >= 200 && response.status < 300) {
        return Promise.resolve(response)
    } else {
        return Promise.reject(new Error(response.statusText))
    }
}

function json(response) {
    return response.json()
}

export function GetTests(){
    let headers = new Headers();
    headers.append('Accept', 'application/json');
    let myInit = { method: 'GET',
        headers: headers,
        mode: 'cors'
    };
    let request = new Request(COMPETITION_TESTS_BASE_URL, myInit);

    console.log('Inainte de fetch GET pentru '+COMPETITION_TESTS_BASE_URL)

    return fetch(request)
        .then(status)
        .then(json)
        .then(data=> {
            console.log('Request succeeded with JSON response', data);
            return data;
        }).catch(error=>{
            console.log('Request failed', error);
            return Promise.reject(error);
        });

}

export function DeleteTest(id){
    console.log('inainte de fetch delete')
    let myHeaders = new Headers();
    myHeaders.append("Accept", "application/json");

    let antet = { method: 'DELETE',
        headers: myHeaders,
        mode: 'cors'
    };

    const testDelUrl=COMPETITION_TESTS_BASE_URL +'/'+id;
    console.log('URL pentru delete   '+testDelUrl)
    return fetch(testDelUrl,antet)
        .then(status)
        .then(response=>{
            console.log('Delete status '+response.status);
            return response.text();
        }).catch(e=>{
            console.log('error '+e);
            return Promise.reject(e);
        });

}


export function AddTest(test){

    let testType = {id: test.type_id,
                    type: test.type};

    let testCategory = {id: test.category_id,
                        minAge: test.minAge,
                        maxAge: test.maxAge};

    let newTest = {id: test.id,
                    type: testType,
                    category: testCategory}

    console.log("test")
    console.log(testType);
    console.log("category")
    console.log(testCategory);


    console.log('inainte de fetch post'+JSON.stringify(newTest));

    let myHeaders = new Headers();
    myHeaders.append("Accept", "application/json");
    myHeaders.append("Content-Type","application/json");

    let antet = { method: 'POST',
        headers: myHeaders,
        mode: 'cors',
        body:JSON.stringify(newTest)};


    return fetch(COMPETITION_TESTS_BASE_URL,antet)
        .then(status)
        .then(response=>{
            return response.text();
        }).catch(error=>{
            console.log('Request failed', error);
            return Promise.reject(error);
        });


}

export function UpdateTest(test){

    let testType = {id: test.type_id,
        type: test.type};

    let testCategory = {id: test.category_id,
        minAge: test.minAge,
        maxAge: test.maxAge};

    let newTest = {id: test.id,
        type: testType,
        category: testCategory}

    console.log("test")
    console.log(testType);
    console.log("category")
    console.log(testCategory);


    console.log('inainte de fetch post'+JSON.stringify(newTest));

    let myHeaders = new Headers();
    myHeaders.append("Accept", "application/json");
    myHeaders.append("Content-Type","application/json");

    let antet = { method: 'PUT',
        headers: myHeaders,
        mode: 'cors',
        body:JSON.stringify(newTest)};

    const testUpdateUrl=COMPETITION_TESTS_BASE_URL +'/'+test.id;
    return fetch(testUpdateUrl,antet)
        .then(status)
        .then(response=>{
            return response.text();
        }).catch(error=>{
            console.log('Request failed', error);
            return Promise.reject(error);
        });


}


