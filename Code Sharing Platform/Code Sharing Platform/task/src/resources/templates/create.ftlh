<html>
<head>
<title>Create</title>
<script>
function send() {
    let object = {
        "code": document.getElementById("code_snippet").value,
        "time": document.getElementById("time_restriction").value,
        "views": document.getElementById("views_restriction").value
    };

    let json = JSON.stringify(object);

    let xhr = new XMLHttpRequest();
    xhr.open("POST", '/api/code/new', false)
    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    xhr.send(json);

    if (xhr.status == 200) {
      alert("Success!");
    }
}
</script>

<style>
body {
    margin: auto;
    width: 50%;
    padding: 10px;
}
</style>

<link rel="stylesheet"
       target="_blank" href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css">
<script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js"></script>
<script>hljs.initHighlightingOnLoad();</script>

</head>

<body>
<form>
<textarea id="code_snippet" placeholder="// Write your code here" rows=20 cols=100 required></textarea><br><br>
<input id="time_restriction" type="text" placeholder="time in seconds" default="0" required/><br>
<input id="views_restriction" type="text" placeholder="views" default="0" required/>
<button id="send_snippet" type="submit" onclick="send()">Submit</button>
</form>
</body>

</html>