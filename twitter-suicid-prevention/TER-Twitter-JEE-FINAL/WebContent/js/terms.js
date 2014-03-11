

jQuery(document).ready(function($) {


	$("#categoriename").change(function() {
		var val = $(this).val();
		if(val=='Mental'){
			var options = "<option value='Hurt'>Hurt</option><option value='Lonelieness'>Lonelieness</option>" +
			"<option value='Fear'>Fear</option><option value='Depression'>Depression</option>" +
			"<option value='lonely'>lonely</option><option value='sentence'>sentence</option>";
			$("#subcategory").html(options);

		}

		else {
			var options = "<option value='blonde'>"+val+"</option>";
			$("#subcategory").html(options);

		}
	});

	var thmeaticform = $('#thmeaticform');

	thmeaticform.submit(function (event) {
		event.preventDefault();
		var incr=0;
		selectedSources = new Array();



		var cat = $( "#categoriename option:selected" ).text();
		var subcat = $( "#subcategory option:selected" ).text();

		var jqxhr = $.post('thematics',
				{category: cat, subcategory: subcat},
				function( data ) {
					//alert(data.head.vars);
					showresultTerms(data);

				}
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
			$('#wait-modal').modal('hide');

		});
		$('#wait-modal').modal('show');

	});




});

function showresultTerms(data)
{
	var $resultsColumns = $('.results-columns');
	$resultsColumns.empty();
	$resultsColumns.append('<th>Terme</th><th>Category</th><th>SubCategory</th>');
	$.fn.dataTableExt.oApi.fnPagingInfo = function ( oSettings )
    {
        return {
            "iStart":         oSettings._iDisplayStart,
            "iEnd":           oSettings.fnDisplayEnd(),
            "iLength":        oSettings._iDisplayLength,
            "iTotal":         oSettings.fnRecordsTotal(),
            "iFilteredTotal": oSettings.fnRecordsDisplay(),
            "iPage":          oSettings._iDisplayLength === -1 ?
                    0 : Math.ceil( oSettings._iDisplayStart / oSettings._iDisplayLength ),
                    "iTotalPages":    oSettings._iDisplayLength === -1 ?
                            0 : Math.ceil( oSettings.fnRecordsDisplay() / oSettings._iDisplayLength )
        };
    }

    /* Bootstrap style pagination control */
    $.extend( $.fn.dataTableExt.oPagination, {
        "bootstrap": {
            "fnInit": function( oSettings, nPaging, fnDraw ) {
                var oLang = oSettings.oLanguage.oPaginate;
                var fnClickHandler = function ( e ) {
                    e.preventDefault();
                    if ( oSettings.oApi._fnPageChange(oSettings, e.data.action) ) {
                        fnDraw( oSettings );
                    }
                };

                $(nPaging).addClass('pagination').append(
                        '<ul>'+
                        '<li class="prev disabled"><a href="#">&larr; '+oLang.sPrevious+'</a></li>'+
                        '<li class="next disabled"><a href="#">'+oLang.sNext+' &rarr; </a></li>'+
                        '</ul>'
                );
                var els = $('a', nPaging);
                $(els[0]).bind( 'click.DT', { action: "previous" }, fnClickHandler );
                $(els[1]).bind( 'click.DT', { action: "next" }, fnClickHandler );
            },

            "fnUpdate": function ( oSettings, fnDraw ) {
                var iListLength = 5;
                var oPaging = oSettings.oInstance.fnPagingInfo();
                var an = oSettings.aanFeatures.p;
                var i, j, sClass, iStart, iEnd, iHalf=Math.floor(iListLength/2);

                if ( oPaging.iTotalPages < iListLength) {
                    iStart = 1;
                    iEnd = oPaging.iTotalPages;
                }
                else if ( oPaging.iPage <= iHalf ) {
                    iStart = 1;
                    iEnd = iListLength;
                } else if ( oPaging.iPage >= (oPaging.iTotalPages-iHalf) ) {
                    iStart = oPaging.iTotalPages - iListLength + 1;
                    iEnd = oPaging.iTotalPages;
                } else {
                    iStart = oPaging.iPage - iHalf + 1;
                    iEnd = iStart + iListLength - 1;
                }

                for ( i=0, iLen=an.length ; i<iLen ; i++ ) {
                    // Remove the middle elements
                    $('li:gt(0)', an[i]).filter(':not(:last)').remove();

                    // Add the new list items and their event handlers
                    for ( j=iStart ; j<=iEnd ; j++ ) {
                        sClass = (j==oPaging.iPage+1) ? 'class="active"' : '';
                        $('<li '+sClass+'><a href="#">'+j+'</a></li>')
                        .insertBefore( $('li:last', an[i])[0] )
                        .bind('click', function (e) {
                            e.preventDefault();
                            oSettings._iDisplayStart = (parseInt($('a', this).text(),10)-1) * oPaging.iLength;
                            fnDraw( oSettings );
                        } );
                    }

                    // Add / remove disabled classes from the static elements
                    if ( oPaging.iPage === 0 ) {
                        $('li:first', an[i]).addClass('disabled');
                    } else {
                        $('li:first', an[i]).removeClass('disabled');
                    }

                    if ( oPaging.iPage === oPaging.iTotalPages-1 || oPaging.iTotalPages === 0 ) {
                        $('li:last', an[i]).addClass('disabled');
                    } else {
                        $('li:last', an[i]).removeClass('disabled');
                    }
                }
            }
        }
    } );


	var oTable = null;
	$(document).ready(function(){
		oTable = $('#mytable').dataTable({
			"aoColumns": [{ "mDataProp": "term" }, { "mDataProp": "category" },{ "mDataProp": "subcategory" }],
			"aaData": data,
			"bJQueryUI": true,
			"iDisplayLength" : 10,
            "sPaginationType": "bootstrap",
			"bServerSide" : false,
			"bPaginate": true,
			"aLengthMenu": [
			                [10, 20, 50,  -1],
			                [10, 20, 50,  "All"]
			                ], 
			                //important  -- headers of the json
			                "sDom": "<'span8 well'<'span3'l><'span3'f>t<'span3'i><'span3'p>>",

			                "fnDrawCallback":function(){
			                	if(data.length > 0){
			                		$(oTable).find('div.dataTables_paginate').show();
			                	} else {
			                		$(oTable).find('div.dataTables_paginate').hide();
			                	}},                	
		});

	});


}


