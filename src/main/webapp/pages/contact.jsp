<style>
    .contact-form {
        margin-top:70px;
    }
</style>
<div id="send" style="display: none" class="alert alert-success">Correo enviado con �xito</div>
<div id="cf">
    <div class="col-sm-4">
        <h3>M�ndame un mensaje</h3>
        <hr>
        <address>
            <strong>Email:</strong> <a href="mailto:#"> name@domain.com</a><br><br>
            <strong>Phone:</strong> (666)666-2014
        </address>
    </div>

    <div class="col-sm-8 contact-form">
        <form id="contact" method="post" class="form" role="form" action="Contact">
            <div class="row">
                <div class="col-xs-6 col-md-6 form-group">
                    <input class="form-control" id="name" name="name" placeholder="Name" type="text" required autofocus />
                </div>
                <div class="col-xs-6 col-md-6 form-group">
                    <input class="form-control" id="email" name="email" placeholder="Email" type="email" required />
                </div>
            </div>
            <textarea class="form-control" id="message" name="message" placeholder="Message" rows="5"></textarea>
            <br />,
            <div class="row">
                <div class="col-xs-12 col-md-12 form-group">
                    <button class="btn btn-primary pull-right" type="submit">Submit</button>
                </div>
            </div>
        </form>
    </div>
</div>

<script>
    $(document).ready(function() {
        $("#contact").submit(function() {
            $("#send").css('display','block');
            $("#cf").css('display','none');
            return false;
        });
    });
</script>