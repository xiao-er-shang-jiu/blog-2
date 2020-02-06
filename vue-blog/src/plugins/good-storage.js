import Vue from 'vue'
import storage from 'good-storage'

const SEARCH_kEY = '__blog__'

function saveBlog(blog){
    storage.set(SEARCH_kEY, blog);
}

function loadBlog() {
    return storage.get(SEARCH_kEY, null);
}

function removeBlog() {
    storage.remove(SEARCH_kEY);
}

Vue.prototype.saveStorage = saveBlog;
Vue.prototype.loadStorage = loadBlog;
Vue.prototype.removeStorage = removeBlog;