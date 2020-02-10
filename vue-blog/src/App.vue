<template>
  <div id="app">
    <router-view :scrollTop = "scrollTop"></router-view>
  </div>
</template>

<script>
  export default {
      name: 'app',
      data () {
          return {
              scrollTop: 0
          }
      },
      methods: {
          handleScroll (e) {
             this.scrollTop = e.target.scrollTop;
          }
     },
     mounted() {
         document.querySelector('#app').addEventListener('scroll', this.handleScroll,false)
     }
  }
</script>

<style>
  html,body{
    width: 100%;
    height: 100%;
    margin: 0;
  }
  #app{
    height: 100%;
    overflow: auto;
  }
</style>