<template>
  <div id="app">
    <!-- <div id="nav" v-if="$store.state.token != ''"> -->
      <!-- <router-link id="link" v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link> -->
      <!-- <h4>Welcome, {{$store.state.user.username}}</h4> -->
    <!-- </div> -->
    <site-header/>
    <router-view />
  </div>
</template>

<script>

import SiteHeader from './components/SiteHeader.vue' 

export default {
    name: 'app',
    components: {
      SiteHeader
    }  
  
}
</script>

<style scoped>
#app {
  text-align: center;
}
#nav {
  display: flex;
  flex-direction: row;
  background-color:rgba(31, 30, 30, 0.829);
  justify-content: space-between;
}
#logout-btn {
  color: white;
  font-weight: bold;
  padding: 5px;
}
#nav h4 {
  margin-right: .75rem;
  color: white;
  font-size: 1.5rem;
}
#link {
  color: white;
  font-size: 1.5rem;
  margin-left: .75rem;
}

</style>

