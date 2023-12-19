//login validation

//eventlistener through all document
document.addEventListener('DOMContentLoaded',function(){


    //form
    let form= document.querySelector('form');

    //set email & password from html elements
    let email= document.getElementById('email');
    let password=document.getElementById('password');


    //set email & password error messages from html elements
    let emailErrorMessage= document.getElementById('email_errormessage');
    let passwordErrorMessage= document.getElementById('password_errormessage')

    //Email validate method
    function emailValidate(){
        if(email.value.trim()==='' || email.value=== null){
            emailErrorMessage.innerHTML='Email cannot be blank';
            return false;
        }else{
            emailErrorMessage.innerHTML='';
            return true;
        }
    }
    function passwordValidate(){
        if(password.value.trim()==='' || password.value===null){
            passwordErrorMessage.innerHTML='Password can\'t be blank';
            return false;
        }else if(password.length<6){
            passwordErrorMessage.innerHTML= 'password should be at least 6 character';
            return false;

        } else{
            passwordErrorMessage.innerHTML='';
            return true;
        }

    }

    //Everytime input is put, validate function are called
    email.addEventListener('input', emailValidate );
    password.addEventListener('input', passwordValidate);


    //form submission preventer if false
    form.addEventListener('submit',function(event){


        if(!emailValidate() || !passwordValidate()){
            event.preventDefault();
        }


    })



















})















//function declaration



// function loginFormValidation() {
//
//     //input values calling and setting a variable
//     let email = document.getElementById("email").value.trim();
//     let password = document.getElementById("password").value.trim();
//
//     //conditions for validations
//     if (email == null || email === "") {
//
//         alert("Email can't be blank");
//         return false;
//     }
//
//     if (password == null || email==="") {
//
//         alert("Password should not be blank")
//         return false;
//
//     }
//     if (password.length < 6){
//         alert("Password Length should be at least 6 characters")
//         return false;
//     }
//
//
//
//
// }