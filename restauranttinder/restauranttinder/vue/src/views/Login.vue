<template>
<div class="login">
 <div class="left-half">
  <div id="login-content"> 
   <img class="logo" src="../assets/logo.png">
   <h1 id="site-title">Feed Me</h1>
  </div> 
 </div>  
 <div class="right-form">
 <form id="right-half" class="form-signin" @submit.prevent="login">
  <h1 id="greeting">Welcome</h1>
   <div
    class="alert alert-danger"
    role="alert"
    v-if="invalidCredentials"
    >Invalid username and password!</div>
    <div
    class="alert alert-success"
    role="alert"
    v-if="this.$route.query.registration"
    >Thank you for registering, please sign in.
    </div>
    <div id="login-info">
     <label for="username" class="sr-only">Username</label>
     <input
       type="text"
       id="username"
       class="form-control"
       placeholder="Username"
       v-model="user.username"
       required
       autofocus
      />
      <label for="password" class="sr-only">Password</label>
      <input
        type="password"
        id="password"
        class="form-control"
        placeholder="Password"
        v-model="user.password"
        required
      />
      
      <button id="btn" class="button" type="submit">
        Sign In
      </button>
     </div>        
      <router-link id="need-account-button" :to="{ name: 'register' }">Click here to create a new account</router-link>
    </form>  
 </div>
</div>
</template>

<script>
import authService from "../services/AuthService";

export default {
  name: "login",
  components: {},
  data() {
    return {
      user: {
        username: "",
        password: ""
      },
      invalidCredentials: false
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then(response => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push(`/user-display/${this.$store.state.user.id}`);
          }
        })
        .catch(error => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    }
  }
};
</script>
<style scoped>

@import url('https://fonts.googleapis.com/css2?family=Cookie&family=Montserrat&display=swap');

.login {
  min-height: 100vh;
  min-width:100vw;
  display: flex;  
  align-items: center;
  justify-content: center;   
  background-image: url(../assets/background2.jpg);
  background-repeat:no-repeat;
  background-size: cover;
  
}

.left-half {
  display: flex;
  justify-content: center;
  align-items: center;    
  background-color: rgb(255, 255, 255, 1);  
  height: 50vh;
  width: 40vw;
/*   flex-basis: 40%;   */
  border: 1px solid black;
  /* flex: 1; */
}

#right-half {  
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center; 
  color: white;  
  background-color: rgb(0, 0, 0, 0.9);  
  height: 50vh;
  width: 40vw;
 /*  flex-basis: 40%; */
  border: 1px solid black; 
  /* flex: 1;     */
}

#login-info {
  width: 50%;
  
}

#username, #password, #btn {
  width: 100%;  
}

#greeting, #site-title {
  padding: 0px;  
  font-family: 'Cookie', cursive;
  color: white;
	font-size: 6rem;
	text-shadow: -1px 1px 2px #000,
	1px 1px 2px #000,
	1px -1px 0 #000,
  -1px -1px 0 #000;
}

#need-account-button {
  padding: 0px;
  margin-top: 10px;
  font-size: 2rem;  
  color: white;
  font-family: 'Cookie', cursive;	
	text-shadow: -1px 1px 2px #000,
	1px 1px 2px #000,
	1px -1px 0 #000,
	-1px -1px 0 #000;
}

.btn {
  background: #222;
  height: 3rem;
  min-width: 10%;
  border: none;
  border-radius: 10px;
  color: #eee;
  font-size: 30px;
  font-family: 'Cookie', cursive;
  position: relative;
  transition: 2s;
  -webkit-tap-highlight-color: transparent;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  padding-top: 5px;
  margin-top: 10px;
}

#btn:hover {
  background: transparent;
}

.button {
  font-size: 100%;
  font-family: inherit;
  border: 0;
  padding: 0;  
  background: transparent;
  color: #eee;


}
@media (max-width:700px) {
  .login {
    display: flex;
    flex-direction: column;
    justify-items: center;
    align-items: center;
  }

  .right-form{
  grid-area: right-side;
  display: flex;
  flex-direction: column;
  justify-items: center;
  margin-top: -50%;
  }
  #right-half {
    width: 100vw;
  }
  .left-half {
    display: none;
  }
}

</style>
