var listprefixes = new Array();
var existingNamesSpaces = new Array();
var allexistingNamesSpaces = new Array();
var oldSources = new Array();
var newSources = new Array();

jQuery(document).ready(function($) {

	var selectedSources = new Array();

	var $queryForm = $('#query-form');


	$queryForm.submit(function (event) {
		event.preventDefault();
		var incr=0;
		selectedSources = new Array();

		$("input:checkbox[name=myCB]:checked").each(function(i)
				{
			//alert($(this).val());

			var selectedSource = {
					selectedSource: $(this).val()
			};

			selectedSources.push($(this).val());
			incr++;
				});

		var json = JSON.stringify(selectedSources)

		var jqxhr = $.post('http://localhost:8085/ProjetJEE/generate',
				{'choices[]': selectedSources}
		/*,function( data ) {
					//alert(data.head.vars);
					showresult(data);

				}*/
		)
		.success(function() {

		})
		.error(function (xhr, ajaxOptions, thrownError) {

		})
		.complete(function() {
			var text = $('#querytext').val();
			text = text.replace(/\n/g, "///");
			var query = {
					query: text
			};
			var generate = $.post('http://localhost:8085/ProjetJEE/rdfquery',
					query
					,function( data ) {
				//alert(data.head.vars);
				showresult(data);

			}
			)
			.success(function() {

			})
			.error(function (xhr, ajaxOptions, thrownError) {

			})
			.complete(function() {
				$('#wait-modal').modal('hide');

			});

		});
		$('#wait-modal').modal('show');




	});






	$environmentSelection = $('.datasources');

	// Environment parameters are retrieved via JSON.
	$.getJSON('data/sources.json', function(data) {
		var datasourceHTML = '';
		var environmentHTML = '';
		var rowdata='';
		var sources = {};
		var test = 0;
		var dataTitleHTML ='';

		// For each datastore, we create a description inside span2s.
		for (var i = 0; i < data.sources.length; i++) {

			dataTitleHTML += '<fieldset class="span5"><legend>'+data.sources[i].title+'</legend>';


			dataTitleHTML += '<div class="checkbox">';
			for (var k = 0; k < data.sources[i].data.length; k++) {
				dataTitleHTML += '<label>';
				dataTitleHTML += '<input type="checkbox" id="checkbox1"  onclick="handleClick(this);" name="myCB" value="'+data.sources[i].name+'-'+data.sources[i].data[k]+'" >'+data.sources[i].data[k];
				dataTitleHTML += '</label>'

			}
			dataTitleHTML += '</checkbox">';


			dataTitleHTML +='</fieldset>';

			if (i !== 0 && (i + 1) % 2 === 0) {

				rowdata +=  '<div class="row-fluid">'+dataTitleHTML+'</div>';
				dataTitleHTML='';
			}
			if(i==data.sources.length-1){
				rowdata +=  '<div class="row-fluid">'+dataTitleHTML+'</div>';
				dataTitleHTML='';
			}

		}

		$environmentSelection.append(rowdata);

		// Append everything.
	});




});


function handleClick(cb) {
	if(cb.checked==true){
		//alert("Clicked, new value = " + cb.value);
		var namevalue = cb.value;
		var name = namevalue[0];
		var value = namevalue[1];


		var test = $.inArray(namevalue, listprefixes);
		//if(test=='-1'){
		//alert('pushtoarray');
		listprefixes.push(namevalue);
		//}


		$.getJSON('data/prefixes.json', function(data) {



			for(var j=0;j<data.prefixes.length;j++){

				if(data.prefixes[j].title==namevalue.toLowerCase()){

					allexistingNamesSpaces.push(data.prefixes[j].name);
					if($.inArray(data.prefixes[j].name, existingNamesSpaces)=='-1'){


						//alert(data.prefixes[j].url);

						var pref = data.prefixes[j].url;

						var $prefixes = $('.prefixes');

						$prefixes.append('<li>'+data.prefixes[j].name+': <a href="'+pref+'">'+pref+'</a></li>');
						var pref = 'PREFIX ' +data.prefixes[j].name+': <'+pref+'>';
						var preff = pref.toString();
						var box = $("#querytext");

						box.val(box.val() + preff+'\n');

						//	$('#querytext').append((document.createTextNode(preff+'\n'))); 
						//$('#querytext').append(pref);

						existingNamesSpaces.push(data.prefixes[j].name);

					}

				}

			}

			//alert(allexistingNamesSpaces.length);

		});


	}
	else{

		var namevalue = cb.value;
		var $prefixes = $('.prefixes');

		$prefixes.empty();
		//$('#querytext').empty();
		var box = $("#querytext");

		box.val('');

		//var namevalue = cb.value.split('-');

		var name = namevalue[0];
		var value = namevalue[1];


		var test = $.inArray(namevalue, listprefixes);
		//alert(test);
		if(test!='-1'){
			listprefixes.splice(test,1);
		}
		existingNamesSpaces = new Array();

		//alert(allexistingNamesSpaces.length);



		$.getJSON('data/prefixes.json', function(data) {


			for(var j=0;j<data.prefixes.length;j++){


				if(data.prefixes[j].title==namevalue.toLowerCase()){
					var testing = $.inArray(data.prefixes[j].name, allexistingNamesSpaces);
					if(testing!='-1'){
						allexistingNamesSpaces.splice(testing,1);
					}

				}


			}


			for(var j=0;j<data.prefixes.length;j++){

				for (var i = 0; i < allexistingNamesSpaces.length; i++) {


					if(data.prefixes[j].name==allexistingNamesSpaces[i].toLowerCase()){

						if($.inArray(data.prefixes[j].name, existingNamesSpaces)=='-1'){

							var pref = data.prefixes[j].url;


							$prefixes.append('<li>'+data.prefixes[j].name+': <a href="'+pref+'">'+pref+'</a></li>');
							var pref = 'PREFIX ' +data.prefixes[j].name+': <'+pref+'>';
							var preff = pref.toString();
							var box = $("#querytext");

							box.val(box.val() + preff+'\n');

							existingNamesSpaces.push(data.prefixes[j].name);

						}


					}


				}


			}

		});

	}




}


function showresult(data){
	var $resultsColumns = $('.results-columns');
	var $resultsRows = $('.results-rows');
	var $prefixes = $('.prefixes');
	$('input:checkbox').removeAttr('checked');
	// $( "#resulttable" ).empty();
	$resultsColumns.empty();
	$resultsRows.empty();
	$prefixes.empty();
	$('#querytext').val('');
	listprefixes = new Array();
	existingNamesSpaces=new Array();
	allexistingNamesSpaces = new Array();



	$resultsColumns.append('<th>'+data.head.vars.join('</th><th>')+'</th>');

	for (var j = 0; j < data.results.bindings.length; j++) {
		var currentCells = '';
		$.each(data.results.bindings[j], function (key, val) {
			currentCells += '<td>'+val.value+'</td>';
		});
		$resultsRows.append('<tr>'+currentCells+'</tr>');
	}


}