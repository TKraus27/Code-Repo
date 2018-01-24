/** index.js
 * @file manages the modular functions of the site
 * @author Tony Kraus
 * @version 1-23-18
 */

//initializing all of the modules
const express = require('express');
const hbs = require('express-handlebars');
const path = require('path');
const app = express();

//setting the engine and file directories
app.set('views', path.join(__dirname, 'views'));
app.engine('handlebars', hbs({defaultLayout: 'main'}));
app.set('view engine', 'handlebars');
app.use(express.static(path.join(__dirname, 'public')));

//directing the user to the right destination
var num = 0;
app.get('/', function(req, res) {
  res.render('home');
  num++;
});

app.get('/count', function(req, res) {
  res.render('countview', { count: num++ });
});

//local server for testing
const port = 3000;
app.listen(port, function(err) {
  if (err) {
    return console.log('Error : ', err);
  }
  console.log('Server listening on port '+port);
});
