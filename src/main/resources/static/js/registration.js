$(document).ready(function() {
    $("#registrationForm").submit(function(event) {
        event.preventDefault();
        
        var username = this.username.value;
        var email = this.email.value;
        var password = this.password.value;

        fetch(`/api/users/registrate?username=${username}&email=${email}&password=${password}`)
            .then(response => {
                if (response.ok) {
                    window.location.href = "/login";
                } else {
                    console.error('Error');
                }
            })
        .catch(error => {
            console.error('Error:', error);
        });
    });
});
