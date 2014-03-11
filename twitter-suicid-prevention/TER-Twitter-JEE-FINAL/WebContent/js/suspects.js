

jQuery(document).ready(function($) {


	var jqxhr = $.get('suspects',			
			function( data ) {
		//alert(data.head.vars);
		showSuspects(data);
	}			
	)
	.success(function() {

	})
	.error(function (xhr, ajaxOptions, thrownError) {

	})



});





function showSuspects(data)
{
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
    
	var $resultsColumns = $('.suspect-columns');
	$resultsColumns.empty();
	$resultsColumns.append('<th>Id</th><th>Content</th>');

	var oTable = null;
	$(document).ready(function(){
		oTable = $('#suspecttable').dataTable({
	        "sPaginationType": "bootstrap",
			"aoColumns": [{ "mDataProp": "idtweet" }, { "mDataProp": "content" }],
			"aaData": data,
			"bJQueryUI": true,
			"iDisplayLength" : 10,

			"bServerSide" : false,
			"bPaginate": true,
			"aLengthMenu": [
			                [10, 20, 50,],
			                [10, 20, 50,]
			                ], 
			                //important  -- headers of the json
			                "sDom": "<'span9 well'<'span4'l><'span4'f>t<'span4'i><'span4'p>>",

			                "fnDrawCallback": function( oSettings ) {
                                $('#suspecttable tbody td:nth-child(1)').each(function(index) {
                                    $(this).addClass('hover');
                                    var text = $(this).text();
                                    var link = $('<a href="javascript:void(0)" onclick="showpopup(text)" class="edit" id="suspectid" />').text(text).bind('click', function(){

                                    });
                                    $(this).html(link);
                                });
                            }                	
		});

	});


	$('#suspecttable tbody td:nth-child(1)').each(function(index) {
		$(this).addClass('hover');
		var text = $(this).text();
		var link = $('<a href="javascript:void(0)" onclick="showpopup(text)" class="edit" id="suspectid" />').text(text).bind('click', function(){

		});
		$(this).html(link);
	});





}

function showpopup(text){
	//alert(text);

	var jqxhr = $.get('tweetdetail',{idtweet: text},
			function( data ) {
		//alert(data.head.vars);
		var incr = '<h4> Subcategories found : <br/></h4><h5>';
		for(var i=0;i<data.subcategory.length;i++){

			incr += data.subcategory[i]+ '<br/>';		
		}
		incr+='</h5>';

		$(".modal-body #left #category").html(incr);


		$(".modal-body #right #image").html('<img src="'+data.image+'" />' );
		$(".modal-body #right #username").html('<h5> Username : '+data.username+'</h5>');
		$(".modal-body #right #name").html('<h5> Name: '+data.name+'</h5>');
		if(data.geolocalistion!=''){
			$(".modal-body #right #location").html('<h5> Location : '+data.geolocalistion+'</h5>');
		}
		var hist = '<h5>';
		for(var i=0;i<data.history.length;i++){
			
			hist += '<tr><td>'+data.history[i]+ '</td></tr>';		
		}
		$(".modal-body #history #tablehistory").html(hist);

		
		$("#myModal").modal('show');

	}			

	)
	.success(function() {

	})
	.error(function (xhr, ajaxOptions, thrownError) {

	})


}



