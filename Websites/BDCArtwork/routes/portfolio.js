const express = require('express');
const router = express.Router();
const {Storage} = require('@google-cloud/storage');
const storage = new Storage({
    projectId: 'bdc-artwork',
    keyFilename: 'encryption/service-account-key.json'
});
const BUCKET_NAME = 'portfolio-images321';
const myBucket = storage.bucket(BUCKET_NAME);

router.get('/*', function(req, res) {
    let dataFile = myBucket.file('data.json');
    let rstream = dataFile.createReadStream();
    let buf = '';
    rstream.on('data', function(d) {
        buf += d;
    }).on('end', function() {
        let json = JSON.parse(buf);
        let category = req.url.replace('/', '');
        let objs = {};
        if (json) {
            objs = json[category];
        }
        res.render('slideshow', { imgs: objs, close: "block" });
    });
});

module.exports = router;