const express = require('express');
const router = express.Router();

/* GET home page. */
router.get('/', function(req, res) {
    res.render('index', { close:"none" });
});

router.get('/about', function(req, res) {
    res.render('about', { close: "block" });
});

router.get('/contact', function(req, res) {
    res.render('contact', { close:"block" });
});

router.get('/media', function(req, res) {
    res.render('media', { close: "block" });
});

router.get('/portfolio', function(req, res) {
    res.render('portfolio', { close: "block" });
});

module.exports = router;
