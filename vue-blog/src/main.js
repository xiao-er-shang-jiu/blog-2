import Vue from 'vue'
import App from './App.vue'
import router from './router'
import './plugins/iview.js'
import './plugins/mavonEditor'
import './plugins/axios'
import './plugins/good-storage'

Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
