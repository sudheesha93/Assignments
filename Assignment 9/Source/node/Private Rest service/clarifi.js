/**
 * Created by prake on 9/14/2016.
 */
function getCredentials(cb) {
    var data = {
        'grant_type': 'client_credentials',
        'client_id': CLIENT_ID,
        'client_secret': CLIENT_SECRET
    };
    var url = 'https://api.clarifai.com/v1/token';

    return axios.post(url, data, {
        'transformRequest': [
            function() {
                return transformDataToParams(data);
            }
        ]
    }).then(function(r) {
        localStorage.setItem('accessToken', r.data.access_token);
        localStorage.setItem('tokenTimestamp', Math.floor(Date.now() / 1000));
        cb();
    }, function(err) {
        console.log(err);
    });
}

function transformDataToParams(data) {
    var str = [];
    for (var p in data) {
        if (data.hasOwnProperty(p) && data[p]) {
            if (typeof data[p] === 'string'){
                str.push(encodeURIComponent(p) + '=' + encodeURIComponent(data[p]));
            }
            if (typeof data[p] === 'object'){
                for (var i in data[p]) {
                    str.push(encodeURIComponent(p) + '=' + encodeURIComponent(data[p][i]));
                }
            }
        }
    }
    return str.join('&');
}

function postImage(imgurl) {
    var accessToken = localStorage.getItem('accessToken');
    var data = {
        'url': imgurl
    };
    var url = 'https://api.clarifai.com/v1/tag';
    return axios.post(url, data, {
        'headers': {
            'Authorization': 'Bearer ' + accessToken
        }
        /*'content-type': 'application/x-www-form-urlencoded'*/
    }).then(function(r) {
        parseResponse(r.data);
    }, function(err) {
        console.log('Sorry, something is wrong: ' + err);
    });
}

function parseResponse(resp) {
    var tags = [];
    if (resp.status_code === 'OK') {
        var results = resp.results;
        tags = results[0].result.tag.classes;
    } else {
        console.log('Sorry, something is wrong.');
    }
    var val = document.getElementById('imgurl').value,
        img = document.createElement('img');
    img.src = val;
    img.width=100;
    img.height=100;
    val.height=100;
    val.width=10;
    document.getElementById('data_for_img').innerHTML='Retrieved tags:';
    document.getElementById('data_bef_img').innerHTML='';
    document.getElementById('check').innerHTML = "<img src='"+val+"' height='200' width='350'/>";
    document.getElementById('tags').innerHTML = tags.toString().replace(/,/g, ', ');

    var hello= document.getElementById('tags').innerHTML = tags.toString().replace(/,/g, ', ');
    var hello1 = hello.toString().replace(/, /g, ' %20 ');
    var url=("https://api.uclassify.com/v1/uclassify/GenderAnalyzer_v5/Classify?readkey=ALx6B0U2HsG8&text="+hello1);
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            myFunction(this.responseText);
        }
    }
    xmlhttp.open("GET", url, true);
    xmlhttp.send();
    function myFunction(response) {
        var obj = JSON.parse(response);
        document.getElementById("demo").innerHTML = "FEMALE" +
            obj.female + " MALE " + obj.male;


    }
}

function run(imgurl) {
    if (Math.floor(Date.now() / 1000) - localStorage.getItem('tokenTimeStamp') > 86400
        || localStorage.getItem('accessToken') === null) {
        getCredentials(function() {
            postImage(imgurl);
        });
    } else {
        postImage(imgurl);
    }
}