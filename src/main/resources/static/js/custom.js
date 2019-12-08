$(document).ready(function() {

    $('#returnradio').click(function() {
        $('#returnFlightTimeDiv').css('visibility', 'visible');
        $('#returnFlightTimeDiv').append('<label class="datepicker" for="returnFlightTime">Pick a return date</label>' +
            '<input type="date" id="returnFlightTime" name="returnFlightTime" min="">');

        $("#returnFlightTime").attr("min", $("#flightTime").val());

        $("#returnFlightTime").on("change paste keyup", function() {
            var value = $(this).val();
            $("#flightTime").attr("max", value);
        });
    });

    $('#onewayradio').click(function() {
        $('#returnFlightTimeDiv').css('visibility', 'hidden');
        $("input[name='returnFlightTime']").remove();
        $("label[for='returnFlightTime']").remove();
        $("#flightTime").attr("max", null);
    });

    $("#flightTime").on("change paste keyup", function() {
        var value = $(this).val();
        $("#returnFlightTime").attr("min", value);
    });


    $("#fromLocation").on("change paste keyup", function () {
        $("#locationlist option").each(function () {

            if ($(this).val() == $("#fromLocation").val()){
                $(this).attr("selected", true);
            }

            if ($(this).val() != $("#fromLocation").val()){
                $(this).removeAttr("selected");
            }


        });
    });

    $("#toLocation").on("change paste keyup", function () {
        $("#locationlist2 option").each(function () {

            if ($(this).val() == $("#toLocation").val()){
                $(this).attr("selected", true);
            }

            if ($(this).val() != $("#toLocation").val()){
                $(this).removeAttr("selected");
            }


        });
    });

    $("#search-submit").click(function () {




        if ($("#locationlist option:selected").hasClass("locationoption")) {
            $("#formFromInput").attr({
                name: "fromLocationId",
                value: $("#locationlist option:selected").attr('data-value')
            });
        } else {
            $("#formFromInput").attr({
                name: "fromAirport",
                value: $("#locationlist option:selected").attr('data-value')
            });
        }

        if ($("#locationlist2 option:selected").hasClass("locationoption")) {
            $("#formToInput").attr({
                name: "toLocationId",
                value: $("#locationlist2 option:selected").attr('data-value')
            });
        } else {
            $("#formToInput").attr({
                name: "toAirport",
                value: $("#locationlist2 option:selected").attr('data-value')
            });
        }


    });

    $('#cancelReservation').click(function () {
        $('#editCancelReservationForm').attr({
            action: "/booking/manage/cancel"
        });
    });

    $('#changeReservationName').click(function () {
        $('input[name="name"]').val($('#bookingName').val());
        $('#editCancelReservationForm').attr({
            action: "/booking/manage/edit"
        });
    });
    
    $('#flightModalButton').click(function (event) {
        var button = $(this);
        var flightName = button.data('object-name');
        var flightCapacity = button.data('object-capacity');
        var id = button.data('object-id');

        $('#flightid').val(id);
        $('#name').val(flightName);
        $('#capacity').val(flightCapacity);
        $('.modal-title').text('Edit flight: ' + flightName);
    });

});


