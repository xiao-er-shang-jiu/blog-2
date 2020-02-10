import Vue from 'vue'
import App from './App.vue'
import router from './router'
import './plugins/mavonEditor'
import './plugins/axios'
import './plugins/good-storage'
import './plugins/element.js'
import './plugins/love'
import './plugins/vue-clap-button'

Vue.config.productionTip = false

router.beforeEach((to, from, next) => {
  document.querySelector('#app').scroll({top: 0, behavior: 'smooth'});
  next();
});

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
