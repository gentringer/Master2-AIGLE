	function showPie() {
		$( "#container_columns" ).hide();
		$( "#container_pie" ).show();
		$(function() {
			//var chart;

			var countYes, countNo;

			$
					.get(
							'HighCharts',
							function(responseJson) {
								//alert(responseJson[0] + ' - ' + responseJson[1]);
								countYes = responseJson[0];
								countNo = responseJson[1];
								//alert(countYes + " - " + countNo);

								// Build the chart
								$('#container_pie')
										.highcharts(
												{

													chart : {
														plotBackgroundColor : null,
														plotBorderWidth : null,
														plotShadow : false
													},
													title : {
														text : 'Suspects tweets (suicidal ones and not suicidal)'
													},
													tooltip : {
														pointFormat : '{series.name}: <b>{point.percentage:.1f}%</b>'
													},
													plotOptions : {
														pie : {
															allowPointSelect : true,
															cursor : 'pointer',
															dataLabels : {
																enabled : false
															},
															showInLegend : true
														}
													},
													series : [ {
														type : 'pie',
														name : 'Browser share',
														data : [
																[
																		'Suspects tweets but not suicidal',
																		countNo ],
																{
																	name : 'Suspects and suicidal tweets',
																	y : countYes,
																	sliced : false,
																	selected : true
																}, ]
													} ]
												});
							});
		});
	}

	function drawColumn() {
		$( "#container_pie" ).hide();
		$( "#container_columns" ).show();
		$(function() {

			var count1, count2, count3, count4, count5, count6, count7, count8, count9, count10;

			$
					.get(
							'HighCharts',
							function(responseJson) {
								
								
								count1 = responseJson[2];
								count2 = responseJson[3];
								count3 = responseJson[4];
								count4 = responseJson[5];
								count5 = responseJson[6];
								count6 = responseJson[7];
								count7 = responseJson[8];
								count8 = responseJson[9];
								count9 = responseJson[10];
								count10 = responseJson[11];
							    

								$('#container_columns')
										.highcharts(
												{
													chart : {
														type : 'column'
													},
													title : {
														text : 'percentage of tweets related to different categories'
													},
													xAxis : {
														categories : [
																'Anorexia',
																'Cyberbullying',
																'Depression',
																'Fear', 'Hurt',
																'Insults',
																'Loneliness',
																'Lonely',
																'Method',
																'Sentence' ]
													},
													yAxis : {
														min : 0,
														title : {
															text : 'Results'
														}
													},
													tooltip : {
														headerFormat : '<span style="font-size:10px">{point.key}</span><table>',
														pointFormat : '<tr><td style="color:{series.color};padding:0">{series.name}: </td>'
																+ '<td style="padding:0"><b>{point.y:.1f} </b></td></tr>',
														footerFormat : '</table>',
														shared : true,
														useHTML : true
													},
													plotOptions : {
														column : {
															pointPadding : 0.2,
															borderWidth : 0
														}
													},
													series : [ {
														name : 'Subcategories',
														data : [ count1,
																count2, count3,
																count4, count5,
																count6, count7,
																count8, count9,
																count10 ]

													} ]
												});
							});
		});
	}
