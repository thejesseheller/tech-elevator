<template>
  <nav class="navbar">      
    <div class="navbar-header">
     <div class="logo-name">
      <img class="logo" src="../assets/logo.png">
       <span class="nav-name">Feed Me</span>
     </div>
    </div>
    <div class="navbar-right">
    <ul v-if="this.$store.state.token !== ''">            
     <router-link :to="{name: 'user-display'}" style=color:black >
      <span class="home">Home</span>| </router-link>
     <router-link :to="{name: 'favorites', params: {id: this.$store.state.user.id}}" style=color:black>
      <span class="favorite">My Favorites</span>| </router-link>
     <router-link :to="{name: 'update-profile'}" style=color:black >
      <span class="profile">Profile</span>| </router-link>
     <router-link :to="{name: 'logout'}" style=color:black >
      <span class="logout">Logout</span>
     </router-link>
    </ul>  
    </div>      
  </nav>
</template>

<script>

export default {
    name: "site-header"
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Cookie&family=Montserrat&display=swap');

.logo {
  height: 75px;
  width: auto;
  padding-bottom: 10px;
  filter: grayscale(100%);
  height: 75px;
  width: auto;  
  padding-bottom: 0;
}

.nav-name {
  font-family: 'Cookie', cursive;
  font-size: 35px;
}

.favorite, .logout, .home, .profile {
  font-family: 'Cookie', cursive;
  font-size: 30px;
}

.favorite, .home, .profile {
  padding-right: 7.5px;
}

.navbar-right {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 0px;
  padding-right: 1.5rem;
  flex-wrap: nowrap;
}
@media (max-width: 700px) {
  .navbar {
    display: flex;
    flex-direction: column;
  }
}

</style>