
document.addEventListener('DOMContentLoaded', function () {
    var form = document.querySelector('form');

    //Set input box elements
    var nameField = document.getElementById('name');
    var emailField = document.getElementById('email');
    var passwordField = document.getElementById('password');
    var confirmPasswordField = document.getElementById('confirmPassword');

    //Set error message elements under input boxes
    var nameErrorMessage = document.getElementById('name-error-message');
    var emailErrorMessage = document.getElementById('email-error-message');
    var passwordErrorMessage = document.getElementById('password-error-message');
    var confirmErrorMessage = document.getElementById('error-message');

    // Function to validate name
    function validateName() {
        if (nameField.value.trim() === '') {
            nameErrorMessage.innerHTML = 'Name is required!';
            return false;
        } else if (!/^[a-zA-Z\s]*$/.test(nameField.value.trim())) {
            nameErrorMessage.innerHTML = 'Name must not contain special characters or numbers!';
            return false;
        } else {
            nameErrorMessage.innerHTML = '';
            return true;
        }
    }

    // Function to validate email
    function validateEmail() {
        if (emailField.value.trim() === '') {
            emailErrorMessage.innerHTML = 'Email is required!';
            return false;
        } else {
            emailErrorMessage.innerHTML = '';
            return true;
        }
    }

    // Function to validate password
    function validatePassword() {
        if (passwordField.value.length < 6) {
            passwordErrorMessage.innerHTML = 'Password must be at least 6 characters!';
            return false;
        } else if (!/[0-9]/.test(passwordField.value)) {
            passwordErrorMessage.innerHTML = 'Password must contain at least one number!';
            return false;
        } else if (!/[!@#$%^&*(),.?":{}|<>]/.test(passwordField.value)) {
            passwordErrorMessage.innerHTML = 'Password must contain a special character!';
            return false;
        } else {
            passwordErrorMessage.innerHTML = '';
            return true;
        }
    }

    // Function to validate confirm password
    function validateConfirmPassword() {
        if (confirmPasswordField.value !== passwordField.value) {
            confirmErrorMessage.innerHTML = 'Passwords do not match!';
            return false;
        } else {
            confirmErrorMessage.innerHTML = '';
            return true;
        }
    }

    // Attach input event listeners for real-time validation
    //two argument, if this event happens, this method works
    nameField.addEventListener('input', validateName);
    emailField.addEventListener('input', validateEmail);
    passwordField.addEventListener('input', validatePassword);
    confirmPasswordField.addEventListener('input', validateConfirmPassword);

    form.addEventListener('submit', function (event) {
        // Check all validations before submitting the form
        if (!validateName() || !validateEmail() || !validatePassword() || !validateConfirmPassword()) {
            // Prevent the form from submitting if any validation fails
            event.preventDefault();
        }
    });
});






// function validateForm() {
//     var nameField = document.getElementById('name');
//     var emailField = document.getElementById('email');
//     var passwordField = document.getElementById('password');
//     var confirmPasswordField = document.getElementById('confirmPassword');
//     var nameErrorMessage = document.getElementById('name-error-message');
//     var emailErrorMessage = document.getElementById('email-error-message');
//     var passwordErrorMessage = document.getElementById('password-error-message');
//     var confirmErrorMessage = document.getElementById('error-message');
//
//     // Reset previous error messages
//     nameErrorMessage.innerHTML = '';
//     emailErrorMessage.innerHTML = '';
//     passwordErrorMessage.innerHTML = '';
//     confirmErrorMessage.innerHTML = '';
//
//     // Validate Name
//     if (nameField.value.trim() === '') {
//         nameErrorMessage.innerHTML = 'Name is required!';
//         setTimeout(function () {
//             nameErrorMessage.innerHTML = '';
//         }, 3000);
//
//     }
//
//     else if (!/^[a-zA-Z\s]*$/.test(nameField.value.trim())) {
//         nameErrorMessage.innerHTML = 'Name must not contain special characters or numbers!';
//         setTimeout(function () {
//             nameErrorMessage.innerHTML = '';
//         }, 3000);
//     }
//
//     // Validate email
//     if (emailField.value.trim() === '') {
//         emailErrorMessage.innerHTML = 'Email is required!';
//         setTimeout(function () {
//             emailErrorMessage.innerHTML = '';
//         }, 3000);
//     }
//
//     // Validate Password
//     if (passwordField.value.length < 6) {
//         passwordErrorMessage.innerHTML = 'Password must be at least 6 characters!';
//         setTimeout(function () {
//             passwordErrorMessage.innerHTML = '';
//         }, 3000);
//     }else if(!/[0-9]/.test(passwordField.value)){
//         passwordErrorMessage.innerText='Password must contain at least one number!';
//         setTimeout(function () {
//             passwordErrorMessage.innerHTML = '';
//         }, 3000);
//
//     }
//     else if(!/[!@#$%^&*(),.?":{}|<>]/.test(passwordField.value)){
//         passwordErrorMessage.innerText='Password must contain a specail character!';
//         setTimeout(function () {
//             passwordErrorMessage.innerHTML = '';
//         }, 3000);
//
//     }
//
//     // Validate Confirm Password
//     if (confirmPasswordField.value !== passwordField.value) {
//         confirmErrorMessage.innerHTML = 'Passwords do not match!';
//         setTimeout(function () {
//             confirmErrorMessage.innerHTML = '';
//         }, 3000);
//     }
//
//     // You can add more validations for other fields here if needed
//
//     // Return true only if there are no error messages
//     //if the error messages are empty , all the conditions are true , the return will be true;
//     return nameErrorMessage.innerHTML === ''&& emailErrorMessage.innerHTML === ''  && passwordErrorMessage.innerHTML === '' && confirmErrorMessage.innerHTML === '';
// }
//
// document.addEventListener('DOMContentLoaded', function () {
//     var form = document.querySelector('form');
//
//     form.addEventListener('submit', function (event) {
//         if (!validateForm()) {
//             // Prevent the form from submitting if validation fails
//             event.preventDefault();
//         }
//     });
// });