<template>
  <div id="app">
    <router-view></router-view>
    <div class="background">
      <audio autoplay loop>
        <source src="./assets/background/mp3/yinyue.mp3">
      </audio>

      <div id="background" class="wall"></div>
      <div id="midground" class="wall"></div>
      <div id="foreground" class="wall"></div>
      <div id="top" class="wall"></div>
    </div>
  </div>
</template>

<script>


export default {
  name: 'app'
}
</script>

<style>
  .background{
    position: fixed;
    top: 0;
    right: 0;
    width: 100%;
    height: 100%;
    z-index: -1;
  }
  audio{
    z-index: 5;
    position: absolute;
    bottom: 0;
    opacity: 0.1;
    -webkit-transition: all 2s;
    -moz-transition: all 2s;
    -ms-transition: all 2s;
    -o-transition: all 2s;
    transition: all 2s;
  }
  audio:hover{
    opacity: 1;
  }
  .wall{
    position: absolute;
    top: 0;
    left: 0;
    bottom: 0;
    right: 0;
  }
  div#background{
    background: url("./assets/background/img/preview.jpg")no-repeat;
    -webkit-animation: dd 100s linear infinite;
    -moz-animation: dd 100s linear infinite;
    -o-animation: dd 100s linear infinite;
    animation: dd 100s linear infinite;
    background-size: cover;
  }
  div#midground{
    background: url("./assets/background/img/midground.png");
    z-index: 1;
    -webkit-animation: cc 100s linear infinite;
    -moz-animation: cc 100s linear infinite;
    -o-animation: cc 100s linear infinite;
    animation: cc 100s linear infinite;
  }
  div#foreground{
    background: url("./assets/background/img/foreground.png");
    z-index: 2;
    -webkit-animation: cc 153s linear infinite;
    -o-animation: cc 153s linear infinite;
    -moz-animation: cc 153s linear infinite;
    animation: cc 153s linear infinite;
  }
  div#top{
    background: url("./assets/background/img/midground.png");
    z-index: 4;
    -webkit-animation: dd 100s linear infinite;
    -o-animation: dd 100s linear infinite;
    animation: da 100s linear infinite;
  }
  @-webkit-keyframes cc {
    from{
      background-position: 0 0;
      transform: translateY(10px);
    }
    to{
      background-position: 600% 0;
    }
  }
  @-o-keyframes cc {
    from{
      background-position: 0 0;
      transform: translateY(10px);
    }
    to{
      background-position: 600% 0;
    }
  }
  @-moz-keyframes cc {
    from{
      background-position: 0 0;
      transform: translateY(10px);
    }
    to{
      background-position: 600% 0;
    }
  }
  @keyframes cc {
    0%{
      background-position: 0 0;
    }
    100%{
      background-position: 600% 0;
    }
  }

  @keyframes da {
    0%{
      background-position: 0 0;
    }
    100%{
      background-position: 0 600%;
    }
  }
  @-webkit-keyframes da {
    0%{
      background-position: 0 0;
    }
    100%{
      background-position: 0 600%;
    }
  }
  @-moz-keyframes da {
    0%{
      background-position: 0 0;
    }
    100%{
      background-position: 0 600%;
    }
  }
  @-ms-keyframes da {
    0%{
      background-position: 0 0;
    }
    100%{
      background-position: 0 600%;
    }
  }

</style>
