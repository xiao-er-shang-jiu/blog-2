import Vue from 'vue';
import axios from "axios";
import qs from "qs";

if (process.env.NODE_ENV == 'development') {

  axios.defaults.baseURL = '/api';

}else if (process.env.NODE_ENV == 'debug') {

  axios.defaults.baseURL = '';

}else if (process.env.NODE_ENV == 'production') {

  axios.defaults.baseURL = '';

}

axios.defaults.headers.get['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8';
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8';
axios.defaults.timeout = 10000;

axios.interceptors.request.use(
  function(config) {
    // Do something before request is sent
    return config;
  },
  function(error) {
    // Do something with request error
    return Promise.reject(error);
  }
);

// Add a response interceptor
axios.interceptors.response.use(
  function(response) {
    // Do something with response data
    if(response.status === 200){
      return Promise.resolve(response);
    }
    else{
      return Promise.reject(response);
    }
  },
  function(error) {
    // Do something with response error
    return Promise.reject(error);
  }
);


function get(url, params){
  return new Promise((resolve, reject) => {
    axios.get(url, {
      params: params
    }).then(res => {
        resolve(res.data)
    }).catch(err => {
        if(err.response.status === 401){
            window.location.href = '/login'
        }
        else {
            reject(err)
        }
    })
  });
}

function post(url, params){
  return new Promise((resolve, reject) => {
    axios.post(url, qs.stringify(params))
        .then(res => {
            resolve(res.data)
        })
        .catch(err => {
            if(err.response.status === 401){
                window.location.href = '/login'
            }
            else {
                reject(err)
            }
        })
  });
}

Vue.prototype.get = get;
Vue.prototype.post = post;
