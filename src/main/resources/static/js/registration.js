$(document).ready(function() {
    $("#registrationForm").submit(function(event) {
        event.preventDefault();

        const user = {
            username: this.username.value,
            email: this.email.value,
            password: this.password.value,
        };

        console.log(user);

        $.ajax({
            type: "POST",
            url: "/api/registration",
            data: JSON.stringify(user),
            contentType: "application/json",
            success: function(response) {
                console.log(response);
            },
            error: function(xhr, status, error) {
                var errorMessage = xhr.responseText;
                alert("Произошла ошибка: " + errorMessage);
            }
        });
    });
});
