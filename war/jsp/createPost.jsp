<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Create Group Chat Page</title>
</head>
<body>
	<form action="/social/CreatePost" method="post">
		<input type = "hidden" name = "owner" value = "${it.owner}" >
		Content: <textarea rows = "6" cols = "50" name = "content"></textarea> <br>
		<input type = "hidden" name = "timelineid" value = "${it.timelineid}" >
		Post Privacy: <input type="text" name="privacy" /> <br>
  		<input type="submit" value="Create Post">
  	</form></body>
</html>