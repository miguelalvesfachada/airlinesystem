$('#returnradio').click(function() {
    $('#returnFlightTimeDiv').css('visibility', 'visible');
    $('#returnFlightTimeDiv').append('<label class="datepicker" for="returnFlightTime">Pick a return date</label>' +
        '<input type="date" id="returnFlightTime" name="returnFlightTime">');
    $('#searchflights').attr('action', '/search/return');
});

$('#onewayradio').click(function() {
    $('#returnFlightTimeDiv').css('visibility', 'hidden');
    $("input[name='returnFlightTime']").remove();
    $("label[for='returnFlightTime']").remove();
    $('#searchflights').attr('action', '/search');
});
