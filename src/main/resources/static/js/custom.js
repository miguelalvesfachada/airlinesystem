$(document).ready(function() {

    $('#returnradio').click(function() {
        $('#returnFlightTimeDiv').css('visibility', 'visible');
        $('#returnFlightTimeDiv').append('<label class="datepicker" for="returnFlightTime">Pick a return date</label>' +
            '<input type="date" id="returnFlightTime" name="returnFlightTime" min="">');
        $('#searchflights').attr('action', '/search/return');

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
        $('#searchflights').attr('action', '/search');
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
            $("#fromLocation").attr({
                name: "fromLocationId",
                    type: "hidden",
                value: $("#locationlist option:selected").attr('data-value')
            });
        } else {
            $("#fromLocation").attr({
                name: "fromAirport",
                type: "hidden",
                value: $("#locationlist option:selected").attr('data-value')
            });
        }

        if ($("#locationlist2 option:selected").hasClass("locationoption")) {
            $("#toLocation").attr({
                name: "toLocationId",
                type: "hidden",
                value: $("#locationlist2 option:selected").attr('data-value')
            });
        } else {
            $("#toLocation").attr({
                name: "toAirport",
                type: "hidden",
                value: $("#locationlist2 option:selected").attr('data-value')
            });
        }


    });




});


