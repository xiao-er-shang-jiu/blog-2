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
  // chrome
  document.body.scrollTop = 0
  // firefox
  document.documentElement.scrollTop = 0
  // safari
  window.pageYOffset = 0
  next()
});

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
