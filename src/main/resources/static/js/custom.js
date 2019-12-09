$(document).ready(function() {

    $('#onewayradio').attr("checked");

    $('#returnradio').click(function() {
        $('#returnFlightTimeDiv').css('visibility', 'visible');
        if(!$('#returnFlightTime').length) {
            $('#returnFlightTimeDiv').append('<label class="datepicker" for="returnFlightTime">Pick a return date</label>' +
                '<input type="date" id="returnFlightTime" name="returnFlightTime" min="">');
        }
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

    $('.flightModalButton').click(function (event) {
        var button = $(this);
        var flightName = button.data('object-name');
        var flightCapacity = button.data('object-capacity');
        var id = button.data('object-id');

        $('#flightid').val(id);
        $('#name').val(flightName);
        $('#capacity').val(flightCapacity);
        $('.modal-title').text('Edit flight: ' + flightName);
    });

    $('.airportModalButton').click(function (event) {
        var button = $(this);
        var airportName = button.data('object-name');
        var airportCode = button.data('object-code');
        var id = button.data('object-id');
        var locationId = button.data('object-locationid');

        $('#airportid').val(id);
        $('#name').val(airportName);
        $('#code').val(airportCode);
        $('#locationid').val(locationId);
        $('.modal-title').text('Edit airport: ' + airportName);
    });

    $('.locationModalButton').click(function (event) {
        var button = $(this);
        var locationName = button.data('object-name');
        var id = button.data('object-id');

        $('#locationid').val(id);
        $('#name').val(locationName);
        $('.modal-title').text('Edit location: ' + locationName);
    });

    $('.scheduleModalButton').click(function (event) {
        var button = $(this);
        var id = button.data('object-id');
        var from = button.data('object-from');
        var to = button.data('object-to');
        var depttime = button.data('object-depttime');
        var arrtime = button.data('object-arrtime');
        var remcap = button.data('object-remcap');

        $('#scheduleid').val(id);
        $('#airportcode').val(from);
        $('#returnairportcode').val(to);
        $('input[name=deptTime]').val(depttime);
        $('input[name=arrivalTime]').val(arrtime);
        $('#remcapacity').val(remcap);
        $('.modal-title').text('Edit schedule:');
    });
});


