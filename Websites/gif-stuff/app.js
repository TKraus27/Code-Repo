var express = require('express');
var app = express();
var exphbs  = require('express-handlebars');

app.engine('handlebars', exphbs({ defaultLayout: 'main' }));
app.set('view engine', 'handlebars');

app.get('/', function (req, res) {
  var gifUrl = 'https://media4.giphy.com/avatars/100soft/WahNEDdlGjRZ.gif'
  res.render('hello-gif', {gifUrl: gifUrl})
})


app.listen(3000, function () {
  console.log('Gif Search listening on port localhost:3000!');
});
