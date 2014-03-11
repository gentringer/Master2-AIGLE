<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="fr">
<meta charset="utf-8">
<head>
<!-- Css 	   -->
<link href="./css/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="./css/webfonts.css" rel="stylesheet">
<!-- Scripts   -->
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.7/jquery.js"></script>
<script class="jsbin"
	src="http://datatables.net/download/build/jquery.dataTables.nightly.js"></script>

<script src="./js/highcharts.js"></script>
<script src="./js/exporting.js"></script>
<script src="./js/stats.js"></script>
<style>

	.extra {
		  width: 100%;
		  bottom: 0;
		  left: 0;
		  position: fixed;
		  border-top: 1px solid #000;
		  padding: 10.2px 0;
		  font-size: 12px;
		  color: #999;
		  background: #1A1A1A;
		  -webkit-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.3);
		  box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.3);
	}
	.extra a {
	  color: #FFF;
	}
	.extra h4 {
	  margin-bottom: 1em;
	  font-weight: 400;
	}
	.extra ul {
	  padding: 0;
	  margin: 0;
	}
	.extra ul li {
	  margin-bottom: .6em;
	  list-style: none;
	}
</style>


</head>

<body>
	<!-- Header -->
	<div class="page-header">
		<div class="row-fluid">
			<div class="span4" align="right">
				<a class="brand" href="index.jsp"> <img
					style="height: 86px; width: 208px;" src="img/logo.png"></a>
			</div>
			<div class="span4" align="center">
				<div class="span4" align="center">			
					 	<img style="height: 72px; width: 147px;" src="img/lirmm.jpg">				
				</div>
				<div class="span4" align="center">
					
						<img style="height: 100px; width: 100px;" src="img/cnrs.jpg"> 
				</div>
				<div class="span4" >				
					 	<img style="height: 80px; width: 147px;" src="img/um2.png"> 			 		
				</div>
			</div>
			<div class="span4" align="center">
				<h3>
					<span style="color: #1BCAFF; font-family: picoblackregular">
						Twitter Suicide prevention </span><br /> <small>Prévention des
						suicides via Twitter</small>
				</h3>
			</div>
		</div>
	</div>

	<!-- Container:Body -->
	<div class="container">
		<div class="tabbable tabs-left">

			<ul class="nav nav-tabs ">
				<li class="active"><a href="#home" data-toggle="tab">Home</a></li>
				<li><a href="#suspecTweets" data-toggle="tab">Suspicious
						Tweets</a></li>
				<li><a href="#Termes" data-toggle="tab">Terms by Category</a></li>
				<li><a href="#stats" data-toggle="tab" onclick="showPie()">Statistics</a></li>
				<li><a href="#demo" data-toggle="tab">Demonstration</a></li>
			</ul>

			<div class="tab-content">
			
				<!-- Home page -->
				<div class="tab-pane active" align="center" id="home">
					<div class="nav-header">
						<h4 class="breadcrumb">Home</h4>
					</div>
					<div class="container-fluid">
						<div class="row-fluid">
							<div class="span6">
								<ul>
									<br />
									<h5>
										<p class="text-center">What's up?</p>
									</h5>
									<br />
									<li><p class="text-left">Tweeter Prevent is based on
											real Twitter Database.</p></li>
									<br />
									<li><p class="text-left">Uses Weka Machine Learning
											Techniques.</li>
									<br />
									<li><p class="text-left">
											Including <a href="#Termes" data-toggle="tab">Vocabulary
												and Signs of Suicide.</a></li>
									<br />
								</ul>
							</div>
							<div class="span6">
								<img src="img/sp.jpg" class="img-polaroid">
							</div>
						</div>
					</div>
				</div>

				<!-- Suspicious Tweets -->
				<div class="tab-pane" align="center" id="suspecTweets">
					<div class="nav-header">
						<h4 class="breadcrumb">Suspicious Tweets</h4>
					</div>

					<table cellpadding="0" cellspacing="0" border="0"
						class="table table-condensed" id="suspecttable">

						<thead>
							<tr id="suspect" class="suspect-columns">
							</tr>
						</thead>

						</tbody>
					</table>
				</div>
				<!-- Terms By Category -->
				<div class="tab-pane" id="Termes">
					<div class="nav-header">
						<h4 class="breadcrumb" align="center">Terms by Category</h4>
					</div>
					<form class="row-fluid" method="get" name="thmeaticform"
							id="thmeaticform">
					
						

							<div class="controls" align= "center">

								<select id="categoriename" name="categoriename">
									<option selected="selected" value="">-- Select One --</option>
									<option value="Mental">Mental State</option>
									<option value="Method">Method</option>
									<option value="Insults">Insults</option>
									<option value="anorexia">anorexia</option>
									<option value="cyberbullying">Cyberbullying</option>
								</select>
							</div>

							<div class="controls" align= "center">

								<select id="subcategory" name="subcategory">
									<option selected="selected" value="">-- Select One --</option>
								</select>

							</div>
						
				

							
							<div class="center-block controls"  align= "center">
								<button type="submit"  class="btn   btn-info">Consult</button>
							</div>
				
					</form>
					<fieldset class="span9">

						<table cellpadding="0" cellspacing="0" border="0"
							class="table table-condensed" id="mytable">
							<thead>
								<tr id="resulttable" class="results-columns">
								</tr>
							</thead>
							</tbody>
						</table>
					</fieldset>
				
				</div>
				<!-- Statistics -->
				<div class="tab-pane" align="center" id="stats">
					<div class="nav-header">
						<h4 class="breadcrumb">Statistics</h4>
					</div>
					<ul class="nav nav-tabs ">
						<li class="active"><a href="#container_pie" data-toggle="tab"
							onclick="showPie()">Pie Gradient</a>
						</li>
						<li><a href="#container_columns" data-toggle="tab"
							onclick="drawColumn()">Columns</a>
						</li>
					</ul>

					<div class="tab-content">


						<div class="tab-pane active" align="center" id="container_pie"
							style="min-width: 310px; height: 400px; margin: 0 auto"></div>

						<div class="tab-pane" align="center" id="container_columns"
							style="min-width: 310px; height: 400px; margin: 0 auto"></div>
					</div>
				</div>
				<!-- Demonstration -->
				<div class="tab-pane" align="center" id="demo">
						<div class="nav-header">
							<h4 class="breadcrumb">Demonstration</h4>
						</div>
						<ul class="nav nav-tabs ">
							<li class="active"><a href="#video_part1" data-toggle="tab"
								>Pretreatment</a>
							</li>
							<li><a href="#video_part2" data-toggle="tab"
								>J2EE Application</a>
							</li>
						</ul>
	
						<div class="tab-content">
	
	
							<div class="tab-pane active" align="center" id="video_part1"
								style="min-width: 310px; height: 400px; margin: 0 auto">
								<div  align="center">
									<iframe width="560" height="315" src="//www.youtube.com/embed/nhxn2ouO2Fo" frameborder="0" allowfullscreen></iframe>
								</div>	
							</div>
	
							<div class="tab-pane" align="center" id="video_part2"
								style="min-width: 310px; height: 400px; margin: 0 auto">
								<div  align="center">
									<iframe width="560" height="315" src="//www.youtube.com/embed/FNsVZglhOj4" frameborder="0" allowfullscreen></iframe>	
								</div>	
							</div>
						</div>
					
				
						
					
				</div>



				<!-- Modal -->
				<div id="wait-modal" class="modal2 hide fade" tabindex="-1"
					role="dialog" aria-labelledby="wait-modal-label" aria-hidden="true">
					<div class="modal2-header">
						<button type="button" class="close" data-dismiss="modal2"
							aria-hidden="true">×</button>
						<h5 id="wait-modal-label">Please wait</h5>
					</div>
					<div class="modal2-body row-fluid">
						<img class="span4 offset4"
							src="data:image/gif;base64,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">
					</div>
					<div class="modal-footer">
						<button class="btn" data-dismiss="modal2" aria-hidden="true">Dismiss</button>
					</div>
				</div>
			</div>

		</div>
	</div>
	<!-- /Container:Body -->

	
	<div class="modal hide fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true" href="#">&times;</button>
					<h4 class="modal-title" id="myModalLabel">Tweet details</h4>
				</div>
				<div class="modal-body">

					<fieldset class="span3" id="left">

						<div id="category"></div>

					</fieldset>

					<fieldset class="span2" id="right">
						<div id="image"></div>
					</fieldset>
					<fieldset class="span4" id="right">
						<div id="username"></div>
						<div id="name"></div>
						<div id="location"></div>
					</fieldset>

					<fieldset class="span8" id="history">
						<h4>User's history</h4>
						<table id="tablehistory" class="table">


						</table>
					</fieldset>


				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
	
	<!-- Footer -->
	<div class="extra">
	
		<div class="container">
			<div class="row">
			</div>
			<div class="row">
				
				<div id="footer-copyright" align="center">
				&copy; 2014 <a href="">TER 2013-2014</a> &nbsp;&nbsp;|&nbsp;&nbsp; All Rights Reserved &nbsp;&nbsp;
				</div>
		
			</div>
		</div>

	</div>
	<!-- /Footer -->
	<script type="text/javascript" src="./css/bootstrap/js/bootstrap.js"></script>
	<script type="text/javascript" src="./js/terms.js"></script>
	<script type="text/javascript" src="./js/suspects.js"></script>

</body>
</html>

