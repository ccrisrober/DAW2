/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function() {
    $('#loginform').submit(function(event) {
        event.stopPropagation();
        var user = $('#userfield').val();
        var pass = $('#passfield').val();

        $.ajax({
            url: "Login",
            data: {user: userfield, pass: passfield},
            type: 'post',
            'dataType': 'json',
            error: function(error) {
                alert("Error: " + error);
            },
            success: function(data) {
                alert(data.data);
                if (!data.error) {
                    window.location = "Index";
                }
            }
        });
        return false;
    });
});