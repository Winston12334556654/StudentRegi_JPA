
document.addEventListener('DOMContentLoaded', function () {
    let formConstraint = document.querySelector('form');

    let name = document.getElementById('name');
    let nameErrorMessage = document.getElementById('name_errormessage');
    let phone =document.getElementById('phone');
    let phoneErrorMessage= document.getElementById('phone_errormessage');
    let date = document.getElementById('dob');
    let dateErrorMessage= document.getElementById('dob_errormessage');
    let attend= document.getElementById('gridRadios1');
    let courseErrorMessage= document.getElementById('course_errormessage');
    let checkboxes = document.querySelectorAll('input[type="checkbox"][path="courseIds"]');

    let photo = document.getElementById('photo');
    let photoErrorMessage = document.getElementById('photo_errormessage');


    // Validation function for the name field
    function nameValidate() {
        if (name.value.trim() === '' || name.value === null) {
            nameErrorMessage.innerHTML = 'Name cannot be blank!';
            return false;
        } else if (!/^[a-zA-Z\s]*$/.test(name.value.trim())) {
            nameErrorMessage.innerHTML = 'Name must not contain special characters or numbers!';
            return false;
        } else {
            nameErrorMessage.innerHTML = '';
            return true;
        }
    }



    function dateValidate() {
        //create new date
        let currentDate = new Date();

        //value empty
        if (!date.value) {
            dateErrorMessage.innerHTML = 'Date of birth cannot be blank!';
            return false;
        } else {
            //input date setting through argument
            let inputDate = new Date(date.value);

            if (inputDate > currentDate) {
                dateErrorMessage.innerHTML = 'Date of birth cannot be in the future!';
                return false;
            } else {
                dateErrorMessage.innerHTML = '';
                return true;
            }
        }
    }




    function phoneValidate(){
        let phoneNumber = phone.value.trim();
        let phonePattern = /^(?:\+95|\b09)\d{6}$|^(?:\+95|\b09)\d{9}$/;
        if(!phoneNumber){
            phoneErrorMessage.innerHTML='Phone Number Cannot be Blank';
            return false;
        }else if (!phonePattern.test(phoneNumber)){
            phoneErrorMessage.innerHTML='Invalid Burmese phone number.';

            return false;
        }else{
            phoneErrorMessage.innerHTML='';
            return true;
        }
    }






    checkboxes.forEach(function (checkbox) {
        checkbox.addEventListener('change', function () {
            if (isAtLeastOneCheckboxChecked(checkboxes)) {
                courseErrorMessage.textContent = '';
            } else {
                courseErrorMessage.textContent = 'Please select at least one course.';
            }
        });
    });
    //
    // function isAtLeastOneCheckboxChecked(checkboxes) {
    //     return Array.from(checkboxes).some(function (checkbox) {
    //         return checkbox.checked;
    //     });
    // }
    //
    //
    function photoValidate() {
        if (!photo.value) {
            photoErrorMessage.innerHTML = 'Photo cannot be blank!';
            return false;
        } else {
            photoErrorMessage.innerHTML = '';
            return true;
        }
    }

    // Event listeners for input changes
    name.addEventListener('input', nameValidate);
    phone.addEventListener('input', phoneValidate);
    date.addEventListener('change', dateValidate)
    photo.addEventListener('change', photoValidate);

    // attend.addEventListener('change', function () {
    //     if (!isAtLeastOneCheckboxChecked(checkboxes)) {
    //         courseErrorMessage.textContent = 'Please select at least one course.';
    //     } else {
    //         courseErrorMessage.textContent = '';
    //     }
    // });

    // Add listeners for other inputs

    // Form submission preventer if false
    formConstraint.addEventListener('submit', function (event) {
        // Check all validations before submitting the form
        if (!nameValidate() || !phoneValidate() || !dateValidate() || !photoValidate()) {
            // Prevent the form from submitting if any validation fails
            event.preventDefault();
        }
    });
});

