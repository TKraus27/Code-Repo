const express = require('express');
const router = express.Router();
const bcrypt = require('bcrypt');
const multer = require('multer');
const streamifier = require('streamifier');
const fs = require('fs');
const {Storage} = require('@google-cloud/storage');
const storage = new Storage({
    projectId: 'bdc-artwork',
    keyFilename: 'encryption/service-account-key.json'
});
const BUCKET_NAME = 'portfolio-images321';
const myBucket = storage.bucket(BUCKET_NAME);
const mstorage = multer.memoryStorage();
const upload = multer({ storage: mstorage });

router.get('/', function (req, res) {
    if (req.session && req.session.user) {
        res.render('manage', { close: "block" });
    } else {
        res.redirect('/manage/login');
    }
});

router.get('/login', function(req, res) {
    res.render('login', { close: "block" });
});

router.post('/login', function(req, res) {
    let password = req.body.password;
    let dataFile = myBucket.file('hash.txt');
    let rstream = dataFile.createReadStream();
    let buf = '';
    rstream.on('data', function(d) {
        buf += d;
    }).on('end', function() {
        bcrypt.compare(password, buf, function(err, result) {
            if (err) { throw (err); }
            if (result) {
                req.session.user = result;
                res.redirect('/manage');
            } else {
                res.render('login', { close: "block", failed: true });
            }
        });
    });
});

router.get('/upload', function(req, res) {
    if (req.session && req.session.user) {
        res.render('upload', { close: "block" });
    } else {
        res.redirect('/manage/login');
    }
});

router.post('/upload', upload.single('file'), function (req, res) {
    let imgFile = myBucket.file('/' + req.body.category + '/' + req.file.originalname);
    let dataFile = myBucket.file('data.json');
    let rstream = dataFile.createReadStream();
    let wstream = dataFile.createWriteStream();
    let buf = '';
    rstream.on('data', function(d) {
        buf += d;
    }).on('end', function() {
        let imgExists = false;
        let json = JSON.parse(buf);
        let obj = { image: 'https://storage.googleapis.com/' + BUCKET_NAME + '/' + req.body.category + '/' + req.file.originalname, description: req.body.description };
        let objlist = json[req.body.category];
        for (let o of objlist) {
            if (o.image === obj.image) {
                imgExists = true;
                break;
            }
        }
        if (!imgExists) {
            objlist.push(obj);
            json[req.body.category] = objlist;
            let jsonString = JSON.stringify(json);
            streamifier.createReadStream(jsonString).pipe(wstream)
                .on('error', function(err) {})
                .on('finish', function() {
                    console.log('JSON updated!');
                });
            streamifier.createReadStream(req.file.buffer).pipe(imgFile.createWriteStream())
                .on('error', function(err) {})
                .on('finish', function() {
                    imgFile.makePublic();
                    console.log('image ' + req.file.originalname + ' uploaded');
                });
            res.render('upload', { close: "block", uploaded: true });
        }else {
            res.render('upload', { close: "block", exists: imgExists });
        }
    });
});

router.get('/delete', function(req, res) {
    if (req.session && req.session.user && req.query.category) {
        let cat = req.query.category;
        let Cat = cat.charAt(0).toUpperCase() + cat.slice(1);
        myBucket.getFiles({ prefix: cat },function(err, files) {
            if (!err) {
                let x = 0;
                let fileList= [];
                while (x < files.length) {
                    fileList[x] = files[x].name.replace(cat + '/', '');
                    x++;
                }
                res.render('delete', { close: "block", Category: Cat, category: cat, files: fileList });
            } else {
                return err;
            }
        });
    } else if (req.session && req.session.user) {
        res.render('_delete-category', { close: "block" });
    } else {
        res.redirect('/manage/login');
    }
});

router.post('/delete', upload.none(), function (req, res) {
    let cat = req.body.category;
    let fileName = req.body.fileName;
    let imgFile = myBucket.file('/' + cat + '/' + fileName);
    let dataFile = myBucket.file('data.json');
    let rstream = dataFile.createReadStream();
    let wstream = dataFile.createWriteStream();
    let buf = '';
    rstream.on('data', function(d) {
        buf += d;
    }).on('end', function() {
        let imgUrl = 'https://storage.googleapis.com/' + BUCKET_NAME + '/' + cat + '/' + fileName;
        let json = JSON.parse(buf);
        let objlist = json[cat];
        console.log(objlist);
        let x = 0;
        while (x < objlist.length) {
            if (objlist[x].image === imgUrl) {
                objlist.splice(x,1);
                break;
            }
            x++;
        }
        json[cat] = objlist;
        let jsonString = JSON.stringify(json);
        streamifier.createReadStream(jsonString).pipe(wstream)
            .on('error', function(err) {})
            .on('finish', function() {
                console.log('JSON updated!');
            });
        imgFile.delete().then(
            function (result) {
                let Cat = cat.charAt(0).toUpperCase() + cat.slice(1);
                myBucket.getFiles({ prefix: cat },function(err, files) {
                    if (!err) {
                        let x = 0;
                        let fileList= [];
                        while (x < files.length) {
                            fileList[x] = files[x].name.replace(cat + '/', '');
                            x++;
                        }
                        res.render('delete', { close: "block", Category: Cat, category: cat, files: fileList, deleted: fileName });
                    } else {
                        return err;
                    }
                });
            },
            function (reason) {
                return reason;
            });
    });
});

router.get('/change-password', function (req, res) {
    if (req.session && req.session.user) {
        res.render('change-password', { close: "block" });
    } else {
        res.redirect('/manage/login');
    }
});

router.post('/change-password', upload.none(), function (req, res) {
    let first = req.body.firstpass;
    let second = req.body.secondpass;
    let dataFile = myBucket.file('hash.txt');
    let wstream = dataFile.createWriteStream();
    if (first === second) {
        bcrypt.hash(first, 10, function(err, hash) {
            if (!err) {
                streamifier.createReadStream(hash).pipe(wstream)
                    .on('error', function(err) {})
                    .on('finish', function() {
                        console.log('Password updated!');
                    });
                res.render('change-password', { close: "block", updated: true })
            }
        });
    } else {
        res.render('change-password', { close: "block", nonmatch: true })
    }
});

module.exports = router;