//login validation

//function declaration
function loginFormValidation() {

    //input values calling and setting a variable
    var email = document.getElementById("email");
    var password = document.getElementById("password");

    //conditions for validations
    if (email == null || email == "") {

        alert("Email can't be blank");
        return false;
    }
    if (password == null || email == "") {

        alert("Password should be blank")
        return false;

    }
}