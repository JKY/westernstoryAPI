var fs = require('fs');

process.argv.forEach(function (val, index, array) {

    if(array.length != 3) {
        throw new Error("command must have three params.");
    }

    if(index == 2) {
        if(val == 'dev') {
            deploy(1);
        } else if(val == 'qa') {
            deploy(2);
        } else if(val == 'pro') {
            deploy(9);
        } else {
            throw new Error("invalid params.");
        }
    }
});


function deploy(mode) {
    var javaConfig = "",
        propertyConfig = "",
        coreCss = "",
        coreJs = "",
        ftlConfig = "",
        logback = "";

    if(mode == 1) {
        // development
        javaConfig = "./development/Config.java";
        propertyConfig = "./development/config.properties";
        logback = "./development/logback.xml";
    } else if(mode == 2) {
        // qatesting
        javaConfig = "./qatesting/Config.java";
        propertyConfig = "./qatesting/config.properties";
        coreCss = './qatesting/core.min.css';
        coreJs = './qatesting/core.min.js';
        ftlConfig = "./qatesting/config.ftl";
        logback = "./qatesting/logback.xml";
    } else {
        // production
        javaConfig = "./production/Config.java";
        propertyConfig = "./production/config.properties";
        coreCss = './production/core.min.css';
        coreJs = './production/core.min.js';
        ftlConfig = "./production/config.ftl";
        logback = "./production/logback.xml";
    }

    console.log('--------------------------------------------------');
    console.log('!!!! Runing with the debug mode: ' + mode + ' !!!!');
    console.log('');

    if(javaConfig) {
        var rs = fs.createReadStream(javaConfig);
        var ws = fs.createWriteStream('../../java/info/meihua/mhhome/config/Config.java');
        rs.pipe(ws);
        ws.on('close',function(){
            console.log('---- Config.java completed ----');
        });
    }

    if(propertyConfig) {
        var rs = fs.createReadStream(propertyConfig);
        var ws = fs.createWriteStream('../../resources/config/config.properties');
        rs.pipe(ws);
        ws.on('close',function(){
            console.log('---- config.properties completed ----');
        });
    }

    if(logback) {
        var rs = fs.createReadStream(logback);
        var ws = fs.createWriteStream('../../resources/config/logback.xml');
        rs.pipe(ws);
        ws.on('close',function(){
            console.log('---- logback.xml completed ----');
        });
    }

    if(coreCss) {
        var rs = fs.createReadStream(coreCss);
        var ws = fs.createWriteStream('../www/dist/core/core.min.css');
        rs.pipe(ws);
        ws.on('close',function(){
            console.log('---- core.min.css completed ----');
        });
    }

    if(coreJs) {
        var rs = fs.createReadStream(coreJs);
        var ws = fs.createWriteStream('../www/dist/core/core.min.js');
        rs.pipe(ws);
        ws.on('close',function(){
            console.log('---- core.min.js completed ----');
        });
    }

    if(ftlConfig) {
        var rs = fs.createReadStream(ftlConfig);
        var ws = fs.createWriteStream('../www/dist/ftl/cmp/config.ftl');
        rs.pipe(ws);
        ws.on('close',function(){
            console.log('---- config.ftl completed ----');
        });
    }
}