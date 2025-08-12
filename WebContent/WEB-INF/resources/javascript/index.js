var archive_data;
function currentTime() {

  var date = new Date(); 
  var hh = date.getHours();
  var mm = date.getMinutes();
  var ss = date.getSeconds();

   hh = (hh < 10) ? "0" + hh : hh;
   mm = (mm < 10) ? "0" + mm : mm;
   ss = (ss < 10) ? "0" + ss : ss;
    
   return hh + ":" + mm + ":" + ss;

}
function processMatchTime() {
	document.getElementById('match_time_hdr').innerHTML = currentTime();
}
function processWaitingButtonSpinner(whatToProcess) 
{
	switch (whatToProcess) {
	case 'START_WAIT_TIMER': 
		$('.spinner-border').show();
		$(':button').prop('disabled', true);
		break;	
	case 'END_WAIT_TIMER': 
		$('.spinner-border').hide();
		$(':button').prop('disabled', false);
		break;
	}
}
function afterPageLoad(whichPageHasLoaded)
{
	switch (whichPageHasLoaded) {
	case 'ARCHIVE':
		setInterval(processMatchTime, 800);
		processCricketArchiveProcedures("GET-ALL-SEASONS");
		break;
	}
}
function initialiseForm(whatToProcess, dataToProcess)
{
}
function uploadFormDataToSessionObjects(whatToProcess)
{
	var formData = new FormData();
	var url_path;

	$('input, select, textarea').each(
		function(index){  
			if($(this).is("select")) {
				formData.append($(this).attr('id'),$('#' + $(this).attr('id') + ' option:selected').val());  
			} else {
				formData.append($(this).attr('id'),$(this).val());  
			}	
		}
	);
	
	url_path = 'upload_match_setup_data';
	
	$.ajax({    
		headers: {'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')},
        url : url_path,     
        data : formData,
        cache: false,
        contentType: false,
        processData: false,
        type: 'POST',     
        success : function(data) {

        },    
        error : function(e) {    
       	 	console.log('Error occured in uploadFormDataToSessionObjects with error description = ' + e);     
        }    
    });		
	
}
function processUserSelection(whichInput)
{
	switch (whichInput.id) {
	case 'select_season':
		if($(whichInput).val().length <= 0) {
			return false;
		}
		processWaitingButtonSpinner('START_WAIT_TIMER');
		processCricketArchiveProcedures("GET-SEASON-SERIES-DATA");
		break;
	case 'select_series':
		if($(whichInput).val().length <= 0) {
			return false;
		}
		processWaitingButtonSpinner('START_WAIT_TIMER');
		processCricketArchiveProcedures("GET-SERIES-MATCHES-DATA");
		break;
	case 'select_match':
		if($(whichInput).val().length <= 0) {
			return false;
		}
		processWaitingButtonSpinner('START_WAIT_TIMER');
		processCricketArchiveProcedures("GET-SINGLE-MATCH-DATA");
		break;
	}
}
function processCricketArchiveProcedures(whatToProcess)
{
	var value_to_process; 
	
	switch(whatToProcess) {
	case 'GET-SEASON-SERIES-DATA':
		value_to_process = $('#select_season').val().replace('/','%2F');
		break;
	case 'GET-SERIES-MATCHES-DATA':
		value_to_process = $('#select_series').val();
		break;
	case 'GET-SINGLE-MATCH-DATA':
		value_to_process = $('#select_match').val();
		break;
	}

	$.ajax({    
        type : 'Get',     
        url : 'processCricketArchiveProcedures.html',     
        data : 'whatToProcess=' + whatToProcess + '&valueToProcess=' + value_to_process, 
        dataType : 'json',
        success : function(data) {
			archive_data = data;
			switch(whatToProcess) {
			case 'GET-ALL-SEASONS':
				addItemsToList('POPULATE-ALL-SEASON-DATA',data);
				break;
			case 'GET-SEASON-SERIES-DATA':
				addItemsToList('POPULATE-SEASON-SERIES-DATA',data);
				break;
			case 'GET-SERIES-MATCHES-DATA':
				addItemsToList('POPULATE-SERIES-MATCHES-DATA',data);
				break;
			case 'GET-SINGLE-MATCH-DATA':
				addItemsToList('POPULATE-SINGLE-MATCH-DATA',data);
				break;
			}
    		processWaitingButtonSpinner('END_WAIT_TIMER');
	    },    
	    error : function(e) {    
	  	 	console.log('Error occured in ' + whatToProcess + ' with error description = ' + e);     
	    }    
	});
}
function addItemsToList(whatToProcess, dataToProcess)
{
	var div,row,cell,header_text,select,option,tr,th,thead,text,table,tbody;
	
	switch(whatToProcess) {
	case 'POPULATE-ALL-SEASON-DATA':
		
		$('#select_event_div').empty();

		table = document.createElement('table');
		table.setAttribute('class', 'table table-bordered');
		tbody = document.createElement('tbody');
		tbody.id = 'archive_table_body';
		table.appendChild(tbody);
		row = tbody.insertRow(tbody.rows.length);

		select = document.createElement('select');
		select.id = 'select_season';
		option = document.createElement('option');
		option.value = '';
	    option.text = '';
	    select.appendChild(option);
	    dataToProcess.seasons.forEach(function(season,season_index,season_arr){
			option = document.createElement('option');
			option.value = season.label;
		    option.text = season.label;
		    select.appendChild(option);
	    });
		select.setAttribute('onchange','processUserSelection(this);');
		header_text = document.createElement('label');
		header_text.innerHTML = 'Season';
		header_text.htmlFor = select.id;
		
		row.insertCell(0).appendChild(header_text).appendChild(select)
		
		document.getElementById('select_event_div').appendChild(table);

		break;
		
	case 'POPULATE-SEASON-SERIES-DATA':
		
		tbody = document.getElementById('archive_table_body');
		row = tbody.insertRow(tbody.rows.length);
		row.id = 'season_series_data_row';
		
		select = document.createElement('select');
		select.id = 'select_series';
		option = document.createElement('option');
		option.value = '';
	    option.text = '';
	    select.appendChild(option);
	    dataToProcess.series.forEach(function(serie,serie_index,serie_arr){
			option = document.createElement('option');
			option.value = serie.label;
		    option.text = serie.label;
		    select.appendChild(option);
	    });
		select.setAttribute('onchange','processUserSelection(this);');
		header_text = document.createElement('label');
		header_text.innerHTML = 'Series';
		header_text.htmlFor = select.id;
		
		row.insertCell(0).appendChild(header_text).appendChild(select)
		
		break;
		
	case 'POPULATE-SERIES-MATCHES-DATA':

		tbody = document.getElementById('archive_table_body');
		row = tbody.insertRow(tbody.rows.length);

		select = document.createElement('select');
		select.id = 'select_match';
		option = document.createElement('option');
		option.value = '';
	    option.text = '';
	    select.appendChild(option);
	    dataToProcess.matches.forEach(function(match,match_index,match_arr){
			option = document.createElement('option');
			option.value = match.label;
		    option.text = match.label.replaceAll('-', ' ');
		    select.appendChild(option);
	    });
		select.setAttribute('onchange','processUserSelection(this);');
		header_text = document.createElement('label');
		header_text.innerHTML = 'Match';
		header_text.htmlFor = select.id;
		
		row.insertCell(0).appendChild(header_text).appendChild(select)
		
		break;
		
	}
}
